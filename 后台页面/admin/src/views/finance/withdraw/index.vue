<template>
  <div>
    <div>
    <el-form style="margin-top: 20px;display: inline-block;" :inline="true">
      <el-form-item>
        <el-input
          v-model="listQuery.value"
          style="width: 200px; margin-right: 20px"
          placeholder="输入手机号搜索"
          clearable
        />
      </el-form-item>
      <el-button type="primary" @click="seach()">搜索</el-button>
    </el-form>
      <el-button style="display: inline-block;margin-left: 70px" type="info" @click="alls()">批量审核</el-button>
      <el-button style="display: inline-block;margin-left: 10px" type="info" @click="daochu()">导出数据</el-button>
    </div>

    <el-tabs v-model="activeName">
      <el-tab-pane label="待审核" name="first" >
        <el-table @selection-change="handleSelectionChange" ref="table" :data="list1" border fit size="small">
          <el-table-column
            type="selection"
            header-align="center"
            align="center"
            width="50">
          </el-table-column>
          <el-table-column label="用户名">
            <template slot-scope="{ row }">
              {{ row.username }}
            </template>
          </el-table-column>
          <el-table-column label="手机号" prop="phone" />
          <el-table-column label="提现余额" prop="money" />
          <el-table-column label="当时账户余额" prop="balance" />
          <el-table-column label="提现时间" prop="time" />
          <el-table-column label="支付宝姓名" prop="alipay_name" />
          <el-table-column label="支付宝账号" prop="alipay" />
          <el-table-column label="状态">
            <template slot-scope="{ row }">
              <el-tag type="info">待审核</el-tag>
            </template>
          </el-table-column>
          <el-table-column label="操作" width="300">
            <template slot-scope="{ row }">
              <el-button type="text" @click="edit(row)" >审核</el-button>
            </template>
          </el-table-column>
        </el-table>

      </el-tab-pane>
      <el-tab-pane label="已通过" name="second" >
        <el-table ref="table" :data="list2" border fit size="small">
          <el-table-column label="用户名">
            <template slot-scope="{ row }">
              {{ row.username }}
            </template>
          </el-table-column>
          <el-table-column label="手机号" prop="phone" />
          <el-table-column label="提现余额" prop="money" />
          <el-table-column label="当时账户余额" prop="balance" />
          <el-table-column label="提现时间" prop="time" />
          <el-table-column label="支付宝姓名" prop="alipay_name" />
          <el-table-column label="支付宝账号" prop="alipay" />
          <el-table-column label="状态">
            <template slot-scope="{ row }">
              <el-tag type="success">已通过</el-tag>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
      <el-tab-pane label="已驳回" name="three" >
        <el-table ref="table" :data="list3" border fit size="small">
          <el-table-column label="用户名">
            <template slot-scope="{ row }">
              {{ row.username }}
            </template>
          </el-table-column>
          <el-table-column label="手机号" prop="phone" />
          <el-table-column label="提现余额" prop="money" />
          <el-table-column label="当时账户余额" prop="balance" />
          <el-table-column label="提现时间" prop="time" />
          <el-table-column label="支付宝姓名" prop="alipay_name" />
          <el-table-column label="支付宝账号" prop="alipay" />
          <el-table-column label="状态">
            <template slot-scope="{ row }">
              <el-tag type="danger">已驳回</el-tag>
              <el-button
                type="text"
                style="margin-left: 10px"
                @click="look(row)"
              >查看原因</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-tab-pane>
    </el-tabs>
    <el-dialog
      title="编辑"
      :visible.sync="showDialog"
      width="40%"
      @close="showDialog = false"
    >
      <el-form ref="form" :model="form" label-width="120px">
        <el-form-item label="审核状态">
          <el-radio-group v-model="form.status2">
            <el-radio :key="1" :label="1"> 审核通过 </el-radio>
            <el-radio :key="-1" :label="-1"> 审核不通过 </el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="原因">
          <el-input
            v-model="form.textarea"
            type="textarea"
            :rows="2"
            placeholder="请输入驳回理由"
            style="width: 300px"
          />
        </el-form-item>
      </el-form>
      <span slot="footer">
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="editBtn()">确定</el-button>
      </span>
    </el-dialog>
  </div>
</template>

<script>

import XLSX from "xlsx";

export default {
  name: "FinanceWithdraw",

  data() {
    return {
      activeName: "first",
      types: null,
      services: null,
      showDialog: false,
      form: {},
      form1:{},
      index:1,
      selectRows:[],
      list1: [],
      list2: [],
      list3: [],
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        page_size: 20,
        type: "",
        service_type: "",
        start_at: "",
        end_at: "",
        user_id: "",
      },
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  created() {
    this.getList();
  },
  methods: {
    look(row) {
      this.$alert(`用户: ${row.username} ${row.reason}`, '查看原因', {
        confirmButtonText: '确定',
        type: 'warning'
      })
    },
    async getList() {
      this.listLoading = true
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      this.$axios.post("/bill/list",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          let list = data.data.list;
          for (var i = 0; i < list.length; i++) {
            if (list[i].status == 0){
              this.list1.push(list[i]);
            }else if(list[i].status == 1){
              this.list2.push(list[i]);
            }else {
              this.list3.push(list[i]);
            }
          }
          this.listLoading = false
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    edit(row) {
      this.index = 1;
      this.showDialog = true;
      this.form1 = row;
    },
    editBtn(){
      this.showDialog = false
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('admin_token'));
      params.append("status",this.form.status2);
      params.append("reason",this.form.textarea);
      if (this.index == 1){
        params.append("id",this.form1.id);
        this.$axios.post("/bill/update",params).then((res) => {
          var data = res.data
          this.$message.error(data.msg);
          this.$router.go(0)
        });
      }else if (this.index == 2){
        params.append("data",JSON.stringify(this.selectRows))
        this.$axios.post("/bill/allupdate",params).then((res) => {
          var data = res.data
          this.$message.error(data.msg);
          this.$router.go(0)
        });
      }

    },
    seach(){
      if (this.listQuery.value != ""){
        this.listLoading = true
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('admin_token'));
        params.append("name","phone");
        params.append("type",3);
        params.append("words",this.listQuery.value);
        params.append("status",0);
        this.$axios.post("/search",params).then((res) => {
          var data = res.data
          if (data.code == 0){
            let list = data.data;
            for (var i = 0; i < list.length; i++) {
              if (list[i].status == 0){
                this.list1 = []
                this.list1.push(list[i]);
              }else if(list[i].status == 1){
                this.list2 = []
                this.list2.push(list[i]);
              }else {
                this.list3 = []
                this.list3.push(list[i]);
              }
            }
            this.listLoading = false
          }else {
            this.$message.success(data.msg);
          }
        });
      }else {
        this.$router.go(0)
      }

    },
    daochu(){
      let tableData = [
        ['序号 (必填)','收款方支付宝账号 (必填)','收款方姓名 (必填)','金额 (必填，单位：元)','备注 (选填)']//导出表头
      ] // 表格表头
      this.list1.forEach ((item,index)=> {
        let rowData = []
        //导出内容的字段
        rowData = [
          index+1,
          item.alipay,
          item.alipay_name,
          item.money,
        ]
        tableData.push(rowData)
      })
      let ws = XLSX.utils.aoa_to_sheet(tableData)
      let wb = XLSX.utils.book_new()
      XLSX.utils.book_append_sheet(wb, ws, '支付记录') // 工作簿名称
      XLSX.writeFile(wb, '支付记录.xlsx') // 保存的文件名
    },
    alls(){
      if (this.selectRows.length >0){
        this.index = 2;
        this.showDialog = true;
      }else {
        this.$message.error("请先选择需要操作的订单")
      }

    },
    handleSelectionChange(val){
      this.selectRows=val;

    }
  },
};
</script>

<style scoped></style>
