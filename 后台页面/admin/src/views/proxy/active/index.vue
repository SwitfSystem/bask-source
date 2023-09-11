<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item style="width: 320px">
        <el-input
          v-model="filter.value"
          style="width: 200px; margin-right: 20px"
          placeholder="输入激活码搜索"
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
          {{ scope.row.username }}
        </template>
      </el-table-column>
      <el-table-column label="手机号">
        <template slot-scope="scope">
          {{ scope.row.phone }}
        </template>
      </el-table-column>
      <el-table-column label="余额">
        <template slot-scope="scope">
          {{ scope.row.balance }}
        </template>
      </el-table-column>
      <el-table-column label="用户状态">
        <template slot-scope="{ row }">
          <el-tag type="success"> {{"已激活" }}</el-tag>
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
      title="查看代理信息"
      :visible.sync="showDialog"
      width="40%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="用户身份"> 客服 </el-form-item>
        <el-form-item label="代理身份"> 个人代理 </el-form-item>
        <el-form-item label="账号状态"> 正常 </el-form-item>
        <el-form-item label="当前余额"> 100 元 </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="showDialog = false">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
  import { getList } from '@/api/table'

  export default {
    name: 'ProxyCodeList',
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
        activeName: 'first',
        showDialog: false,
        list: [],
        list1:[],
        listLoading: true,
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
      this.fetchData()
    },
    methods: {
      edit(row) {
        this.form = Object.assign({}, row)
        this.showDialog = true
      },
      fetchData() {
        this.listLoading = true
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('admin_token'));
        params.append("page",this.page);
        this.$axios.post("/agent/active",params).then((res) => {
          var data = res.data
          if (data.code == 0){
            this.list = data.data.list
            this.totalData = data.data.count
            this.loading = false
            this.listLoading = false
          }else if (data.code == -1){
            this.$router.push({path: '/login'})
          }

        });
      },
      handleCurrentChange(val) {
        this.page = val
        this.loading = true
        this.fetchData()

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
