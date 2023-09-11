<template>
  <div>
    <el-tabs style="margin-top: 40px"
      v-model="activeName">
      <el-tab-pane label="待审核" name="first" >
        <el-table ref="table" :data="list1" border fit size="small">
          <el-table-column label="用户名">
            <template slot-scope="{ row }">
              {{ row.username }}
            </template>
          </el-table-column>
          <el-table-column label="手机号" prop="phone" />
          <el-table-column label="提现余额" prop="money" />
          <el-table-column label="当时账户余额" prop="balance" />
          <el-table-column label="提现时间" prop="time" />
          <el-table-column label="支付宝账号" prop="alipay" />
          <el-table-column label="状态">
            <template slot-scope="{ row }">
              <el-tag type="info">待审核</el-tag>
            </template>
          </el-table-column>

        </el-table>

      </el-tab-pane>
      <el-tab-pane label="已通过" name="second" >
        <el-table ref="table" :data="list2" border fit size="small">
          <el-table-column label="用户名">
            <template slot-scope="{ row }">
              {{ row.username }}
            </template>
          </el-table-column>
          <el-table-column label="手机号" prop="phone" />
          <el-table-column label="提现余额" prop="money" />
          <el-table-column label="当时账户余额" prop="balance" />
          <el-table-column label="提现时间" prop="time" />
          <el-table-column label="支付宝账号" prop="alipay" />
          <el-table-column label="状态">
            <template slot-scope="{ row }">
              <el-tag type="success">已通过</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="已驳回" name="three" >
        <el-table ref="table" :data="list3" border fit size="small">
          <el-table-column label="用户名">
            <template slot-scope="{ row }">
              {{ row.username }}
            </template>
          </el-table-column>
          <el-table-column label="手机号" prop="phone" />
          <el-table-column label="提现余额" prop="money" />
          <el-table-column label="当时账户余额" prop="balance" />
          <el-table-column label="提现时间" prop="time" />
          <el-table-column label="支付宝账号" prop="alipay" />
          <el-table-column label="状态">
            <template slot-scope="{ row }">
              <el-tag type="danger">已驳回</el-tag>
              <el-button
                type="text"
                style="margin-left: 10px"
                @click="look(row)"
              >查看原因</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: 'FinanceWithdraw',
  data() {
    return {
      activeName: 'first',
      types: null,
      services: null,
      showDialog: false,
      form: {},
      list1: [],
      list2: [],
      list3: [],
      total: 0,
      form1:{},
      listLoading: true,
      listQuery: {
        page: 1,
        page_size: 20,
        type: '',
        service_type: '',
        start_at: '',
        end_at: '',
        user_id: ''
      }
    }
  },
  computed: {},
  watch: {},
  mounted() {},
  created() {
    this.getList()
  },
  methods: {
    look(row) {
      this.$alert(`用户: ${row.username} ${row.reason}`, '查看原因', {
        confirmButtonText: '确定',
        type: 'warning'
      })
    },
    async getList() {
      this.listLoading = true
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('token'));
      this.$axios.post("/bill/list",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          let list = data.data.list;
          for (var i = 0; i < list.length; i++) {
            if (list[i].status == 0){
              this.list1.push(list[i]);
            }else if(list[i].status == 1){
              this.list2.push(list[i]);
            }else {
              this.list3.push(list[i]);
            }
          }
          this.listLoading = false
        }else {
          this.$router.push({ path: '/login' })
        }
      });

    },
    edit(row) {
      this.showDialog = true
      this.form1 = row;
    },
    editBtn(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('token'));
      params.append("status",this.form.status2);
      params.append("id",this.form1.id);
      params.append("reason",this.form.textarea);
      this.$axios.post("/bill/update",params).then((res) => {
        var data = res.data
        this.$message.error(data.msg);
      });
    }
  }
}
</script>

<style scoped></style>
