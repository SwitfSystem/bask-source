package com.cloud.config;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.ArrayList;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.StoredField;
import org.apache.lucene.document.StringField;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.wltea.analyzer.lucene.IKAnalyzer;
import com.cloud.bean.User;

public class LuceneIndexManager {
	private static Logger log = LoggerFactory.getLogger(LuceneIndexManager.class);
	/**
	 * 创建索引库
	 * @param list 索引对象
	 * @param indexdir 索引库地址
	 * @return 成功true/失败false
	 */
	public boolean createIndex(ArrayList<User> list,String indexdir) {
		ArrayList<Document> docList = new ArrayList<Document>();
		for(User user:list) {
			Document doc = new Document();
			doc.add(new StringField("id", user.getId().toString(), Field.Store.YES));
			doc.add(new TextField("username", user.getUsername(), Field.Store.YES));
			doc.add(new StringField("phone", user.getPhone(), Field.Store.YES));
			doc.add(new StoredField("status", user.getStatus()));
			doc.add(new StoredField("vip", user.getViplevel()));
			doc.add(new StoredField("agent", user.getAgentlevel()));
			doc.add(new StoredField("pid", user.getPid()));
			doc.add(new StoredField("balance", user.getBalance()));
			docList.add(doc);
		}
		Analyzer analyzer = new IKAnalyzer();
		try {
			Directory dir = FSDirectory.open(Paths.get(indexdir));
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			IndexWriter index = new IndexWriter(dir, config);
			for(Document doc : docList) {
				index.addDocument(doc);
			}
			index.close();
			return true;
		} catch (IOException e) {
			log.error("=====  创建索引库出错！  =====");
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 更新索引
	 * @param file 修改文件
	 * @param indexdir 索引库路径
	 * @return 成功true/失败false
	 */
	public boolean updateIndex(User user,String indexdir) {
		Document doc = new Document();
		doc.add(new StringField("id", user.getId().toString(), Field.Store.YES));
		doc.add(new TextField("username", user.getUsername(), Field.Store.YES));
		doc.add(new StringField("phone", user.getPhone(), Field.Store.YES));
		doc.add(new StoredField("status", user.getStatus()));
		doc.add(new StoredField("vip", user.getViplevel()));
		doc.add(new StoredField("agent", user.getAgentlevel()));
		doc.add(new StoredField("pid", user.getPid()));
		doc.add(new StoredField("balance", user.getBalance()));
		Analyzer analyzer = new IKAnalyzer();
		try {
			Directory dir = FSDirectory.open(Paths.get(indexdir));
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			IndexWriter index = new IndexWriter(dir, config);
			index.updateDocument(new Term("id",user.getId().toString()), doc);
			index.close();
			return true;
		} catch (IOException e) {
			log.error("=====  更新索引出错！  =====");
			e.printStackTrace();
		}
		return false;
	}
	/**
	 * 删除索引
	 * @param file 删除的文件
	 * @param indexdir 索引库路径
	 * @return 成功true/失败false
	 */
	public boolean deleteIndex(User user, String indexdir) {
		Analyzer analyzer = new IKAnalyzer();
		try {
			Directory dir = FSDirectory.open(Paths.get(indexdir));
			IndexWriterConfig config = new IndexWriterConfig(analyzer);
			IndexWriter index = new IndexWriter(dir, config);
			index.deleteDocuments(new Term("id",user.getId().toString()));
			index.close();
			return true;
		} catch (IOException e) {
			log.error("=====  删除索引出错！   =====");
			e.printStackTrace();
		}
		return false;
	}
}
