<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item style="width: 320px">
        <el-input
          v-model="filter.value"
          style="width: 200px; margin-right: 20px"
          placeholder="输入手机型号/用户名搜索"
        />
        <el-button type="primary">搜索</el-button>
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
      <!-- 挂机手机型号 -->
      <el-table-column label="手机型号">
        <template slot-scope="scope">
          <span>{{ scope.row.os }}</span>
        </template>
      </el-table-column>
      <el-table-column label="激活码">
        <template slot-scope="scope">
          {{ scope.row.invate }}
        </template>
      </el-table-column>
      <el-table-column label="MAC地址">
        <template slot-scope="scope">
          <el-button type="text">
            {{ scope.row.mac }}
          </el-button>
        </template>
      </el-table-column>
      <el-table-column label="IP地址">
        <template slot-scope="scope">
          <el-button type="text">
            {{ scope.row.ip }}
          </el-button>
        </template>
      </el-table-column>
      <!-- 显示当前设备状态 -->
      <el-table-column label="状态">
        <template slot-scope="{ row }">
          <el-tag v-if="row.status == 1" type="success"> 任务中</el-tag>
          <el-tag v-if="row.status == 2" type="info"> 空闲中</el-tag>
          <el-tag v-if="row.status == 3" type="info"> 未在线</el-tag>
          <el-tag v-if="row.status == 4" type="danger"> 异常设备</el-tag>
          <!-- 异常状况，并于脚本端提示用户 -->
        </template>
      </el-table-column>
      <!-- 累计挂机时长 -->
      <el-table-column label="累计挂机时长">
        <template slot-scope="scope">
          {{ scope.row.counttime }}
        </template>
      </el-table-column>
      <el-table-column label="最后在线时间">
        <template slot-scope="scope">
          {{ scope.row.lasttime }}
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="编辑"
      :visible.sync="showDialog"
      width="40%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="用户名">
          {{ form.username }}
        </el-form-item>
        <el-form-item label="手机型号">
          {{ form.os }}
        </el-form-item>
        <el-form-item label="MAC地址">
          {{ form.mac }}
        </el-form-item>
        <el-form-item label="ip地址">
          {{ form.ip }}
        </el-form-item>
        <el-form-item label="状态">
          <el-tag v-if="form.status === 1" type="success"> 任务中</el-tag>
          <el-tag v-if="form.status === 2" type="info"> 空闲中</el-tag>
          <el-tag v-if="form.status === 3" type="info"> 未在线</el-tag>
          <el-tag v-if="form.status === 4" type="danger"> 异常设备</el-tag>
        </el-form-item>
        <el-form-item label="设备IP数量限制">
          <el-input v-model="form.limitIpNumber" style="width:240px" />
        </el-form-item>
        <el-form-item label="挂机数量限制">
          <el-input v-model="form.hangUpNumber" style="width:240px" />
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

      },
      rolesConfig: [
        {
          label: '代理A',
          value: 1
        },
        {
          label: '代理B',
          value: 2
        },
        {
          label: '代理C',
          value: 3
        }
      ]
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
        this.getDeviceInfo()
      }
    },
    getDeviceInfo(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('token'));
      this.$axios.post("/driver/list",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.list = data.data.list
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    // 删除账号
    deleteAmount() {
      this.$confirm('此操作将删除该账号, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$message({
            type: 'success',
            message: '删除成功!'
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
