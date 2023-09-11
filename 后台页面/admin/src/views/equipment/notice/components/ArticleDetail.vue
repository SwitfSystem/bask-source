<template>
  <div>
    <el-form
      v-loading="detailLoading"
      :model="form"
      size="mini"
      label-width="100px"
    >
      <div class="createPost-main-container">
        <el-form-item label="标题：">
          <el-input v-model="form.title" class="w300" />
        </el-form-item>
        <!-- <el-form-item label="关键词：">
          <el-input v-model="form.keywords" class="w300" />
        </el-form-item>
        <el-form-item label="描述：">
          <el-input
            v-model="form.description"
            class="w600"
            type="textarea"
            :autosize="{ minRows: 4, maxRows: 4}"
            placeholder="请输入文章概述"
            maxlength="100"
            show-word-limit
          />
        </el-form-item> -->
        <el-form-item prop="content" label="文章内容：">
          <Tinymce ref="editor" v-model="form.content" style="width:96%" />
        </el-form-item>
        <el-form-item label="作者：">
          <el-input v-model="form.author" class="w200" />
        </el-form-item>
        <el-form-item label="发布时间：">
          <el-date-picker v-model="form.published_at" class="w200" type="datetime" format="yyyy-MM-dd HH:mm:ss" value-format="yyyy-MM-dd HH:mm:ss" placeholder="选择日期和时间" />
        </el-form-item>
        <el-form-item label="" class="mt20">
          <el-button type="primary" size="medium" @click="submitForm">{{ isEdit?'立即修改':'立即发布' }}</el-button>
        </el-form-item>
      </div>
    </el-form>
  </div>
</template>

<script>
import Tinymce from '@/components/Tinymce'
import { parseTime } from '@/utils'

const defaultForm = {
  category_id: '', // 分类ID
  title: '', // 文章题目
  keywords: '', // 关键词
  description: '', // 描述
  content: '', // 文章内容
  author: '', // 作者
  source: '', // 来源
  views: '', // 浏览量
  order: 0, // 排序
  enable: true,
  published_at: parseTime(new Date())
}

export default {
  name: 'AdminArticleDetail',
  components: { Tinymce },
  props: {
    isEdit: {
      type: Boolean,
      default: false
    },
    rowId: {
      type: Number,
      default: 0
    }
  },
  data() {
    return {
      typeData: [],
      detailLoading: false,
      form: Object.assign({}, defaultForm)
    }
  },
  created() {
    // this.getClass()
    // if (this.isEdit) {
    //   this.getDetail(this.rowId)
    // }
    // this.detailLoading = false
  },
  methods: {
    // 获取分类
    async getClass() {
      const res = await this.$api.station.getAnnouncementCategories()
      this.typeData = res.data.data
    },
    // 获取文章详情
    getDetail(id) {
      this.$api.station.getAnnouncementDetail(id).then(res => {
        this.detailLoading = false
        this.form = res.data.data
      })
    },
    // 提交表单
    submitForm() {
      if (this.isEdit) {
        this.updateAnnouncements()
      } else {
        this.addAnnouncements()
      }
    },
    // 更新文章
    async updateAnnouncements() {
      const data = this.deepClone(this.form)
      data.published_at = this.form.published_at
      data.enable = data.enable ? 1 : 0
      await this.$api.station.updateAnnouncements(this.rowId, data)
      this.$message.success('文章修改成功')
      this.$emit('done')
    },
    // 新增文章
    async addAnnouncements() {
      const data = this.deepClone(this.form)
      data.published_at = this.form.published_at

      data.enable = data.enable ? 1 : 0

      await this.$api.station.addAnnouncements(data)
      this.$message.success('文章发布成功')
      this.$emit('done')
    }

  }
}
</script>

<style  scoped>
.w300{
  width: 300px;
}
.w400{
  width: 400px;
}
.w600{
  width: 600px;
}
.w200{
  width: 200px;
}
.ml10{
  margin-left: 10px;
}
.w100{
  width: 100px;
}
.createPost-container  .createPost-main-container{
    padding: 22px 35px 0 8px;
  }
</style>
