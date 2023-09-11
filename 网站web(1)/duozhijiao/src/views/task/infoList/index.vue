<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item>
        <el-input
          v-model="filter.value"
          style="width: 200px; margin-right: 20px"
          placeholder="输入手机号/用户名搜索"
        />
        <el-button type="primary" style="margin-right: 20px">搜索</el-button>
        <el-popover
          placement="top-start"
          width="200"
          trigger="hover"
          content="通过导入素材库抖音或快手账号去私信，不可重复私信"
        >
          <el-button
            slot="reference"
            type="primary"
            style="margin-right: 20px"
            @click="handlerDialog(1)"
          >账号私信</el-button>
        </el-popover>
        <el-popover
          placement="top-start"
          width="200"
          trigger="hover"
          content="私信正在直播的主播，不可重复私信。"
        >
          <el-button
            slot="reference"
            type="primary"
            style="margin-right: 20px"
            @click="handlerDialog(2)"
          >直播私信</el-button>
        </el-popover>
        <el-popover
          placement="top-start"
          width="200"
          trigger="hover"
          content="通过输入视频链接私信视频下面的评论，不可重复私信"
        >
          <el-button
            slot="reference"
            type="primary"
            style="margin-right: 20px"
            @click="handlerDialog(3)"
          >评论私信</el-button>
        </el-popover>
        <el-popover
          placement="top-start"
          width="200"
          trigger="hover"
          content="私信直播间内看直播的用户，不可重复私信。"
        >
          <el-button
            slot="reference"
            type="primary"
            style="margin-right: 20px"
            @click="handlerDialog(4)"
          >直播间私信</el-button>
        </el-popover>
        <span style="color: #666; margin-right: 20px">
          目前空闲设备数量：10台</span>
        <span style="color: #666"> 当前在线设备数量：20台</span>
      </el-form-item>
    </el-form>
    <el-tabs v-model="activeName">
      <el-tab-pane label="抖音" name="first" />
      <el-tab-pane label="快手" name="second" />
    </el-tabs>
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
      <!-- <el-table-column label="用户名">
        <template slot-scope="scope">
          {{ scope.row.userName }}（{{ scope.row.userID }}）
        </template>
      </el-table-column>
      <el-table-column label="手机号">
        <template slot-scope="scope">
          <span>{{ scope.row.phone }}</span>
        </template>
      </el-table-column> -->
      <el-table-column label="私信类型">
        <template slot-scope="scope">
          {{ scope.row.userType }}
        </template>
      </el-table-column>
      <el-table-column label="发送时间">
        <template slot-scope="scope">
          {{ scope.row.regTime }}
        </template>
      </el-table-column>
      <el-table-column label="私信内容">
        <template slot-scope="scope">
          {{ scope.row.content }}
        </template>
      </el-table-column>
      <el-table-column label="任务状态">
        <template slot-scope="{ $index, row }">
          <el-tag v-if="$index===1" type="info">
            {{ row.status || "执行中" }}</el-tag>
          <el-tag v-else-if="$index===2" type="success"> {{ row.status || "已完成" }}</el-tag>
          <el-tag v-else type="danger"> {{ row.status || "已停止" }}</el-tag>
        </template>
      </el-table-column>
      <!-- <el-table-column label="状态">
        <template slot-scope="{ $index, row }">
          <el-tag v-if="$index % 2" type="success">
            {{ row.status || "已读" }}</el-tag>
          <el-tag v-else type="danger"> {{ row.status || "未读" }}</el-tag>
        </template>
      </el-table-column> -->
      <el-table-column label="操作" width="300">
        <template slot-scope="">
          <el-button type="text" @click="deleteAmount(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :title="`编辑${dialogTitle}`"
      :visible.sync="showDialog"
      width="36%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="150px">
        <el-form-item v-if="dialogType!==4" label="平台">
          <el-radio-group v-model="form.type">
            <el-radio :key="1" :label="1"> 抖音 </el-radio>
            <el-radio :key="2" :label="2"> 快手 </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="dialogType===4" label="抖音号或快手号">
          <el-input v-model="form.amount" type="text" placeholder="请输入抖音号或快手号" style="width: 260px;margin-right:10px" />
          <el-checkbox v-model="form.payAttention" label="1">自动关注</el-checkbox>
        </el-form-item>
        <el-form-item label="控制设备数量">
          <!-- <el-input v-model="form.equipment" type="number" style="width: 300px">
            <template slot="append">台</template>
          </el-input> -->
          <el-input v-model="form.equipment" type="number" style="width: 260px;margin-right:10px">
            <template slot="append">台</template>
          </el-input>
          <el-checkbox v-if="dialogType!==2" v-model="form.assignPrivate" label="1">分配私信</el-checkbox>
        </el-form-item>
        <!-- <el-form-item label="设备分配私信">
          <el-radio-group v-model="form.type3">
            <el-radio :key="1" :label="1"> 分配 </el-radio>
            <el-radio :key="2" :label="2"> 不分配 </el-radio>
          </el-radio-group>
        </el-form-item> -->
        <el-form-item label="每台设备私信个数">
          <el-tooltip class="item" effect="dark" content="到达数量自动停止任务" placement="top-start">
            <el-input v-model="form.number2" type="number" style="width: 260px">
              <template slot="append">条</template>
            </el-input>
          </el-tooltip>
        </el-form-item>
        <el-form-item label="是否加入随机字符">
          <el-radio-group v-model="form.type2">
            <el-radio :key="1" :label="1"> 是 </el-radio>
            <el-radio :key="2" :label="2"> 否 </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="dialogType===3" label="视频链接">
          <el-input
            v-model="form.url"
            type="textarea"
            :rows="2"
            style="width: 260px"
            placeholder="请输入内容"
          />
        </el-form-item>
        <el-form-item label="启用素材">
          <el-select
            v-model="form.material"
            clearable
            filterable
            style="width: 260px"
          >
            <el-option
              v-for="item in materials"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="消息内容">
          <el-select
            v-model="form.material2"
            clearable
            filterable
            style="width: 260px"
          >
            <el-option
              v-for="item in materials"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
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
  name: 'TaskInfoList',
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
      activeName: 'first',
      showDialog: false,
      dialogTitle: '',
      dialogType: 1,
      options: [],
      list: [
        {
          id: '710000197410037922',
          userName: '小明',
          userID: 28,
          phone: 66631697175626,
          material: '',
          userType: '账号私信',
          regTime: '1974-05-18 19:00:06',
          content: '100私信内容',
          role: 1
        },
        {
          id: '340000199512231494',
          userName: '小明',
          userID: 3,
          phone: 5495499889228,
          material: '',
          userType: '直播私信',
          regTime: '1990-07-21 01:44:40',
          content: '100私信内容'
        },
        {
          id: '540000200606126452',
          userName: '小明',
          userID: 14,
          phone: 9441121874972,
          userType: '评论私信',
          material: '',
          regTime: '1990-06-30 08:32:00',
          content: '100私信内容'
        }
      ],
      listLoading: false,
      filter: {
        value: ''
      },
      materials: [],

      form: {
        material: '',
        name: '',
        region: '',
        date1: '',
        date2: '',
        delivery: false,
        type: [],
        resource: '',
        desc: '',
        role: '',
        type2: '',
        type3: ''
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
        this.getTaskList()
      }
    },
    getTaskList(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('token'));
      this.$axios.post("/task/list",params).then((res) => {
        var data = res.data
        console.log(data)
        if (data.code == 0){
          this.list = data.data.list
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    handlerDialog(index) {
      switch (index) {
        case 1:
          this.dialogTitle = '账号私信'
          this.dialogType = 1
          this.showDialog = true
          break
        case 2:
          this.dialogTitle = '直播私信'
          this.dialogType = 2
          this.showDialog = true
          break
        case 3:
          this.dialogTitle = '评论私信'
          this.dialogType = 3
          this.showDialog = true
          break
        case 4:
          this.dialogTitle = '直播间私信'
          this.dialogType = 4
          this.showDialog = true
          break
        default:
          break
      }
    },
    // 删除账号
    deleteAmount() {
      this.$confirm('此操作将删除该私信, 是否继续?', '提示', {
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
    resend() {
      this.$confirm('此操作将重发该私信, 是否继续?', '提示', {
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
