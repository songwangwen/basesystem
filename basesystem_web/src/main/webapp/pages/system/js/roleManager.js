
var divkuangH = undefined;
var divkuangW = undefined;
$(function(){
		//修改高宽自适应屏幕
		$(window).resize(function () {
			reSize();
		});
		//tree.init();//左侧组织架构树初始化
		grid.init();//表格初始化
		/******************************鼠标监听事件**********************************/
		$("#a_search").click(function(){grid.search()});//鼠标单击搜索按钮
		$("#refresh_btn").click(function(){grid.refresh()});//鼠标单击刷新按钮
		$("#add_btn").click(function(){grid.add()});//鼠标单击新增按钮
		$("#edit_btn").click(function(){grid.edit()});//编辑按钮
		$("#del_btn").click(function(){grid.del()});//删除按钮
		$("#menu_authority").click(function(){grid.editMenuAuthority()});//操作权限，即操作权限
		$("#data_authority").click(function(){grid.editDataAuthority()});//数据权限，即查看数据的权限
		
		/******************************键盘监听事件**********************************/
		$(document).keydown(function(e){
			if(e.keyCode==13){
				grid.search();
			}
		});
		/******************注册按钮点击事件*************************************/
	});
	
/**
 * 表格初始化代码
 * songwangwen
 */
var gridURL = "/role/list.json";//代维专业人员信息
var grid = {
	    init:function () {
		    $("#roleGrid").JTables({
		        sAjaxSource: gridURL,
		        //sWidth:"1024px",
		        aoColumns: [
		            { "cName": "id", "cHide": true},
		            { "cName": "roleName","cText": "角色名称","cSort": true,"cWidth":"150"},
		            { "cName": "remark","cText": "备注信息","cSort": false,"cWidth":"400","fnRender": function (obj) {
		            	var h = obj;
	                    if(h&&h.length>35){
	                    	h = h.substr(0,35);
	                    	h+="......";
	                    }
		            	return h;
	            	}
		            },
		            { "cName": "createTime","cText": "创建时间","cSort": true,"cWidth":"200"}
		        ],
		        bSelectAll: true,//显示复选框
		        bSelectCol: "id", //选定的ID
		        bShowPage: true, //显示分页
		        fnCallback: function () {
		    		//加载完成触发方法
		    		//alert('完成加载');
		        },
		        oPageSize:10,//每页数据条数
		        oSortCol: "id",//排序字段
		        oSortType: "desc",//排序方式
		        bProcessing: true,
		        mask_height: "150px",
		        bSelectPageSize: true,
		        bLockTableHead: true,//表头固定
                lockTHScrollC: window
		    });
	    },
	    search:function(){
	    	var jsonForm  = createParamForm("roleForm");
	    	$$.listSubmit(4,jsonForm,'roleGrid');
	    },
	    refresh:function(){
	    	var jsonForm  = {};
	    	$$.listSubmit(4,jsonForm,'roleGrid');
	    },
	    add:function(){
	    	var addUrl = "/pages/system/html/roleForm.html?v="+Math.random();
	    	openDialog(addUrl, "addNewRole", "新增角色",650, 350, function (iframe) {
	    		top.frames[iframe].AcceptClick(function(){
	    			grid.refresh();
	    		});
	  	    });
	    },
	    edit:function(){
	    	var r = $$.getSelectedRows("roleGrid");
	    	if(r.length==0){
	    		top.msg.warning('请选择需要编辑的角色！');
	    	}
	    	if(r.length>1){
	    		top.msg.warning('一次只能选择一个角色！');
	    	}
	    	if(r.length==1){
		    	var addUrl = "/pages/system/html/roleForm.html?v="+Math.random()+"&KeyValue="+r[0].id;
		    	openDialog(addUrl, "editRole", "编辑角色",650, 350, function (iframe) {
		    		top.frames[iframe].AcceptClick(function(){
		    			grid.refresh();
		    		});
		  	    });
	    	}
	    	
	    },
	    del:function(){
	    	//所有选中数据
	    	var r = $$.getSelectedRows("roleGrid");
	    	if(r.length==0){
	    		top.msg.warning('请先选中需要删除的数据！');
	    	}else if(r.length>0){
	    		top.msg.confirm("确认删除角色["+r[0].roleName+"]?",function(){
	    			var delUrl = "/role/removeRole.json";
	    			AjaxJson(delUrl,{"id":r[0].id}, function (data) {
	    				top.msg.ok(data.msg);
	                    if(data.flag){
	                    	//刷新界面
	                    	grid.refresh();
	                    }
	                });
	    			
	    		});
	    	}
	    },
	     editMenuAuthority:function(){
	    	var r = $$.getSelectedRows("roleGrid");
	    	if(r.length==0){
	    		top.msg.warning('请选择需要配置操作权限的角色！');
	    	}
	    	if(r.length>1){
	    		top.msg.warning('一次只能选择一个角色！');
	    	}
	    	if(r.length==1){
		    	var addUrl = "/pages/system/html/editMenuAuthority.html?v="+Math.random()+"&KeyValue="+r[0].id;
		    	top.openDialog(addUrl, "editMenuAuthority", "配置操作权限",650, 350,function (iframe) {
		    		top.frames[iframe].AcceptClick();
		  	    });
	    	}
	    },
	    editDataAuthority:function(){
	    	var r = $$.getSelectedRows("roleGrid");
	    	if(r.length==0){
	    		top.msg.warning('请选择需要配置数据权限的角色！');
	    	}
	    	if(r.length>1){
	    		top.msg.warning('一次只能选择一个角色！');
	    	}
	    	if(r.length==1){
	    		alert('数据权限');
	    	}
	    }
	};

	/**
	 * 改变标签的高宽属性
	 * @return
	 */
	function reSize(){
			divkuangH = top.document.documentElement.clientHeight-90;
			divkuangW = top.document.documentElement.clientWidth-215;
			$(".layoutPanel").css("width",divkuangW+"px");
			/*
	        $(".bd").css("height",divkuangH+"px");
	        divkuangH = divkuangH-5;
	        $(".layout-center").css("left","2px");
	        $(".layout-center").css("height",divkuangH+"px");
	        $(".layout-center").css("width",divkuangW+"px");
	        $(".M_DivHeader").css("width",(divkuangW)+"px");
	        $(".M_DivTable").css("height",(divkuangH-50)+"px");
	        $(".M_DivTable").css("width",(divkuangH-50)+"px");
	        $(".M_DivTable").css("height",(divkuangH-50)+"px");
	        $("#mainTable").css("height",(divkuangH-80)+"px");
	        $(".div_table").css("height",(divkuangH-150)+"px");
//	        alert((divkuangH-250));*/
	 }