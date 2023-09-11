<template>
  <div>
    <el-form style="margin-top: 20px">
      <el-form-item>

        <el-button type="primary" @click="showDialog=true">新增素材</el-button>
      </el-form-item>
    </el-form>
    <el-tabs v-model="activeName">
      <el-tab-pane label="文案素材" name="first" >
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
          <el-table-column label="素材内容">
            <template slot-scope="scope">
              <span>{{ scope.row.comment }}</span>
            </template>
          </el-table-column>
          <el-table-column label="更新时间">
            <template slot-scope="scope">
              {{ scope.row.updatetime }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="300">
            <template slot-scope="{ row }">
<!--              <el-button type="text" @click="edit(row)">编辑</el-button>-->
              <el-button type="text" @click="deleteAmount(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>

      </el-tab-pane>
      <el-tab-pane label="账号素材" name="second" >
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
          <el-table-column label="账号信息">
            <template slot-scope="scope">
              <span>{{ scope.row.account }}</span>
            </template>
          </el-table-column>
          <el-table-column label="更新时间">
            <template slot-scope="scope">
              {{ scope.row.updatetime }}
            </template>
          </el-table-column>
          <el-table-column label="操作" width="300">
            <template slot-scope="{ row }">
              <!--              <el-button type="text" @click="edit(row)">编辑</el-button>-->
              <el-button type="text" @click="deleteAmount(row)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>

    <el-dialog
      :title="form.id?'编辑素材':'新增'"
      :visible.sync="showDialog"
      width="32%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="70px">
        <el-form-item label="分类">
          <el-radio-group v-model="form.type">
            <el-radio :key="1" :label="1"> 文案素材 </el-radio>
            <el-radio :key="2" :label="2"> 账号素材 </el-radio>
            <el-radio :key="3" :label="3"> 上传素材 </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item v-if="form.type === 1" label="素材内容">
          <el-input v-model="form.content" style="width: 300px" />
        </el-form-item>
        <el-form-item v-if="form.type === 2" label="账号信息">
          <el-input v-model="form.acc" style="width: 300px" />
        </el-form-item>
        <el-form-item v-if="form.type === 3" label="素材内容">
          <el-upload
            class="avatar-uploader"
            name="uploadfile"
            action="https://api.zgxunge.com:8080/source/upload"
            :show-file-list="false"
            accept=""
            :on-success="handleAvatarSuccess"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" :title="txtName">
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
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
  name: 'MaterialInfoList',
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
      options: [],
      list1: [],
      list2: [],
      listLoading: false,
      filter: {
        value: ''
      },
      form: {
        name: '',
        content: '',
        separate: '&'
      },
      imageUrl: '',
      url:'',
      txtName: ''
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
        this.getMaterialList()
      }
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
              this.list1.push(list[i]);
            }else if(list[i].type == 2){
              this.list2.push(list[i]);
            }
          }
        }else if(data.code == -1){
          this.$router.push({ path: '/login' })
        }
      });
    },
    handleAvatarSuccess(res, file) {
      this.txtName = file.name
      this.imageUrl = URL.createObjectURL(file.raw)
      this.url = res.data;
    },
    handlerDialog(index) {
      switch (index) {
        case 1:
          this.dialogTitle = '账号素材'
          this.showDialog = true
          break
        case 2:
          this.dialogTitle = '直播素材'
          this.showDialog = true
          break
        case 3:
          this.dialogTitle = '评论素材'
          this.showDialog = true
          break
        default:
          break
      }
    },
    // 删除账号
    deleteAmount(row) {
      this.$confirm('此操作将删除该素材, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      })
        .then(() => {
          var params = new URLSearchParams();
          params.append("token",localStorage.getItem('admin_token'));
          params.append("id",row.id);
          this.$axios.post("/source/del",params).then((res) => {
            var data = res.data
            if (data.code == 0){
              this.$message({
                type: 'success',
                message: '删除成功!'
              })
              this.$router.go(0)
            }else if(data.code == -1){
              this.$router.push({ path: '/login' })
            }
          });

        })
        .catch(() => {})
    },
    resend() {
      this.$confirm('此操作将重发该素材, 是否继续?', '提示', {
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
      params.append("type",this.form.type);
      params.append("comment",this.form.content);
      params.append("acc",this.form.acc);
      params.append("url",this.url);
      this.$axios.post("/source/add",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.$router.go(0)
        }else if (data.code == -1){
          this.$router.push({path: '/login'})
        }
      });
    }
  }
}
</script>
<style>
  .avatar-uploader .el-upload {
    border: 1px dashed #d9d9d9;
    border-radius: 6px;
    cursor: pointer;
    position: relative;
    overflow: hidden;
  }
  .avatar-uploader .el-upload:hover {
    border-color: #409EFF;
  }
  .avatar-uploader-icon {
    font-size: 28px;
    color: #8c939d;
    width: 178px;
    height: 178px;
    line-height: 178px;
    text-align: center;
  }
  .avatar {
    width: 178px;
    height: 178px;
    display: block;
  }
</style>
