<%@page import="java.text.SimpleDateFormat"%>
<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>商户通知消息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link rel="stylesheet" href="<%=path %>/51cwz/dist/css/bootstrap.min.css">
    <style type="text/css">
        body {
            min-height: 1000px;
            padding-top: 70px;
        }
    </style>
</head>
<body>

<s:action name="getheader" namespace="/c2c" executeResult="true" ignoreContextParams="true"></s:action>

<div class="container" style="padding-top:20px; width: 100%;">
    <div class="row">      	
        <div class="col-md-12">
        <!-- 过滤条件 -->
        <div class="panel panel-info" style="padding: 20px;">
					<div class="panel-heading">
						<form action="/providerPush/pushMessageList.htm" method="post" name="myForm">
							<div class="row">
								<div class="col-md-4">
									<div class="input-group" >
										<span class="input-group-addon">发布时间：</span>
										<input type="text" class="form-control" name="publishTime" 
										id = "publishTime" onclick="SelectDate(this,'yyyy-MM-dd')" value="${publishTime}">
									</div>
								</div>
								<div class="col-md-4">
									<div class="input-group">
										<span class="input-group-addon">内容：</span>
										<input type="text" class="form-control" id="content-search" name="content" value="${content}"/>
										<span class="input-group-btn">
											<input type="submit" class="btn btn-default"  value="筛选">
										</span>
									</div>
								</div>
								<div class="col-md-4">
									<div class="pull-right" style="float;padding-top:5px;">
										<div class="form-group">
											<a href="javascript:add();" data-toggle="modal"  class="btn btn-warning">发布通知</a>
										</div>
									</div>
								</div>
							</div>
							</form>
					</div>
				</div>
			</div>
		</div>
        <!-- 过滤条件 -->
    <!-- 通知消息列表 -->
    <div class="row">
        <div class="col-md-12">
            <h3>商户通知</h3>
            <table class="table table-bordered table-striped" id="messageList">
                <thead>
                <tr style="text-align: center;">
              		<th style="width: 6%;">序号</th>
                    <th style="width: 35%;">通知内容</th>
                    <th style="width: 15%;">通知时间</th>
                    <th style="width: 10%;">通知方式</th>
                    <th style="">通知区域/商户</th>
                    <th style="width: 10%;">操作</th>
                </tr>
                </thead>
                <tbody>
                <!-- 订单记录 S -->
                <s:if test="messageList == null || messageList.size()==0">
                	<tr>
                		<td colspan="11" align="center">亲，没有更多的纪录了！</td>
                	</tr>
                </s:if>
                <s:else>
                <s:iterator value="messageList" id="message" status="row">
                <tr>
              		<td><s:property value="#row.count" /></td>
                    <td><s:property value="#message.get('content')" /></td>
                    <td><s:property value="#message.get('pushtime')" /></td>
                    <td><s:property value="#message.get('pushtype')" /></td>
                    <td><s:property value="#message.get('cityNames')" /></td>
                    <td class="text-center">
                        <a href="javaScript:void(0)" onclick="editMessage('<s:property value="#message.get('id')" />')" class="text-primary">修改</a>
                        <a href="javaScript:void(0)" onclick="deleteMessage('<s:property value="#message.get('id')" />')" class="text-primary">删除</a>
                    </td>
                </tr>
                </s:iterator>
                </s:else>
                <!-- 订单记录 E -->
                </tbody>
            </table>
           	<input type="hidden" id="currentPage" value="${pageModel.page}" />
				<ul class="pagination pull-right">
	            		<span>共检索出${pageModel.totalCount }条数据</span>
	            		<span>第${pageModel.page}页/共${pageModel.pageCount }页</span>
	            		<a href="javaScript:void(0);" onclick="topage(1);">首页</a>
	            		<c:if test="${pageModel.page>1 }">
	            			<a href="javaScript:void(0);" onclick="topage(${pageModel.prev});" >上一页</a>
	            		</c:if>
	            		<c:forEach var="p" items="${requestScope.pageModel.prevPages }" varStatus="items">
	            			<a href="javaScript:void(0);" onclick="topage(${p});">${p }</a>
	            		</c:forEach>
	            		<span>${pageModel.page }</span>
	            		<c:forEach var="next" items="${requestScope.pageModel.nextPages }" >
	            			<a href="javaScript:void(0);" onclick="topage(${next});">${next }</a>
	            		</c:forEach>
	            		<c:if test="${pageModel.page<pageModel.last }">
	            			<a href="javaScript:void(0);" onclick="topage(${pageModel.next});">下一页</a>
	            		</c:if>
	            		<a href="javaScript:void(0);" onclick="topage(${pageModel.last});">尾页</a>
	            </ul>
        </div>
    </div><!-- end row -->
    <!-- 订单列表 -->
    
 </div><!-- /container -->
<script src="http://cdn.bootcss.com/jquery/1.10.2/jquery.min.js"></script>
<script src="<%=path %>/51cwz/dist/js/bootstrap.min.js"></script>
<script type="text/javascript" src="<%=path %>/51cwz/dist/js/adddate.js"></script>
<script type="text/javascript">
	jQuery(document).ready(function(){
		//生成结算单操作会触发表单提交动作，故而这里做return false 的控制
	});	

	//跳转至添加通知界面
	function add(){
		document.myForm.action="/providerPush/toAddPushMessage.htm";
		document.myForm.submit(); 
	}

	//跳转至修改界面
	function editMessage(id){
		document.myForm.action="/providerPush/toEditPushMessage.htm?messageId="+id;
		document.myForm.submit(); 
	}
	//跳转至修改界面
	function deleteMessage(id){
		if(confirm("删除不可恢复，是否确定删除当前通知记录?")){
			document.myForm.action="/providerPush/deletePushMessage.htm?messageId="+id;
			document.myForm.submit(); 
		}
	}
	//分页使用
	function topage(index){
		document.myForm.action = "/providerPush/pushMessageList.htm?pageIndex=" + index;
		document.myForm.submit();
	}
</script>
</body>
</html>