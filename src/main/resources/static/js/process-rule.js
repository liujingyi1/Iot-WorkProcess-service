$(document).ready(function () {
	initAmmeterGrid();
	initDeviceGrid();
	initRepairGrid();
	initAlarmGrid();
});

function initAmmeterGrid(){
	$("#ammeter").jqGrid({
	    url: 'charts/ydDetail',
	    mtype: "GET",
		styleUI : 'Bootstrap',
	    datatype: "json",
	    colNames : ['id','设备类型','设备组别','设备名称', '设备地址','MAC地址','状态','上月读数','当月读数','用电量','金额','安装时间','设备负责人'/*,'操作'*/],
	    colModel: [
	    	{ name: 'id', hidden : true},
	        { name: 'deviceCategory'},
	        { name: 'deviceGroup'},
	        { name: 'deviceName'},
	        { name: 'address'},
	        { name: 'deviceMAC' },
	        { name: 'status' },
	        
	        { name: 'lastMonth' },
	        { name: 'currMonth' },
	        { name: 'uesed' },
	        { name: 'je' },
	        
	        { name: 'installedDate' },
	        { name: 'installBy' },
//	        {
//				name : '',
//				fixed : true,
//				sortable : false,
//				resize : false,
//				formatter : 'actions',
//				formatoptions : {
//					keys : false,
//					editOptions:{
//						url: 'level/save',
//						recreateForm: true, 
////						beforeShowForm : beforeEditCallback
//					}
//				}
//			}
	    ],
		viewrecords: true,
		autowidth:true,
		height : '100%',
	    rowNum: 15,
	    rowList : ['15:15', '30:30', '60:60', '120:120'],
	    pager: "#jqGridPager"
    });
	
	$('#ammeter').navGrid("#jqGridPager", {                
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


function initDeviceGrid(){
	$("#device").jqGrid({
	    url: 'charts/ydDetail',
	    mtype: "GET",
		styleUI : 'Bootstrap',
	    datatype: "json",
	    colNames : ['id','设备类型','设备组别','设备名称', '设备地址','MAC地址','状态','上月读数','当月读数','用电量','金额','安装时间','设备负责人','操作'],
	    colModel: [
	    	{ name: 'id', hidden : true},
	        { name: 'deviceCategory'},
	        { name: 'deviceGroup'},
	        { name: 'deviceName'},
	        { name: 'address'},
	        { name: 'deviceMAC' },
	        { name: 'status' },
	        
	        { name: 'lastMonth' },
	        { name: 'currMonth' },
	        { name: 'uesed' },
	        { name: 'je' },
	        
	        { name: 'installedDate' },
	        { name: 'installBy' },
	        {
				name : '',
				fixed : true,
				sortable : false,
				resize : false,
				formatter : 'actions',
				formatoptions : {
					keys : false,
					editOptions:{
						url: 'level/save',
						recreateForm: true, 
//						beforeShowForm : beforeEditCallback
					}
				}
			}
	    ],
		viewrecords: true,
		autowidth:true,
		height : '100%',
	    rowNum: 15,
	    rowList : [10, 20, 50, 100],
	    pager: "#jqGridPager2"
    });
}


function initRepairGrid(){
	$("#repair").jqGrid({
	    url: 'charts/ydDetail',
	    mtype: "GET",
		styleUI : 'Bootstrap',
	    datatype: "json",
	    colNames : ['id','设备类型','设备组别','设备名称', '设备地址','MAC地址','状态','上月读数','当月读数','用电量','金额','安装时间','设备负责人','操作'],
	    colModel: [
	    	{ name: 'id', hidden : true},
	        { name: 'deviceCategory'},
	        { name: 'deviceGroup'},
	        { name: 'deviceName'},
	        { name: 'address'},
	        { name: 'deviceMAC' },
	        { name: 'status' },
	        
	        { name: 'lastMonth' },
	        { name: 'currMonth' },
	        { name: 'uesed' },
	        { name: 'je' },
	        
	        { name: 'installedDate' },
	        { name: 'installBy' },
	        {
				name : '',
				fixed : true,
				sortable : false,
				resize : false,
				formatter : 'actions',
				formatoptions : {
					keys : false,
					editOptions:{
						url: 'level/save',
						recreateForm: true, 
//						beforeShowForm : beforeEditCallback
					}
				}
			}
	    ],
		viewrecords: true,
		autowidth:true,
		height : '100%',
	    rowNum: 15,
	    rowList : [10, 20, 50, 100],
	    pager: "#jqGridPager3"
    });
}


function initAlarmGrid(){
	$("#alarm").jqGrid({
	    url: 'charts/ydDetail',
	    mtype: "GET",
		styleUI : 'Bootstrap',
	    datatype: "json",
	    colNames : ['id','类型编号','类型名称','设备厂商', '归属领域','备注'],
	    colModel: [
	    	{ name: 'id', hidden : true},
	        { name: 'deviceCategory'},
	        { name: 'category'},
	        { name: 'manufacturer'},
	        { name: 'chapter'},
	        { name: 'remarks',search:false }
	    ],
		viewrecords: false,
		autowidth:true,
		height : '100%',
	    rowNum: 15,
	    rowList : [10, 20, 50, 100],
	    pager: "#jqGridPager4"
    });
	
	$('#alarm').navGrid("#jqGridPager4", {                
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


