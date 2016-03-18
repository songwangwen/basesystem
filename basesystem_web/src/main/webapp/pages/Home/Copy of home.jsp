<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE HTML>
<html>
  <head>
    <title>My JSP 'roleManager.jsp' starting page</title>
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
  </head>
  <script type="text/javascript">
  	function cc(){
		var ss = top.msg.confirm("删除?",function(){alert(123);});
  	}
  	function bb(){
		var ss = top.msg.prompt("输入值");
  	}

  	function Support() {
  	    top.Dialog("/pages/Home/SupportPage.html", "Support", "7 × 24 技术支持服务", 600, 275);
  	}

  //关于我们
  	function About() {
  	    top.alertDialog("力软信息化系统快速开发框架LR-FDMS<br>版本4.1<br>上海力软信息技术有限公司<br>保留所有权利", 0);
  	}

  //快捷方式设置
  	function Shortcuts() {
  	    var url = "/Home/Shortcuts";
  	    top.openDialog(url, "Shortcut", "快捷方式设置", 500, 300, function (iframe) {
  	        top.frames[iframe].AcceptClick()
  	    });
  	}
  </script>
  <style>
  	a {font-size:16px}   
	a:link {color: blue; text-decoration:none;} //未访问：蓝色、无下划线   
	a:active:{color: red; } //激活：红色   
	a:visited {color:purple;text-decoration:none;} //已访问：紫色、无下划线   
	a:hover {color: red; text-decoration:underline;} //鼠标移近：红色、下划线   
  </style>
  <body>
     <a href="javascript:top.msg.alert('msg');">弹框</a><br/>
     <a href="javascript:top.msg.ok('hello');;">成功</a><br/>
     <a href="javascript:top.msg.error('hello');">失败</a><br/>
     <a href="javascript:top.msg.warning('hello');;">警告</a><br/>
     <a href="javascript:cc();">确认</a><br/>
     <a href="javascript:bb();">输入</a><br/>
     <hr/>
     <a href="javascript:Support();">弹出框（无按钮）</a><br/>
     <a href="javascript:About();">弹出框（确认按钮）</a><br/>
     <a href="javascript:Shortcuts();">弹出框（确认、取消按钮）</a><br/>
  </body>
</html>
