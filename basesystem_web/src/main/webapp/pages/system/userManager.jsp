<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@ include  file="/pages/jstl.jsp"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <title>用户管理</title>
	<link href="<%=path%>/css/lhgdialog/default.css" rel="stylesheet" id="lhgdialoglink">
    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
    <link href="<%=path%>/images/favicon.ico" rel="shortcut icon" type="image/x-icon">
    <!--框架必需start-->
    <link href="<%=path%>/css/style/learunui-startmenu.css" rel="stylesheet">
    <link href="<%=path%>/css/style/learunui-accordion.css" rel="stylesheet">
    <link href="<%=path%>/css/style/learunui-framework.css" rel="stylesheet">
    <link href="<%=path%>/css/WdatePicker.css" rel="stylesheet" type="text/css">
    <link href="<%=path%>/css/icon.css" rel="stylesheet" type="text/css">
     <!-- JQuery alertify组件，右下角提示弹框-->
    <link href="<%=path%>/css/alertify/alertify.core.css" rel="stylesheet" type="text/css">
    <link href="<%=path%>/css/alertify/alertify.default.css" rel="stylesheet" type="text/css">
    <!-- 树 -->
    <link href="<%=path%>/js/easyui/easyui.css" rel="stylesheet" type="text/css">
    <!-- 表格  -->
    <link href="<%=path%>/css/JQTable/css/jquery.mytables.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/JQTable/css/jquery.mask.css" rel="stylesheet" type="text/css" />
    <link href="<%=path%>/css/main.css" rel="stylesheet" type="text/css" />
    <!-- bootstrap -->
    <link href="<%=path%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
  
    <style type="text/css">
        body
        {
            margin: 0;
            padding: 0;
            font-family: Arial, Helvetica, sans-serif;
            font-size: 12px;
        }
        .test
        {
            color: red !important;
            text-align: center !important;
        }
    </style>
    
 		<script src="/js/common/jquery-1.8.2.min.js"></script
     <!-- bootstrap -->
    <script src="/js/common/learunui-framework.js" type="text/javascript"></script>
    <!--框架必需end-->
    <!--引入弹窗组件start-->
    <script src="/js/common/lhgdialog.min.js" type="text/javascript"></script>
    <!-- 初始化组件js -->
    <script src="/js/common/index.js" type="text/javascript"></script>
    <!-- JQuery alertify组件，右下角提示弹框-->
    <script src="/js/alertify/alertify.min.js" type="text/javascript"></script>
    <!-- 树 -->
    <script src="/js/easyui/jquery.easyui.min.js" type="text/javascript"></script>
    <!-- 表格 -->
    <script src="/js/JQTable/jquery.mask.js" type="text/javascript"></script>
    <script src="/js/JQTable/jquery.mytables.js" type="text/javascript"></script>
      <!-- validate验证 -->
    <script src="/bootstrap/js/import/jquery.validate.js"></script>
	<!-- 这里引入的是校验提示信息的JS文件 -->
	<script src="/bootstrap/js/import/messages_zh.js"></script>
	<!--
	  自定义js
	-->
    <script src="/pages/js/main.js" type="text/javascript"></script>
	<script src="/pages/system/js/userManager.js" type="text/javascript"></script>
  </head>
  
  <body onload="reSize();">
		<div class="bd">
		    <div class="ScrollBar">
		    	  <!--中间-->
					    <div style="position: absolute; z-index: 99;" class="layoutPanel layout-center">
					                <div class="tools_bar">
										  <div class="PartialButton">
											   <span id="refresh_btn" class="tools_btn icon-center-arrow_refresh"><div style="margin-top:20px">刷新</div></span>
<!--											   <span id="" class="tools_btn icon-center-page_white_magnify"><div style="margin-top:20px">高级查询</div></span>-->
											   <div class="tools_separator"></div>
						                	   <c:set value="0" var="count1"></c:set>
										  	   <c:if test="${menuMap['user_icon-center-add']!=null}">
							                	  <c:set value="${count1+1}" var="count1"></c:set>
												   <span id="add_btn" class="tools_btn icon-center-add"><div style="margin-top:20px">新增</div></span>
										  	   </c:if>
										  	   <c:if test="${menuMap['user_icon-center-edit']!=null}">
							                	  <c:set value="${count1+1}" var="count1"></c:set>
											   	   <span id="edit_btn" class="tools_btn icon-center-edit"><div style="margin-top:20px">编辑</div></span>
										  	   </c:if>
										  	   <c:if test="${menuMap['user_icon-center-delete']!=null}">
							                	  <c:set value="${count1+1}" var="count1"></c:set>
											  		<span id="del_btn" class="tools_btn icon-center-delete"><div style="margin-top:20px">删除</div></span>
										  	   </c:if>
										  	   <c:if test="${count1>0}">
											  	  <div  class="tools_separator"></div>
										  	   </c:if>
										  	    <c:set value="0" var="count2"></c:set>
										  	   <c:if test="${menuMap['user_icon-center-key']!=null}">
										  	   		 <c:set value="${count2+1}" var="count1"></c:set>
											   		<span id="reset_password" class="tools_btn icon-center-key"><div style="margin-top:20px">重置密码</div></span>
										  	   </c:if>
										  	   <c:if test="${menuMap['user_icon-control_power_blue']!=null}">
										  	   		 <c:set value="${count2+1}" var="count1"></c:set>
											   		<span id="enabled" class="tools_btn icon-control_power_blue"><div style="margin-top:20px">停启用</div></span>
										  	   </c:if>
										  	   <c:if test="${count2>0}">
											   	<div class="tools_separator"></div>
											   </c:if>
										  	   <c:if test="${menuMap['user_icon-center-group']!=null}">
											   	 <span id="configure_role" class="tools_btn icon-center-group"><div style="margin-top:20px">角色配置</div></span>
										  	   </c:if>
										  </div> 
							        </div><!-- end  tools_bar-->
							        	<div class="search">
											<form id="userForm" name="form1" method="post">
												<label>登录名：</label>
												<input type="text" name="userName"  class="text" placeholder="请输登陆名"/>
												&nbsp;&nbsp;
												<label>真实姓名：</label>
												<input type="text" name="realName" class="text"  placeholder="请输入用户真实姓名"/>
												&nbsp;
											    <input id="a_search" type="button" class="button" value="搜索"/>
											</form>
										</div><!-- end search -->
							        <div id="mainTable" style="overflow:hidden;">
								        <div class="M_DivHeader">
								            <div class="M_DivHeader_Icon">
								            </div>
								            <span class="M_DivHeader_Title">账号列表</span>
								        </div>
								        <div id="userGrid" class="M_DivTable">
								        </div>
							        </div>
					    </div><!-- end  layout-center-->
		    </div><!-- end  -->
</div>
	</div>
  </body>
</html>
