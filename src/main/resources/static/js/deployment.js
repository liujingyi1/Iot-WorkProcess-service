$(document).ready(function () {
	initDeployListGrid();
	initView();
});

function initDeployListGrid(){
	$("#deploy-list").jqGrid({
	    url: 'repository/listDeployPage',
	    mtype: "GET",
		styleUI : 'Bootstrap',
	    datatype: "json",
	    colNames : ['id','流程名称','类型','tenantId','是否挂起','当前实例数','部署日期','操作'/*,'操作'*/],
	    colModel: [
	    	{ name: 'id', hidden : true},
	        { name: 'name'},
	        { name: 'category'},
	        { name: 'tenantId' },
	        { name: 'suspended' },
	        { name: 'runProcessCount' },
			{ name: 'deploymentTime'},
			{ name: 'action'},
	    ],
		gridComplete :
			function() {
			var ids = jQuery("#deploy-list").jqGrid('getDataIDs');
			var editText;
			var count;
			var isSuspended;
			var ebtn;
			var dbtn;
			var actionRow;
			for (var i = 0; i < ids.length; i++) {
				var cl = ids[i];

				count = jQuery("#deploy-list").getCell(cl, "runProcessCount");
				isSuspended = jQuery("#deploy-list").getCell(cl, "suspended");
				isSuspended = eval(isSuspended);
				editText = isSuspended ? "激活" : "挂起";

				ebtn = "<a href='javascript:;' onclick=onEditAction('" + cl + "\',\'" + count + "')>  " + editText + " </a>";
				dbtn = "<a href='javascript:;' class='tpl-table-black-operation-del' onclick=onDeleteAction('" + cl + "\',\'" + count + "')>  删除 </a>";
				actionRow = "<div class='tpl-table-black-operation'> " + ebtn + " " + dbtn + "</div>";

				jQuery("#deploy-list").jqGrid('setRowData', ids[i],
					{
						action: actionRow
					});
			}
		},
		loadComplete: function() {
		},
		viewrecords: true,
		autowidth:true,
		height : '100%',
	    rowNum: 15,
		// multiselect: true,
		// multiboxonly: true,
	    rowList : ['15:15', '30:30', '60:60', '120:120'],
	    pager: "#jqGridPager"
    });
	
	$('#deploy-list').navGrid("#jqGridPager", {
	        search: true, // show search button on the toolbar
	        add: false,
	        edit: false,
	        del: false,
	        refresh: true
	    },
	    {}, // edit options
        {}, // add options
        {}, // delete options
        { multipleSearch: true } // search options - define multiple search
	);
}

function onEditAction(i, count) {
	if (count > 0) {
		showMode("不能操作", "还有 " + count + " 个流程正在运行");
	} else {
		$('#my-confirm .am-modal-hd').empty();
		$('#my-confirm .am-modal-hd').append("确定吗?");
		$('#my-confirm .am-modal-bd').empty();
		$('#my-confirm').modal({
			relatedElement: this,
			onConfirm:  function () {
				var isSuspended = jQuery("#deploy-list").getCell(i, "suspended");
				isSuspended = eval(isSuspended);
				if (isSuspended) {
					$.get("/workProcess/repository/activateProcess", { id: i},function(data){
						jQuery("#deploy-list").trigger("reloadGrid");
					});
				} else {
					$.get("/workProcess/repository/suspendProcess", { id: i},function(data){
						jQuery("#deploy-list").trigger("reloadGrid");
					});
				}
			},
			onCancel: function () {
			}
		});
	}
}

function onDeleteAction(i, count) {
	if (count > 0) {
		showMode("不能操作", "还有 " + count + " 个流程正在运行");
	} else {
		$('#my-confirm .am-modal-hd').empty();
		$('#my-confirm .am-modal-hd').append("确定删除吗?");
		$('#my-confirm .am-modal-bd').empty();
		$('#my-confirm .am-modal-bd').append("删除将不能恢复");
		$('#my-confirm').modal({
			relatedElement: this,
			onConfirm: function() {
				$.get("/workProcess/repository/delete", { id: i},function(data){
					jQuery("#deploy-list").trigger("reloadGrid");
				});
			},
			onCancel: function() {
			}
		});
	}
}

function showMode(title, content) {
	$('#doc-modal-1 .am-modal-hd').empty();
	$('#doc-modal-1 .am-modal-hd').append("<a href='javascript: void(0)' class='am-close am-close-spin' data-am-modal-close>&times;</a>");
	$('#doc-modal-1 .am-modal-hd').append(title);
	$('#doc-modal-1 .am-modal-bd').empty();
	$('#doc-modal-1 .am-modal-bd').append(content);
	$('#doc-modal-1').modal();
}

function initView() {
	$('#deploy-commit-btn').click(function () {

		var tenant = $("#deploy-tenant-id").val();
		var category = $("#deploy-category").val();
		var fileName = $("#deploy-file-name").val();

		var formData = new FormData();
		formData.append("tenantId",tenant);
		formData.append("category",category);
		formData.append("fileName",fileName);
		formData.append("file",$("#deploy-file")[0].files[0]);

		$.ajax({
			url:'/workProcess/repository/deploy',
			type:'post',
			data: formData,
			contentType: false,
			processData: false,
			success:function(res){
				if (res.code == 1) {
					alert("部署成功");
				} else {
					alert("部署失败 " + res.message);
				}
			}
		})
	});

	document.querySelector("#deploy-file")
		.addEventListener("change",function () {
		var file = $("#deploy-file")[0].files[0];
		var filename = file.name;

		var _URL = window.URL || window.webkitURL;
		var file, img;
		if ((file = this.files[0])) {
			$("#deploy-file-name").val(filename);

			img = new Image();
			img.onload = function() {
				$("#deploy-file-image").attr('src', this.src);
			};
			img.src = _URL.createObjectURL(file);
		}
	});
}