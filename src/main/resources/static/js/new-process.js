$(function() {
	initBuildings();
	initProcess();
	initView();
})

/**
 * 初始化流程选择列表
 */
function initProcess() {
	$.ajax({
		url : '/workProcess/prcessType/list',
		async : false,
		success : function(data) {
			var option;
			$.each(data.result, function(index, value) {
				option += "<option value=" + value.id + ">"
					+ value.proName + "</option>";
			})
			$('#process-select').append(option);
		}
	});
}

/**
 * 初始化楼栋下拉选信息
 */
function initBuildings() {
	$.ajax({
		url : '/workProcess/position/buildings',
		async : false,
		success : function(data) {
			var result = data.result;
			var option;
			$.each(result, function(index, value) {
				option += "<option value=" + value.id + ">"
					+ value.alias + "</option>";
			})
			$('#position-select').append(option);
		}
	});
}

var files = [];
function initView() {
	document.querySelector("#upload-pic-file")
		.addEventListener("change",function() {
			var file = $("#upload-pic-file")[0].files[0];
			var filename = file.name;
			var _URL = window.URL || window.webkitURL;
			if ((file == this.files[0])) {
				var img = new Image();
				img.onload = function() {
					var imgLen = $("#image-group").children(".upload-pic").length;
					var imgView = "<div class='am-u-sm-1 image-border upload-pic'><img class='am-thumbnail' src='" + this.src + "'/></div>";
					$(imgView).insertBefore("#image-add");
					files[imgLen] = file;
				};
				img.src = _URL.createObjectURL(file);
			}
		});

	$('#init-submit-btn').click(function() {
		// var imgGroup = $("#image-group").children(".upload-pic");
		// $(imgGroup).each(function(index,element){
		// 	alert(index);
		// 	alert($(this).children("img").attr("src"));
		// });

		// $(files).each(function (index, value) {
		// 	alert(index+"  "+value);
		// })

		var createDate = new Date();
		var order = {
			userId: "User1",
			customerName: $("#customer-name").val(),
			customerPhone: $("#customer-phone").val(),
			processType: $('#process-select option:selected').val(),
			positionID: $('#position-select option:selected').val(),
			orderTitle: $('#customer-title').val(),
			orderSummary: $('#customer-summary').val(),
			createdDate: createDate,
			modifiedDate: createDate,
			modifiedBy: "User1"
		};


		var formData = new FormData();
		formData.append("files",files);

		$.ajax({
			url : '/workProcess/order/submit',
			type: 'post',
			dataType : 'json',
			contentType: 'application/json;charset=utf-8',
			data: JSON.stringify(order),
			success : function(data){
				if(data.code == 1){
					alert("提交成功！");
					loadPageHtml("show-process.html");
				}else{
					alert("提交失败！");
				}
			}
		});
	});

}

