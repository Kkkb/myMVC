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
        <title>留言板</title>
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
        <!-- html 中的空格是会被转义的, 所以显示的和写的是不一样的 -->
        <!-- 代码写了很多空格, 显示的时候就只有一个 -->
        <div class="navbar">
            <div class="navbar-inner">
                <div class="container">

                    <ul class="nav pull-right">
                        <li><a href="/">首页</a></li>

                        <li><a href="/message">留言板</a></li>
                        <li><a href="/todo">Todo</a></li>

                        <#if curUser.role == "admin">
                            <li><a href="/admin/users">用户列表</a></li>
                        </#if>
                        <#if curUser.role == "guest">
                            <li><a href="/register">注册</a></li>
                            <li><a href="/login">登录</a></li>
                        <#else>
                            <li><a href="/logout">退出</a></li>
                        </#if>

                    </ul>
                    <a class="btn btn-navbar" id="responsive-sidebar-trigger">
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                        <span class="icon-bar"></span>
                    </a>
                </div>
            </div>
        </div>

        <div class="text-center">
            <h2>Message</h2>


                <form class="form-inline" action="/message" method="POST">
                    <div class="form-group">
                        <input name="author" class="hidden"/>
                        <textarea class="form-control" name="message" placeholder="想说的话" style="margin: 0px; width: 334px; height: 35px;"></textarea>
                        <button class="btn btn-default" type="submit">发送</button>
                    </div>
                </form>
                <ul class="list-group">
                    <li role="separator" class="divider"></li>
            <#list messageList as m>
                <li class="list-group-item"><a href="/profile?u=${m.author}">${m.author}</a>: ${m.message}</li>
            </#list>
        </ul>


        </div>
        <!-- jQuery (Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边) -->
        <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
        <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
    </body>
</html>