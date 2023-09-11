<template>
  <div class="upload-container">
    <el-button
      :style="{ background: color, borderColor: color }"
      icon="el-icon-upload"
      type="primary"
      size="mini"
      @click="dialogVisible = true"
    >点击上传</el-button>
    <el-dialog
      :visible.sync="dialogVisible"
      :modal="false"
      width="600px"
      top="22vh"
    >
      <el-upload
        :multiple="true"
        :file-list="fileList"
        :show-file-list="true"
        :on-remove="handleRemove"
        :on-success="handleSuccess"
        :before-upload="beforeUpload"
        class="editor-slide-upload"
        :action="actionUrl"
        :name="fileName"
        list-type="picture-card"
      >
        <el-button type="primary" size="mini">点击上传</el-button>
      </el-upload>
      <el-button @click="dialogVisible = false">取消</el-button>
      <el-button type="primary" @click="handleSubmit">确定</el-button>
    </el-dialog>
  </div>
</template>

<script>
// import api from '@/api/api'
const api = {}
export default {
  name: 'EditorSlideUpload',
  props: {
    upload: {
      type: String,
      default: process.env.VUE_APP_BASE_API + api.fileUpload
    },
    // 参数name 默认attachment
    fileName: {
      type: String,
      default: 'attachment'
    },
    color: {
      type: String,
      default: '#1890ff'
    }
  },
  data() {
    return {
      dialogVisible: false,
      listObj: {},
      fileList: [],
      fileSrcList: []
    }
  },

  computed: {
    actionUrl() {
      // var date = new Date()
      // var timer = date.getTime().toString()
      return this.upload
    }
  },
  methods: {
    checkAllSuccess() {
      return Object.keys(this.listObj).every(
        (item) => this.listObj[item].hasSuccess
      )
    },
    handleSubmit() {
      this.$emit('successCBK', this.fileSrcList)
      this.listObj = {}
      this.fileList = []
      this.fileSrcList = []
      this.dialogVisible = false
    },
    handleSuccess(res, file) {
      this.fileSrcList.push(res.data.data)
    },
    handleRemove(file) {
      const uid = file.uid
      const objKeyArr = Object.keys(this.listObj)
      for (let i = 0, len = objKeyArr.length; i < len; i++) {
        if (this.listObj[objKeyArr[i]].uid === uid) {
          delete this.listObj[objKeyArr[i]]
          return
        }
      }
    },
    beforeUpload(file) {
      const _self = this
      const _URL = window.URL || window.webkitURL
      const fileName = file.uid
      this.listObj[fileName] = {}
      return new Promise((resolve, reject) => {
        const img = new Image()
        img.src = _URL.createObjectURL(file)
        img.onload = function() {
          _self.listObj[fileName] = {
            hasSuccess: false,
            uid: file.uid,
            width: this.width,
            height: this.height
          }
        }
        resolve(true)
      })
    }
  }
}
</script>

<style scoped>
.editor-slide-upload {
  margin-bottom: 20px;
}
.editor-slide-upload /deep/ .el-upload--picture-card {
  width: 100%;
}
</style>
