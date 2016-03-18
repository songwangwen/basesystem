
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
		$("#reset_password").click(function(){grid.resetPassword()});//重置密码
		$("#enabled").click(function(){grid.changeEnabled()});//停用启用
		$("#configure_role").click(function(){grid.configureRole()});//设置用户角色
		
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
var gridURL = "/user/list.json";//代维专业人员信息
var grid = {
	    init:function () {
		    $("#userGrid").JTables({
		        sAjaxSource: gridURL,
		        //sWidth:"2024",
		        aoColumns: [
		            { "cName": "id", "cHide": true},
		            { "cName": "userName","cText": "登录名","cSort": true,"cWidth":"150"},
		            { "cName": "realName","cText": "真实姓名","cSort": true,"cWidth":"200"},
		            { "cName": "lastLoginIp","cText": "最后登录IP","cSort": true,"cWidth":"200"},
		            { "cName": "lastLoginTime","cText": "最后登录时间","cSort": true,"cWidth":"200"
		            },
		            { "cName": "roleNames", "cText": "角色", "cSort": false,"cWidth":"200","fnRender": function (obj) {
	                    return obj;
	            	},"cHide": false },
		            { "cName": "enabled", "cText": "是否有效", "cSort": false,"cWidth":"100",
		                "fnRender": function (obj) {
		                    if(!obj){
		                		return "<span style='color:red'>停用</span>";
		                     }else{
		                    	 return  "<span style='color:green'>启用</span>";
		                     }
		            	},
		                 "cHide": false }
		        ],
		        bSelectAll: true,
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
	    	var jsonForm  = createParamForm("userForm");
	    	$$.listSubmit(4,jsonForm,'userGrid');
	    },
	    refresh:function(){
	    	var jsonForm  = {};
	    	$$.listSubmit(4,jsonForm,'userGrid');
	    },
	    add:function(){
	    	var addUrl = "/pages/system/html/userForm.html?v="+Math.random();
	    	openDialog(addUrl, "addNewUser", "新增用户",650, 350, function (iframe) {
	    		top.frames[iframe].AcceptClick(function(){
	    			grid.refresh();
	    		});
	  	    });
	    },
	    edit:function(){
	    	var r = $$.getSelectedRows("userGrid");
	    	if(r.length==0){
	    		top.msg.warning('请选择需要编辑的用户！');
	    	}
	    	if(r.length>1){
	    		top.msg.warning('一次只能选择一个用户！');
	    	}
	    	if(r.length==1){
		    	var addUrl = "/pages/system/html/userForm.html?v="+Math.random()+"&KeyValue="+r[0].id;
		    	openDialog(addUrl, "editUser", "编辑用户",650, 350, function (iframe) {
		    		top.frames[iframe].AcceptClick(function(){
		    			grid.refresh();
		    		});
		  	    });
	    	}
	    	
	    },
	    del:function(){
	    	//所有选中数据
	    	var deleteURL = "/user/removeUser.json";
	    	var r = $$.getSelectedRows("userGrid");
	    	if(r.length==0){
	    		top.msg.warning('请先选中需要删除的用户！');
	    	}if(r.length>1){
	    		top.msg.warning('一次只能删除一个用户！');
	    	}else{
	    		top.msg.confirm("删除将不可恢复,确认删除选中用户["+r[0].realName+"]?",function(){
	    			AjaxJson(deleteURL,{"id":r[0].id}, function (data) {
	                	if(data.flag){//成功
	    	                javascript:top.msg.ok(data.msg);
		    				grid.refresh();
	                	}else{
	                		//失败
	    	                javascript:top.msg.error(data.msg);
	                	}
	                });
	    		});
	    	}
	    },
	    resetPassword:function(){
	    	var URL = "/user/resetPassword.json";
	    	var r = $$.getSelectedRows("userGrid");
	    	if(r.length==0){
	    		top.msg.warning('请先选中重置密码的用户！');
	    	}else if(r.length>0){
	    		top.msg.confirm("确定重置用户["+r[0].realName+"]的登录密码?",function(){
	    			AjaxJson(URL,{"id":r[0].id}, function (data) {
	                	if(data.flag){//成功
	    	                javascript:top.msg.ok(data.msg);
		    				grid.refresh();
	                	}else{
	                		//失败
	    	                javascript:top.msg.error(data.msg);
	                	}
	                });
	    		});
	    	}
	    },
	    changeEnabled:function(){
	    	var URL = "/user/enabled.json";
	    	var r = $$.getSelectedRows("userGrid");
	    	if(r.length==0){
	    		top.msg.warning('请先选中停/启用的用户！');
	    	}if(r.length>1){
	    		top.msg.warning('一次只能停/启用一个用户！');
	    	}else{
	    		top.msg.confirm("确定将【"+r[0].realName+"】设置为"+(r[0].enabled?"停用":"启用")+"?",function(){
	    			AjaxJson(URL,{"id":r[0].id}, function (data) {
	                	if(data.flag){//成功
	    	                javascript:top.msg.ok(data.msg);
		    				grid.refresh();
	                	}else{
	                		//失败
	    	                javascript:top.msg.error(data.msg);
	                	}
	                });
	    		});
	    	}
	    },
	    configureRole:function(){
	    	var URL = "/user/configureRole.json";
	    	var r = $$.getSelectedRows("userGrid");
	    	if(r.length==0){
	    		top.msg.warning('请先选中配置角色的用户！');
	    	}if(r.length>1){
	    		top.msg.warning('一次只能配置一个用户！');
	    	}else{
	    		var url = "/pages/system/html/UserRole.html?v="+Math.random()+"&userId="+r[0].id;
		    	openDialog(url, "configureUserRole", "配置用户角色",650, 350, function (iframe) {
		    		top.frames[iframe].AcceptClick(function(){
		    			grid.refresh();
		    		});
		  	    });
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