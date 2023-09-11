<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item style="width: 320px">
        <el-input
          v-model="filter.value"
          style="width: 200px; margin-right: 20px"
          placeholder="输入手机号/ID搜索"
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
      <el-table-column label="用户类型">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.group_id >0"
            type="text"
            @click="showGroup(scope.row.group_id,2)"
          >用户会员组</el-button>
        </template>
      </el-table-column>
      <el-table-column label="代理类型">
        <template slot-scope="scope">
          <el-button
            v-if="scope.row.agent_id >0"
            type="text"
            @click="showGroup(scope.row.agent_id,1)"
          >代理详情</el-button>
        </template>
      </el-table-column>
      <el-table-column label="邀请码">
        <template slot-scope="scope">
          <el-button
            type="text"
            @click="showPid(scope.row.id)"
          >{{ scope.row.invitationcode }}</el-button>

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
          <el-tag v-if="row.status ==0"  @click="setAccStatus(false,row.id,row.reason)" type="success">
            {{ "正常" }}</el-tag>
          <template v-else>
            <el-tag type="danger" @click="setAccStatus(true,row.id,row.reason)"> {{ "冻结" }}</el-tag>
          </template>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="scope">
          <el-button type="text" @click="edit(scope.row.id)"
            >设置Vip</el-button>
          <el-button
            type="text"
            @click="setPasswd(scope.row.
            id)"
          >修改密码</el-button>
          <el-button
            type="text"
            @click="setBalance(scope.row.id,scope.row.balance)"
          >修改余额</el-button>
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
      title="变更用户会员"
      :visible.sync="showDialog"
      width="30%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="设定用户身份">

          <el-select v-model="form.role" clearable filterable>
            <el-option
              v-for="item in group"
              :key="item.id"
              :label="item.name"
              :value="item.id"
            />
          </el-select>
        </el-form-item>

      </el-form>
      <span slot="footer">
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="okBtn4()">确定</el-button>
      </span>
    </el-dialog>
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
    <el-dialog
      title="代理信息"
      :visible.sync="showDialog1"
      width="20%"
      @close="showDialog1 = false"
    >
      <el-form ref="form" :model="list9" label-width="120px">
        <el-form-item label="名称">
          {{list9.name}}
        </el-form-item>
        <el-form-item label="挂机返佣">
          一级 {{list9.fake_back_1}} % |
          二级 {{list9.fake_back_2}} %
        </el-form-item>
        <el-form-item label="Vip返佣">
          一级 {{list9.vip_back_1}} % |
          二级 {{list9.vip_back_2}} %
        </el-form-item>
        <el-form-item label="代理返佣">
          一级 {{list9.agent_back_1}} % |
          二级 {{list9.agent_back_2}} %
        </el-form-item>
        <el-form-item label="激活码">
           {{list9.active_code}} 个
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button type="primary" @click="showDialog1 = false">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="解冻用户"
      :visible.sync="userDialog"
      width="40%"
      @close="userDialog = false"
    >
      <el-form ref="form" :model="userStatus" label-width="120px">
        <el-form-item label="冻结该账号">
          <el-checkbox v-model="userStatus.status" label="2">冻结该账号</el-checkbox>
        </el-form-item>

        <el-form-item label="">
          <el-checkbox v-model="userStatus.status2" label="2">冻结下级所有子用户</el-checkbox>
        </el-form-item>
        <el-form-item label="原因">
          <el-input
            v-model="userStatus.textarea"
            type="textarea"
            :rows="2"
            placeholder="请输入冰洁理由"
            style="width: 300px"
          />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="userDialog = false">取消</el-button>
        <el-button type="primary" @click="okBtn1()">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="用户余额修改"
      :visible.sync="balanceDialog"
      width="40%"
      @close="balanceDialog = false"
    >
      <el-form ref="form" :model="userMoney" label-width="120px">
        <el-form-item label="修改余额">
          <el-input v-model="userMoney.balance" style="width: 320px" clearable />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="balanceDialog  = false">取消</el-button>
        <el-button type="primary" @click="okBtn2()">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="修改用户密码"
      :visible.sync="passwdDialog"
      width="40%"
      @close="passwdDialog = false"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="修改密码">
          <el-input
            v-model="form.newPassword"
            :show-password="true"
            clearable
            type="password"
            style="width: 320px"
          />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="passwdDialog = false">取消</el-button>
        <el-button type="primary" @click="okBtn3()">确定</el-button>
      </span>
    </el-dialog>
    <el-dialog
      title="修改用户上级"
      :visible.sync="pidDialog"
      width="40%"
      @close="pidDialog = false"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="填写新验证码">
          <el-input
            v-model="form.yan"
            :show-password="true"
            clearable
            type="password"
            style="width: 320px"
          />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="pidDialog = false">取消</el-button>
        <el-button type="primary" @click="okBtn5()">确定</el-button>
      </span>
    </el-dialog>
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
      passwdDialog: false,
      pidDialog:false,
      balanceDialog: false,
      showDialog1:false,
      showDialog2: false,
      userDialog :false,
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
        this.getGroupList()
      }
    },
    getGroupList(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      this.$axios.post("/group/list",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          let list = data.data.list;
          for (var i = 0; i < list.length; i++) {
            if (list[i].type == 0){
              this.group.push(list[i]);
            }else if(list[i].type == 1){
              this.agent.push(list[i]);
            }
          }
        }
      });
    },
    showGroup(id,key) {
      if (key == 1){
        this.showDialog1 = true
      }else if (key ==2){
        this.showDialog2 = true
      }
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
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
    getUserInfo(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
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
    edit(id) {
      this.showDialog = true;
      this.id = id;
    },
    setAccStatus(status,id,reason){
      this.userDialog = true
      this.userStatus.status = status;
      this.userStatus.textarea = reason;
      this.id = id;
    },
    setBalance(id,balance){
      this.balanceDialog = true
      this.userMoney.balance = balance
      this.id = id;
    },
    setPasswd(id){
      this.passwdDialog = true
      this.id = id
    },
    showPid(id){
      this.pidDialog = true
      this.id = id
    },
    okBtn1(){
      this.userDialog = false
      //冻结账号
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("status",this.userStatus.status);
      params.append("status2",this.userStatus.status2);
      params.append("reason",this.userStatus.textarea);
      params.append("id",this.id);
      this.$axios.post("/user/status",params).then((res) => {
        var data = res.data
        console.log(data)
        if (data.code == 0){
          this.$router.go(0)
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    okBtn2(){
      this.balanceDialog = false
      //余额修改
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("balance",this.userMoney.balance);
      params.append("id",this.id);
      this.$axios.post("/user/balance",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.$router.go(0)
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    okBtn3(){
      this.passwdDialog =false
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("passwd",this.form.newPassword);
      params.append("id",this.id);
      this.$axios.post("/user/passwd",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.$message.success(data.msg);
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    okBtn4(){
      this.$confirm('是否需要再次计算该用户的上一级和上上一级的会员收益?', '提示', {
        confirmButtonText: '计算收益',
        cancelButtonText: '不计算收益',
        type: 'warning'
      }) .then(() => {
        this.passwdDialog =false
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('admin_token'));
        params.append("group",this.form.role);
        params.append("type",1);
        params.append("id",this.id);
        this.$axios.post("/user/group",params).then((res) => {
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
          this.passwdDialog =false
          var params = new URLSearchParams();
          params.append("token",localStorage.getItem('admin_token'));
          params.append("group",this.form.role);
          params.append("type",2);
          params.append("id",this.id);
          this.$axios.post("/user/group",params).then((res) => {
            var data = res.data
            if (data.code == 0){
              this.$message.success(data.msg);
              this.$router.go(0)
            }else {
              this.$router.push({ path: '/login' })
            }
          });
        })

    },
    okBtn5(){
      this.pidDialog =false
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("value",this.form.yan);
      params.append("id",this.id);
      this.$axios.post("/user/editpid",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.$message.success(data.msg);
        }else {
          this.$router.push({ path: '/login' })
        }
      });
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
