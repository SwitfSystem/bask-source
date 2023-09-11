<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item style="width: 320px">
        <el-button
          @click="handlerDialog(1)"
          type="primary">设立代理关系</el-button>
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
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="代理类型">
        <template slot-scope="scope">
          <el-button type="text" @click="goCode(scope.row)">
            {{scope.row.name}}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="开通时间">
        <template slot-scope="scope">
          {{ scope.row.createtime }}
        </template>
      </el-table-column>
      <el-table-column label="累计收益">
        <template slot-scope="scope"> {{ scope.row.balance || 0 }} 元 </template>
      </el-table-column>
      <el-table-column label="下级用户">
        <template slot-scope="scope">
          <el-button type="text" @click="goShow(scope.row)">
            下级用户
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
    <el-dialog
      title="详情"
      :visible.sync="showDialog1"
      width="40%"
      @close="showDialog1 = false"
    >
        <el-table ref="table" :data="list9" border fit size="small">
          <el-table-column label="ID" width="95">
            <template slot-scope="scope">
              {{ scope.$index + 1 }}
            </template>
          </el-table-column>
          <el-table-column label="用户名">
            <template slot-scope="{ row }">
              {{ row.username }}
            </template>
          </el-table-column>
          <el-table-column label="手机号" prop="phone" />
          <el-table-column label="余额" prop="balance" />
          <el-table-column label="创建时间" prop="createtime" />
        </el-table>
      <span slot="footer">
        <el-button @click="showDialog1 = false">取消</el-button>
        <el-button type="primary" @click="showDialog1 = false">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="激活码列表"
      :visible.sync="codeDialog"
      width="40%"
      @close="codeDialog = false"
    >
      <el-table ref="table" :data="list10" border fit size="small">
        <el-table-column label="激活码">
          <template slot-scope="{ row }">
            {{ row.code }}
          </template>
        </el-table-column>
        <el-table-column label="激活码状态">
          <template slot-scope="{ row }">
            <el-tag v-if="row.uu != null" type="success"> {{"已激活" }}</el-tag>
            <el-tag v-else type="danger"> {{"未激活" }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column label="使用用户">
          <template slot-scope="{ row }">
            {{ row.uu }}
          </template>
        </el-table-column>
      </el-table>
      <span slot="footer">
        <el-button @click="codeDialog = false">取消</el-button>
        <el-button type="primary" @click="codeDialog = false">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="详情"
      :visible.sync="showDialog"
      width="40%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="选择用户">
          <el-select
            v-model="form.material"
            clearable
            filterable
            style="width: 260px"
          >
            <el-option
              v-for="item in user"
              :key="item.id"
              :label="item.phone"
              :value="item.id"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="选择代理组">
          <el-select
            v-model="form.material2"
            clearable
            filterable
            style="width: 260px"
          >
            <el-option
              v-for="item in list1"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
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
// import { getList } from "@/api/table";
// import listJson from "@/data/listJson.js";
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
      list: [],
      user:[],
      list1:[],
      list9:[],
      list10:[],
      listLoading: true,
      showDialog: false,
      showDialog1: false,
      codeDialog: false,
      dialogTitle: '',
      dialogType: 1,
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
    // this.fetchData();
    this.listLoading = false
  },
  methods: {
    init() {
      if (localStorage.getItem('admin_token') == null){
        this.$router.push({ path: '/login' })
      }else {
        this.getAgentList()
        this.getAgentList1()
      }
    },
    getAgentList(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("page",this.page);
      this.$axios.post("/agent/list",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.totalData = data.data.count
          this.loading = false
          this.list = data.data.list
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    getAgentList1(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      this.$axios.post("/agent/listo",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.user = data.data.user
          this.list1 = data.data.group
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    goShow(row) {
      this.showDialog1 = true
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("id",row.id);
      this.$axios.post("/agent/nextlist",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.list9 = data.data
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    goCode(row) {
      this.codeDialog = true
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("id",row.id);
      this.$axios.post("/agent/code",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.list10 = data.data.list
        }else if (data.code == -1){
          this.$router.push({path: '/login'})
        }

      });
    },
    handlerDialog(index) {
      switch (index) {
        case 1:
          this.dialogTitle = '设立代理关系'
          this.dialogType = 1
          this.showDialog = true
          break
        default:
          break
      }
    },
    handleCurrentChange(val) {
      this.page = val
      this.loading = true
      this.getAgentList()
    },
    okBtn(){
      this.showDialog = false
      if (this.form.material != null ||this.form.material2 != null){
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('admin_token'));
        params.append("user",this.form.material);
        params.append("group",this.form.material2);
        this.$axios.post("/agent/exchange",params).then((res) => {
          var data = res.data
          this.$message.error(data.msg);
          this.$router.go(0)
        });
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
