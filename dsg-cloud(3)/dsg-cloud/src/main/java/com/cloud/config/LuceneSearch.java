package com.cloud.config;

import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryparser.classic.QueryParser;
import org.apache.lucene.search.BooleanClause;
import org.apache.lucene.search.BooleanQuery;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.search.WildcardQuery;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;
import com.cloud.bean.User;
import com.cloud.util.SysResult;

public class LuceneSearch {
	/**
	 * 索引查询 -- 分词器
	 * @param keywords 关键字
	 * @param indexdir 索引库路径
	 * @param type 索引字段
	 * @param num 选取数量
	 * @return
	 * @throws Exception
	 */
	public SysResult search(String keywords, String indexdir, String type, int num, int page, int counts) throws Exception {
		ArrayList<User> list = new ArrayList<User>();
		Analyzer analyzer = new IKAnalyzer();
		QueryParser queryParser = new QueryParser(type,analyzer);
		Query parse = queryParser.parse(keywords);
		Directory dir = FSDirectory.open(Paths.get(indexdir));
		IndexReader indexReader = DirectoryReader.open(dir);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		TopDocs topDocs = indexSearcher.search(parse, counts);
		long count = topDocs.totalHits;
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		if(scoreDocs != null) {
			page(page,num,list,indexSearcher,scoreDocs);
		}
		int code = Integer.parseInt(""+count);
		return new SysResult(code,"搜索成功",true,list);
	}
	/**
	 * 按id搜索一个
	 * @param id
	 * @param indexdir
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public User search(Integer id, String indexdir,int counts) throws Exception {
		String a = id+"";
		SysResult result = search(a,indexdir,"id",1,1,counts);
		ArrayList<User> list = (ArrayList<User>)result.getData();
		return list.get(0);
	}
	/**
	 * 通配符搜索
	 * @param indexdir
	 * @param type
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public SysResult search(String indexdir,String type,int num, int page, int counts) throws Exception {
		ArrayList<User> list = new ArrayList<User>();
		Term term = new Term(type,"*");
		WildcardQuery query = new WildcardQuery(term);
		Directory dir = FSDirectory.open(Paths.get(indexdir));
		IndexReader indexReader = DirectoryReader.open(dir);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		TopDocs topDocs = indexSearcher.search(query,counts);
		long count = topDocs.totalHits;
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		if(scoreDocs != null) {
			page(page,num,list,indexSearcher,scoreDocs);
		}
		int code = Integer.parseInt(""+count);
		return new SysResult(code,"搜索成功",true,list);
	}
	/**
	 * 组合查询
	 * @param keywords 多个查询关键字
	 * @param type 路径类型
	 * @param indexdir 索引库地址
	 * @param types 字段组合
	 * @param num
	 * @return
	 * @throws Exception
	 */
	public SysResult search(String keywords, String type, boolean flag, String indexdir, String[] types, int num, int page, int counts) throws Exception {
		ArrayList<User> list = new ArrayList<User>();
		Analyzer analyzer = new IKAnalyzer();
		BooleanQuery.Builder query = new BooleanQuery.Builder();
		QueryParser queryParser = new QueryParser(types[0],analyzer);
		Query a = queryParser.parse(type);
		query.add(a,BooleanClause.Occur.MUST);
		if(flag) {
			QueryParser b = new QueryParser(types[1],analyzer);
			Query c = b.parse(keywords);
			query.add(c,BooleanClause.Occur.MUST);
		}
		Directory dir = FSDirectory.open(Paths.get(indexdir));
		IndexReader indexReader = DirectoryReader.open(dir);
		IndexSearcher indexSearcher = new IndexSearcher(indexReader);
		TopDocs topDocs = indexSearcher.search(query.build(),counts);
		long count = topDocs.totalHits;
		ScoreDoc[] scoreDocs = topDocs.scoreDocs;
		if(scoreDocs != null) {
			page(page,num,list,indexSearcher,scoreDocs);
		}
		int code = Integer.parseInt(""+count);
		return new SysResult(code,"搜索成功",true,list);
	}
	
	private void page(int page,int size,ArrayList<User> list,IndexSearcher indexSearcher,ScoreDoc[] scoreDocs) throws Exception {
		int start = (page - 1) * size;
		int end = page * size;
		if(end > scoreDocs.length) {
			end = scoreDocs.length;
		}
		for(int i = start;i < end; i++) {
			Document doc = indexSearcher.doc(scoreDocs[i].doc);
			User user = new User();
			user.setId(Integer.parseInt(doc.get("id")));
			user.setUsername(doc.get("username"));
			user.setStatus(Integer.parseInt(doc.get("status")));
			user.setPhone(doc.get("phone"));
			list.add(user);
		}
	}
}
