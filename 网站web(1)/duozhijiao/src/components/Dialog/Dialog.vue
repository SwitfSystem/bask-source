<template>
  <el-dialog
    ref="epower-dialog"
    :visible.sync="visible"
    :title="title"
    :width="width"
    :top="top"
    :custom-class="customClass"
    :close-on-click-modal="closeOnClickModal"
    :show-close="showClose"
    :before-close="close"
    :lock-scroll="lockScroll"
  >
    <component :is="_component" ref="component" />
  </el-dialog>
</template>

<script>
import { Dialog } from 'element-ui'

export default {
  components: {
    [Dialog.name]: Dialog
  },

  props: {
    /* eslint-disable-next-line */
    title: {
      type: String
    },
    customClass: {
      type: String,
      default: 'epower-dialog'
    },
    width: {
      type: String,
      default: '40%'
    },
    top: {
      type: String,
      default: '8vh'
    },
    // 点击遮罩关闭，默认不关闭
    closeOnClickModal: {
      type: Boolean,
      default: false
    },
    redirect: {
      type: Boolean,
      default: true
    },

    showClose: {
      type: Boolean,
      default: true
    },
    /* eslint-disable-next-line */
    beforeClose: {
      type: Function
    },

    lockScroll: {
      type: Boolean,
      default: false
    },

    // 动态component完成自己的任务后是否关闭弹窗，默认为是
    closeAfterDone: {
      type: Boolean,
      default: true
    },

    // 返回jsx的函数
    /* eslint-disable-next-line */
    component: {
      type: Array,
      required: true
    },
    /* eslint-disable-next-line */
    done: {
      type: Function
    }
  },

  data() {
    return {
      visible: true
    }
  },
  computed: {
    _component() {
      // 这一步很重要，让component收集到了这个计算属性的依赖，否则当component变化时不会重新渲染组件
      const fn = this.component

      // 返回vue选项对象
      const that = this
      return {
        name: 'dynamic-wrapper',

        render(h) {
          // fn的运行一定要在render函数中，也是为了挂载依赖
          // 原来的写法有点绕，改一下--
          let vNode = null
          if (typeof fn === 'function') {
            vNode = fn()
          } else if (fn instanceof Array) {
            vNode = h(fn[0], { props: fn[1] || {}})
          } else {
            vNode = h(fn.component, { props: fn.props || {}})
          }
          // 在vNode上手动添加done事件和cancel事件，使弹窗自动关闭
          if (that.closeAfterDone) {
            // 直接写jsx的
            if (!vNode.componentOptions) {
              return vNode
            }

            /* eslint-disable */
            let listeners = vNode.componentOptions.listeners;

            if (!listeners) {
              listeners = {};
              vNode.componentOptions.listeners = listeners;
            }

            // 添加done
            const orginDoneHandler = listeners.done;
            /* eslint-disable func-names */
            listeners.done = function (...p) {
              // 弹框确定之后重新加载页面
              if(that.redirect === true)that.refreshPage(that)
              if (orginDoneHandler) orginDoneHandler(...p);
              that.handleComponentDone(...p);
            };

            // 添加cancel
            const orginCancelHandler = listeners.cancel;
            /* eslint-disable func-names */
            listeners.cancel = function (...p) {
              if (orginCancelHandler) orginCancelHandler(...p);
              that.close();
            };
          }

          return vNode;
        },
      };
    },
  },
watch: {
      $route() {
      setTimeout(() => {
         this.close();
      }, 100)
    }
},

  methods: {
    // 组件处理完自己的事情触发，默认关闭弹窗
    handleComponentDone() {
      if (this.closeAfterDone) this.close();
      if (this.done) this.done();
    },

    handleComponentCancel() {
      this.close();
    },

    close(done) {
      if (this.beforeClose) this.beforeClose();

      // 从handleComponentDone处调用的close没有done参数
      if (done) {
        done();
      } else {
        this.visible = false;
      }

      this.$refs.component.$destroy();
    },

    show() {
      this.visible = true;
    },
  },
};
</script>
