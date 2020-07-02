<!DOCTYPE html>
<html>
    <head>
        <!-- meta charset 指定了页面编码, 否则中文会乱码 -->
        <meta charset="utf-8">
        <!-- title 是浏览器显示的页面标题 -->
        <title>Weibo</title>
        <link rel="stylesheet" href="/static?file=index.css">

        <!-- 最新版本的 Bootstrap 核心 CSS 文件 -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap.min.css" integrity="sha384-BVYiiSIFeK1dGmJRAkycuHAHRg32OmUcww7on3RYdg4Va+PmSTsz/K68vbdEjh4u" crossorigin="anonymous">

        <!-- 可选的 Bootstrap 主题文件（一般不用引入） -->
        <link rel="stylesheet" href="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/css/bootstrap-theme.min.css" integrity="sha384-rHyoN1iRsVXV4nD0JutlnGaslCJuC7uwjduW9SVrLvRYooPp2bWYgmgJQIXwl/Sp" crossorigin="anonymous">

        <!-- 最新的 Bootstrap 核心 JavaScript 文件 -->
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
            <h1>Weibo <small>Weibo list</small></h1>
        </div>

        <form class="navbar-form" role="search" action="/weibo/add" method="post">
            <div class="form-group">
                <textarea class="form-control" name="content" placeholder="To do Something"></textarea>
                <button class="btn btn-default" type="submit">ADD ITEM</button>
            </div>
        </form>
    <div id="id-weibos">
        <#list weiboList as m>
<#--            <#if m.completed>-->
                <h4><span class="label label-info">${m.user.username}</span>: ${m.content}
<#--                    <span class="label label-success">已完成</span>-->
                </h4>
            <p>评论:
            <#if m.comments??>
            <#list m.comments as c> ${c.user.username}: ${c.content} <br> </#list>
            </#if>
            </p>
<#--                <div>发布于 ${m.createdTime}</div>-->
<#--                <div>编辑于 ${m.updatedTime}</div>-->
                <div class="btn-group-sm weibo-div" role="group" aria-label="...">
                    <a href="/weibo/edit?id=${m.id}" class="btn btn-default" role="button">Edit</a>
                    <a href="/weibo/delete?id=${m.id}" class="btn btn-default" role="button">Delete</a>
                    <a href="/comment/addview?weiboId=${m.id}" class="btn btn-default" role="button">评论</a>

                    <#--                    <a href="/weibo/completed?id=${m.id}" class="btn btn-default disabled" role="button">Complete</a>-->

                </div>
                <br>
<#--            <#else>-->
<#--                <h4><span class ="label label-info">${m.id}</span> ${m.content} <span class="label label-primary">NEW</span></h4>-->
<#--                <div>发布于 ${m.createdTime}</div>-->
<#--                <div>编辑于 ${m.updatedTime}</div>-->
<#--                <div class="btn-group-sm" role="group" aria-label="...">-->
<#--                    <a href="/weibo/edit?id=${m.id}" class="btn btn-default" role="button">Edit</a>-->
<#--                    <a href="/weibo/delete?id=${m.id}" class="btn btn-default" role="button">Delete</a>-->
<#--                    <a href="/weibo/completed?id=${m.id}" class="btn btn-default" role="button">Complete</a>-->

<#--                </div>-->
<#--                <br>-->
<#--            </#if>-->
        </#list>
        </div>
        <!-- jQuery Bootstrap 的所有 JavaScript 插件都依赖 jQuery，所以必须放在前边 -->
        <script src="https://cdn.jsdelivr.net/npm/jquery@1.12.4/dist/jquery.min.js"></script>
        <!-- 加载 Bootstrap 的所有 JavaScript 插件。你也可以根据需要只加载单个插件。 -->
        <script src="https://cdn.jsdelivr.net/npm/bootstrap@3.3.7/dist/js/bootstrap.min.js"></script>
<#--        <script>-->
<#--            var log = console.log.bind(console, new Date().toLocaleString())-->

<#--            var bindEvents = function () {-->
<#--                var buttonComment = document.querySelector("#id-weibos")-->
<#--                console.log("button", buttonComment)-->
<#--                buttonComment.addEventListener("click", function (e) {-->
<#--                    var target = e.target-->
<#--                    log("target", target)-->
<#--                    if (target. === '评论') {-->
<#--                        var input = document.querySelector(".form-comment")-->
<#--                        input.style.display = 'inline'-->
<#--                        console.log("click input", input)-->
<#--                    }-->


<#--                })-->
<#--            }-->

<#--            var _main = function() {-->
<#--                bindEvents()-->
<#--            }-->

<#--            _main()-->


<#--        </script>-->

    </body>
</html>
