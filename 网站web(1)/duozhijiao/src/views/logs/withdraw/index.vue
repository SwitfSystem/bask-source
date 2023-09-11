<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item style="width: 320px">
        <el-input
          v-model="filter.value"
          style="width: 200px; margin-right: 20px"
          placeholder="输入关键词搜索"
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
          {{ scope.row.userName }}（{{ scope.row.userID }}）
        </template>
      </el-table-column>
      <el-table-column label="提现金额">
        <template slot-scope="scope">
          <span>{{ scope.row.money||'100元' }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作时间">
        <template slot-scope="scope">
          {{ scope.row.regTime }}
        </template>
      </el-table-column>
      <el-table-column label="状态">
        <template slot-scope="{ $index, row }">
          <el-tag v-if="$index % 2" type="success">
            {{ row.status || "成功" }}</el-tag>
          <el-tag v-else type="danger"> {{ row.status || "失败" }}</el-tag>
        </template>
      </el-table-column>
    </el-table>
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
      list: [
        {
          id: '710000197410037922',
          userName: '小明',
          userID: 28,
          phone: 66631697175626,
          userType: '普通用户',
          proxyType: '二级代理',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1974-05-18 19:00:06',
          balance: '100',
          role: 1
        },
        {
          id: '340000199512231494',
          userName: '小明',
          userID: 3,
          phone: 5495499889228,
          userType: '普通用户',
          proxyType: '二级代理',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1990-07-21 01:44:40',
          balance: '100',
          role: 1
        }
      ],
      listLoading: false,
      filter: {
        value: ''
      },
      form: {
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
        role: ''
      },
      rolesConfig1: [
        {
          label: '管理员',
          value: 1
        },
        {
          label: '运维',
          value: 2
        },
        {
          label: '客服',
          value: 3
        }
      ],
      rolesConfig2: [
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
      this.form = Object.assign({}, row)
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
