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
          {{ scope.row.userName }}
        </template>
      </el-table-column>
      <el-table-column label="角色组类型">
        <template slot-scope="scope">
          {{ scope.row.userName }}
        </template>
      </el-table-column>
      <el-table-column label="规则详情">
        <template slot-scope="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="创建时间">
        <template slot-scope="scope">
          {{ scope.row.regTime }}
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
            <el-input v-model="form.roleName" style="width: 240px">
              <template slot="append">元</template>
            </el-input>
          </el-form-item>
          <el-form-item label="每日最多挂机时长">
            <el-input v-model="form.roleName" style="width: 240px">
              <template slot="append">小时</template>
            </el-input>
          </el-form-item>
          <el-form-item label="注册后默认身份">
            <el-switch v-model="form.normal" />
          </el-form-item>
        </template>
        <template v-if="form.type === 2">
          <el-form-item label="身份名称">
            <el-input v-model="form.roleName" style="width: 240px" />
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
            <el-input v-model="form.price" style="width: 240px">
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
        <el-button type="primary" @click="showDialog = false">确定</el-button>
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
      list: [
        {
          id: '710000197410037922',
          userName: '小明',
          userID: 28,
          type: 1,
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
          type: 2,
          phone: 5495499889228,
          userType: '普通用户',
          proxyType: '二级代理',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1990-07-21 01:44:40',
          balance: '100',
          role: 1
        },
        {
          id: '540000200606126452',
          userName: '小明',
          userID: 14,
          type: 2,
          phone: 9441121874972,
          userType: '普通用户',
          proxyType: '二级代理',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1990-06-30 08:32:00',
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
        type: 1,
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
      this.$confirm('此操作将删除该角色组, 是否继续?', '提示', {
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
