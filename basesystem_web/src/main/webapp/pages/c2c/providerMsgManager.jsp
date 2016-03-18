<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<jsp:include  page="/jstl.jsp"/>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
    <base href="<%=path%><%=basePath%>">
    
    <title>商户通知消息</title>
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <!-- Bootstrap -->
    <link href="<%=path %>/bootstrap/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<!-- 主要内容 S -->
<!-- 管理区 S-->
<%--<div class="container-fluid">--%>
<div>
    <div class="row">
        <!-- 表格区 S -->
        <div class="col-sm-9 col-sm-offset-3 col-md-10 col-md-offset-2">
            <div class="panel panel-primary">
                <div class="panel-body clearfix">
                    <h3 class="page-header pull-left">
                        服务分组列表
                    </h3>
                    <div class="pull-right">
                        <a href="group_add.html" class="btn btn-primary">添加服务分组</a>
                    </div>
                </div>
            </div>
            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">快捷入口组</h3>
                </div>

                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th>分组名称</th>
                            <th style="width: 15%">是否显示名称</th>
                            <th style="width: 15%">首页展示数</th>
                            <th style="width: 10%">包含服务数量</th>
                            <th style="width: 18%">最后修改时间</th>
                            <th style="width: 15%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>快捷入口组</td>
                            <td>否</td>
                            <td>0</td>
                            <td>5</td>
                            <td>2015-08-09 16:26:10</td>
                            <td><a href="group_detail.html">管理分组</a></td>
                        </tr>
                    </table>
                </div>
            </div>

            <div class="panel panel-default">
                <div class="panel-heading">
                    <h3 class="panel-title">普通服务组</h3>
                </div>
                <div class="table-responsive">
                    <table class="table table-bordered table-striped">
                        <thead>
                        <tr>
                            <th style="width: 10%">序号</th>
                            <th>分组名称</th>
                            <th style="width: 15%">是否显示名称</th>
                            <th style="width: 15%">首页展示数</th>
                            <th style="width: 10%">包含服务数量</th>
                            <th style="width: 18%">最后修改时间</th>
                            <th style="width: 15%">操作</th>
                        </tr>
                        </thead>
                        <tbody>
                        <tr>
                            <td>1</td>
                            <td>成为专车司机</td>
                            <td>是</td>
                            <td>3</td>
                            <td>5</td>
                            <td>2015-08-09 16:26:10</td>
                            <td><a href="group_detail.html">管理分组</a></td>
                        </tr>
                        <tr>
                            <td>2</td>
                            <td>上门保养</td>
                            <td>否</td>
                            <td>3</td>
                            <td>5</td>
                            <td>2015-08-09 16:26:10</td>
                            <td><a href="group_detail.html">管理分组</a></td>
                        </tr>
                        <tr>
                            <td>3</td>
                            <td>汽车电商</td>
                            <td>是</td>
                            <td>3</td>
                            <td>5</td>
                            <td>2015-08-09 16:26:10</td>
                            <td><a href="group_detail.html">管理分组</a></td>
                        </tr>
                        </tbody>
                    </table>
                </div>
                <div class="panel-footer text-right">
                    <a href="group_sort.html" class="btn btn-primary">分组排序</a>
                </div>
            </div>
<!--分组数量应该不会很多，暂时可以不考虑分页问题-->
        </div>
        <!-- 表格区 E -->
    </div>
</div>
<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
<script src="<%=path%>/js/common/jquery-1.9.1.min.js"></script>
<script src=<%=path %>/bootstrap/dist/js/bootstrap.min.js"></script>
</body>
</html>