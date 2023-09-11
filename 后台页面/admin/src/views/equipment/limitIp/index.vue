<template>
  <div>
    <el-table
      style="margin-top: 100px"
      v-loading="listLoading"
      :data="list"
      element-loading-text="Loading"
      border
      fit
      highlight-current-row
    >

      <el-table-column label="IP数量限制">
        <template slot-scope="scope">
          <div v-if="scope.row.ip_key == 1">
            {{ scope.row.ipnum }}
            <el-button
              type="text"
              @click="noLimitAmount(`此操作将取消IP数量限制, 是否继续?`,1)"
            >不限制</el-button>
          </div>
          <div v-else>
            {{ scope.row.ipnum }}
            <el-button
              type="text"
              @click="noLimitAmount(`此操作将限制IP数量限制, 是否继续?`,2)"
            >限制</el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="挂机数量限制">
        <template slot-scope="scope">
          <div v-if="scope.row.gj_key == 1">
            {{ scope.row.gjnum }}
            <el-button
              type="text"
              @click="noLimitAmount(`此操作将取消挂机数量限制, 是否继续?`,3)"
            >不限制</el-button>
          </div>
          <div v-else>
            {{ scope.row.gjnum }}
            <el-button
              type="text"
              @click="noLimitAmount(`此操作将限制挂机数量限制, 是否继续?`,4)"
            >限制</el-button>
          </div>
        </template>
      </el-table-column>
      <el-table-column label="挂机数量限制时间段">
        <template slot-scope="scope">
          {{ scope.row.gj_start }} -  {{ scope.row.gj_end }}
        </template>
      </el-table-column>
      <!-- 累计挂机时长 -->
      <el-table-column label="当前在线设备数量">
        <template slot-scope="scope">
          {{ num }}
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
        <el-form-item label="设备IP数量限制">
          <el-input v-model="form.ipnum" style="width:240px" />
        </el-form-item>
        <el-form-item label="挂机数量限制">
          <el-input v-model="form.gjnum" style="width:240px" />
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
      num:0,
      list: [],
      listLoading: false,
      filter: {
        value: ''
      },
      form:{}
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      if (localStorage.getItem('admin_token') == null){
        this.$router.push({ path: '/login' })
      }else {
        this.getSet()
      }
    },
    getSet(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      this.$axios.post("/driver/showset",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.list.push(data.data.list)
          this.form = data.data.list
          this.num = data.data.num
          this.loading = false
        }
      });
    },
    noLimitAmount(title,key) {
      this.$confirm(`${title}`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          var params = new URLSearchParams();
          params.append("token",localStorage.getItem('admin_token'));
          params.append("key",key)
          this.$axios.post("/driver/setkey",params).then((res) => {
            var data = res.data
            if (data.code == 0){
              this.$message({
                type: 'success',
                message: '操作成功!'
              })
              this.$router.go(0)
            }
          });

        })
        .catch(() => {})
    },
    edit(row) {
      this.showDialog = true
    },
    okBtn() {
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("gjnum",this.form.gjnum)
      params.append("ipnum",this.form.ipnum)
      this.$axios.post("/driver/set",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.$message({
            type: 'success',
            message: '操作成功!'
          })
          this.$router.go(0)
        }
      });
    },
  }
}
</script>
