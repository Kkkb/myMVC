# myMVC
## 基于 Socket 的 MVC Web 开发框架 + HTTP 服务器
*技术栈 Java + ORM + Socket + HTTP*

*项目源码 https://github.com/kkkb/myMVC*
* 自制 Web 框架
    * MVC 架构组织代码
        * Model 模型层采用基于本地持久化存储的自制 ORM, 实现数据的序列化和反序列化
        * View 视图, 自定义配置的模板引擎 FreeMarker 渲染, 生成HTML Web页面
        * Controller 采用 HashMap 和方法引用实现分发请求, 组装 Model 和 View
    * 定制化 debug 日志, 易于调试
* HTTP 服务器
    * 实现接收符合 HTTP 协议的请求, 将其解析, 经 MVC 框架处理后, 返回响应
    * 底层通过操作系统提供的 Socket 建立 TCP/IP 通信
    * 考虑到服务器的性能要求, 采用多线程, 构建线程池, 实现了并发访问
* 基于本框架构建一个微博项目
    * 用户模块
        * 登录注册, 密码保存为加盐摘要, 提高安全性
        * Session 持久化, 记住用户状态, 退出登录
    * 微博的发表, 删除, 修改, 每个微博提供添加评论功能
    
### 登录/注册演示
![登录/注册演示](https://s1.ax1x.com/2020/07/02/Nblwi8.gif)

### 微博发布评论演示
![微博发布演示](https://s1.ax1x.com/2020/07/02/Nbl0JS.gif)

### debug 日志
![journal1](https://s1.ax1x.com/2020/07/09/UnTmVA.png)
![journal2](https://s1.ax1x.com/2020/07/09/UnTnUI.png)
