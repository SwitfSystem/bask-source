<template>
  <div>
    <el-table
      style="margin-top: 40px"
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="用户名">
        <template slot-scope="scope">
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <el-table-column label="手机号">
        <template slot-scope="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="用户类型">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.group_id >0"
            type="text"
            @click="showGroup(scope.row.group_id)"
          >用户会员组</el-button>
        </template>
      </el-table-column>
      <el-table-column label="注册时间">
        <template slot-scope="scope">
          {{ scope.row.createtime }}
        </template>
      </el-table-column>
      <el-table-column label="用户余额">
        <template slot-scope="scope">
          {{ scope.row.balance || "*" }} 元
        </template>
      </el-table-column>
      <el-table-column label="用户状态">
        <template slot-scope="{ $index, row }">
          <el-tag v-if="row.status ==0" type="success">
            {{ "正常" }}</el-tag>
          <template v-else>
            <el-tag type="danger"> {{ "冻结" }}</el-tag>
            <el-button
              type="text"
              style="margin-left: 10px"
              @click="look(row)"
            >查看原因</el-button>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="设备激活">
        <template slot-scope="{ $index, row }">
          <el-tag v-if="row.activated ==1" type="success">
            {{ "已激活" }}</el-tag>
            <el-tag v-else type="danger"> {{ "未激活" }}</el-tag>
        </template>
      </el-table-column>
    </el-table>
    <div class="article_pagination">
      <el-pagination
        :current-page.sync="page"
        :disabled="loading"
        :page-size="pageSize"
        @current-change="handleCurrentChange"
        layout="prev,pager,next"
        :total="totalData"
        background/>
    </div>
    <el-dialog
      title="用户组信息"
      :visible.sync="showDialog2"
      width="20%"
      @close="showDialog2 = false"
    >
      <el-form ref="form" :model="list9" label-width="120px">
        <el-form-item v-if="list9.is_use ==1" label="默认用户组">
        </el-form-item>
        <el-form-item label="名称">
          {{list9.name}}
        </el-form-item>
        <el-form-item label="挂机收益">
          {{list9.price}}

        </el-form-item>
        <el-form-item label="挂机时长">
          {{list9.time}}  小时
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="primary" @click="showDialog2 = false">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getList } from '@/api/table'
export default {
  name: 'ProxyInfoList',
  filters: {
    statusFilter(status) {
      const statusMap = {
        published: 'success',
        draft: 'gray',
        deleted: 'danger'
      }
      return statusMap[status]
    }
  },
  data() {
    return {
      page:1,
      pageSize:20,
      totalData:null,
      loading:true,
      showDialog: false,
      showDialog2: false,
      list: [],
      listLoading: false,
      filter: {
        value: ''
      },
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
        role: ''
      },
      list9:{
        is_use:0,
      },
    }
  },
  created() {
    this.init();
    // this.fetchData();
    // this.listLoading =
  },
  methods: {
    init() {
      if (localStorage.getItem('token') == null){
        this.$router.push({ path: '/login' })
      }else {
        this.getUserInfo()
      }
    },
    //获取信息
    getUserInfo(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('token'));
      params.append("page",this.page);
      this.$axios.post("/user/list",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.list = data.data.list
          this.totalData = data.data.count
          this.loading = false
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    look(row) {
      this.$alert(`用户${row.username}违反用户规则 原因 ：${row.reason}`, '查看原因', {
        confirmButtonText: '确定',
        type: 'warning'
      })
    },
    showGroup(id) {
      this.showDialog2 = true

      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('token'));
      params.append("id",id);
      this.$axios.post("/group/showgroup",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.list9 = data.data
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    // 删除账号
    deleteAmount() {
      this.$confirm('此操作将删除该账号, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
        .catch(() => {})
    },
    freezeAmount() {
      this.$confirm('此操作将解冻该账号, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$message({
            type: 'success',
            message: '解冻成功!'
          })
        })
        .catch(() => {})
    },
    edit(row) {
      this.form = Object.assign({}, row)
      console.log(this.form)
      this.showDialog = true
    },
    fetchData() {
      this.listLoading = true
      getList().then((response) => {
        this.list = response.data.items
        this.listLoading = false
      })
    },
    handleCurrentChange(val) {
      this.page = val
      this.loading = true
      this.getUserInfo()
    }
  }
}
</script>
<style lang="scss">
  .article_pagination{
    margin-top: 40px;
    margin-bottom: 20px;
    text-align: right;
  }
</style>
