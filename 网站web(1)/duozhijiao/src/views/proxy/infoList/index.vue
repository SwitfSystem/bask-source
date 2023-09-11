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
      <el-table-column label="代理类型">
        <template slot-scope="scope">
          <el-button type="text" @click="goCode(scope.row)">
            {{ scope.row.name }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="开通时间">
        <template slot-scope="scope">
          {{ scope.row.createtime }}
        </template>
      </el-table-column>
      <el-table-column label="累计收益">
        <template slot-scope="scope"> {{ scope.row.balance || 0}} 元 </template>
      </el-table-column>
      <el-table-column label="下级用户">
        <template slot-scope="{ $index }">
          <el-button type="text" @click="goToPage('UserInfoList',$index)">
            下级用户
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="编辑"
      :visible.sync="showDialog"
      width="40%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="list10" label-width="120px">
        <el-form-item label="名称">
          {{ list10.name }}
        </el-form-item>
        <el-form-item label="挂机收益">
          一级 {{ list10.fake_back_1 }} % 二级  {{ list10.fake_back_2 }} %
        </el-form-item>
        <el-form-item label="开通Vip比例">
          一级 {{ list10.vip_back_1 }} % 二级  {{ list10.vip_back_2 }}
        </el-form-item>
        <el-form-item label="开通代理比例">
          一级 {{ list10.agent_back_1 }} % 二级  {{ list10.agent_back_2 }}
        </el-form-item>
        <el-form-item label="激活码数量">
          {{ list10.active_code }}
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="showDialog = false">确定</el-button>
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
      list10:{}
    }
  },
  created() {
    this.init();
    // this.fetchData();
    this.listLoading = false
  },
  methods: {
    init() {
      if (localStorage.getItem('token') == null){
        this.$router.push({ path: '/login' })
      }else {
        this.getAgentList()
      }
    },
    getAgentList(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('token'));
      this.$axios.post("/agent/list",params).then((res) => {
        var data = res.data

        if (data.code == 0){
          this.list = data.data.list
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    goToPage(name, index) {
      this.$router.push({
        name,
        query: {
          proxyId: index
        }
      })
    },
    goCode(row) {
      this.showDialog = true
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('token'));
      params.append("id",row.id);
      this.$axios.post("/group/showgroup",params).then((res) => {
        var data = res.data
console.log(data)
        if (data.code == 0){
          this.list10 = data.data
        }else if (data.code == -1){
          this.$router.push({path: '/login'})
        }

      });
    }
  }
}
</script>
