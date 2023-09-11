<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item style="width: 320px" label="请输入版本号">
        <el-input
          v-model="filter.value"
          style="width: 200px; margin-right: 20px"
          placeholder=""
        />
      </el-form-item>
      <el-form-item label="">
        <el-button type="primary" style="margin-left:96px" @click="onSubmit">立即更新</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import { getList } from '@/api/table'
export default {
  name: 'EquipmentUpdate',
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
    // this.fetchData();
    // this.listLoading =
  },
  methods: {
    onSubmit() {

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
    }
  }
}
</script>
