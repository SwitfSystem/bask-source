<template>
  <div>
    <el-tabs style="margin-top: 40px" v-model="activeName">
      <el-tab-pane label="全部" name="1" >
        <el-table ref="table" :data="list" border fit size="small">
          <el-table-column label="用户名">
            <template slot-scope="{ row }">
              {{ row.username }}
            </template>
          </el-table-column>
          <el-table-column label="手机号" prop="phone" />
          <el-table-column label="佣金数额" prop="price" />
          <el-table-column label="佣金类型" prop="type">
            <template slot-scope="{ row }">
              <span v-if="row.type === 1">挂机佣金</span>
              <span v-if="row.type === 3">用户开通vip返佣</span>
              <span v-if="row.type === 2">用户开通代理返佣</span>
            </template>
          </el-table-column>
          <el-table-column label="当时佣金余额" prop="old_price" />
          <el-table-column label="返佣等级" prop="level" />
          <el-table-column label="返佣时间" prop="create_time" />
          <el-table-column label="佣金来源" prop="from_phone" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="挂机佣金" name="2" >
        <el-table ref="table" :data="list1" border fit size="small">
          <el-table-column label="用户名">
            <template slot-scope="{ row }">
              {{ row.username }}
            </template>
          </el-table-column>
          <el-table-column label="手机号" prop="phone" />
          <el-table-column label="佣金数额" prop="price" />
          <el-table-column label="佣金类型" prop="type">
            <template slot-scope="{ row }">
              <span>挂机佣金</span>
            </template>
          </el-table-column>
          <el-table-column label="当时佣金余额" prop="old_price" />
          <el-table-column label="返佣等级" prop="level" />
          <el-table-column label="返佣时间" prop="create_time" />
          <el-table-column label="佣金来源" prop="from_phone" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="用户开通vip返佣" name="3" >
        <el-table ref="table" :data="list2" border fit size="small">
          <el-table-column label="用户名">
            <template slot-scope="{ row }">
              {{ row.username }}
            </template>
          </el-table-column>
          <el-table-column label="手机号" prop="phone" />
          <el-table-column label="佣金数额" prop="price" />
          <el-table-column label="佣金类型" prop="type">
            <template slot-scope="{ row }">
              <span>用户开通vip返佣</span>
            </template>
          </el-table-column>
          <el-table-column label="当时佣金余额" prop="old_price" />
          <el-table-column label="返佣等级" prop="level" />
          <el-table-column label="返佣时间" prop="create_time" />
          <el-table-column label="佣金来源" prop="from_phone" />
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="用户开通代理返佣" name="4" >
        <el-table ref="table" :data="list3" border fit size="small">
          <el-table-column label="用户名">
            <template slot-scope="{ row }">
              {{ row.username }}
            </template>
          </el-table-column>
          <el-table-column label="手机号" prop="phone" />
          <el-table-column label="佣金数额" prop="price" />
          <el-table-column label="佣金类型" prop="type">
            <template slot-scope="{ row }">
              <span>用户开通代理返佣</span>
            </template>
          </el-table-column>
          <el-table-column label="当时佣金余额" prop="old_price" />
          <el-table-column label="返佣等级" prop="level" />
          <el-table-column label="返佣时间" prop="create_time" />
          <el-table-column label="佣金来源" prop="from_phone" />
        </el-table>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script>
export default {
  name: 'FinanceCommission',
  data() {
    return {
      showDialog: false,
      form: {},
      types: null,
      services: null,
      activeName: '1',
      list: [
      ],
      list1: [],
      list2: [],
      list3: [],
      total: 0,
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
    this.init();
  },
  methods: {
    init() {
      if (localStorage.getItem('token') == null){
        this.$router.push({ path: '/login' })
      }else {
        this.getList()
      }
    },
    async getList() {
      this.listLoading = true
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('token'));
      this.$axios.post("/bill/detaillist",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          let list = data.data;
          this.list = list;
          for (var i = 0; i < list.length; i++) {
            if (list[i].type == 1){
              this.list1.push(list[i]);
            }else if(list[i].type == 2){
              this.list3.push(list[i]);
            }else if (list[i].type == 3){
              this.list2.push(list[i]);
            }
          }
        }else if(data.code == -1){
          this.$router.push({ path: '/login' })
        }
      });
      this.listLoading = false
    },
    look(row) {
      this.$alert(`用户${row.userName}查无此人，不予返佣`, '查看原因', {
        confirmButtonText: '确定',
        type: 'warning'
      })
    },
    edit(row) {
      this.showDialog = true
    }
  }
}
</script>

<style scoped></style>
