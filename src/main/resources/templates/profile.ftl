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
        <title>profile</title>
        <link rel="stylesheet" href="/static?file=index.css">
    </head>
    <!-- body 中是浏览器要显示的内容 -->
    <body>
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
        <img src="/static?file=doge1.jpg" title="dog" class="img-circle">
        <div><span class="label label-primary">id</span> ${curUser.id}</div>
        <div><span class="label label-primary">用户名</span>${curUser.username}</div>
        <div><span class="label label-primary">签名</span>${curUser.note}</div>
        <div><span class="label label-primary">角色</span> ${curUser.role}</div>
    </div>
    </body>
</html>
