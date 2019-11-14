
$(document).ready(function(){

    $("#confirm").click(function(){


    });

    var instanceId = $("#processInstanceId").text();

    $("#process-image-box").empty();

    var str = '<embed src="/workProcess/processTest/showImage?instanceId=' + instanceId + '" style="display:block;width:1000px;height:600px" />';

    $(str).appendTo('#process-image-box');

});