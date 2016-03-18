<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%@taglib prefix="s" uri="/struts-tags"%>
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
</style>
</head>
<body>
<s:action name="getheader" namespace="/c2c" executeResult="true" ignoreContextParams="true"></s:action>
	<div class="container">
		<h4>${title}</h4>
		<hr/>
		<!-- 输入条件 -->
        <div class="panel panel-info container" style="padding: 20px;">
				<form action="/providerPush/addPushMessage.htm" method="post" onsubmit="return toSub();">
					<div class="panel-heading">
								<div class="form-group row">
									<label class="col-sm-2 control-label">通知内容</label>
									<div class="col-sm-7">
										<textarea style="width: 400px;height: 100px;" maxlength="200" rows="1" cols="1" id="content"
										name="pushMessage.content" class="form-control get-control" 
										placeholder="必填项,最多200个字符，支持图文混排，支持超链接" class="input-xlarge" required></textarea>
									</div>
								</div>
								<div class="form-group row">
									<label class="col-sm-2 control-label">通知时间</label>
										<div class="col-sm-7">
									          <input size="16" id="pushtime" type="text" name="pushMessage.pushtimeStr" 
									          value="${currentTime}"  class="form-control get-control" readonly="readonly"
									          style="height: 34px;width:200px">
									    </div>
								</div>
								<div class="form-group row">
									<label class="col-sm-2 control-label">发送范围类型</label>
									<div>
									   <label class="checkbox-inline">
									      <input type="radio" name="pushMessage.pushtype" id="optionsRadios3" 
									         value="1"  checked="checked" onclick="checkChange(1);"> 地区
									   </label>
									   <label class="checkbox-inline">
									      <input type="radio" name="pushMessage.pushtype" id="optionsRadios4" 
									         value="2" onclick="checkChange(2);">服务商
									   </label>
									</div>
								</div>
								<div id="cityDiv" class="form-group row">
									<label class="col-sm-2 control-label">地区</label>
									<div class="col-sm-7">
										<a href="javascript:initCityTree();" class="btn btn-primary">选择地区</a>
										<textarea id="cityids_textarea" style="width: 400px;height: 100px;" maxlength="200" rows="1" cols="1" id="content"
										placeholder="点击“选择地区”按钮选择通知发布的地区" 
										class="form-control get-control"  required readonly="readonly"></textarea>
										<input id="cityids" type="hidden" name="pushMessage.cityids" value=""/>
									</div>	
								</div>
								<div id="providerDiv" class="form-group row" style="display:none;">
									<label class="col-sm-2 control-label">服务商</label>
									<div class="col-sm-7">
										<a href="javascript:initProviderTree();" class="btn btn-primary">选择商户</a>
										<textarea style="width: 400px;height: 100px;" maxlength="200" rows="1" cols="1" id="providerids_textarea"
										placeholder="点击“选择商户”按钮选择通知的商户" 
										class="form-control get-control"  required readonly="readonly"></textarea>
										<input id="providerids" type="hidden" name="pushMessage.providerids" value=""/>
									</div>	
								</div>
							<div class="row">
								<div class="col-md-4">
									<div class="pull-right" style="float;padding-top:5px;">
										<div class="form-group">
											<input type="submit" class="btn  btn-success"  value="发布">
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
	<!-- 弹出层，用于增加省市信息-start -->
	<div class="modal fade" id="addCity" tabindex="-1" role="dialog"
			aria-labelledby="myReviewLabel" aria-hidden="true">
			<div class="modal-dialog">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title" id="myEditOwnerInfoLabel">城市信息</h4>
					</div>
					<div class="modal-body" style="height:400px;overflow:scroll;">
						<ul id="cityTree" class="easyui-tree"></ul>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>
						<button type="button" id="addCitys"  class="btn btn-success" onclick="addCitys();">确定</button>
					</div>
				</div>
			</div>
		</div>
	<!-- 弹出层，用于增加省市信息 --end-->
	<!-- 弹出层，用于增加商户信息-start -->
	<div class="modal fade" id="addProviders" tabindex="-1" role="dialog" aria-labelledby="myReviewLabel" aria-hidden="true" width="700px">
			<div class="modal-dialog" style="width:850px">
				<div class="modal-content">
					<div class="modal-header">
						<button type="button" class="close" data-dismiss="modal">&times;</button>
						<h4 class="modal-title" id="myEditOwnerInfoLabel">选择商户</h4>
					</div>
					<div class="modal-body" style="height:410px;overflow:scroll;">
						<div id="main_layout" class="easyui-layout" style="width:800px;height:350px;" >
							<div id="body_west" data-options="region:'west',split:true,collapsible:false" style="width:210px;height:345">
									<ul id="provider_cityTree" class="easyui-tree"></ul>
							</div>
							<div data-options="region:'center'" style="padding:10px" style="width:500px;height:345">
								<table class="table table-bordered table-striped" id="providerTable" style="width:550px">
						                <thead>
						                <tr style="text-align: center;">
						              		<th style="width: 6%;"><input type="checkbox" id="all"/></th>
						              		<th style="width: 10%;">城市</th>
						              		<th style="width: 45%;">商户名称</th>
						                    <th style="width: 20%;">手机/登录名</th>
						                    <th>所属用户</th>
						                </tr>
						                </thead>
						                <tbody id="providerTable_body">
						                </tbody>
						            </table>
							</div>
						</div>
					</div>
					<div class="modal-footer">
						<button type="button" class="btn btn-warning" data-dismiss="modal">取消</button>
						<button type="button" id="addCitys"  class="btn btn-success" onclick="addProviders();">确定</button>
					</div>
				</div>
			</div>
		</div>
	<!-- 弹出层，用于增加商户信息-end -->
	</div><!-- end container -->
	<script src="<%=path%>/51cwz/dist/js/ajaxfileupload.js"></script>
	<script type="text/javascript">
		$(function(){ 
			//$("input[name='pushMessage.pushtype'][value=1]").attr("checked",true); 
			//商户列表全选全不选
			$("#all").click(function(){    
			    if(this.checked){    
			        $("input[name='providerId_check']").prop("checked","checked");   
			    }else{    
			        $("input[name='providerId_check']").prop("checked",null); 
			    }    
			});  
			//手动设置左侧树状结构的宽度
			$('#main_layout').layout('panel','west').panel('resize',{width:210});
			$('#main_layout').layout('panel','west').panel('resize',{height:350});
			$('#main_layout').layout('resize');
			$(".layout-panel-center").css("left","220px");
		}); 
		$('#pushtime').datetimepicker({
	        language:  'zh-CN',
	        format: 'yyyy-mm-dd hh:ii'
	    });

		//改变单选按钮时，显示隐藏地区或商户的其中一项
	    function checkChange(param){
			if(param==1){//显示地市
				 document.getElementById("cityDiv").style.display="block";
				 document.getElementById("providerDiv").style.display="none";
			}else if(param==2){//显示商户
				 document.getElementById("cityDiv").style.display="none";
				 document.getElementById("providerDiv").style.display="block";
			}
		}

		//初始化城市树
	    function initCityTree(){
		    var cityIds = $("#cityids").val();
	    	$('#cityTree').tree({  
	    		   url:'/providerPush/cityTreeJson.htm?cityIds='+cityIds,
	    		   method:'get',
	    		   animate:true,
	    		   checkbox:true
	    	}); 
			$('#addCity').modal('show');
		}

		//初始化商户选择
		function initProviderTree(){
			$('#provider_cityTree').tree({  
	    		   url:'/providerPush/cityTreeJson.htm',
	    		   method:'get',
	    		   animate:true,
	    		   checkbox:false,
	    		   onClick:function(node) {//单击加载表格数据
			 			if(node.children==null||node.children==undefined||node.children.length==0){
			 				$.post("/providerPush/queryProviderJsonByCityId.htm",{"cityId":node.id}, function(result){
						 			var data = eval("(" + result + ")");
			 					    var s = "";
			 					    if(data.length==0){
										s="<tr><td  colspan='5' style='text-align:center;'>该地市暂无服务商信息</td></tr>";
				 					}else{
				 				        for(var i=0;i<data.length;i++){
					 				        var id = data[i].id;
					 				        var cityName = data[i].cityName;
					 				        var fullName = data[i].fullName;
					 				        var phone = data[i].phone;
					 				        var operatorName = data[i].operatorName;
					 				        s+="<tr>"+
						 				        "<td><input type='checkbox' id='providerId_"+id+"' name='providerId_check' value='"+id+"'></td>"+
						 				        "<td style='cursor:pointer' onclick=selectTR('"+id+"') id='cityName_"+id+"'>"+cityName+"</td>"+
												"<td style='cursor:pointer' onclick=selectTR('"+id+"') id='providerName_"+id+"'>"+fullName+"</td>"+
												"<td style='cursor:pointer' onclick=selectTR('"+id+"') >"+phone+"</td>"+
												"<td style='cursor:pointer' onclick=selectTR('"+id+"') >"+operatorName+"</td></tr>";
					 				    }  
					 				}
				 				    $("#providerTable_body").html(s);
				 				    $("#all").prop("checked",null);
						 	});
						}else{
							if(node.state=='closed'){
				 				$('#provider_cityTree').tree('expand', node.target);
				 			}else{
				 				$('#provider_cityTree').tree('collapse', node.target);
				 			}
						}
			 		}
	    	});
			$('#addProviders').modal('show');
		}

		//点击一行记录，选中或取消选中
		function selectTR(id){
			var o = $("#providerId_"+id);
			var chk=$("#providerId_"+id).is(':checked');
			if(chk==true){
				$("#providerId_"+id).prop("checked",null);
			}else{
				$("#providerId_"+id).prop("checked","checked");
			}
		}

		/**
		将选中的树结构中的id加入到隐藏域中，名称加入到textarea中
		*/
		function addCitys(){
			var nodes = $('#cityTree').tree('getChecked');
			var cityIds = "";
			var cityNames = "";
			var cityIdsArr = new Array();//城市ID集合
			var cityNamesArr = new Array();//城市名称集合
			for(var i=0; i<nodes.length; i++){
				if(nodes[i].id==0){
					cityIds = "all";
					cityNames = "全国";
					break;
				}
				if(nodes[i].children==null||nodes[i].children==undefined||nodes[i].children.length==0){
					cityIdsArr.push(nodes[i].id);
					cityNamesArr.push( nodes[i].text);
				}
			}
			if(cityIds!="all"){
				cityIds = cityIdsArr.join(",");
				cityNames = cityNamesArr.join(",");
			}
			$("#cityids_textarea").val(cityNames);
			$("#cityids").val(cityIds);
			if(cityIds==""){
				alert("至少选中一个城市");
				return false;
			}
			$('#addCity').modal('hide');
		}

		function addProviders(){
			var providerIdArr = new Array();
			var providerNameArr = new Array();
			var oo = $('input[name="providerId_check"]:checked');
			if(oo.length==0){
				alert("至少选中一个商户");
				return false;
			}
			$('input[name="providerId_check"]:checked').each(function(){ 
				var providerId = $(this).val();
				providerIdArr.push(providerId);
				var cityName = $("#cityName_"+providerId).html();
				var providerName = $("#providerName_"+providerId).html();
				providerNameArr.push("("+cityName+")"+providerName);
			}); 
			$("#providerids_textarea").val(providerNameArr.join(","));
			$("#providerids").val(providerIdArr.join(","));
			//关闭遮罩层
			$('#addProviders').modal('hide');
		}

		//提交新增信息
		function toSub(){
			var pushtype = $('input[name="pushMessage.pushtype"]:checked').val();
			if(pushtype==1){//按地区，则地区必选
				var cityids = $("#cityids").val();
				if(cityids==undefined||cityids==""){
					alert('通知地区必须选择!');
					return false;
				}
			}else{
				var providerids = $("#providerids").val();
				if(providerids==undefined||providerids==""){
					alert('通知商户必须选择!');
					return false;
				}
			}
			return true;
		}

	</script>
</body>
</html>