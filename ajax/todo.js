var log = console.log.bind(console, new Date().toLocaleString())


var e = function (selector) {
    return document.querySelector(selector)
}


/*
1. 给 add button 绑定事件
2. 在事件处理函数中, 获取 input 的值
3. 用获取的值, 组装一个 todo-cell html
4. 插入到 todo-list div中
 */

var todoTemplate = function (value) {
    var t = `
        <div class="todo-cell">
            <span>${value}</span>
        </div>
    `
    return t
}

var insertTodo = function(value) {
    var t = todoTemplate(value)
    var todoList = e("#id-todo-list")
    todoList.insertAdjacentHTML("beforeend", t)
}

var bindButtonClick = function () {
    var button = e("#id-button-add")
    log("button: ", button)
    button.addEventListener("click", function (event) {
        log("button click")
        var input = e("#id-input-todo")
        log("input: ", input)
        var value = input.value
        log("input value:", value)

        insertTodo(value)
    })
}

// const ajax = function (method, path, data, callback) {
//     log("ajax")
//     var r = new XMLHttpRequest();
//     r.open(method, path, true)
//
//     r.setRequestHeader("Content-Type", "application/json")
//
//     r.onreadystatechange = function () {
//         if (r.readyState === 4) {
//             callback(r.response)
//         }
//     }
//
//     data = JSON.stringify(data);
//     r.send(data)
// }

var callback = function () {
    log("callback")
}

var account = {a: 1}

// ajax("POST", "http://localhost:9000/ajax", account, callback);

var _main = function () {
    // ajax("POST", "http://localhost:9000/ajax", account, callback);
    bindButtonClick()
}

_main()

