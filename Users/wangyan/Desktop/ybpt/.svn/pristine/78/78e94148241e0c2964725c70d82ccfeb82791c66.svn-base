<template>
  <div class="login-container">
    <el-row>
      <el-col :xs="24" :sm="24" :md="12" :lg="16" :xl="16">
        <div style="color: transparent">占位符</div>
      </el-col>
      <el-col :xs="24" :sm="24" :md="12" :lg="7" :xl="7">
        <el-form
          ref="form"
          :model="form"
          :rules="rules"
          class="login-form"
          label-position="left"
        >
          <div class="title">Hello !</div>
          <div class="title-tips">欢迎来到{{ title }}！</div>
          <div v-if="form.type == '1'">
            <el-form-item style="margin-top: 40px" prop="username">
              <span class="svg-container svg-container-admin">
                <vab-icon :icon="['fas', 'user']" />
              </span>
              <el-input
                v-model.trim="form.username"
                v-focus
                placeholder="请输入用户名"
                tabindex="1"
                type="text"
              />
            </el-form-item>
            <el-form-item prop="password">
              <span class="svg-container">
                <vab-icon :icon="['fas', 'lock']" />
              </span>
              <el-input
                :key="passwordType"
                ref="password"
                v-model.trim="form.password"
                :type="passwordType"
                tabindex="2"
                placeholder="请输入密码"
                @keyup.enter.native="handleLogin"
              />
              <span
                v-if="passwordType === 'password'"
                class="show-password"
                @click="handlePassword"
              >
                <vab-icon :icon="['fas', 'eye-slash']"></vab-icon>
              </span>
              <span v-else class="show-password" @click="handlePassword">
                <vab-icon :icon="['fas', 'eye']"></vab-icon>
              </span>
            </el-form-item>
            <el-form-item prop="captcha">
              <span class="svg-container">
                <vab-icon :icon="['fas', 'shield-alt']" />
              </span>
              <el-input
                v-model.trim="form.captcha"
                placeholder="请输入验证码"
                tabindex="3"
                @keyup.enter.native="handleLogin"
              />
              <div class="captcha-container" @click="refreshCaptcha">
                <div class="captcha-text">
                  <span
                    v-for="(char, index) in captchaText"
                    :key="index"
                    :style="{ color: getRandomColor() }"
                  >
                    {{ char }}
                  </span>
                </div>
              </div>
            </el-form-item>

            <el-button
              :loading="loading"
              class="login-btn"
              type="primary"
              size="medium"
              @click="handleLogin"
            >
              登录
            </el-button>
          </div>
          <!-- <router-link to="/register">
            <div style="margin-top: 20px">注册</div>
          </router-link> -->
          <div class="horizontal-text-carousel">
            <el-carousel
              :interval="3000"
              :autoplay="true"
              :loop="true"
              direction="horizontal"
              indicator-position="none"
              arrow="never"
              height="40px"
            >
              <el-carousel-item v-for="(text, index) in texts" :key="index">
                <div class="carousel-text">{{ text }}</div>
              </el-carousel-item>
            </el-carousel>
          </div>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
  import { isPassword } from '@/utils/validate'
  import md5 from 'js-md5'
  import { getCaptcha } from '@/api/user'
  export default {
    name: 'Login',
    directives: {
      focus: {
        inserted(el) {
          el.querySelector('input').focus()
        },
      },
    },
    data() {
      const validateusername = (rule, value, callback) => {
        if ('' == value) {
          callback(new Error('用户名不能为空'))
        } else {
          callback()
        }
      }
      const validatePassword = (rule, value, callback) => {
        if (!isPassword(value)) {
          callback(new Error('密码不能少于6位'))
        } else {
          callback()
        }
      }
      return {
        nodeEnv: process.env.NODE_ENV,
        title: this.$baseTitle,
        count: 0,
        form: {
          username: '',
          password: '',
          cardinfo: '',
          type: '1',
        },
        rules: {
          username: [
            {
              required: true,
              trigger: 'blur',
              validator: validateusername,
            },
          ],
          password: [
            {
              required: true,
              trigger: 'blur',
              validator: validatePassword,
            },
          ],
          captcha: [
            {
              required: true,
              trigger: 'blur',
              message: '请输入验证码',
            },
          ],
        },
        loading: false,
        passwordType: 'password',
        redirect: undefined,
        texts: [
          '保密重要，切勿泄露信息',
          '保密责任人人担，信息安全日日安！',
          '数据无小事，泄密即事故！',
        ], // 轮播文字
        captchaText: '',
        sessionId: '',
      }
    },
    watch: {
      $route: {
        handler(route) {
          this.redirect = (route.query && route.query.redirect) || '/'
        },
        immediate: true,
      },
    },
    created() {
      document.body.style.overflow = 'hidden'
    },
    beforeDestroy() {
      document.body.style.overflow = 'auto'
    },
    mounted() {
      window.addEventListener('message', this.handleMessage)
      this.refreshCaptcha() // 新增
    },

    methods: {
      handlePassword() {
        this.passwordType === 'password'
          ? (this.passwordType = '')
          : (this.passwordType = 'password')
        this.$nextTick(() => {
          this.$refs.password.focus()
        })
      },
      handleMessage(e) {
        var that = this
        if ('portal' == e.data.type) {
          var cardinfo = encodeURIComponent(e.data.cardInfo)
          if (cardinfo) {
            that.form.type = '2'
            that.form.cardinfo = cardinfo
            that.handleLogin_auto()
          } else {
            that.form.type = '1'
          }
        }
      },
      async refreshCaptcha() {
        try {
          const res = await getCaptcha()
          this.captchaText = res.data.code
          this.sessionId = res.data.sessionId
        } catch (error) {
          console.error('获取验证码失败:', error)
        }
      },
      handleLogin_auto() {
        this.loading = true
        var that = this
        if (this.count != 0) {
          return
        }
        localStorage.setItem('update_pwd_auto', 'auto_login')
        this.count++
        this.$store
          .dispatch('user/login', this.form)
          .then((res) => {
            // const routerPath =
            //     this.redirect === '/404' || this.redirect === '/401'
            //         ? '/'
            //         : this.redirect
            that.$router.push('/index').catch(() => {})
            this.loading = false
            window.removeEventListener('message', that.handleMessage)
          })
          .catch(() => {
            this.loading = false
          })
      },
      handleLogin() {
        this.form.type = '1'
        this.$refs.form.validate((valid) => {
          if (valid) {
            localStorage.setItem('update_pwd', this.form.password)
            this.loading = true
            // this.form.password = md5(this.form.password)
            // this.form.sessionId = this.sessionId
            const loginData = {
              ...this.form,
              password: md5(this.form.password),
              sessionId: this.sessionId,
            }
            this.$store
              .dispatch('user/login', loginData)
              .then((res) => {
                const routerPath =
                  this.redirect === '/404' || this.redirect === '/401'
                    ? '/'
                    : this.redirect
                this.$router.push('/index').catch(() => {})
                this.loading = false
              })
              .catch(() => {
                this.loading = false
              })
          } else {
            return false
          }
        })
      },
      getRandomColor() {
        // 按色系分组，确保颜色协调
        const colorGroups = {
          warm: ['#8B4513', '#8B0000', '#8B7500', '#8B4789', '#8B5A00'],
          cool: ['#2F4F4F', '#00688B', '#2E8B57', '#483D8B', '#528B8B'],
          neutral: ['#696969', '#5C5C5C', '#4A4A4A', '#3D3D3D'],
        }

        const groups = Object.values(colorGroups)
        const randomGroup = groups[Math.floor(Math.random() * groups.length)]
        return randomGroup[Math.floor(Math.random() * randomGroup.length)]
      },
    },
  }
</script>

<style lang="scss" scoped>
  .login-container {
    height: 100vh;
    background: url('~@/assets/login_images/background.jpg') center center fixed
      no-repeat;
    background-size: cover;

    .title {
      font-size: 54px;
      font-weight: 500;
      color: rgba(14, 18, 26, 1);
    }

    .title-tips {
      margin-top: 29px;
      font-size: 26px;
      font-weight: 400;
      color: rgba(14, 18, 26, 1);
      text-overflow: ellipsis;
      white-space: nowrap;
    }

    .login-btn {
      display: inherit;
      width: 220px;
      height: 60px;
      margin-top: 5px;
      border: 0;

      &:hover {
        opacity: 0.9;
      }
    }

    .login-form {
      position: relative;
      max-width: 100%;
      margin: calc((100vh - 425px) / 2) 20% 10% 0;
      // overflow: hidden;

      .forget-password {
        width: 100%;
        margin-top: 40px;
        text-align: left;

        .forget-pass {
          width: 129px;
          height: 19px;
          font-size: 20px;
          font-weight: 400;
          color: rgba(92, 102, 240, 1);
        }
      }
    }

    .tips {
      margin-bottom: 10px;
      font-size: $base-font-size-default;
      color: $base-color-white;

      span {
        &:first-of-type {
          margin-right: 16px;
        }
      }
    }

    .title-container {
      position: relative;

      .title {
        margin: 0 auto 40px auto;
        font-size: 34px;
        font-weight: bold;
        color: $base-color-blue;
        text-align: center;
      }
    }

    .svg-container {
      position: absolute;
      top: 14px;
      left: 15px;
      z-index: $base-z-index;
      font-size: 16px;
      color: #d7dee3;
      cursor: pointer;
      user-select: none;
    }
    .captcha-container {
      position: absolute;
      right: 0px;
      top: 50%;
      transform: translateY(-50%);
      width: 140px;
      height: 56px;
      // background-color: #e9ebfa;
      background-color: rgb(236, 245, 255);
      border: 1px solid rgb(217, 236, 255);
      border-radius: 4px;
      cursor: pointer;
      display: flex;
      align-items: center;
      justify-content: center;
    }
    .captcha-text {
      font-size: 38px;
      font-weight: bold;
      user-select: none;
      letter-spacing: 5px;
      font-style: italic;
      position: relative;
      z-index: 1;
      text-shadow: 1px 1px 2px rgba(0, 0, 0, 0.1);
      transition: all 0.3s ease;
      display: flex;
      justify-content: center;
      align-items: center;
      width: 100%;
      height: 100%;
    }

    .captcha-text span {
      display: inline-block;
      transform: rotate(calc((Math.random() - 0.5) * 15deg));
      transition: all 0.3s ease;
    }

    .captcha-container:hover .captcha-text span {
      transform: scale(1.1) rotate(0deg);
    }

    .show-password {
      position: absolute;
      top: 14px;
      right: 25px;
      font-size: 16px;
      color: #d7dee3;
      cursor: pointer;
      user-select: none;
    }

    ::v-deep {
      .el-form-item {
        padding-right: 0;
        margin: 20px 0;
        color: #454545;
        background: transparent;
        border: 1px solid transparent;
        border-radius: 2px;

        &__content {
          min-height: $base-input-height;
          line-height: $base-input-height;
        }

        &__error {
          position: absolute;
          top: 100%;
          left: 18px;
          font-size: $base-font-size-small;
          line-height: 18px;
          color: $base-color-red;
        }
      }

      .el-input {
        box-sizing: border-box;

        input {
          height: 58px;
          padding-left: 45px;
          font-size: $base-font-size-default;
          line-height: 58px;
          color: $base-font-color;
          background: #f6f4fc;
          border: 0;
          caret-color: $base-font-color;
        }
      }
    }
  }
  .horizontal-text-carousel {
    position: absolute;
    bottom: -60px;
    width: 700px;
    overflow: hidden;
  }
  .carousel-text {
    color: red;
    font-size: 36px;
    font-weight: bold;
    line-height: 40px; /* 与 el-carousel 高度一致 */
    white-space: nowrap; /* 禁止换行 */
    text-align: left;
    padding: 0 0 0 4px; /* 左右留白，避免贴边 */
    display: inline-block; /* 让长文字能完整滚动 */
    // animation: scroll-text 15s linear infinite; /* 长文字滚动动画 */
  }
  @media screen and (max-width: 1900px) {
    .horizontal-text-carousel {
      right: 0;
    }
    .carousel-text {
      text-align: right;
    }
    .el-carousel__item {
      justify-content: flex-end;
    }
  }

  /* 长文字滚动动画（可选） */
  @keyframes scroll-text {
    0% {
      transform: translateX(100%);
    }
    100% {
      transform: translateX(-100%);
    }
  }

  /* 隐藏默认样式 */
  .el-carousel__arrow,
  .el-carousel__indicators {
    display: none !important;
  }

  /* 调整 el-carousel-item 样式 */
  .el-carousel__item {
    display: flex;
    align-items: center;
    overflow: visible !important; /* 允许文字溢出容器 */
  }
</style>
