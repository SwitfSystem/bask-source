<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item>

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
          >视频评论</el-button>
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
          >QQ群营销</el-button>
        </el-popover>
        <el-popover
          placement="top-start"
          width="200"
          trigger="hover"
          content="咸鱼任务。"
        >
          <el-button
            slot="reference"
            type="primary"
            style="margin-right: 20px"
            @click="handlerDialog(9)"
          >咸鱼任务</el-button>
        </el-popover>
        <span style="color: #666; margin-right: 20px">
          目前空闲设备数量：{{free}}台</span>
        <span style="color: #666"> 当前在线设备数量：{{total}}台</span>
      </el-form-item>
    </el-form>
    <el-tabs v-model="activeName">
      <el-tab-pane label="抖音" name="first" >
        <el-table
          v-loading="listLoading"
          :data="dylist"
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
          <el-table-column label="手机号">
            <template slot-scope="scope">
              <span>{{ scope.row.phone }}</span>
            </template>
          </el-table-column>
          <el-table-column label="私信类型">
            <template slot-scope="{ $index, row }">
              <el-tag v-if="row.code ===1" type="info">{{ "账号私信" }}</el-tag>
              <el-tag v-if="row.code ===2" type="info">{{ "直播私信" }}</el-tag>
              <el-tag v-if="row.code ===3" type="info">{{ "评论私信" }}</el-tag>
              <el-tag v-if="row.code ===4" type="info">{{ "直播间私信(账号:"+row.platformid+")" }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="发送时间">
            <template slot-scope="scope">
              {{ scope.row.starttime }}
            </template>
          </el-table-column>
          <el-table-column label="私信内容">
            <template slot-scope="scope">
              {{ scope.row.content }}
            </template>
          </el-table-column>
          <el-table-column label="任务状态">
            <template slot-scope="{ $index, row }">
              <el-tag v-if="$index===0" type="info">
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

        </el-table>
      </el-tab-pane>
      <el-tab-pane label="快手" name="second" >
        <el-table
          v-loading="listLoading"
          :data="kslist"
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
          <el-table-column label="手机号">
            <template slot-scope="scope">
              <span>{{ scope.row.phone }}</span>
            </template>
          </el-table-column>
          <el-table-column label="私信类型">
            <template slot-scope="{ $index, row }">
              <el-tag v-if="row.code ===1" type="info">{{ "账号私信" }}</el-tag>
              <el-tag v-if="row.code ===2" type="info">{{ "直播私信" }}</el-tag>
              <el-tag v-if="row.code ===3" type="info">{{ "视频评论" }}</el-tag>
              <el-tag v-if="row.code ===4" type="info">{{ "QQ群营销(账号:"+row.platformid+")" }}</el-tag>
              <el-tag v-if="row.code ===5" type="info">{{ "咸鱼任务" }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="发送时间">
            <template slot-scope="scope">
              {{ scope.row.starttime }}
            </template>
          </el-table-column>
          <el-table-column label="私信内容">
            <template slot-scope="scope">
              {{ scope.row.content }}
            </template>
          </el-table-column>
          <el-table-column label="任务状态">
            <template slot-scope="{ $index, row }">
              <el-tag v-if="$index===0" type="info">
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

        </el-table>
      </el-tab-pane>
      <el-tab-pane label="咸鱼" name="thire" >
        <el-table
          v-loading="listLoading"
          :data="xylist"
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
          <el-table-column label="手机号">
            <template slot-scope="scope">
              <span>{{ scope.row.phone }}</span>
            </template>
          </el-table-column>
          <el-table-column label="私信类型">
            <template slot-scope="{ $index, row }">
              <el-tag type="info">{{ "咸鱼任务" }}</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="发送时间">
            <template slot-scope="scope">
              {{ scope.row.starttime }}
            </template>
          </el-table-column>
          <el-table-column label="私信内容">
            <template slot-scope="scope">
              {{ scope.row.content }}
            </template>
          </el-table-column>
          <el-table-column label="任务状态">
            <template slot-scope="{ $index, row }">
              <el-tag v-if="$index===0" type="info">
                {{ row.status || "执行中" }}</el-tag>
              <el-tag v-else-if="$index===2" type="success"> {{ row.status || "已完成" }}</el-tag>
              <el-tag v-else type="danger"> {{ row.status || "已停止" }}</el-tag>
            </template>
          </el-table-column>

        </el-table>
      </el-tab-pane>
    </el-tabs>
    <el-dialog
      :title="`编辑${dialogTitle}`"
      :visible.sync="showDialog"
      width="36%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="150px">
        <el-form-item v-if="dialogType!==9 &&dialogType!==4" label="平台">
          <el-radio-group v-model="form.type">
            <el-radio :key="0" :label="0"> 抖音 </el-radio>
            <el-radio :key="1" :label="1"> 快手 </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="dialogType===4" label="QQ群号">
          <el-input v-model="form.amount" type="text" placeholder="请输入QQ群号" style="width: 260px;margin-right:10px" />
        </el-form-item>
        <el-form-item label="控制设备数量">
          <!-- <el-input v-model="form.equipment" type="number" style="width: 300px">
            <template slot="append">台</template>
          </el-input> -->
          <el-input v-model="form.equipment" type="number" style="width: 260px;margin-right:10px">
            <template slot="append">台</template>
          </el-input>
          <el-checkbox v-if="dialogType!==2 && dialogType!==3 && dialogType!==4" v-model="form.assignPrivate" label="1">分配私信</el-checkbox>
        </el-form-item>
        <!-- <el-form-item label="设备分配私信">
          <el-radio-group v-model="form.type3">
            <el-radio :key="1" :label="1"> 分配 </el-radio>
            <el-radio :key="2" :label="2"> 不分配 </el-radio>
          </el-radio-group>
        </el-form-item> -->
        <el-form-item v-if="dialogType === 3" label="每台设备评论个数">
          <el-tooltip class="item" effect="dark" content="到达数量自动停止任务" placement="top-start">
            <el-input v-model="form.number2" type="number" style="width: 260px">
              <template slot="append">条</template>
            </el-input>
          </el-tooltip>
        </el-form-item>
        <el-form-item v-else-if="dialogType === 4" label="每台设备发言个数">
          <el-tooltip class="item" effect="dark" content="到达数量自动停止任务" placement="top-start">
            <el-input v-model="form.number2" type="number" style="width: 260px">
              <template slot="append">条</template>
            </el-input>
          </el-tooltip>
        </el-form-item>
        <el-form-item v-else label="每台设备私信个数">
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
        <el-form-item v-if="dialogType==1" label="启用素材">
          <el-select
            v-model="form.material"
            clearable
            filterable
            style="width: 260px"
          >
            <el-option
              v-for="item in materialsacc"
              :key="item.id"
              :label="item.account"
              :value="item.account"
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
              :key="item.id"
              :label="item.comment"
              :value="item.comment"
            />
          </el-select>
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
      free:0,
      total:0,
      options: [],
      list: [],
      dylist:[],
      kslist:[],
      xylist:[],
      listLoading: false,
      filter: {
        value: ''
      },
      materials: [],
      materialsacc: [],
      form: {
        material: '',
        name: '',
        region: '',
        date1: '',
        date2: '',
        payAttention:false,
        delivery: false,
        type: [],
        resource: '',
        desc: '',
        role: '',
        type2: '',
        type3: ''
      },
    }
  },
  created() {
    this.init();
    // this.fetchData();
    // this.listLoading =
  },
  methods: {
    init() {
      if (localStorage.getItem('admin_token') == null){
        this.$router.push({ path: '/login' })
      }else {
        this.getTaskList()
        this.getMaterialList()
      }
    },
    getTaskList(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      this.$axios.post("/task/flush",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.free = data.data.free
          this.total = data.data.total
          let list = data.data.list
          for (var i = 0; i < list.length; i++) {
            if (list[i].platform == 0){
              this.dylist.push(list[i]);
            }else if(list[i].platform == 1){
              this.kslist.push(list[i]);
            }else {
              this.xylist.push(list[i]);
            }
          }
        }else if(data.code == -1){
          this.$router.push({ path: '/login' })
        }
      });
    },
    getMaterialList(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("type","doc");
      this.$axios.post("/source/get",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          let list = data.data.list;
          for (var i = 0; i < list.length; i++) {
            if (list[i].type == 1){
              this.materials.push(list[i]);
            }else if(list[i].type == 2){
              this.materialsacc.push(list[i]);
            }
          }
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
          this.dialogTitle = '视频评论'
          this.dialogType = 3
          this.showDialog = true
          break
        case 4:
          this.dialogTitle = 'QQ群营销'
          this.dialogType = 4
          this.showDialog = true
          break
        case 9 :
          this.dialogTitle = '咸鱼任务'
          this.dialogType = 9
          this.showDialog = true
          break
        default:
          break
      }
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
    },
    okBtn(){
        this.showDialog = false;
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('admin_token'));
        params.append("code",this.dialogType);
        params.append("platform",this.form.type);
        params.append("content",this.form.material2);
        params.append("num",this.form.equipment);
        params.append("method",0);
        if (this.dialogType == 1){
          params.append("user",this.form.material)
        }
        if (this.dialogType == 4){
          params.append("platformid",this.form.amount);

        }
      params.append("talknum",this.form.number2);
        this.$axios.post("/task/execute",params).then((res) => {
          var data = res.data
          if (data.code == 0){

            this.$message.success(data.msg)
            this.$router.go(0)
          }else {
            this.$message.error(data.msg)
          }
        });

    }
  }
}
</script>
