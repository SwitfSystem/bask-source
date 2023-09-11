<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item>
        <el-input
          v-model="filter.value"
          style="width: 200px; margin-right: 20px"
          placeholder="输入关键词搜索"
        />
        <el-button type="primary">搜索</el-button>
        <el-button type="warning" plain @click="dialogVisible=true">导出日志</el-button>
      </el-form-item>
    </el-form>
    <el-tabs v-model="activeName" @tab-click="handleClick">
      <el-tab-pane label="app日志" name="1">
        <el-table
          v-loading="listLoading"
          :data="list1"
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
          <el-table-column label="脚本操作">
            <template slot-scope="scope">
              {{ scope.row.userName }}（{{ scope.row.userID }}）
            </template>
          </el-table-column>
          <el-table-column label="任务类型">
            <template slot-scope="scope">
              <span>{{ scope.row.phone }}</span>
            </template>
          </el-table-column>
          <el-table-column label="设备数量">
            <template slot-scope="scope">
              {{ scope.row.number || 30 }}
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
      </el-tab-pane>
      <el-tab-pane label="佣金日志" name="2">
        <el-table
          v-loading="listLoading"
          :data="list2"
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
          <el-table-column label="佣金数量">
            <template slot-scope="scope">
              <span>{{ scope.row.money || "100元" }}</span>
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
      </el-tab-pane>
      <el-tab-pane label="提现日志" name="3">
        <el-table
          v-loading="listLoading"
          :data="list3"
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
              <span>{{ scope.row.money || "100元" }}</span>
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
      </el-tab-pane>
      <el-tab-pane label="导出日志" name="4">
        <el-table
          v-loading="listLoading"
          :data="list4"
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
          <el-table-column label="脚本操作">
            <template slot-scope="scope">
              {{ scope.row.userName }}（{{ scope.row.userID }}）
            </template>
          </el-table-column>
          <el-table-column label="任务类型">
            <template slot-scope="scope">
              <span>{{ scope.row.phone }}</span>
            </template>
          </el-table-column>
          <el-table-column label="设备数量">
            <template slot-scope="scope">
              {{ scope.row.number || 30 }}
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
      </el-tab-pane>
    </el-tabs>
    <el-dialog
      title="提示"
      :visible.sync="dialogVisible"
      width="36%"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="请选择导出类型">
          <el-checkbox-group v-model="form.type">
            <el-checkbox v-for="item in items" :key="item.key" :label="item.label">
              {{ item.label }}
            </el-checkbox>
          </el-checkbox-group>
        </el-form-item>
      </el-form>
      <span slot="footer" class="dialog-footer">
        <el-button @click="dialogVisible = false">取 消</el-button>
        <el-button type="primary" @click="dialogVisible = false">确 定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>
import { getList } from '@/api/table'
export default {
  name: 'LogsInfoList',
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
      activeName: '1',
      showDialog: false,
      dialogVisible: false,
      items: [
        {
          label: 'app日志',
          value: 1
        },
        {
          label: '佣金日志',
          value: 2
        },
        {
          label: '提现日志',
          value: 3
        },
        {
          label: '导出日志',
          value: 4
        }
      ],
      list1: [],
      list3: [
      ],
      list4: [
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
      this.$axios.post("/log/list",params).then((res) => {
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
