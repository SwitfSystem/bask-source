<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item style="width: 320px">
        <el-input
          v-model="filter.value"
          style="width: 200px; margin-right: 20px"
          placeholder="无法搜索"
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
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="用户名">
        <template slot-scope="scope">
          {{ scope.row.phone }}
        </template>
      </el-table-column>
      <!-- 挂机手机型号 -->
      <el-table-column label="开始时间">
        <template slot-scope="scope">
          <span>{{ scope.row.start}}</span>
        </template>
      </el-table-column>
      <el-table-column label="结束时间">
        <template slot-scope="scope">
          <el-button type="text">
            {{ scope.row.end }}
          </el-button>
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
        if (localStorage.getItem('token') == null){
          this.$router.push({ path: '/login' })
        }else {
          this.getEquipmentList()
        }
      },
      getEquipmentList(){
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('token'));
        params.append("page",this.page);
        this.$axios.post("/driver/timelog",params).then((res) => {
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
