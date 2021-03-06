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
        <title>users</title>
        <link rel="stylesheet" href="/static?file=index.css">
    </head>
    <!-- body 中是浏览器要显示的内容 -->
    <body class="text-center">
    <div class="navbar">
        <div class="navbar-inner">
            <div class="container">
                <ul class="nav pull-right">
                    <li><a href="/">首页</a></li>

                    <li><a href="/message">留言板</a></li>
                    <li><a href="/todo">Todo</a></li>

                    <li><a href="/admin/users">用户列表</a></li>

                    <li><a href="/logout">退出</a></li>
                </ul>
                <a class="btn btn-navbar" id="responsive-sidebar-trigger">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </a>
            </div>
        </div>
    </div>
    <div class="page-header ">
        <h1>用户列表</h1>
    </div>

            <table id="id-center-table" class="table table-condensed table-striped">
        <tr>
            <th>ID</th>
            <th>用户名</th>
            <th>签名</th>
            <th>角色</th>
        </tr>
        <#list userList as u>
            <tr>
                <td>${u.id}</td>
                <td>${u.username}</td>
                <td>${u.note}</td>
                <td>${u.role}</td>
            </tr>
        </#list>
            </table>


    <br>
    <div>修改对应 id 的密码</div>
    <form class="form-horizontal" action="/admin/users/update" method="POST">
        <div class="form-group">
            <input class="form-control" name="id" placeholder="请输入id">
        </div>

        <div class="form-group">
            <input class="form-control" name="password" placeholder="请输入密码">
        </div>

        <div class="form-group">
            <button class="btn btn-default" type="submit">update</button>
        </div>
    </form>
    </body>
</html>
