<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item>
        <el-button
          type="primary"
          @click="showDialog = true"
        >新增角色组</el-button>
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
      <el-table-column label="角色组名称">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="角色组类型">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.type == 0" type="info">{{ "普通用户" }}</el-tag>
          <el-tag v-if="scope.row.type == 1" type="info">{{ "代理" }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="规则详情">
        <template slot-scope="scope">
          <template v-if="scope.row.type == 0">
            {{ "Vip价格: "+scope.row.vip_price+" 元/小时 挂机时长: "+scope.row.time+" 小时"}}
          </template>
          <template v-else>
            {{ "挂机返佣：1级 "+scope.row.fake_back_1+" % 2级 "+scope.row.fake_back_2+" % Vip返佣: 1级 "+scope.row.vip_back_1+" % 2级"+scope.row.vip_back_2+" % 代理返佣 : 1级 "+scope.row.agent_back_1+" % 2级 "+scope.row.agent_back_2+" %"}}
          </template>
        </template>
      </el-table-column>
      <el-table-column label="价格">
        <template slot-scope="scope">
          <template v-if="scope.row.type == 0">
            {{ "挂机收益: "+scope.row.price+" 元"}}
          </template>
          <template v-else>
            {{ "代理价格 "+scope.row.price+" 元 "}}
          </template>
        </template>
      </el-table-column>


      <el-table-column label="激活码数量">
        <template slot-scope="scope">
          {{ scope.row.active_code}}
        </template>
      </el-table-column>
      <el-table-column label="默认选择">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.is_use == 1" type="info">{{ "默认身份" }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="创建时间">
        <template slot-scope="scope">
          {{ scope.row.create_time }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="{row}">
          <el-button type="text" @click="edit(row)">编辑</el-button>
          <el-button type="text" @click="deleteAmount(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="编辑"
      :visible.sync="showDialog"
      width="40%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="150px">
        <el-form-item label="类型">
          <el-radio-group v-model="form.type">
            <el-radio :key="1" :label="1"> 用户 </el-radio>
            <el-radio :key="2" :label="2"> 代理 </el-radio>
          </el-radio-group>
        </el-form-item>
        <template v-if="form.type === 1">
          <el-form-item label="身份名称">
            <el-input v-model="form.roleName" style="width: 240px" />
          </el-form-item>
          <el-form-item label="挂机收益">
            <el-input v-model="form.gjsy" style="width: 240px">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
          <el-form-item label="不同身份vip价格">
            <el-input v-model="form.vip" style="width: 240px">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
          <el-form-item label="每日最多挂机时长">
            <el-input v-model="form.time" style="width: 240px">
              <template slot="append">小时</template>
            </el-input>
          </el-form-item>
          <el-form-item label="注册后默认身份">
            <el-switch v-model="form.normal" />
          </el-form-item>
        </template>
        <template v-if="form.type === 2">
          <el-form-item label="身份名称">
            <el-input v-model="form.roleagent" style="width: 240px" />
          </el-form-item>
          <el-form-item label="挂机返佣比例">
            <span for="">一级返佣</span>
            <el-input
              v-model="form.gjfybl1"
              style="width: 120px; margin-left: 10px"
            >
              <template slot="append">%</template>
            </el-input>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <span for="">二级返佣</span>
            <el-input
              v-model="form.gjfybl2"
              style="width: 120px; margin-left: 10px"
            >
              <template slot="append">%</template>
            </el-input>
          </el-form-item>
          <el-form-item label="开通vip返佣比例">
            <span for="">一级返佣</span>
            <el-input
              v-model="form.vipfybl1"
              style="width: 120px; margin-left: 10px"
            >
              <template slot="append">%</template>
            </el-input>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <span for="">二级返佣</span>
            <el-input
              v-model="form.vipfybl2"
              style="width: 120px; margin-left: 10px"
            >
              <template slot="append">%</template>
            </el-input>
          </el-form-item>
          <el-form-item label="开通代理返佣比例">
            <span for="">一级返佣</span>
            <el-input
              v-model="form.ktdlfybl1"
              style="width: 120px; margin-left: 10px"
            >
              <template slot="append">%</template>
            </el-input>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <span for="">二级返佣</span>
            <el-input
              v-model="form.ktdlfybl2"
              style="width: 120px; margin-left: 10px"
            >
              <template slot="append">%</template>
            </el-input>
          </el-form-item>
          <el-form-item label="代理价格">
            <el-input v-model="form.price1" style="width: 240px">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
          <el-form-item label="激活码数量">
            <el-input v-model="form.number" style="width: 240px" />
          </el-form-item>
        </template>
      </el-form>
      <span slot="footer">
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="okBtn()">确定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="编辑"
      :visible.sync="showDialog1"
      width="40%"
      @close="showDialog1 = false"
    >
      <el-form ref="form" :model="form1" label-width="150px">

          <el-form-item label="身份名称">
            <el-input v-model="form1.name" style="width: 240px" />
          </el-form-item>
          <el-form-item label="挂机收益">
            <el-input v-model="form1.price" style="width: 240px">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
          <el-form-item label="不同身份vip价格">
            <el-input v-model="form1.vip_price" style="width: 240px">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
          <el-form-item label="每日最多挂机时长">
            <el-input v-model="form1.time" style="width: 240px">
              <template slot="append">小时</template>
            </el-input>
          </el-form-item>
          <el-form-item label="注册后默认身份">
            <el-switch v-model="form1.is_use" :active-value= "1" :inactive-value="0" />
          </el-form-item>

      </el-form>
      <span slot="footer">
        <el-button @click="showDialog1 = false">取消</el-button>
        <el-button type="primary" @click="okBtn1()">确定</el-button>
      </span>
    </el-dialog>

    <el-dialog
      title="编辑"
      :visible.sync="showDialog2"
      width="40%"
      @close="showDialog2 = false"
    >
      <el-form ref="form" :model="form2" label-width="150px">
          <el-form-item label="身份名称">
            <el-input v-model="form2.name" style="width: 240px" />
          </el-form-item>
          <el-form-item label="挂机返佣比例">
            <span for="">一级返佣</span>
            <el-input
              v-model="form2.fake_back_1"
              style="width: 120px; margin-left: 10px"
            >
              <template slot="append">%</template>
            </el-input>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <span for="">二级返佣</span>
            <el-input
              v-model="form2.fake_back_2"
              style="width: 120px; margin-left: 10px"
            >
              <template slot="append">%</template>
            </el-input>
          </el-form-item>
          <el-form-item label="开通vip返佣比例">
            <span for="">一级返佣</span>
            <el-input
              v-model="form2.vip_back_1"
              style="width: 120px; margin-left: 10px"
            >
              <template slot="append">%</template>
            </el-input>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <span for="">二级返佣</span>
            <el-input
              v-model="form2.vip_back_2"
              style="width: 120px; margin-left: 10px"
            >
              <template slot="append">%</template>
            </el-input>
          </el-form-item>
          <el-form-item label="开通代理返佣比例">
            <span for="">一级返佣</span>
            <el-input
              v-model="form2.agent_back_1"
              style="width: 120px; margin-left: 10px"
            >
              <template slot="append">%</template>
            </el-input>
            &nbsp;&nbsp;&nbsp;&nbsp;
            <span for="">二级返佣</span>
            <el-input
              v-model="form2.agent_back_2"
              style="width: 120px; margin-left: 10px"
            >
              <template slot="append">%</template>
            </el-input>
          </el-form-item>
          <el-form-item label="代理价格">
            <el-input v-model="form2.price" style="width: 240px">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
          <el-form-item label="激活码数量">
            <el-input v-model="form2.active_code" style="width: 240px" />
          </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="showDialog2 = false">取消</el-button>
        <el-button type="primary" @click="okBtn2()">确定</el-button>
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
      showDialog: false,
      showDialog1: false,
      showDialog2: false,
      list: [
      ],
      id:0,
      listLoading: false,
      filter: {
        value: ''
      },
      form: {
        roleName: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: 1,
        resource: '',
        desc: '',
        role: ''
      },
      form1:{},
      form2:{},
    }
  },
  created() {
    this.init();
    // this.fetchData();
    // this.listLoading =
  },
  methods: {
    init() {
      if (localStorage.getItem('admin_token') == null) {
        this.$router.push({path: '/login'})
      } else {
        this.getGroupList()
      }
    },
    getGroupList(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      this.$axios.post("/group/list",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.list = data.data.list
        }else if (data.code == -1){
          this.$router.push({path: '/login'})
        }
      });
    },
    // 删除账号
    deleteAmount(row) {
      this.$confirm('此操作将删除该角色组, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          var params = new URLSearchParams();
          params.append("token",localStorage.getItem('admin_token'));
          params.append("id",row.id);
          this.$axios.post("/group/deluser",params).then((res) => {
            var data = res.data
            if (data.code == 0){
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              this.$router.go(0)
            }else if (data.code == -1){
              this.$router.push({path: '/login'})
            }
          });

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
      this.id = row.id
      if (row.type ==0){
        this.showDialog1 = true
        this.form1 = row
      }else if (row.type ==1){
        this.showDialog2 = true
        this.form2 = row
      }


    },
    fetchData() {
      this.listLoading = true
      getList().then((response) => {
        this.list = response.data.items
        this.listLoading = false
      })
    },
    okBtn(){
      this.showDialog = false;
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      if (this.form.type == 1){
        params.append("name",this.form.roleName);
        params.append("vip",this.form.vip);
        params.append("price",this.form.gjsy);
        params.append("time",this.form.time);
        params.append("id",0);
        params.append("use",this.form.normal ? 1 :0);
        this.$axios.post("/group/user",params).then((res) => {
          var data = res.data
          if (data.code == 0){
            this.$router.go(0)
          }else if (data.code == -1){
            this.$router.push({path: '/login'})
          }
        });
      }else if (this.form.type == 2){
        params.append("id",0);
        params.append("name",this.form.roleagent);
        params.append("fake1",this.form.gjfybl1);
        params.append("fake2",this.form.gjfybl2);
        params.append("vip1",this.form.vipfybl1);
        params.append("vip2",this.form.vipfybl2);
        params.append("agent1",this.form.ktdlfybl1);
        params.append("agent2",this.form.ktdlfybl2);
        params.append("acode",this.form.number);
        params.append("price",this.form.price1);
        this.$axios.post("/group/agent",params).then((res) => {
          var data = res.data
          if (data.code == 0){
            this.$router.go(0)
          }else if (data.code == -1){
            this.$router.push({path: '/login'})
          }
        });
      }

    },okBtn1(){
      this.showDialog1 = false;
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("name",this.form1.name);
      params.append("vip",this.form1.vip_price);
      params.append("price",this.form1.price);
      params.append("time",this.form1.time);
      params.append("id",this.id);
      params.append("use",this.form1.is_use);
      this.$axios.post("/group/user",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.$router.go(0)
        }else if (data.code == -1){
          this.$router.push({path: '/login'})
        }
      });
    },okBtn2(){
      this.showDialog2 = false;
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("id",this.id);
      params.append("name",this.form2.name);
      params.append("fake1",this.form2.fake_back_1);
      params.append("fake2",this.form2.fake_back_2);
      params.append("vip1",this.form2.vip_back_1);
      params.append("vip2",this.form2.vip_back_2);
      params.append("agent1",this.form2.agent_back_1);
      params.append("agent2",this.form2.agent_back_2);
      params.append("acode",this.form2.active_code);
      params.append("price",this.form2.price);
      this.$axios.post("/group/agent",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.$router.go(0)
        }else if (data.code == -1){
          this.$router.push({path: '/login'})
        }
      });
    }
  }
}
</script>
