<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8">
        <title>编辑微博</title>
        <link rel="stylesheet" href="/static?file=index.css">
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js" integrity="sha384-Tc5IQib027qvyjSMfHjOMaLkfuWVxZxUPnCJA7l2mCWNIpG9mGCD8wGNIcPD7Txa" crossorigin="anonymous"></script>

    </head>
    <body class="text-center">
    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">

                <ul class="nav pull-right">
                    <li><a href="/">首页</a></li>

                    <li><a href="/message">留言板</a></li>
                    <li><a href="/todo">Todo</a></li>
                    <li><a href="/weibo">Weibo</a></li>

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
    <div class="page-header">
        <h1>微博 <small>编辑微博</small></h1>
    </div>
    <form class="navbar-form" role="search" action="/weibo/update" method="POST">
        <div class="form-group">
            <input class="form-control" name="id" value="${id}" style="display: none"/>
            <input class="form-control" name="content" value="${oldContent}"/>
            <button class="btn btn-default" type="submit">更新</button>
        </div>
    </form>
    <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
    </body>
</html>
