<template>
  <div>

    <el-tabs style="margin-top: 40px"
      v-model="activeName" @tab-click="handleClick">
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
        <el-table-column label="激活码">
          <template slot-scope="scope">
            {{ scope.row.code }}
          </template>
        </el-table-column>
        <el-table-column label="用户状态">
          <template slot-scope="{ row }">
            <el-tag v-if="row.uu == null " type="danger"> {{"未激活" }}</el-tag>
            <el-tag v-else type="success"> {{"已激活" }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="使用用户">
          <template slot-scope="{row}">
            <el-button type="text" @click="showDialog = true">{{row.uu}}</el-button>
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
    </el-tabs>
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
    handleClick() {},
    edit(row) {
      this.form = Object.assign({}, row)
      this.showDialog = true
    },
    fetchData() {
      this.listLoading = true
      if (localStorage.getItem('token') == null){
        this.$router.push({ path: '/login' })
      }else {
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('token'));
        params.append("page",this.page);
        this.$axios.post("/agent/invitecode",params).then((res) => {
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
        // this.list = response.data.items
        // this.listLoading = false
      }
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
