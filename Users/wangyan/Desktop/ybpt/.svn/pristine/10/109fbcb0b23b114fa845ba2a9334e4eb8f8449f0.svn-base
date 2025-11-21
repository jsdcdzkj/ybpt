<template>
  <div :class="'logo-container-' + layout">
    <router-link to="/">
      <!-- 这里是logo变更的位置 -->
      <el-image
        :src="require('@/assets/logo.svg')"
        style="
          width: 30px;
          display: inline-block;
          line-height: 30px;
          padding-top: 0px;
          margin-right: 10px;
          vertical-align: middle;
        "
      ></el-image>

      <span
        class="title"
        :class="{ 'hidden-xs-only': layout === 'horizontal' }"
        :title="title_new"
      >
        {{ title_new }}
      </span>
    </router-link>
  </div>
</template>
<script>
  import { mapGetters } from 'vuex'
  import { logo } from '@/config'

  export default {
    name: 'VabLogo',
    data() {
      return {
        title_new: '医保综合管理服务模块',
        title: this.$baseTitle,
      }
    },
    created() {
      var userinfo = JSON.parse(localStorage.getItem('userinfo'))
      if (userinfo.user_type == '4' || userinfo.user_type == '5') {
        this.title_new = '徐州市公务员健康管理系统'
      } else {
        this.title_new = '医保综合管理服务模块'
      }
    },
    computed: {
      ...mapGetters({
        logo: 'settings/logo',
        layout: 'settings/layout',
      }),
    },
  }
</script>
<style lang="scss" scoped>
  @mixin container {
    position: relative;
    height: $base-top-bar-height;
    overflow: hidden;
    line-height: $base-top-bar-height;
    background: $base-menu-background;
  }

  @mixin logo {
    display: inline-block;
    width: 32px;
    height: 32px;
    margin-right: 5px;
    color: $base-title-color;
    vertical-align: middle;
  }

  @mixin title {
    display: inline-block;
    overflow: hidden;
    font-size: 20px;
    line-height: 55px;
    color: $base-title-color;
    text-overflow: ellipsis;
    white-space: nowrap;
    vertical-align: middle;
  }

  .logo-container-horizontal {
    @include container;

    .logo {
      @include logo;
    }

    .title {
      @include title;
    }
  }

  .logo-container-vertical {
    @include container;

    height: $base-logo-height;
    line-height: $base-logo-height;
    text-align: center;

    .logo {
      @include logo;
    }

    .title {
      @include title;

      max-width: calc(#{$base-left-menu-width} - 60px);
    }
  }
</style>
