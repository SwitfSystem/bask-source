<template>
  <div>
    <el-form ref="form2" :model="form2" label-width="120px" style="margin-top:40px">
      <el-form-item label="余额"> {{form2.price}} 元 </el-form-item>
      <el-form-item label="支付宝姓名">
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
        form2: { num: 20,price:0 },
      };
    },
    computed: {},
    watch: {},
    mounted() {},
    created() {
      this.init();
    },
    methods: {
      init() {
        if (localStorage.getItem('token') == null){
          this.$router.push({ path: '/login' })
        }else {
          this.getInfo()
        }
      },
      getInfo(){
        var params = new URLSearchParams();
        params.append("token",localStorage.getItem('token'));
        this.$axios.post("/bill/price",params).then((res) => {
          var data = res.data
          if (data.code == 0){
            this.form2.price = data.data
          }else if(data.code == -1){
            this.$router.push({ path: '/login' })
          }
        });
      },
      submit(){
        if (this.form2.price < 20){
          this.$message.error("最小提现金额20元")
        }else {
          var params = new URLSearchParams();
          params.append("token",localStorage.getItem('token'));
          params.append("name",this.form2.textarea)
          params.append("alipay",this.form2.amount)
          params.append("price",this.form2.num)
          this.$axios.post("/bill/exchange",params).then((res) => {
            var data = res.data
            if (data.code == 0){
              this.$message.success(data.msg)
              this.$router.go(0)
            }else{
              this.$message.error(data.msg)
            }
          });
        }
      }
    },
  };
</script>

<style scoped></style>
