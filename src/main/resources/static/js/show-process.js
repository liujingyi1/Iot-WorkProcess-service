$(document).ready(function () {
	initAcceptGrid();
	initDispatchGrid();
	initFixGrid();
	initCheckGrid();
	initCompleteGrid();
});

var responseDataAccept;
function initAcceptGrid(){
	$("#process-list-accept").jqGrid({
	    url: 'order/listByState?state=0',
	    mtype: "GET",
		styleUI : 'Bootstrap',
	    datatype: "json",
	    colNames : ['id','工单编号','工单主题','工单类型','创建时间', '发起人','工单状态','优先级','处理人','操作'/*,'操作'*/],
	    colModel: [
	    	{ name: 'id', hidden : true},
	        { name: 'customOrder.id'},
	        { name: 'customOrder.orderTitle'},
	        { name: 'processType.proName'},
	        { name: 'customOrder.createdDate', },
	        { name: 'customOrder.userId' },
	        { name: 'processState.content' },
	        { name: 'customOrder.priority' },
			{ name: 'customOrder.handlePerson' },
			{ name: 'action'}
	    ],
		loadComplete: function(data) {
			responseDataAccept = data.rows;

			var ids = jQuery("#process-list-accept").jqGrid('getDataIDs');
			var ebtn;
			var actionRow;
			var state = 0;
			for (var i = 0; i < ids.length; i++) {

				var state = responseDataAccept[i].customOrder.state;

				if (state == "0") {
					ebtn = "<a href='javascript:;' onclick=onAcceptAction('" + i + "')>  " + "受理" + " </a>";
				} else if (state == "1") {
					ebtn = "<a href='javascript:;' onclick=onDispatchAction('" + i + "')>  " + "分配" + " </a>";
				} else if (state == "2") {
					ebtn = "<a href='javascript:;' onclick=onFixAction('" + i + "')>  " + "维修" + " </a>";
				} else if (state == "3") {
					ebtn = "<a href='javascript:;' onclick=onCheckAction('" + i + "')>  " + "审核" + " </a>";
				}
				actionRow = "<div class='tpl-table-black-operation'> " + ebtn + "</div>";

				jQuery("#process-list-accept").jqGrid('setRowData', ids[i],
					{
						action: actionRow
					});
			}
		},
		gridComplete : function() {

		},
		viewrecords: true,
		autowidth:true,
		height : '100%',
	    rowNum: 15,
	    rowList : ['15:15', '30:30', '60:60', '120:120'],
	    pager: "#jqGridPager1"
    });
	
	$('#process-list-accept').navGrid("#jqGridPager1", {
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

var responseDataDispatch;
function initDispatchGrid(){
	$("#process-list-dispatch").jqGrid({
		url: 'order/listByState?state=1',
		mtype: "GET",
		styleUI : 'Bootstrap',
		datatype: "json",
		colNames : ['id','工单编号','工单主题','工单类型','创建时间', '发起人','工单状态','优先级','处理人','操作'/*,'操作'*/],
		colModel: [
			{ name: 'id', hidden : true},
			{ name: 'customOrder.id'},
			{ name: 'customOrder.orderTitle'},
			{ name: 'processType.proName'},
			{ name: 'customOrder.createdDate', },
			{ name: 'customOrder.userId' },
			{ name: 'processState.content' },
			{ name: 'customOrder.priority' },
			{ name: 'customOrder.handlePerson' },
			{ name: 'action'}
		],
		loadComplete: function(data) {
			responseDataDispatch = data.rows;

			var ids = jQuery("#process-list-dispatch").jqGrid('getDataIDs');
			var ebtn;
			var actionRow;
			var state = 0;
			for (var i = 0; i < ids.length; i++) {

				var state = responseDataDispatch[i].customOrder.state;

				if (state == "0") {
					ebtn = "<a href='javascript:;' onclick=onAcceptAction('" + i + "')>  " + "受理" + " </a>";
				} else if (state == "1") {
					ebtn = "<a href='javascript:;' onclick=onDispatchAction('" + i + "')>  " + "分配" + " </a>";
				} else if (state == "2") {
					ebtn = "<a href='javascript:;' onclick=onFixAction('" + i + "')>  " + "维修" + " </a>";
				} else if (state == "3") {
					ebtn = "<a href='javascript:;' onclick=onCheckAction('" + i + "')>  " + "审核" + " </a>";
				}
				actionRow = "<div class='tpl-table-black-operation'> " + ebtn + "</div>";

				jQuery("#process-list-dispatch").jqGrid('setRowData', ids[i],
					{
						action: actionRow
					});
			}
		},
		gridComplete : function() {

		},
		viewrecords: true,
		autowidth:true,
		height : '100%',
		rowNum: 15,
		rowList : ['15:15', '30:30', '60:60', '120:120'],
		pager: "#jqGridPager2"
	});

	$('#process-list-dispatch').navGrid("#jqGridPager2", {
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

var responseDataFix;
function initFixGrid(){
	$("#process-list-fix").jqGrid({
		url: 'order/listByState?state=2',
		mtype: "GET",
		styleUI : 'Bootstrap',
		datatype: "json",
		colNames : ['id','工单编号','工单主题','工单类型','创建时间', '发起人','工单状态','优先级','处理人','操作'/*,'操作'*/],
		colModel: [
			{ name: 'id', hidden : true},
			{ name: 'customOrder.id'},
			{ name: 'customOrder.orderTitle'},
			{ name: 'processType.proName'},
			{ name: 'customOrder.createdDate', },
			{ name: 'customOrder.userId' },
			{ name: 'processState.content' },
			{ name: 'customOrder.priority' },
			{ name: 'customOrder.handlePerson' },
			{ name: 'action'}
		],
		loadComplete: function(data) {
			responseDataFix = data.rows;

			var ids = jQuery("#process-list-fix").jqGrid('getDataIDs');
			var ebtn;
			var actionRow;
			var state = 0;
			for (var i = 0; i < ids.length; i++) {

				var state = responseDataFix[i].customOrder.state;

				if (state == "0") {
					ebtn = "<a href='javascript:;' onclick=onAcceptAction('" + i + "')>  " + "受理" + " </a>";
				} else if (state == "1") {
					ebtn = "<a href='javascript:;' onclick=onDispatchAction('" + i + "')>  " + "分配" + " </a>";
				} else if (state == "2") {
					ebtn = "<a href='javascript:;' onclick=onFixAction('" + i + "')>  " + "维修" + " </a>";
				} else if (state == "3") {
					ebtn = "<a href='javascript:;' onclick=onCheckAction('" + i + "')>  " + "审核" + " </a>";
				}
				actionRow = "<div class='tpl-table-black-operation'> " + ebtn + "</div>";

				jQuery("#process-list-fix").jqGrid('setRowData', ids[i],
					{
						action: actionRow
					});
			}
		},
		gridComplete : function() {

		},
		viewrecords: true,
		autowidth:true,
		height : '100%',
		rowNum: 15,
		rowList : ['15:15', '30:30', '60:60', '120:120'],
		pager: "#jqGridPager3"
	});

	$('#process-list-fix').navGrid("#jqGridPager3", {
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

var responseDataCheck;
function initCheckGrid(){
	$("#process-list-check").jqGrid({
		url: 'order/listByState?state=3',
		mtype: "GET",
		styleUI : 'Bootstrap',
		datatype: "json",
		colNames : ['id','工单编号','工单主题','工单类型','创建时间', '发起人','工单状态','优先级','处理人','操作'/*,'操作'*/],
		colModel: [
			{ name: 'id', hidden : true},
			{ name: 'customOrder.id'},
			{ name: 'customOrder.orderTitle'},
			{ name: 'processType.proName'},
			{ name: 'customOrder.createdDate', },
			{ name: 'customOrder.userId' },
			{ name: 'processState.content' },
			{ name: 'customOrder.priority' },
			{ name: 'customOrder.handlePerson' },
			{ name: 'action'}
		],
		loadComplete: function(data) {
			responseDataCheck = data.rows;

			var ids = jQuery("#process-list-check").jqGrid('getDataIDs');
			var ebtn;
			var actionRow;
			var state = 0;
			for (var i = 0; i < ids.length; i++) {

				var state = responseDataCheck[i].customOrder.state;

				if (state == "0") {
					ebtn = "<a href='javascript:;' onclick=onAcceptAction('" + i + "')>  " + "受理" + " </a>";
				} else if (state == "1") {
					ebtn = "<a href='javascript:;' onclick=onDispatchAction('" + i + "')>  " + "分配" + " </a>";
				} else if (state == "2") {
					ebtn = "<a href='javascript:;' onclick=onFixAction('" + i + "')>  " + "维修" + " </a>";
				} else if (state == "3") {
					ebtn = "<a href='javascript:;' onclick=onCheckAction('" + i + "')>  " + "审核" + " </a>";
				}
				actionRow = "<div class='tpl-table-black-operation'> " + ebtn + "</div>";

				jQuery("#process-list-check").jqGrid('setRowData', ids[i],
					{
						action: actionRow
					});
			}
		},
		gridComplete : function() {

		},
		viewrecords: true,
		autowidth:true,
		height : '100%',
		rowNum: 15,
		rowList : ['15:15', '30:30', '60:60', '120:120'],
		pager: "#jqGridPager4"
	});

	$('#process-list-check').navGrid("#jqGridPager4", {
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

var responseDataComplete;
function initCompleteGrid(){
	$("#process-list-complete").jqGrid({
		url: 'order/listByState?state=4',
		mtype: "GET",
		styleUI : 'Bootstrap',
		datatype: "json",
		colNames : ['id','工单编号','工单主题','工单类型','创建时间', '发起人','工单状态','操作'/*,'操作'*/],
		colModel: [
			{ name: 'id', hidden : true},
			{ name: 'customOrder.id'},
			{ name: 'customOrder.orderTitle'},
			{ name: 'processType.proName'},
			{ name: 'customOrder.createdDate', },
			{ name: 'customOrder.userId' },
			{ name: 'processState.content' },
			{ name: 'action'}
		],
		loadComplete: function(data) {
			responseDataComplete = data.rows;

			var ids = jQuery("#process-list-complete").jqGrid('getDataIDs');
			var ebtn;
			var actionRow;
			var state = 0;
			for (var i = 0; i < ids.length; i++) {

				var state = responseDataComplete[i].customOrder.state;

				if (state == "0") {
					ebtn = "<a href='javascript:;' onclick=onAcceptAction('" + i + "')>  " + "受理" + " </a>";
				} else if (state == "1") {
					ebtn = "<a href='javascript:;' onclick=onDispatchAction('" + i + "')>  " + "分配" + " </a>";
				} else if (state == "2") {
					ebtn = "<a href='javascript:;' onclick=onFixAction('" + i + "')>  " + "维修" + " </a>";
				} else if (state == "3") {
					ebtn = "<a href='javascript:;' onclick=onCheckAction('" + i + "')>  " + "审核" + " </a>";
				} else if (state == "4") {
					ebtn = "<a href='javascript:;' onclick=onCompleteDetailAction('" + i + "')>  " + "详情" + " </a>";
				}
				actionRow = "<div class='tpl-table-black-operation'> " + ebtn + "</div>";

				jQuery("#process-list-complete").jqGrid('setRowData', ids[i],
					{
						action: actionRow
					});
			}
		},
		gridComplete : function() {
		},
		viewrecords: true,
		autowidth:true,
		height : '100%',
		rowNum: 15,
		rowList : ['15:15', '30:30', '60:60', '120:120'],
		pager: "#jqGridPager5"
	});

	$('#process-list-complete').navGrid("#jqGridPager5", {
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

function onAcceptAction(index) {
	var id = responseDataAccept[index].customOrder.id;
	$.get("/workProcess/process/start", { orderId: id},function(data){
		if (data.code == 1) {
			alert("成功");
			jQuery("#process-list-accept").trigger("reloadGrid");
		}
	});
}

function onDispatchAction(index) {
	var id = responseDataDispatch[index].customOrder.id;
	$.ajax({
		url : "handle-process.html",
		asycn : false,
		data: {id:id},
		success : function(data) {
			$('.tpl-content-wrapper').empty();
			$('.tpl-content-wrapper').append(data);
		}
	});
}

function onFixAction(index) {
	var id = responseDataFix[index].customOrder.id;
	$.ajax({
		url : "handle-process.html",
		asycn : false,
		data: {id:id},
		success : function(data) {
			$('.tpl-content-wrapper').empty();
			$('.tpl-content-wrapper').append(data);
		}
	});
}

function onCheckAction(index) {
	var id = responseDataCheck[index].customOrder.id;
	$.ajax({
		url : "handle-process.html",
		asycn : false,
		data: {id:id},
		success : function(data) {
			$('.tpl-content-wrapper').empty();
			$('.tpl-content-wrapper').append(data);
		}
	});
}


function onCompleteDetailAction(index) {
	var id = responseDataComplete[index].customOrder.id;
	$.ajax({
		url : "handle-process.html",
		asycn : false,
		data: {id:id},
		success : function(data) {
			$('.tpl-content-wrapper').empty();
			$('.tpl-content-wrapper').append(data);
		}
	});
}

