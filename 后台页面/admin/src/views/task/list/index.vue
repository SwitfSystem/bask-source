<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item style="width: 320px">
        <el-input
          v-model="filter.value"
          style="width: 200px; margin-right: 20px"
          placeholder="暂时无法使用"
        />
        <el-button type="primary">搜索</el-button>
      </el-form-item>
    </el-form>
    <el-table
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >
      <el-table-column label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.row.id }}
        </template>
      </el-table-column>
      <el-table-column label="任务类型">
        <template slot-scope="scope">
          <span v-if="scope.row.task_code == 1">账号私信</span>
          <span v-if="scope.row.task_code == 2">直播私信</span>
          <span v-if="scope.row.task_code == 3">评论私信</span>
          <span v-if="scope.row.task_code == 4">直播间私信</span>
          <span v-if="scope.row.task_code == 5">直播间热度</span>
        </template>
      </el-table-column>
      <el-table-column label="任务开始时间">
        <template slot-scope="scope">
          <span>{{ scope.row.task_start_time }}</span>
        </template>
      </el-table-column>
      <el-table-column label="使用机器数量">
        <template slot-scope="scope">
          <span>{{ scope.row.task_user_count }}</span>
        </template>
      </el-table-column>
      <el-table-column label="已完成机器数">
        <template slot-scope="scope">
          <span>{{ scope.row.task_complete_count }}</span>
        </template>
      </el-table-column>
      <el-table-column label="在线机器">
        <template slot-scope="scope">
          <span>{{ scope.row.onLine }}</span>
        </template>
      </el-table-column>
      <el-table-column label="任务状态">
        <template slot-scope="{ $index, row }">
          <el-tag v-if="row.task_status===0" type="info">
            执行中</el-tag>
          <el-tag v-else-if="row.task_status===1" type="success"> 已完成</el-tag>
          <el-tag v-else type="danger"> 已停止</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.task_status == 0"
            type="text"
            @click="stop(scope.row)"
          >停止任务</el-button>
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
  </div>
</template>

<script>
import { getList } from '@/api/table'
export default {
  name: 'TaskList',
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
      list: [
      ],
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
        this.getTaskList()
      }
    },
    getTaskList(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("page",this.page)
      this.$axios.post("/task/log",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.list = data.data.list
          this.totalData = data.data.count
          this.loading = false
        }else if(data.code == -1){
          this.$router.push({ path: '/login' })
        }
      });
    },
    // 删除账号
    stop(row) {
      this.$confirm('此操作将停止该任务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          var params = new URLSearchParams();
          params.append("token",localStorage.getItem('admin_token'));
          params.append("id",row.id);
          this.$axios.post("/task/stop",params).then((res) => {
            var data = res.data
            if (data.code == 0){
              this.$message({
                type: 'success',
                message: '停止成功!'
              })
              this.$router.go(0)
            }else if(data.code == -1){
              this.$router.push({ path: '/login' })
            }
          });
        })
        .catch(() => {})
    },
    handleCurrentChange(val) {
      this.page = val
      this.loading = true
      this.getTaskList()

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
