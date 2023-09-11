<template>
  <div>
    <el-form ref="form2" :model="form2" label-width="120px" style="margin-top:40px">
      <el-form-item label="余额"> 100886 元 </el-form-item>
      <el-form-item label="支付宝名称">
        <el-input v-model="form2.textarea" style="width: 300px" />
      </el-form-item>
      <el-form-item label="支付宝账号">
        <el-input v-model="form2.amount" style="width: 300px" />
      </el-form-item>
      <el-form-item label="提现金额">
        <el-tooltip
          class="item"
          effect="dark"
          content="最小提现金额20元"
          placement="right"
        >
          <el-input v-model="form2.num" style="width: 300px" />
        </el-tooltip>
      </el-form-item>
      <el-form-item label="">
        <el-button type="primary" @click="submit">立即提现</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
export default {
  name: "FinanceWithdrawDetail",
  data() {
    return {
      form2: { num: 20 },
    };
  },
  computed: {},
  watch: {},
  mounted() {},
  created() {
  },
  methods: {
  submit(){

  }
  },
};
</script>

<style scoped></style>
