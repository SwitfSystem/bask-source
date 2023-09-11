<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item>
        <el-button type="primary" @click="showDialog=true">限制IP</el-button>
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
      <!-- <el-table-column label="ID" width="95">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="IP地址">
        <template slot-scope="scope">
          {{ scope.row.ip }} <br>
          {{ scope.row.ip }} <br>
          {{ scope.row.ip }}
        </template>
      </el-table-column> -->

      <el-table-column label="IP数量限制">
        <template slot-scope="{row}">
          <div>{{ row.ipnum }}
            <el-button
              type="text"
              @click="noLimitAmount(`IP数量限制`)"
            >不限制</el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="IP数量限制时间段">
        <template slot-scope="scope">
          {{ scope.row.regTime }} -  {{ scope.row.regTime }}
        </template>
      </el-table-column>
      <el-table-column label="挂机数量限制">
        <template slot-scope="{row}">
          <div>{{ row.gjnum }}
            <el-button
              type="text"
              @click="noLimitAmount(`挂机数量限制`)"
            >不限制</el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="挂机数量限制时间段">
        <template slot-scope="scope">
          {{ scope.row.regTime }} -  {{ scope.row.regTime }}
        </template>
      </el-table-column>
      <!-- 累计挂机时长 -->
      <el-table-column label="当前在线设备数量">
        <template slot-scope="scope">
          {{ scope.row.hangUpNumber }}
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="{ row }">
          <el-button
            type="text"
            @click="edit(row)"
          >编辑</el-button>
          <!-- <el-button
            type="text"
            @click="deleteAmount(row)"
          >禁用</el-button> -->
        </template>
      </el-table-column>
      <!-- <el-table-column label="限制时间">
        <template slot-scope="scope">
          {{ scope.row.regTime }}
        </template>
      </el-table-column> -->
      <!-- <el-table-column label="操作">
        <template slot-scope="{ row }">
          <el-button
            type="text"
            @click="edit(row)"
          >编辑</el-button>
          <el-button
            type="text"
            @click="deleteAmount(row)"
          >禁用</el-button>
        </template>
      </el-table-column> -->
    </el-table>
    <el-dialog
      title="编辑"
      :visible.sync="showDialog"
      width="40%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="160px">
        <!-- <el-form-item label="IP地址">
          <el-input v-model="form.ip" type="textarea" style="width:240px" />
        </el-form-item> -->
        <el-form-item label="设备IP数量限制">
          <el-input v-model="form.limitIpNumber" style="width:240px" />
        </el-form-item>
        <el-form-item label="设备IP数量限制时间段">
          <el-date-picker
            v-model="form.value1"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
          />
        </el-form-item>
        <el-form-item label="挂机数量限制">
          <el-input v-model="form.hangUpNumber" style="width:240px" />
        </el-form-item>
        <el-form-item label="挂机数量限制时间段">
          <el-date-picker
            v-model="form.value2"
            type="datetimerange"
            range-separator="至"
            start-placeholder="开始日期"
            end-placeholder="结束日期"
            align="right"
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
import { getList } from '@/api/table'
export default {
  name: 'EquipmentInfoList',
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
      listLoading: false,
      filter: {
        value: ''
      },
      form: {
        value1: [],
        value2: []
      }
    }
  },
  created() {
    this.init();
    // this.fetchData();
    // this.listLoading =
  },
  methods: {
    init() {
      if (localStorage.getItem('token') == null){
        this.$router.push({ path: '/login' })
      }else {
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('token'));
        this.$axios.post("/driver/listset",params).then((res) => {
          var data = res.data
          if (data.code == 0){
            this.list = data.data
          }else {
            this.$router.push({ path: '/login' })
          }
        });
      }
    },
    noLimitAmount(title) {
      this.$confirm(`此操作将取消${title}, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
        })
        .catch(() => {})
    },
    // 删除账号
    deleteAmount() {
      this.$confirm('此操作将禁用该规则, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
        })
        .catch(() => {})
    },
    edit(row) {
      this.form = Object.assign({}, row)
      console.log(this.form)
      this.showDialog = true
    },
    fetchData() {
      this.listLoading = true
      getList().then((response) => {
        this.list = response.data.items
        this.listLoading = false
      })
    },
    onSubmit() {
      console.log('submit!')
    }
  }
}
</script>
