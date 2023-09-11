<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item style="width: 320px">
        <el-input
          v-model="filter.value"
          style="width: 200px; margin-right: 20px"
          placeholder="输入手机型号搜索"
        />
        <el-button type="primary" @click="seach()">搜索</el-button>
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
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="用户名">
        <template slot-scope="scope">
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <!-- 挂机手机型号 -->
      <el-table-column label="账号">
        <template slot-scope="scope">
          <span>{{ scope.row.phone}}</span>
        </template>
      </el-table-column>
      <el-table-column label="IMEI">
        <template slot-scope="scope">
          <el-button type="text">
            {{ scope.row.mac }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="IP地址">
        <template slot-scope="scope">
          <el-button type="text">
            {{ scope.row.ip }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="型号">
        <template slot-scope="scope">
          <el-button type="text">
            {{ scope.row.os }}
          </el-button>
        </template>
      </el-table-column>
      <!-- 显示当前设备状态 -->
      <el-table-column label="状态">
        <template slot-scope="{ row }">
          <el-tag v-if="row.status == 0" type="success"> 在线</el-tag>
          <el-tag v-if="row.status == 1" type="info"> 未在线</el-tag>
          <!-- 异常状况，并于脚本端提示用户 -->
        </template>
      </el-table-column>
      <!-- 累计挂机时长 -->
      <el-table-column label="开始挂机时间">
        <template slot-scope="scope">
          {{ scope.row.counttime }}
        </template>
      </el-table-column>
      <el-table-column label="最后在线时间">
        <template slot-scope="scope">
          {{ scope.row.lasttime }}
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
  name: 'EquipmentInfoList',
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
        this.getEquipmentList()
      }
    },
    getEquipmentList(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("page",this.page);
      this.$axios.post("/driver/list",params).then((res) => {
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
    handleCurrentChange(val) {
      this.page = val
      this.loading = true
      this.getEquipmentList()
    },
    seach(){
      if (this.filter.value != ""){
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('admin_token'));
        params.append("name","phone");
        params.append("type",4);
        params.append("words",this.filter.value);
        params.append("status",0);
        this.$axios.post("/search",params).then((res) => {
          var data = res.data
          if (data.code == 0){
            this.list = data.data
          }else {
            this.$message.success(data.msg);
          }
        });
      }else {
        this.$router.go(0)
      }

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
