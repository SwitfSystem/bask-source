<template>
  <div>
    <el-button
      style="margin-top: 50px"
      size="small"
      type="primary"
      icon="el-icon-plus"
      @click="handleAdd"
    >新增公告</el-button>
    <el-table :data="list" border fit size="small" style="margin-top: 20px">
      <el-table-column label="ID" width="80">
        <template slot-scope="{ $index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="标题" width="400">
        <template slot-scope="{ row }">
          {{ row.title }}
        </template>
      </el-table-column>
      <el-table-column label="内容" width="400">
        <template slot-scope="{ row }">
          {{ row.content }}
        </template>
      </el-table-column>
      <el-table-column label="创建日期">
        <template slot-scope="scope">
          <span>{{ scope.row.date }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="danger"
            size="mini"
            @click="handleDelete(scope.row)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="新增公告"
      :visible.sync="showDialog"
      width="40%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="160px">
        <!-- <el-form-item label="IP地址">
          <el-input v-model="form.ip" type="textarea" style="width:240px" />
        </el-form-item> -->
        <el-form-item label="标题">
          <el-input v-model="form.title" style="width:240px" />
        </el-form-item>
        <el-form-item label="内容">
          <el-input v-model="form.neirong" style="width:240px" />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="okBtn()">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import ArticleDetail from './components/ArticleDetail'
export default {
  name: 'EquipmentNotice',
  data() {
    return {
      typeData: null,
      showDialog: false,
      form: {
      },
      list: [
      ],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        category_id: null,
        page_size: 10
      }
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      if (localStorage.getItem('admin_token') == null){
        this.$router.push({ path: '/login' })
      }else {
        this.getNoteList()
      }
    },
    getNoteList(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      this.$axios.post("/driver/notelist",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.list = data.data
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    // 新增文章
    handleAdd() {
      this.showDialog = true;
    },
    handleDelete(row) {
      this.$confirm('确定要删除这个公告吗?', '系统提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('admin_token'));
        params.append("id",row.id);
        this.$axios.post("/driver/notedel",params).then((res) => {
          var data = res.data
          if (data.code == 0){
            this.$message({
              type: 'success',
              message: '删除成功!'
            })
            this.$router.go(0);
          }else {
            this.$router.push({ path: '/login' })
          }
        });

        // 刷新列表
        // this.getList()
      })
    },
    okBtn(){
      this.showDialog = false;
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("content",this.form.neirong);
      params.append("title",this.form.title);
      this.$axios.post("/driver/note",params).then((res) => {
        var data = res.data
        console.log(data)
        if (data.code == 0){
          this.$router.go(0);
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    }
  }
}
</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}
.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}
</style>
