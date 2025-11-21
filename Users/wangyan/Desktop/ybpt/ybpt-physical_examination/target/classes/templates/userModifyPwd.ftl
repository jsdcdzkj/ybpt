<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="user-scalable=0,width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,target-densitydpi=high-dpi"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>账号管理</title>
    <meta name="keywords" content="账号管理"/>
    <meta name="description" content="账号管理"/>
    <link rel="stylesheet" href="/civil/lib/fonts/iconfont.css">
    <link rel="stylesheet" href="/civil/lib/css/css.css">
    <script src="/civil/lib/js/jquery-3.6.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/userModifyPwd.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/layer/layer.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/vcs.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="wrap fillinfo">
    <header class="header headerwhitebg">
        <div class="topbar">
            <span class="btn-left"><i class="iconfont icon-fanhui"></i></span>
            <div class="words ellipsis">密码修改</div>
            <span class="btn-right"><i class="iconfont icon-home"></i></span>
        </div>
    </header>
    <div class="contWrap">
        <ul class="info">
            <li>
                <div class="p1 ">原密码</div>
                <div class="p2">
                    <div class="van-cell van-field">
                        <div class="van-cell__value van-field__value">
                            <div class="van-field__body">
                                <input name="oralPwd" type="text" placeholder="请输入原密码" class="van-field__control">
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="p1">新密码</div>
                <div class="p2" id="showType">
                    <div class="van-cell van-field">
                        <div class="van-cell__value van-field__value">
                            <div class="van-field__body">
                                <input name="newPwd" type="text" placeholder="请输入新密码" class="van-field__control">
                            </div>
                        </div>
                    </div>
                </div>
            </li>
            <li>
                <div class="p1">再次输入</div>
                <div class="p2">

                    <div class="van-cell van-field">
                        <div class="van-cell__value van-field__value">
                            <div class="van-field__body">
                                <input name="passwordConfirm" type="text" placeholder="请再次输入密码"
                                       class="van-field__control">
                            </div>
                        </div>
                    </div>
                </div>
            </li>
        </ul>

    </div>

    <div class="bottom">
        <div class="submit">保存</div>
    </div>
    <!-- <footer class="menu_bar block" id="footer">
          <div id="home" class="menu-tab-item">
             <span class="icon"><i class="iconfont icon-zhuye1"></i></span>
             <span class="menu-tab-label">首页</span>
          </div>
          <div id="rebate" class="menu-tab-item">
             <span class="icon"><i class="iconfont icon-tianjiashebei1"></i></span>
             <span class="menu-tab-label">票据</span>
          </div>
          <div id="member" class="menu-tab-item on">
             <span class="icon"><i class="iconfont icon-huiyuan1"></i></span>
             <span class="menu-tab-label">我的</span>
          </div>
      </footer> -->
</div>
</body>
</html>