
var log = console.log.bind(console, new Date().toLocaleString())

var ajax = function (method, url, data, callback) {
    log("ajax")
    let r = new XMLHttpRequest()
// 设置请求方法和请求地址
    r.open(method, url, true)
    r.setRequestHeader("Content-Type", "application/json")
// 注册响应函数
// 注册响应函数
    r.onreadystatechange = function() {
        if (r.readyState === 4) {
            callback(r.response)
        }
    }
// 发送请求
//     let account = {}
    data = JSON.stringify(data)
    r.send(data)
}


var todoTemplate = function (todo) {
    var t = `
            <div class="todo-cell">
                <span id="id-span-${todo.id}">${todo.id}: ${todo.content} 发布于 ${todo.createdTime}
                    <button id="${todo.id}" type="submit">删除</button>
                    <a id="${todo.id}" type="submit">编辑</a>
                </span>
            </div>
        `

    return t
}

var insertTodo = function (todoHtml) {
    var divTodo = document.querySelector("#id-todo-list")
    divTodo.insertAdjacentHTML("beforeend", todoHtml)
}

var deleteTodo = function (todo) {
    var q = `#id-span-${todo.id}`
    var spanTodo = document.querySelector(q)
    spanTodo.remove()
}

var updateTodo = function (todo) {
    let q = `#id-span-${todo.id}`
    let spanTodo = document.querySelector(q)
    let updateHtml = `${todo.id}: ${todo.content}
                    <button id="${todo.id}" type="submit">删除</button>
                    <a id="${todo.id}" type="submit">编辑</a>`
    spanTodo.innerHTML = updateHtml
}

var bindEvents = function () {
    var buttonAdd = document.querySelector("#id-button-add")
    buttonAdd.addEventListener("click", function () {

        var input = document.querySelector("#id-input-todo")
        var content = input.value
        log("content", content)

        var url = "/ajax/todo/add"
        var method = "POST"
        var data = {
            content: content
        }

        ajax(method, url, data, function (response) {
            log("ajax todo add:", response)
            var todo = JSON.parse(response)
            todo["createdTime"] = simplyToRelativeTime(todo["createdTime"])
            var todoHtml = todoTemplate(todo)
            insertTodo(todoHtml)
        })
    })
}

var bindDeleteEvents = function () {
    document.getElementById('id-todo-list').addEventListener('click', function (e) {
        var target = e.target
        if (target.innerHTML === '删除') {
            var id = target.id
            console.log(id);

            var url = "/ajax/todo/delete"
            var method = "POST"
            var data = {
                id: id
            }

            ajax(method, url, data, function (response) {
                log("ajax todo delete:", response)
                var todo = JSON.parse(response)

                deleteTodo(todo)
            })
        }
    });
}

var bindEditEvents = function () {
    document.getElementById('id-todo-list').addEventListener('click', function (e) {
        var target = e.target
        var target_id = target.id
        if (target.nodeName.toLocaleLowerCase() === 'a') {
            let editHtml = `
                    <input type="text" id="id-edit-todo-${target_id}"><button id="id-button-update-${target_id}" type="submit">更新</button>
                `
            target.insertAdjacentHTML('afterend', editHtml)
        }
    });
}

var bindUpdateEvents = function () {
    document.getElementById('id-todo-list').addEventListener('click', function (e) {
        var target = e.target
        if (target.innerHTML === '更新') {
            let tId = target.id
            let spStr = tId.split('-')
            let id = spStr[spStr.length - 1]
            let qs = `#id-edit-todo-${id}`
            var editInput = document.querySelector(qs)
            var updateContent = editInput.value

            var url = "/ajax/todo/update"
            var method = "POST"
            var data = {
                id: id,
                content: updateContent
            }

            ajax(method, url, data, function (response) {
                log("response: ", response)
                let todo = JSON.parse(response)
                updateTodo(todo)
            })
        }
    });
}

var loadTodos = function () {
    var url = "/ajax/todo/all"
    var method = "GET"
    var data = ""

    ajax(method, url, data, function (response) {
        log("response: ", response)
        var todoList = JSON.parse(response)
        log("todoList: ", todoList)

        for (let i = 0; i < todoList.length; i++) {
            var todo = todoList[i]
            let createdTime = todo["createdTime"]
            let createdRelativeTime = simplyToRelativeTime(createdTime)
            todo["createdTime"] = createdRelativeTime
            var todoHtml = todoTemplate(todo)
            insertTodo(todoHtml)
        }
    })
}

var simplyToRelativeTime = function (timestamp) {
    // 当前时间的秒数
    let currentUnixTime = Math.round((new Date()).getTime() / 1000);
    // 当前时间与要转换的时间差（ s ）
    let deltaSecond = currentUnixTime - parseInt(timestamp, 10);
    let result;

    if (deltaSecond < 60) {
        result = deltaSecond + '秒前';
    } else if (deltaSecond < 3600) {
        result = Math.floor(deltaSecond / 60) + '分钟前';
    } else if (deltaSecond < 86400) {
        result = Math.floor(deltaSecond / 3600) + '小时前';
    } else {
        result = Math.floor(deltaSecond / 86400) + '天前';
    }
    return result;
}

var _main = function() {
    loadTodos()
    bindEvents()
    bindDeleteEvents()
    bindEditEvents()
    bindUpdateEvents()
}

_main()

