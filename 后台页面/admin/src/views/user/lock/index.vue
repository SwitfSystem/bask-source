<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item style="width: 320px">
        <el-input
          v-model="filter.value"
          style="width: 200px; margin-right: 20px"
          placeholder="输入手机号搜索"
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
          {{ scope.row.id }}
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
      <el-table-column label="注册时间">
        <template slot-scope="scope">
          {{ scope.row.createtime }}
        </template>
      </el-table-column>
      <el-table-column label="PID">
        <template slot-scope="scope">
          {{ scope.row.pid }}
        </template>
      </el-table-column>
      <el-table-column label="用户余额">
        <template slot-scope="scope"> {{ scope.row.balance || 0 }} 元 </template>
      </el-table-column>
      <el-table-column label="用户状态">
        <template slot-scope="{ $index, row }">
          <template>
            <el-tag type="danger"> {{ "锁定" }}</el-tag>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button type="text" @click="okBtn(scope.row.id)"
          >解锁</el-button>
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
  import { getList } from "@/api/table";
  export default {
    name: "ProxyInfoList",
    filters: {
      statusFilter(status) {
        const statusMap = {
          published: "success",
          draft: "gray",
          deleted: "danger",
        };
        return statusMap[status];
      },
    },
    data() {
      return {
        page:1,
        pageSize:20,
        totalData:null,
        loading:true,
        id:0,
        showDialog: false,
        userStatus:{
          status:false,
          status2:false,
          textarea:"",
        },
        userMoney:{
          balance:0,
        },
        list: [],
        listLoading: false,
        filter: {
          value: "",
        },
        form: {
          role: "",
        },
        group:[],
        agent:[],
        list9:{
          is_use:0,
        },
      };
    },
    created() {
      this.init();
      // this.fetchData();
      // this.listLoading =
    },
    methods: {
      init() {
        if (localStorage.getItem('admin_token') == null){
          this.$router.push({ path: '/login' })
        }else {
          this.getUserInfo()
        }
      },
      getUserInfo(){
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('admin_token'));
        params.append("page",this.page);
        this.$axios.post("/user/lock",params).then((res) => {
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
      okBtn(id){
        this.$confirm('是否需要解锁该账户?', '提示', {
          confirmButtonText: '解锁',
          cancelButtonText: '取消',
          type: 'warning'
        }) .then(() => {
          this.passwdDialog =false
          var params = new URLSearchParams();
          params.append("token",localStorage.getItem('admin_token'));
          params.append("id",id);
          this.$axios.post("/user/unlock",params).then((res) => {
            var data = res.data
            if (data.code == 0){
              this.$message.success(data.msg);
              this.$router.go(0)
            }else {
              this.$router.push({ path: '/login' })
            }
          });
        })
          .catch(() => {
          })

      },
      handleCurrentChange(val) {
        this.page = val
        this.loading = true
        this.getUserInfo()
      },
      seach(){
        if (this.filter.value != ""){
          var params = new URLSearchParams();
          params.append("token",localStorage.getItem('admin_token'));
          params.append("name","phone");
          params.append("type",1);
          params.append("words",this.filter.value);
          params.append("status",0);
          this.$axios.post("/search",params).then((res) => {
            var data = res.data
            if (data.code == 0){
              this.list = []
              this.list.push(data.data)
            }else {
              this.$message.success(data.msg);
            }
          });
        }else {
          this.$router.go(0)
        }

      }
    },
  };
</script>
<style lang="scss">
  .article_pagination{
    margin-top: 40px;
    margin-bottom: 20px;
    text-align: right;
  }
</style>
