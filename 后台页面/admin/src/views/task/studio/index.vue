<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item>
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
            @click="handlerDialog(row,5)"
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
          目前空闲设备数量：{{free}}台</span>
        <span style="color: #666"> 当前在线设备数量：{{total}}台</span>
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
      <el-table-column label="主播平台">
        <template slot-scope="scope">
          <el-tag v-if="scope.row.platform == 0"> {{"抖音" }}</el-tag>
          <el-tag v-else> {{"快手" }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="主播账号">
        <template slot-scope="scope"> {{ scope.row.platformid }} </template>
      </el-table-column>
      <el-table-column label="控制设备数量">
        <template slot-scope="scope"> {{ scope.row.num }}台 </template>
      </el-table-column>
      <el-table-column label="任务时间">
        <template slot-scope="scope"> {{ scope.row.time }}分钟 </template>
      </el-table-column>
      <el-table-column label="是否自动关注">
        <template slot-scope="{ $index, row }">
          <el-tag v-if="row.follow == true" type="success">
            {{"关注" }}</el-tag>
          <el-tag v-else type="info"> {{"不关注" }}</el-tag>
        </template>
      </el-table-column>
      <el-table-column label="任务创建时间">
        <template slot-scope="scope">
          {{ scope.row.starttime }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="380">
        <template slot-scope="{ row }">
          <el-button
            type="text"
            @click="handlerDialog(row,6)"
          >直播间暖场</el-button>
          <el-button type="text" @click="handlerDialog(row,7)">实时互动</el-button>
          <el-button type="text" @click="handlerDialog(row,8)">疯狂点屏</el-button>
          <el-button
            type="text"
            @click="handlerDialog(row,9)"
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
        <el-form-item v-if="dialogType===5" label="平台">
          <el-radio-group v-model="form.type">
            <el-radio :key="0" :label="0"> 抖音 </el-radio>
            <el-radio :key="1" :label="1"> 快手 </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="dialogType===5" label="主播账号">
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
        <el-form-item v-if="dialogType===5" label="控制设备数量">
          <el-input v-model="form.equipment" type="number" style="width: 260px">
            <template slot="append">台</template>
          </el-input>
        </el-form-item>
        <el-form-item v-if="dialogType===5" label="任务时间">
          <el-input v-model="form.time" type="number" style="width: 260px">
            <template slot="append">分钟</template>
          </el-input>
        </el-form-item>
        <el-form-item v-if="dialogType!==9" label="文字素材">
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
                :key="item.id"
                :label="item.comment"
                :value="item.comment"
              />
            </el-select>
          </el-popover>
        </el-form-item>
        <el-form-item v-if="dialogType===6" label="每台设备弹幕延迟时间">
          <el-input v-model="form.time" type="number" style="width: 260px">
            <template slot="append">秒</template>
          </el-input>
        </el-form-item>
        <el-form-item v-if="dialogType===8" label="每台设备点屏延迟时间">
          <el-input v-model="form.time1" type="number" style="width: 260px">
            <template slot="append">秒</template>
          </el-input>
        </el-form-item>
        <el-form-item v-if="dialogType===9" label="自动关注">
          <el-checkbox-group v-model="form.checked">
            <el-checkbox v-for="item in 10" :key="item" :label="item">
              榜{{ item }}
            </el-checkbox>
          </el-checkbox-group>

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
  data() {
    return {
      activeName: 'first',
      showDialog: false,
      dialogTitle: '',
      dialogType: 1,
      free:0,
      total:0,
      row:"",
      options: [],
      list: [],
      listLoading: false,
      filter: {
        value: ''
      },
      materials: [],
      form: {
        material: '',
        payAttention:false,
        time:0,
        time1:0,
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
      }
    }
  },
  created() {
    this.init();
    // this.fetchData();
    // this.listLoading =
  },
  methods: {
    init() {
      if (localStorage.getItem('admin_token') == null) {
        this.$router.push({path: '/login'})
      } else {
        this.getTaskList()
        this.getMaterialList()
      }
    },
    getTaskList(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      this.$axios.post("/task/roomflush",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.free = data.data.free
          this.total = data.data.total
          let list = data.data.list
          for (var key in list) {
            var obj = JSON.parse(key)
            obj['num'] = list[key];
            this.list.push(obj);
          }
        }else if (data.code == -1){
          this.$router.push({path: '/login'})
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
          this.materials = data.data.list
        }
      });
    },
    handlerDialog(row,index) {
      this.row = row;
      this.dialogType = index
      switch (index) {
        case 5:
          this.dialogTitle = '直播间超级热度'
          break
        case 6:
          this.dialogTitle = '直播间暖场'
          break
        case 7:
          this.dialogTitle = '实时互动'
          break
        case 8:
          this.dialogTitle = '疯狂点屏'
          break
        case 9:
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
    },
    okBtn(){
        this.showDialog = false;
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('admin_token'));
        params.append("code",this.dialogType);
        if (this.dialogType == 5){
          params.append("platform",this.form.type);
          params.append("content",this.form.material);
          params.append("num",this.form.equipment);
          params.append("time",this.form.time);
          params.append("method",0);
          params.append("platformid",this.form.amount);
          params.append("isfollow",this.form.payAttention);
          this.$axios.post("/task/execute",params).then((res) => {
            var data = res.data
            if (data.code == 0){
              this.$message.success(data.msg)
              this.$router.go(0)
            }else {
              this.$message.error(data.msg)
            }
          });
        }else {
          params.append("platformid",this.row.platformid);
          if (this.dialogType == 9){
            params.append("content",JSON.stringify(this.form.checked));

          }else {
            params.append("content",this.form.material);
          }

          if (this.dialogType == 8){
            params.append("delay",this.form.time1);
          }else{
            params.append("delay",this.form.time);
          }
          this.$axios.post("/task/executeroom",params).then((res) => {
            var data = res.data
            if (data.code == 0){
              this.$message.success(data.msg)
            }else {
              this.$message.error(data.msg)
            }
          });
        }

    }
  }
}
</script>
