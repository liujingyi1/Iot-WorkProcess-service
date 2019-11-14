$(function() {
    initView();
})

var taskId;
var taskKey;
function initView() {

    taskId = $("#taskId").text();
    taskKey = $("#taskKey").text();
    console.log(taskId);
    console.log(taskKey);

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
    } else if (taskKey == "reportResult") {
        $("#workResultGroup").removeAttr("hidden");
    } else if (taskKey == "checkResult") {
        $("#checkResultGroup").removeAttr("hidden");
    }
}

function checkResponse(data) {
    console.log(data.code);
    if (data.code == 1) {
        loadPageHtml("show-process.html");
    }
}
