<template>
  <div class="dashboard-container">
    <component :is="currentRole" />
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import adminDashboard from './admin'
import editorDashboard from './editor'

export default {
  name: 'Dashboard',
  components: { adminDashboard, editorDashboard },
  data() {
    return {
      currentRole: 'adminDashboard'
    }
  },
  computed: {
    ...mapGetters([
      'roles'
    ])
  },
  created() {
    this.init();
    if (!this.roles.includes('admin')) {
      this.currentRole = 'editorDashboard'
    }
  }, methods: {
    init() {
      if (localStorage.getItem('admin_token') == null){
        this.$router.push({ path: '/login' })
      }else {
        this.$router.push({ path: '/user' })
      }
    }
  }
}
</script>
