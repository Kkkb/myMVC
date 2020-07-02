<!DOCTYPE html>
<!-- c + / -->
 <!--注释是这样的, 不会被显示出来 -->
<!--
    html 格式是浏览器使用的标准网页格式
    简而言之就是 标签套标签
    css 控制显示
-->
<!-- html 中是所有的内容 -->
<html>
    <!-- head 中是放一些控制信息, 不会被显示 -->
    <head>
        <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
        <meta charset="utf-8">
        <!-- title 是浏览器显示的页面标题 -->
        <title>注册</title>
        <link rel="stylesheet" href="/static?file=index.css">
        <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
        <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

        <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>
    </head>
    <!-- body 中是浏览器要显示的内容 -->
    <body>
    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">

                <ul class="nav pull-right">
                    <li><a href="/">首页</a></li>
                    <li><a href="/weibo">微博</a></li>

                    <li><a href="/message">留言板</a></li>
                    <li><a href="/todo">Todo</a></li>

                    <li><a href="/register">注册</a></li>
                    <li><a href="/login">登录</a></li>
                </ul>
                <a class="btn btn-navbar" id="responsive-sidebar-trigger">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
            </div>
        </div>
    </div>

    <div class="row">
        <div class="col-md-4"></div>
        <div class="col-md-3">
            <h2 class="text-center">注册</h2>
            <form class="form-horizontal" action="/register" method="POST">

                <div class="form-group">
                    <label>用户名</label>
                    <div>
                        <input class="form-control" name="username" placeholder="请输入用户名">
                    </div>
                </div>

                <div class="form-group">
                    <label>密码</label>
                    <div>
                        <input class="form-control" name="password" placeholder="请输入密码">
                    </div>
                </div>

<#--                <div class="form-group">-->
<#--                    <label>签名</label>-->
<#--                    <div>-->
<#--                        <input class="form-control" name="note" placeholder="签名">-->
<#--                    </div>-->
<#--                </div>-->

                <div class="form-group">
                    <div>
                        <#if registerResult == "">
                                <button class="btn btn-default" type="submit">注册</button>
                            <#elseif registerResult == "注册成功">
                                <button class="btn btn-success" disabled="disabled">注册成功</button>
                                <a class="btn btn-info" href="/login" role="button">点击登录</a>
                            <#else>
                                <button class="btn btn-default" type="submit">注册</button>
                                <button class="btn btn-warning" disabled="disabled">注册失败</button>
                        </#if>

                    </div>
                </div>
            </form>
        </div>
        <div class="col-md-5"></div>
    </div>


    <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
    <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>

    </body>
</html>
