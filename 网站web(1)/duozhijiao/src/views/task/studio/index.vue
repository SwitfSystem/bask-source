<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item>
        <el-input
          v-model="filter.value"
          style="width: 200px; margin-right: 20px"
          placeholder="输入关键字搜索"
        />
        <el-button type="primary" style="margin-right: 20px">搜索</el-button>
        <el-popover
          placement="top-start"
          width="200"
          trigger="hover"
          content="直播间超级热度"
        >
          <el-button
            slot="reference"
            type="primary"
            icon="el-icon-plus"
            style="margin-right: 20px"
            @click="handlerDialog(1)"
          >直播间超级热度</el-button>
        </el-popover>
        <!-- <el-popover
          placement="top-start"
          width="200"
          trigger="hover"
          content="该任务只能在创建超级热度后执行"
        >
          <el-button
            slot="reference"
            type="primary"
            style="margin-right: 20px"
            @click="handlerDialog(2)"
          >直播间暖场</el-button>
        </el-popover>
        <el-popover
          placement="top-start"
          width="200"
          trigger="hover"
          content="该任务只能在创建超级热度后执行"
        >
          <el-button
            slot="reference"
            type="primary"
            style="margin-right: 20px"
            @click="handlerDialog(3)"
          >实时互动</el-button>
        </el-popover>
        <el-popover
          placement="top-start"
          width="200"
          trigger="hover"
          content="该任务只能在创建超级热度后执行"
        >
          <el-button
            slot="reference"
            type="primary"
            style="margin-right: 20px"
            @click="handlerDialog(4)"
          >疯狂点屏</el-button>
        </el-popover>
        <el-popover
          placement="top-start"
          width="200"
          trigger="hover"
          content="该任务只能在创建超级热度后执行"
        >
          <el-button
            slot="reference"
            type="primary"
            style="margin-right: 20px"
            @click="handlerDialog(5)"
          >关注榜一至榜十</el-button>
        </el-popover> -->
        <span style="color: #666; margin-right: 20px">
          目前空闲设备数量：10台</span>
        <span style="color: #666"> 当前在线设备数量：20台</span>
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
      <el-table-column label="任务ID">
        <template slot-scope="scope">
          {{ scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column label="主播账号">
        <template slot-scope="{}"> 小新新 </template>
      </el-table-column>
      <el-table-column label="控制设备数量">
        <template slot-scope="{}"> 20台 </template>
      </el-table-column>
      <el-table-column label="任务时间">
        <template slot-scope="{}"> 30分钟 </template>
      </el-table-column>
      <el-table-column label="是否自动关注">
        <template slot-scope="{ $index, row }">
          <el-tag v-if="$index % 2" type="success">
            {{ row.status || "关注" }}</el-tag>
          <el-tag v-else type="info"> {{ row.status || "不关注" }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="欢迎语">
        <template slot-scope="{}"> 小新新，你好啊~ </template>
      </el-table-column>
      <!-- <el-table-column label="状态">
        <template slot-scope="{ $index, row }">
          <el-tag v-if="$index % 2" type="success">
            {{ row.status || "启用" }}</el-tag>
          <el-tag v-else type="danger"> {{ row.status || "禁用" }}</el-tag>
        </template>
      </el-table-column> -->
      <el-table-column label="任务创建时间">
        <template slot-scope="scope">
          {{ scope.row.regTime }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="380">
        <template slot-scope="">
          <el-button
            type="text"
            @click="handlerDialog(2)"
          >直播间暖场</el-button>
          <el-button type="text" @click="handlerDialog(3)">实时互动</el-button>
          <el-button type="text" @click="handlerDialog(4)">疯狂点屏</el-button>
          <el-button
            type="text"
            @click="handlerDialog(5)"
          >关注榜一至榜十</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :title="`${dialogTitle}`"
      :visible.sync="showDialog"
      width="36%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="180px">
        <el-form-item v-if="dialogType===1" label="主播账号">
          <el-input
            v-model="form.amount"
            type="text"
            placeholder="请输入主播账号"
            style="width: 260px; margin-right: 10px"
          />
          <el-checkbox
            v-model="form.payAttention"
            label="1"
          >自动关注</el-checkbox>
        </el-form-item>
        <el-form-item v-if="dialogType===1" label="欢迎语">
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
        <el-form-item v-if="dialogType===1" label="控制设备数量">
          <el-input v-model="form.equipment" type="number" style="width: 260px">
            <template slot="append">台</template>
          </el-input>
        </el-form-item>
        <el-form-item v-if="dialogType===1" label="任务时间">
          <el-input v-model="form.time" type="number" style="width: 260px">
            <template slot="append">分钟</template>
          </el-input>
        </el-form-item>
        <el-form-item v-if="dialogType!==5" label="文字素材">
          <el-popover
            placement="top-start"
            width="200"
            trigger="hover"
            effect="dark"
            content="随机发送素材内容"
          >
            <el-select
              slot="reference"
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
          </el-popover>
        </el-form-item>
        <el-form-item v-if="dialogType===2" label="每台设备弹幕延迟时间">
          <el-input v-model="form.time" type="number" style="width: 260px">
            <template slot="append">秒</template>
          </el-input>
        </el-form-item>
        <el-form-item v-if="dialogType===4" label="每台设备点屏延迟时间">
          <el-input v-model="form.time" type="number" style="width: 260px">
            <template slot="append">秒</template>
          </el-input>
        </el-form-item>
        <el-form-item v-if="dialogType===5" label="自动关注">
          <el-checkbox-group v-model="form.checked">
            <el-checkbox v-for="item in 10" :key="item" :label="item">
              榜{{ item }}
            </el-checkbox>
          </el-checkbox-group>

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
          role: 1,
          checked: []
        },
        {
          id: '340000199512231494',
          userName: '小明',
          userID: 3,
          phone: 5495499889228,
          material: '',
          userType: '直播私信',
          regTime: '1990-07-21 01:44:40',
          content: '100私信内容',
          checked: []
        },
        {
          id: '540000200606126452',
          userName: '小明',
          userID: 14,
          phone: 9441121874972,
          userType: '评论私信',
          material: '',
          regTime: '1990-06-30 08:32:00',
          content: '100私信内容',
          checked: []
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
        type3: '',
        checked: []
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
    // this.fetchData();
    // this.listLoading =
  },
  methods: {
    handlerDialog(index) {
      this.dialogType = index
      switch (index) {
        case 1:
          this.dialogTitle = '直播间超级热度'
          break
        case 2:
          this.dialogTitle = '直播间暖场'
          break
        case 3:
          this.dialogTitle = '实时互动'
          break
        case 4:
          this.dialogTitle = '疯狂点屏'
          break
        case 5:
          this.dialogTitle = '关注榜一至榜十'
          break
        default:
          break
      }
      this.showDialog = true
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
