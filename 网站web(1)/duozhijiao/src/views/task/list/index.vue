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
      <el-table-column label="任务类型">
        <template slot-scope="scope">
          {{ scope.row.type1||'任务类型B' }}
        </template>
      </el-table-column>
      <el-table-column label="任务时间">
        <template slot-scope="scope">
          <span>{{ scope.row.phone }}</span>
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
      <el-table-column label="操作" width="300">
        <template slot-scope="">
          <el-button
            type="text"
            @click="stop(row)"
          >停止任务</el-button>
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
        <el-form-item label="设定用户身份">
          <el-select v-model="form.role" clearable filterable>
            <el-option
              v-for="item in rolesConfig1"
              :key="item.value"
              :label="item.label"
              :value="item.value"
            />
          </el-select>
        </el-form-item>
        <el-form-item label="设定代理身份">
          <el-radio-group v-model="form.role2">
            <el-radio :key="1" label="1"> 渠道代理 </el-radio>
            <el-radio :key="2" label="2"> 公司代理 </el-radio>
            <el-radio :key="3" label="3"> 个人代理 </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="冻结账号">
          <el-checkbox v-model="form.freeze" label="1">冻结下级所有账号</el-checkbox>
        </el-form-item>
        <el-form-item label="冻结原因">
          <el-input v-model="form.resean" clearable type="textarea" style="width: 320px" />
        </el-form-item>
        <el-form-item label="修改密码">
          <el-input v-model="form.newPassword" :show-password="true" clearable type="password" style="width: 320px" />
        </el-form-item>
        <el-form-item label="修改余额">
          <el-input v-model="form.balance" style="width: 320px" clearable />
        </el-form-item>
        <el-form-item label="修改上级">
          <el-select
            v-model="form.role3"
            placeholder="请选择"
            style="width: 320px"
            clearable
            filterable
          >
            <el-option
              v-for="item in rolesConfig"
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
  name: 'TaskList',
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
          type1: '任务类型A',
          userID: 28,
          phone: 66631697175626,
          userType: '普通用户',
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
          phone: 9441121874972,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1990-06-30 08:32:00',
          balance: '100',
          role: 1
        },
        {
          id: '230000199512204554',
          userName: '小明',
          userID: 23,
          phone: 9094783977018,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1992-04-06 17:28:59',
          balance: '100',
          role: 1
        },
        {
          id: '82000019990114898X',
          userName: '小明',
          userID: 31,
          phone: 95428593952872,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1990-02-14 06:17:36',
          balance: '100',
          role: 1
        },
        {
          id: '430000200111104184',
          userName: '小明',
          userID: 20,
          phone: 87837688106240,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2003-03-10 02:13:14',
          balance: '100',
          role: 1
        },
        {
          id: '410000199306095143',
          userName: '小明',
          userID: 21,
          phone: 47684681215463,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2014-12-03 06:14:02',
          balance: '100',
          role: 1
        },
        {
          id: '530000202002128710',
          userName: '小明',
          userID: 20,
          phone: 2862385094739,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2019-12-22 16:42:45',
          balance: '100',
          role: 1
        },
        {
          id: '420000199111133121',
          userName: '小明',
          userID: 53,
          phone: 47730597443241,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1992-04-19 14:55:16',
          balance: '100',
          role: 1
        },
        {
          id: '360000197404185114',
          userName: '小明',
          userID: 47,
          phone: 86551192043058,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2012-04-25 20:32:36',
          balance: '100',
          role: 1
        },
        {
          id: '620000197603288355',
          userName: '小明',
          userID: 41,
          phone: 35406808991366,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2004-02-08 02:20:23',
          balance: '100',
          role: 1
        },
        {
          id: '620000199712126733',
          userName: '小明',
          userID: 49,
          phone: 95606629178443,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2008-07-31 09:32:21',
          balance: '100',
          role: 1
        },
        {
          id: '810000200405161687',
          userName: '小明',
          userID: 3,
          phone: 71896807298727,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2017-01-11 09:21:29',
          balance: '100',
          role: 1
        },
        {
          id: '340000197407046650',
          userName: '小明',
          userID: 4,
          phone: 33449457192113,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2005-11-05 08:20:37',
          balance: '100',
          role: 1
        },
        {
          id: '150000199911295932',
          userName: '小明',
          userID: 35,
          phone: 86360161766601,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2006-10-15 06:41:05',
          balance: '100',
          role: 1
        },
        {
          id: '810000200101047252',
          userName: '小明',
          userID: 15,
          phone: 41287252514114,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2009-08-31 03:11:32',
          balance: '100',
          role: 1
        },
        {
          id: '640000202102027533',
          userName: '小明',
          userID: 29,
          phone: 19338430224451,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1992-08-16 03:14:01',
          balance: '100',
          role: 1
        },
        {
          id: '810000199201055289',
          userName: '小明',
          userID: 3,
          phone: 93836773713069,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1994-07-05 07:03:38',
          balance: '100',
          role: 1
        },
        {
          id: '430000198605048354',
          userName: '小明',
          userID: 35,
          phone: 69679208981684,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1992-11-20 06:44:15',
          balance: '100',
          role: 1
        },
        {
          id: '230000199312040743',
          userName: '小明',
          userID: 22,
          phone: 39510074694868,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1972-11-03 05:08:37',
          balance: '100',
          role: 1
        },
        {
          id: '43000019860621617X',
          userName: '小明',
          userID: 59,
          phone: 96138927767999,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1998-06-21 04:19:10',
          balance: '100',
          role: 1
        },
        {
          id: '210000197101178328',
          userName: '小明',
          userID: 57,
          phone: 7394597974437,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2010-08-31 08:03:16',
          balance: '100',
          role: 1
        },
        {
          id: '650000197211043274',
          userName: '小明',
          userID: 21,
          phone: 23305988627536,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2017-02-06 20:53:53',
          balance: '100',
          role: 1
        },
        {
          id: '540000200901146640',
          userName: '小明',
          userID: 52,
          phone: 99656728804401,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2016-10-16 00:07:58',
          balance: '100',
          role: 1
        },
        {
          id: '640000201208225736',
          userName: '小明',
          userID: 22,
          phone: 8881509549020,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2004-06-29 19:12:47',
          balance: '100',
          role: 1
        },
        {
          id: '320000202008286809',
          userName: '小明',
          userID: 49,
          phone: 81744456497514,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2000-08-17 16:01:16',
          balance: '100',
          role: 1
        },
        {
          id: '460000201401227545',
          userName: '小明',
          userID: 49,
          phone: 18640846358827,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1971-09-14 20:24:34',
          balance: '100',
          role: 1
        },
        {
          id: '710000200609146653',
          userName: '小明',
          userID: 19,
          phone: 36889690941314,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2005-10-13 04:40:13',
          balance: '100',
          role: 1
        },
        {
          id: '810000201404146295',
          userName: '小明',
          userID: 30,
          phone: 14380379539999,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '2018-07-09 19:24:15',
          balance: '100',
          role: 1
        },
        {
          id: '710000201512182011',
          userName: '小明',
          userID: 36,
          phone: 45162045004359,
          userType: '普通用户',
          invitationCode: '10086',
          superiorId: '20',
          regTime: '1972-05-12 03:56:59',
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
    // 删除账号
    stop() {
      this.$confirm('此操作将停止该任务, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          this.$message({
            type: 'success',
            message: '停止成功!'
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
