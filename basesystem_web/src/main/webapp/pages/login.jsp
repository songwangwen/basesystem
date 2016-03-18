<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
	  <head>
	    <head><meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
	    <title>橙牛汽车管家业务管理系统</title>
	    <link rel="apple-touch-icon" href="<%=path%>/images/favicon.png">
	    <link rel="icon" href="<%=path%>/images/favicon.png">
	    <!-- bootstrap -->
	    <link href="<%=path%>/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	    <style type="text/css">
        body {
            padding-top: 40px;
            padding-bottom: 40px;
            background-color: #eee;
        }

        .form-signin {
            max-width: 330px;
            padding: 15px;
            margin: 0 auto;
        }
        .form-signin .form-signin-heading,
        .form-signin .checkbox {
            margin-bottom: 10px;
        }
        .form-signin .checkbox {
            font-weight: normal;
        }
        .form-signin .form-control {
            position: relative;
            font-size: 16px;
            height: auto;
            padding: 10px;
            -webkit-box-sizing: border-box;
            -moz-box-sizing: border-box;
            box-sizing: border-box;
        }
        .form-signin .form-control:focus {
            z-index: 2;
        }
        .form-signin input[type="text"] {
            margin-bottom: -1px;
            border-bottom-left-radius: 0;
            border-bottom-right-radius: 0;
        }
        .form-signin input[type="password"] {
            margin-bottom: 10px;
            border-top-left-radius: 0;
            border-top-right-radius: 0;
        }
    </style>
	</head>
	<body>
		<div class="container" id="login">
		    <form class="form-signin" role="form" id="mainform"  action="<%=path %>/login/main.htm" method="post" onsubmit="toSubmi();">
		        <h2 class="form-signin-heading">用户登录</h2>
		        <input type="text" id="name" name="username" class="form-control" placeholder="用户名" value="songwangwen" autofocus>
		        <input type="password" id="pass" name="password" class="form-control" placeholder="密码"  value="123456">
		        <input type="submit"  class="btn btn-lg btn-primary btn-block" id="btn" value="登录">
		        <div id="ts" style="display:block;"><center><font color="red" id="error">${errorMsg}</font></center></div>
		    </form>
		</div>
	</body>
	<script type="text/javascript">
		function toSubmi(){
			var name = document.getElementById("name").value;
			var pass = document.getElementById("pass").value;
			if(name.trim()==""){
			    document.getElementById("error").innerHTML="用户名必须填写";
			    document.getElementById("name").value = "";
				return false;
			}
			if(pass==""){
			    document.getElementById("error").innerHTML="密码必须填写";
				return false;
			}
			return true;
		}
	</script>
</html>
