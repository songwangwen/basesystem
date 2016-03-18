<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
<jsp:include  page="/jstl.jsp"/>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%
	String path = request.getContextPath();
	String basePath = request.getScheme() + "://"
			+ request.getServerName() + ":" + request.getServerPort()
			+ path + "/";
%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>发布通知</title>
<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
<link rel="stylesheet" href="<%=path%>/51cwz/dist/css/bootstrap.min.css">
<link href="<%=path%>/51cwz/bootstrap/css/bootstrap-datetimepicker.min.css" rel="stylesheet" media="screen">

<script src="<%=path%>/51cwz/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path%>/51cwz/bootstrap/js/bootstrap-datetimepicker.min.js" charset="UTF-8"></script>
<script type="text/javascript" src="<%=path%>/51cwz/bootstrap/js/locales/bootstrap-datetimepicker.zh-CN.js" charset="UTF-8"></script>
<!-- 树 -->
<link rel="stylesheet" type="text/css" href="<%=path%>/51cwz/easyui/themes/default/easyui.css">
<link rel="stylesheet" type="text/css" href="<%=path%>/51cwz/easyui/themes/icon.css">
<script type="text/javascript" src="<%=path%>/51cwz/easyui/jquery.easyui.min.js"></script>
<style type="text/css">
 body {
	min-height: 1000px;
	padding-top: 70px;
}
span{
	font-size:14px;
}
</style>
</head>
<body>
<s:action name="getheader" namespace="/c2c" executeResult="true" ignoreContextParams="true"></s:action>
	<div class="container">
		<h4>${title}</h4>
		<hr/>
		<!-- 输入条件 -->
        <div class="panel panel-info container" style="padding: 20px;">
				<form action="/providerPush/editPushMessage.htm" method="post">
					<div class="panel-heading">
								<div class="form-group row">
									<label class="col-sm-2 control-label">通知内容</label>
									<div class="col-sm-7">
									    <input type="hidden" name="pushMessage.id" value="${pushMessage.id}"/>
									    <input type="hidden" name="pushMessage.pushtime" value="${pushMessage.pushtime}"/>
									    <input type="hidden" name="pushMessage.pushtype" value="${pushMessage.pushtype}"/>
									    <input type="hidden" name="pushMessage.cityids" value="${pushMessage.cityids}"/>
									    <input type="hidden" name="pushMessage.providerids" value="${pushMessage.providerids}"/>
										<textarea style="width: 400px;height: 100px;" maxlength="200" rows="1" cols="1" id="content"
										name="pushMessage.content" class="form-control get-control" 
										placeholder="必填项,最多200个字符，支持图文混排，支持超链接" class="input-xlarge" required>${pushMessage.content}</textarea>
									</div>
								</div>
								<div class="form-group row">
									<label class="col-sm-2 control-label">通知时间</label>
										<div class="col-sm-7">
									          <input size="16" id="pushtime" type="text"
									          value="${pushMessage.pushtime}"  class="form-control get-control" readonly="readonly"
									          style="height: 34px;width:200px">
									    </div>
								</div>
								<div class="form-group row">
									<label class="col-sm-2 control-label">发送范围类型</label>
									<div class="col-sm-7">
									     <c:if test="${pushMessage.pushtype == 1}">
									         	<span>地区</span>
									     </c:if>
									     <c:if test="${pushMessage.pushtype == 2}">
									         	<span>服务商</span>
									     </c:if>
									</div>
								</div>
								<c:if test="${pushMessage.pushtype == 1}">
									<div id="cityDiv" class="form-group row">
										<label class="col-sm-2 control-label">地区</label>
										<div class="col-sm-7">
											<textarea id="cityids_textarea" style="width: 400px;height: 100px;" maxlength="200" rows="1" cols="1" id="content"
											placeholder="点击“选择地区”按钮选择通知发布的地区" 
											class="form-control get-control"  required readonly="readonly">${pushMessage.cityNames}</textarea>
										</div>	
									</div>
								</c:if>
								<c:if test="${pushMessage.pushtype == 2}">
									<div id="providerDiv" class="form-group row">
										<label class="col-sm-2 control-label">服务商</label>
										<div class="col-sm-7">
											<textarea style="width: 400px;height: 100px;" maxlength="200" rows="1" cols="1" id="providerids_textarea"
											placeholder="点击“选择商户”按钮选择通知的商户" 
											class="form-control get-control"  required readonly="readonly">${pushMessage.providerNames}</textarea>
										</div>	
									</div>
								</c:if>
							<div class="row">
								<div class="col-md-4">
									<div class="pull-right" style="float;padding-top:5px;">
										<div class="form-group">
											<input type="submit" class="btn  btn-success"  value="提交修改">
											&nbsp;&nbsp;
											<a href="javascript:history.go(-1)" data-toggle="modal"  class="btn btn-warning">取消</a>
										</div>
									</div>
								</div>
							</div>
					</div>
			</form>
		<!-- 输入条件 -->
	</div>
	</div><!-- end container -->
	<script src="<%=path%>/51cwz/dist/js/ajaxfileupload.js"></script>
</body>
</html>