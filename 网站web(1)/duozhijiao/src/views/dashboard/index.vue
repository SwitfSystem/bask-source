<template>
  <div>
  <div class="dashboard-container">
    <div class="avatar-wrapper">
      <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar" v-on:click="submit()">
    </div>
    <div class="dashboard-text" style="margin-top: 20px">用户名: {{ info.username }}</div>
    <div class="dashboard-text">账号: {{ info.phone }}</div>
    <div class="dashboard-text">邀请码: {{ info.invitationcode }}</div>
    <div class="dashboard-text">账户余额: {{ info.balance }} 元</div>
    <div v-if="info.activated == 0" class="dashboard-text">设备未激活</div>
    <div v-else class="dashboard-text">设备已激活</div>

    <div style="margin-top: 30px;">
    <a style="color: #6BA2D6" :href="'https://www2.zgxunge.com/base.apk'">点此下载设备程序</a>
    </div>
  </div>
  <el-dialog
    title="编辑"
    :visible.sync="showDialog"
    width="40%"
    @close="showDialog = false"
  >
    <el-form ref="form" :model="info" label-width="120px">
      <el-form-item label="用户名">
        <el-input v-model="info.username" style="width: 100px" />
      </el-form-item>
      <el-form-item label="头像地址">
        <el-input v-model="info.profile_photo" style="width: 100%" />
      </el-form-item>
    </el-form>
    <span slot="footer">
        <el-button @click="showDialog = false">取消</el-button>
        <el-button type="primary" @click="submit1">确定</el-button>
      </span>
  </el-dialog>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'

export default {
  name: 'Dashboard',
  data() {
    return {
      avatar:"",
      showDialog : false,
      info:{},
      form:{}
    }
  },
  created() {
    this.init();
  },
  methods: {
    init() {
      if (localStorage.getItem('token') == null) {
        this.$router.push({path: '/login'})
      } else {
        this.getDeviceInfo()
      }
    },
    getDeviceInfo(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('token'));
      this.$axios.post("/user/info",params).then((res) => {
        var data = res.data
        if (data.code == 0){
          this.info = data.data
          this.avatar = data.data.profile_photo
        }else {
          this.$router.push({ path: '/login' })
        }
      });
    },
    submit(){
      this.showDialog = true
      this.avatar = this.info.profile_photo
    },
    submit1(){
      var params = new URLSearchParams();
      params.append("token",localStorage.getItem('token'));
      params.append("username",this.info.username);
      params.append("photo",this.info.profile_photo);
      this.$axios.post("/user/profile",params).then((res) => {
        var data = res.data
        this.showDialog = false
        if (data.code = 0){
          this.$message.success(data.msg)


        }

      });
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard {
  &-container {
    margin: 30px;
    margin-left: 100px;

    .avatar-wrapper {
      margin-top: 5px;
      position: relative;

      .user-avatar {
        cursor: pointer;
        width: 140px;
        height: 140px;
        border-radius: 10px;
      }
    }

  }

  &-text {
    font-size: 20px;
    line-height: 46px;
  }
}
</style>
