<!doctype html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=utf-8"/>
    <meta name="viewport"
          content="user-scalable=0,width=device-width,initial-scale=1.0,maximum-scale=1.0,minimum-scale=1.0,user-scalable=no,target-densitydpi=high-dpi"/>
    <meta http-equiv="Pragma" content="no-cache">
    <meta http-equiv="Cache-Control" content="no-cache">
    <title>登录</title>
    <meta name="keywords" content="登录"/>
    <meta name="description" content="登录"/>
    <link rel="stylesheet" href="/civil/lib/fonts/iconfont.css">
    <link rel="stylesheet" href="/civil/lib/css/css.css">
    <link rel="stylesheet" href="/civil/lib/css/login.css">
    <script src="/civil/lib/js/jquery-3.6.0.min.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/layer/layer.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/register.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/getCardId.js" type="text/javascript" charset="utf-8"></script>
    <script src="/civil/lib/js/vcs.js" type="text/javascript" charset="utf-8"></script>
</head>
<body>
<div class="wrap loginbody">

    <header class="header" id="header">
        <div class="topbar">
            <span class="btn-left" style="color: #fff;"><i class="iconfont icon-fanhui"></i></span>
        </div>
    </header>
    <div class="contWrap" style="padding-top:0">
        <header>
            <div class="logo"><img src="/civil/lib/img/logo.png"></div>
        </header>

        <section id="main-mod" class="main-mod">
            <h2>账号注册</h2>
            <div class="form-main">
                <div class="input-item">
                    <div class="form-icon"><img src="/civil/lib/img/id.png"/></div>
                    <input id="sdkIdentity" autocomplete="off" type="text" placeholder="请输入身份证号"/>
                    <a href="javascript:void(0)" class="input-empty" id=""><i class="iconfont icon-cuohaoyuan"></i></a>
                </div>
                <div class="input-item">
                    <div class="form-icon"><img src="/civil/lib/img/user.png"/></div>
                    <input id="sdkRealName" autocomplete="off" type="text" placeholder="请输入真实姓名"/>
                    <a href="javascript:void(0)" class="input-empty"><i
                                class="iconfont icon-cuohaoyuan"></i></a>
                </div>
                <#--            <div class="input-item">-->
                <#--                <div class="form-icon"><img src="/civil/lib/img/user.png"/></div>-->
                <#--                <input id="sdkUsername" autocomplete="off" type="text" placeholder="请输入登录用户名"/>-->
                <#--                <a href="javascript:void(0)" class="input-empty" id="uiEmptyUsername"><i-->
                <#--                            class="iconfont icon-cuohaoyuan"></i></a>-->
                <#--            </div>-->
                <#--            <div class="input-item password-item">-->
                <#--                <div class="form-icon"><img src="/civil/lib/img/pass.png"/></div>-->
                <#--                <input id="sdkPassword" autocomplete="new-password" type="password" placeholder="请输入密码"/>-->
                <#--                <a href="javascript:void(0)" class="input-empty" id="uiEmptyPassword"><i-->
                <#--                            class="iconfont icon-cuohaoyuan"></i></a>-->
                <#--                <a href="javascript:void(0)" class="input-showText" id="uiShowText"><i-->
                <#--                            class="iconfont icon-biyanjing"></i></a>-->
                <#--                <span class="tips-password">密码规则：字母+数字+特殊符号: @#&() 长度为8-16位</span>-->
                <#--            </div>-->


                <#--            <div class="input-item password-item">-->
                <#--                <div class="form-icon"><img src="/civil/lib/img/passR.png"/></div>-->
                <#--                <input id="passwordConfirm" autocomplete="new-password" type="password" placeholder="请再次输入密码"/>-->
                <#--                <a href="javascript:void(0)" class="input-empty" id="uiEmptyPasswordAg"><i-->
                <#--                            class="iconfont icon-cuohaoyuan"></i></a>-->
                <#--                <a href="javascript:void(0)" class="input-showText" id="uiShowTextAg"><i-->
                <#--                            class="iconfont icon-biyanjing"></i></a>-->
                <#--            </div>-->

                <div class="agreement-box">
                    <i class="checkbox"></i>请仔细阅读<a href="javascript:;" class="fwxy">《免责协议》</a>
                </div>

                <div class="btn-item">
                    <button id="register" type="button">注册</button>
                </div>

                <div class="popup">
                    <div class="overlay"></div>
                    <div class="wrapper">
                        <i class="iconfont icon-close"></i>
                        <div class="cont justify">
                            ${agreement!''}
                        </div>
                        <div class="btn">我已阅读并同意</div>
                    </div>
                </div>
                <div type="hidden" id="decryptedCardId" <#if decryptedCardId??> value="${decryptedCardId}" </#if>></div>
            </div>
        </section>
    </div>
</div>
</body>
</html>