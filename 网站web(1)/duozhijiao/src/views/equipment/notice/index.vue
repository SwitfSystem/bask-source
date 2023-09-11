<template>
  <div>
    <el-button
      size="small"
      type="primary"
      icon="el-icon-plus"
      @click="handleAdd"
    >新增公告</el-button>
    <el-table :data="list" border fit size="small" style="margin-top: 20px">
      <el-table-column label="ID" width="80">
        <template slot-scope="{ $index }">
          <span>{{ $index + 1 }}</span>
        </template>
      </el-table-column>
      <el-table-column label="标题" width="400">
        <template slot-scope="{ row }">
          {{ row.title }}
        </template>
      </el-table-column>
      <el-table-column label="创建日期">
        <template slot-scope="scope">
          <span>{{ scope.row.published_at }}</span>
        </template>
      </el-table-column>

      <el-table-column label="作者">
        <template slot-scope="scope">
          <span>{{ scope.row.author }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作">
        <template slot-scope="scope">
          <el-button
            type="primary"
            size="mini"
            @click="handleEdit(scope)"
          >编辑</el-button>
          <el-button
            type="danger"
            size="mini"
            @click="handleDelete(scope)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>
    <el-dialog
      title="编辑公告"
      :visible.sync="showDialog"
      width="40%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="用户名">
          {{ form.userName }}
        </el-form-item>
        <el-form-item label="手机型号">
          {{ form.phoneType }}
        </el-form-item>
        <el-form-item label="MAC地址">
          {{ form.mac }}
        </el-form-item>
        <el-form-item label="ip地址">
          {{ form.ip }}
        </el-form-item>
        <el-form-item label="状态">
          <el-tag v-if="form.status === 1" type="success"> 任务中</el-tag>
          <el-tag v-if="form.status === 2" type="info"> 空闲中</el-tag>
          <el-tag v-if="form.status === 3" type="info"> 未在线</el-tag>
          <el-tag v-if="form.status === 4" type="danger"> 异常设备</el-tag>
        </el-form-item>
        <el-form-item label="设备IP数量限制">
          <el-input v-model="form.limitIpNumber" style="width:240px" />
        </el-form-item>
        <el-form-item label="挂机数量限制">
          <el-input v-model="form.hangUpNumber" style="width:240px" />
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
import ArticleDetail from './components/ArticleDetail'
export default {
  name: 'EquipmentNotice',
  data() {
    return {
      typeData: null,
      showDialog: false,
      form: {

      },
      list: [
      ],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        category_id: null,
        page_size: 10
      }
    }
  },
  created() {
    this.getClass()
  },
  methods: {
    search({ page, limit }) {
      this.listQuery.page = page || 1
      // this.$router.push({
      //   name: this.$route.name,
      //   query: this.listQuery
      // })
      // this.getList()
    },
    // 获取分类
    async getClass() {
      const res = await this.$api.station.getAnnouncementCategories()
      this.typeData = res.data.data.map((v) => {
        v.id = v.id.toString()
        return v
      })
      if (this.typeData.length > 0) {
        this.listQuery.category_id = this.typeData[0].id
      }
      // 默认加载全部数据
      this.getList()
    },
    async getList() {
      this.listLoading = true
      const temp = this.deepClone(this.listQuery)
      const res = await this.$api.get('/admin/announcements', temp)
      this.list = res.data.data
      this.total = res.data.meta.pagination.total
      this.listLoading = false
    },
    // 新增文章
    handleAdd() {
      this.$dialog({
        title: '新增公告',
        top: '2vh',
        width: '70%',
        component: [ArticleDetail, {
        }]
      })
    },
    // 编辑文章
    handleEdit({ row }) {
      // this.showDialog = true
      this.$dialog({
        title: '修改公告',
        top: '2vh',
        width: '70%',
        component: [ArticleDetail, {
          rowId: row.id,
          isEdit: true
        }]
      })
    },
    handleDelete({ row }) {
      this.$confirm('确定要删除这个公告吗?', '系统提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(async() => {
        // await this.$api.station.deleteAnnouncements(row.id)
        this.$message({
          type: 'success',
          message: '删除成功!'
        })
        // 刷新列表
        // this.getList()
      })
    }
  }
}
</script>

<style scoped>
.edit-input {
  padding-right: 100px;
}
.cancel-btn {
  position: absolute;
  right: 15px;
  top: 10px;
}
</style>
