<%@ page language="java" import="java.util.*" pageEncoding="utf-8"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE html>
<html>
	  <head>
	    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	    <meta http-equiv="X-UA-Compatible" content="IE=EmulateIE9">
	    <title>橙牛汽车管家业务管理系统</title>
	    <link rel="apple-touch-icon" href="/images/favicon.png">
	    <link rel="icon" href="/images/favicon.png">
	    <!--框架必需start-->
		<link href="/css/lhgdialog/default.css" rel="stylesheet">
	    <link href="/css/style/learunui-startmenu.css" rel="stylesheet">
	    <link href="/css/style/learunui-accordion.css" rel="stylesheet">
	    <link href="/css/style/learunui-framework.css" rel="stylesheet">
	    <link href="/css/WdatePicker.css" rel="stylesheet" type="text/css">
	    <link href="/css/icon.css" rel="stylesheet" type="text/css">
	     <!-- JQuery alertify组件，右下角提示弹框-->
	    <link href="/css/alertify/alertify.core.css" rel="stylesheet" type="text/css">
	    <link href="/css/alertify/alertify.default.css" rel="stylesheet" type="text/css">
	     <!-- bootstrap -->
	    <link href="/bootstrap/css/bootstrap.min.css" rel="stylesheet">
	    
	     <!-- bootstrap Jquery 1.9以上 -->
<%--	    <script src="/js/common/jquery-1.9.1.min.js"></script>--%>
<%--	    <script src="/js/jquery/dist/jquery.min.js"></script>--%>
	    <script src="/js/common/jquery-1.8.2.min.js"></script>
	     <!-- bootstrap -->
<%--	    <script src="/bootstrap/js/import/bootstrap.min.js"></script>--%>
	    <script src="/js/common/learunui-framework.js"></script>
	    <script src="/js/common/scrollbar.js"></script>
	    <!--框架必需end-->
	    <!--引入弹窗组件start-->
	    <script src="/js/common/lhgdialog.min.js"></script>
	    <!-- 初始化组件js -->
	    <script src="/js/common/index.js"></script>
	    <!-- JQuery alertify组件，右下角提示弹框-->
	    <script src="/js/alertify/alertify.min.js"></script>
	    <script src="/pages/js/main.js"></script>
	    
	    <script>
		    var basePath = "<%=basePath%>";
	        //alert(basePath);
	        /**初始化**/
	        $(document).ready(function () {
	            //ServerCurrentTime();//
	            AddTabMenu('Imain', '/pages/Home/home.jsp', '欢迎首页', "icon-house_star", 'false');
	            GetAccordionMenu();//加载菜单
	            InitializeImpact(); //初始化界面UI效果
	            //快捷方式
	            $(".popup li").click(function () {
	                linkAddTabMenu()
	            })
	        });
	    </script>
	    <style type="text/css">
	    	.input-group{
	    		height:45px;
	    	}
	    	.input-group input{
	    		height:45px;
	    	}
	    </style>
	</head>
 <body onbeforeunload="PageClose()" onselectstart="return false;" style="-moz-user-select: none; overflow: hidden;">
	  <div style="left: 0px; top: 0px; visibility: hidden; position: absolute;" class=""> 
	   <table class="ui_border">
	    <tbody>
	     <tr>
	      <td class="ui_lt"></td>
	      <td class="ui_t"></td>
	      <td class="ui_rt"></td>
	     </tr>
	     <tr>
	      <td class="ui_l"></td>
	      <td class="ui_c">
	       <div class="ui_inner">
	        <table class="ui_dialog">
	         <tbody>
	          <tr>
	           <td colspan="2">
	            <div class="ui_title_bar">
	             <div class="ui_title" unselectable="on" style="cursor: move;"></div>
	             <div class="ui_title_buttons">
	              <a class="ui_min" href="javascript:void(0);" title="最小化" style="display: inline-block;"><b class="ui_min_b"></b></a>
	              <a class="ui_max" href="javascript:void(0);" title="最大化" style="display: inline-block;"><b class="ui_max_b"></b></a>
	              <a class="ui_res" href="javascript:void(0);" title="还原"><b class="ui_res_b"></b><b class="ui_res_t"></b></a>
	              <a class="ui_close" href="javascript:void(0);" title="关闭(esc键)" style="display: inline-block;">&times;</a>
	             </div>
	            </div></td>
	          </tr>
	          <tr>
	           <td class="ui_icon" style="display: none;"></td>
	           <td class="ui_main" style="width: auto; height: auto;">
	            <div class="ui_content" style="padding: 0px;"></div></td>
	          </tr>
	          <tr>
	           <td colspan="2">
	            <div class="ui_buttons" style="display: none;"></div></td>
	          </tr>
	         </tbody>
	        </table>
	       </div></td>
	      <td class="ui_r"></td>
	     </tr>
	     <tr>
	      <td class="ui_lb"></td>
	      <td class="ui_b"></td>
	      <td class="ui_rb" style="cursor: se-resize;"></td>
	     </tr>
	    </tbody>
	   </table>
	  </div> 
	  <div id="ajax-loader" style="position: fixed; top: -50%; left: -50%; width: 200%; height: 200%; z-index: 100; overflow: hidden; display: none; background: rgb(255, 255, 255);"> 
	   <img src="/images/ajax-loader.gif" style="position: absolute; top: 0; left: 0; right: 0; bottom: 0; margin: auto;" /> 
	  </div>
    <!-- header -->
    <div class="header">
        <div class="logo fleft">
            <img src="/images/logo.png"/>
        </div>
        <div id="Headermenu">
            <ul id="topnav">
                <li id="metnav_1" class="list">
                    <a id="nav_1" onclick="Replace();">
                        <span class="c1"></span>
                        	系统首页
                    </a>
                </li>
                <li id="metnav_7" class="list droppopup">
                    <a id="nav_7" class="">
                        <span class="c7"></span>快捷导航
                        <div class="popup" style="display: none; top: 74px; left: 851px;">
                            <i></i>
                            <ul>
                                <li onclick="Shortcuts()">
                                    <img src="/images/shortcuts.png">快捷方式设置</li>
                                <div id="Shortcuts"></div>
                            </ul>
                        </div>
                    </a>
                </li>
                <li id="metnav_3" class="list droppopup">
                    <a id="nav_3" class="">
                        <span class="c3"></span>帮助中心
                        <div class="popup" style="display: none; top: 74px; left: 945px;">
                            <i></i>
                            <ul>
                                <li onclick="alert(&#39;测试关闭&#39;)">
                                    <img src="/images/help.png">查看帮助</li>
                                <li title="将反馈建议提交给开发商进行解决" onclick=" window.open(&#39;http://mail.qq.com/cgi-bin/qm_share?t=qm_mailme&amp;email=uICJioyLiYuPivjJyZbb19U&#39;,&#39;_blank&#39;)">
                                    <img src="/images/email_open.png">反馈建议</li>
                                <li onclick="Support()">
                                    <img src="/images/premium_support.png">技术支持</li>
                                <li onclick="About()">
                                    <img src="/images/information.png">关于我们</li>
                            </ul>
                        </div>
                    </a>
                </li>
                <li id="metnav_2" class="list" onclick="SkinIndex()">
                    <a id="nav_2">
                        <span class="c2"></span>切换皮肤
                    </a>
                </li>
                <li id="metnav_5" class="list" onclick="PersonCenter()">
                    <a id="nav_5">
                        <span class="c5"></span>个人中心
                    </a>
                </li>
                <li id="metnav_4" class="list" onclick="IndexOut();">
                    <a id="nav_4">
                        <span class="c4"></span>
                        	安全退出
                    </a>
                </li>
            </ul>
        </div><!-- Headermenu -->
    </div><!-- header -->
    <div class="taskbarTabs">
        <div id="navigationtitle">
            <div id="CurrentTime" style="text-align:left;padding-left:5px">
           		欢迎使用：
            	<font style="font-size:14px;color:red">${loginUser.realName}</font>
            </div>
        </div>
        <div id="left_btn" style="float:left;padding-right:5px;height:36px;vertical-align:middle;cursor:pointer;">
	           <img alt="上一个" src="/css/icon/48/go-previous.png" 
	            style="width:24px;height:24px;cursor:pointer;margin:2px 2px 4px 2px;">
        </div>
        <!-- 分隔线 -->
        <div class="header_separator"></div>
        <div style="float:left">
            <div id="dww-menu" class="mod-tab">
                <div class="mod-hd">
                    <ul id="tabs_container" class="tab-nav">
                    </ul>
                </div>
                <!-- 当前选中的Tab的ID -->
                <input id="ModuleId" type="hidden" value="Imain">
            </div>
            <div class="rightMenu" style="display: none;">
                <ul>
                    <li onclick="ThisResrshTab()">刷新当前</li>
                    <div class="m-split"></div>
                    <li onclick="ThisCloseTab()">关闭当前</li>
                    <li onclick="AllcloseTab()">全部关闭</li>
                    <li onclick="othercloseTab()">除此之外全部关闭</li>
                </ul>
            </div>
        </div>
        <!-- 分隔线 -->
        <div id="right_btn" style="float:right;padding-right:5px;height:36px">
        		<div class="header_separator"></div>
	            <img alt="下一个" src="/css/icon/48/go-next.png" 
	            style="width:24px;height:24px;cursor:pointer;margin-bottom:4px">
        </div>
    </div><!-- taskbarTabs -->
    <div class="mainPannel">
         <div  class="navigation">
		  <ul id="accordion" class="accordion" style="display: block;"> 
		  </ul>
        </div>
        <div id="overlay_navigation"></div>
        <div id="ContentPannel" style="border:1px solid #fff;">
        </div>
    </div><!-- mainPannel -->
    <div id="footer" class="cs-south" style="height: 25px;">
        <div class="number" style="width: 30%; text-align: left; float: left; line-height: 25px;">
            &nbsp;技术支持：<a href="http://www.ponshine.com/" target="_blank" style="color: white;cursor:pointer;">杭州达则科技有限公司</a>
        </div>
        <div class="number" style="width: 40%; text-align: center; float: left; line-height: 25px;">
            CopyRight 2009-2015 All Rights Reserved.
        </div>
        <div class="clear" style="display:none;"></div>
    </div><!-- end footer -->
    <!--载进度条start-->
    <div id="loading_background" class="loading_background" style="display: none;"></div>
    <div id="loading" onclick="Loading(false);" style="left: 42%; display: none;">
        <img src="/images/loading.gif" style="vertical-align: middle;">&nbsp;<span>正在加载,请稍候...</span>&nbsp;
    </div>
    <div id="loadingGird">
        <img src="/images/loading.gif" style="vertical-align: middle;">&nbsp;正在加载,请稍候...&nbsp;
    </div>
    <!-- 重新登录弹出框 -->
	<div class="modal fade" id="relogin" tabindex="-1" role="dialog" aria-labelledby="addCityModalLabel">
	    <div class="modal-dialog" role="document" style="width:450px">
	        <div class="modal-content">
	            <div class="modal-header">
	                <h3 class="modal-title" id="addCityModalLabel">登录超时，请重新登录</h3>
	            </div>
	            <div class="modal-body">
	                 <form class="form-signin" name="reloginForm" method="post">
									<div class="input-group" >
										<span class="input-group-addon">用户名</span>
										<input type="text" class="form-control" name="username"
										id = "reload_username"  value="${loginUser.userName}">
									</div>
									<br/>
									<div class="input-group">
										<span class="input-group-addon">&nbsp;密&nbsp;&nbsp;码&nbsp;</span>
										<input type="password" class="form-control" id="reload_password" name="password"/>
									</div>
				     </form>
	            </div>
	            <div class="modal-footer" style="text-align:center;">
	                <button type="button" id="reload_btn" class="btn btn-lg btn-primary btn-block">登录</button>
	            </div>
	        </div>
	    </div>
	</div>
  </body>
</html>
