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
        <el-button type="primary" @click="showDialog=true">新增素材</el-button>
      </el-form-item>
    </el-form>
    <el-tabs v-model="activeName">
      <el-tab-pane label="文案素材" name="first" />
      <el-tab-pane label="账号素材" name="second" />
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
      <el-table-column label="素材名称">
        <template slot-scope="scope">
          {{ scope.row.name }}
        </template>
      </el-table-column>
      <el-table-column label="素材内容">
        <template slot-scope="scope">
          <span>{{ scope.row.comment }}</span>
        </template>
      </el-table-column>
      <el-table-column label="分隔符">
        <template slot-scope="scope">
          {{ scope.row.separate || "&" }}
        </template>
      </el-table-column>
      <el-table-column label="更新时间">
        <template slot-scope="scope">
          {{ scope.row.updatetime }}
        </template>
      </el-table-column>
      <el-table-column label="操作" width="300">
        <template slot-scope="">
          <el-button type="text" @click="edit(row)">编辑</el-button>
          <el-button type="text" @click="deleteAmount(row)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      :title="form.id?'编辑素材':'新增'"
      :visible.sync="showDialog"
      width="32%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="分类">
          <el-radio-group v-model="form.type">
            <el-radio :key="1" :label="1"> 文案素材 </el-radio>
            <el-radio :key="2" :label="2"> 账号素材 </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="素材名称">
          <el-input v-model="form.separateName" style="width: 300px" />
        </el-form-item>
        <el-form-item label="分隔符">
          <el-input v-model="form.separate" style="width: 300px" />
        </el-form-item>
        <el-form-item label="素材内容">
          <el-upload
            class="avatar-uploader"
            action="https://jsonplaceholder.typicode.com/posts/"
            :show-file-list="false"
            accept=".doc,.docx,.txt"
            :on-success="handleAvatarSuccess"
            :before-upload="beforeAvatarUpload"
          >
            <img v-if="imageUrl" :src="imageUrl" class="avatar" :title="txtName">
            <i v-else class="el-icon-plus avatar-uploader-icon" />
          </el-upload>
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
      list: [],
      listLoading: false,
      filter: {
        value: ''
      },
      form: {
        name: '',
        content: '',
        separate: '&'
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
      ],
      imageUrl: '',
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
      if (localStorage.getItem('token') == null) {
        this.$router.push({path: '/login'})
      } else {
        this.getMaterial()
      }
    },
    getMaterial(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('token'));
      params.append("type","doc")
      this.$axios.post("/source/get",params).then((res) => {
        var data = res.data
        console.log(data)
        if (data.code == 0){
          this.list = data.data.list
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    handleAvatarSuccess(res, file) {
      console.log(res, file)
      this.txtName = file.name
      this.imageUrl = URL.createObjectURL(file.raw)
    },
    beforeAvatarUpload(file) {
      // const isJPG = file.type === 'image/jpeg'
      // const isLt2M = file.size / 1024 / 1024 < 2

      // if (!isJPG) {
      //   this.$message.error('上传头像图片只能是 JPG 格式!')
      // }
      // if (!isLt2M) {
      //   this.$message.error('上传头像图片大小不能超过 2MB!')
      // }
      // return isJPG && isLt2M
      return true
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
    deleteAmount() {
      this.$confirm('此操作将删除该素材, 是否继续?', '提示', {
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
