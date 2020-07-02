<!DOCTYPE html>

<html>
    <head>
        <meta charset="utf-8">

        <title>myMVC</title>
        <link rel="stylesheet" href="/static?file=index.css">
    </head>

    <body>


    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">

                <ul class="nav pull-right">
                    <li><a href="/">首页</a></li>
                    <li><a href="/weibo">微博</a></li>
                    <li><a href="/message">留言板</a></li>
                    <li><a href="/todo">Todo</a></li>
                    <li><a href="/ajax/todo">AjaxTodo</a></li>

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

    <div id="main">
        <div id="sidebar">
            <div class="panel">
                <div class="header">
                    <span class="col_fade">作者</span>
                </div>
                <div class="inner">
                    <div class="user_card">
                        <div>
                            <a class="user_avatar" href="/profile?u=${curUser.username}">
                                <img src="/static?file=doge1.jpg" title="dog">
                            </a>
                            <span class="user_name"><a class="dark" href="/profile?u=${curUser.username}">${curUser.username}</a></span>
                            <#if curUser.role == "admin">
                                <span class="label label-danger" style="background-color: red">管理员</span>
                            </#if>
                            <div class="board clearfix">
                                <div class="floor">
                                    <span class="big"></span>
                                </div>
                            </div>
                            <div class="space clearfix"></div>
                            <span class="signature">${curUser.note}</span>
                        </div>
                    </div>
                </div>
            </div>

        </div>
        <div id="content">
            <div class="panel">
                <div class="header topic_header">


                </div>
                <div class="inner topic">
                    <h2 class="text-center"><small>欢迎</small> ${curUser.username}</h2>
                    <div class="topic_content">

                    </div>
                </div>
            </div>

            <div class="panel">
                <div class="header">
                    <#list messageList as m>
                         <blockquote><a class="text-info" href="/profile?u=${m.author}">${m.author}</a>: ${m.message}</blockquote>
                    </#list>
                </div>

            </div>

        </div>

    </div>
    </body>

</html>
