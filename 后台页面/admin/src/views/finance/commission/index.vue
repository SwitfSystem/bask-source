<template>
  <div>
    <el-form style="margin-top: 20px" :inline="true">
      <el-form-item>
        <el-input
          v-model="listQuery.value"
          style="width: 200px; margin-right: 20px"
          placeholder="输入手机号搜索"
          clearable
        />
        <el-button type="primary" @click="seach()">搜索</el-button>
      </el-form-item>
    </el-form>
    <el-tabs v-model="activeName">
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

    <el-dialog
      title="编辑"
      :visible.sync="showDialog"
      width="40%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="审核状态">
          <el-radio-group v-model="form.status2">
            <el-radio :key="1" :label="1"> 审核通过 </el-radio>
            <el-radio :key="2" :label="2"> 审核不通过 </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="原因">
          <el-input
            v-model="form.textarea"
            type="textarea"
            :rows="2"
            placeholder="请输入驳回理由"
            style="width: 300px"
          />
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
export default {
  name: 'FinanceCommission',
  data() {
    return {
      page:1,
      pageSize:20,
      totalData:null,
      loading:true,
      showDialog: false,
      form: {},
      types: null,
      services: null,
      activeName: '1',
      list: [],
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
      if (localStorage.getItem('admin_token') == null){
        this.$router.push({ path: '/login' })
      }else {
        this.getList()
      }
    },
    async getList() {
      this.listLoading = true
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("page",this.page);
      this.$axios.post("/bill/detaillist",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          let list = data.data.list;
          this.list = list;
          this.totalData = data.data.count
          this.loading = false
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
    handleCurrentChange(val) {
      this.page = val
      this.loading = true
      this.getList()
    },
    seach(){
      if (this.listQuery.value != ""){
        this.listLoading = true
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('admin_token'));
        params.append("name","phone");
        params.append("type",5);
        params.append("words",this.listQuery.value);
        this.$axios.post("/search",params).then((res) => {
          var data = res.data
          if (data.code == 0){
            let list = data.data;
            this.list = list;
            this.totalData = 0
            this.loading = false
            for (var i = 0; i < list.length; i++) {
              if (list[i].type == 1){
                this.list1.push(list[i]);
              }else if(list[i].type == 2){
                this.list3.push(list[i]);
              }else if (list[i].type == 3){
                this.list2.push(list[i]);
              }
            }
          }else {
            this.$message.success(data.msg);
          }
        });
      }else {
        this.$router.go(0)
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
