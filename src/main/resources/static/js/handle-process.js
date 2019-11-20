$(function() {
    initView();
})

var taskId;
var taskKey;
var processInstanceId;
function initView() {

    taskId = $("#taskId").text();
    taskKey = $("#taskKey").text();
    processInstanceId = $("#processInstanceId").text();
    console.log(taskId);
    console.log(taskKey);
    console.log(processInstanceId);

    $.ajax({
        url: '/workProcess/user/engineers',
        async: false,
        success: function (data) {
            var option;
            $.each(data.result, function (index, value) {
                option += "<option value=" + value + ">"
                    + value + "</option>";
            })
            $('#handle-user-select').append(option);
        }

    });


    $('#task-complete-btn').click(function () {
        if (taskKey == "assigneeUser") {
            var toUser = $("#handle-user-select").val();
            var message = $("#task-complete-summary").val();
            console.log(toUser);
            console.log(message);

            $.get("/workProcess/process/assigneeTask",
                {taskId: taskId,toUser:toUser, message:message},
                function(data) {
                    checkResponse(data);
                });

        } else if (taskKey == "reportResult") {
            var result = $("#fix-result-select").val();
            var message = $("#task-complete-summary").val();
            console.log(result);
            console.log(message);

            $.get("/workProcess/process/wrokResult",
                {taskId: taskId,message:message, result:result},
                function(data) {
                    checkResponse(data);
                });

        } else if (taskKey == "checkResult") {
            var result = $("#check-result-select").val();
            var message = $("#task-complete-summary").val();
            console.log(result);
            console.log(message);

            $.get("/workProcess/process/checkResult",
                {taskId: taskId,message:message, result:result},
                function(data) {
                    checkResponse(data);
                });

        }
    });

    if (taskKey == "assigneeUser") {
        $("#dispatchGroup").removeAttr("hidden");
        $("#task-summary-group").removeAttr("hidden");
        $("#task-complete-btn-group").removeAttr("hidden");
    } else if (taskKey == "reportResult") {
        $("#workResultGroup").removeAttr("hidden");
        $("#task-summary-group").removeAttr("hidden");
        $("#task-complete-btn-group").removeAttr("hidden");
    } else if (taskKey == "checkResult") {
        $("#checkResultGroup").removeAttr("hidden");
        $("#task-summary-group").removeAttr("hidden");
        $("#task-complete-btn-group").removeAttr("hidden");
    }

    $.ajax({
        url: '/workProcess/history/getByInstanceId',
        data:{"processInstanceId":processInstanceId},
        success: function (data) {
            var result = data.result;
            var option="";
            $.each(result, function(index, value) {
                option += newComment(value.identity, value.result, value.message, value.date);
            })
            $('#comment-group').append(option);
        }

    });
}

function newComment(author, result, message, date) {
   var comment = "<li class='am-comment'>\
        <a href='#'>\
        <img src='assets/img/user04.png' class='am-comment-avatar' width='48' height='48'>\
        </a>\
        <div class='am-comment-main'>\
        <header class='am-comment-hd'>\
        <div class='am-comment-meta'>\
        <a href='#link-to-user' class='am-comment-author'>" +author +"</a>  评论于 \
        <time >" + date + "</time>\
        </div>\
        </header>\
        <div class='am-comment-bd'><p>结果：<br>" + result + "<br><br>备注：<br>" + message + "</p>\
        </div>\
        </div>\
        </li>";
   return  comment;
}

function checkResponse(data) {
    console.log(data.code);
    if (data.code == 1) {
        loadPageHtml("show-process.html");
    }
}
