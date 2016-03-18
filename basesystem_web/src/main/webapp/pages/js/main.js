//左右移动
$(document).ready(function(){   
	   	var p = 0;
       	$("#left_btn").click(function(){
       		var liW = calculUL();//li的总宽度
       		var divW = $(".mod-hd").width();
       		if((liW>divW)&&(divW-liW)<p){//只有过宽，部分隐藏li，才能移动
       			p-=120;
       			$("#tabs_container").css('margin-left',p+"px");
       		}
        });   
  
       $("#right_btn").click(function(){   
    	   	var liW = calculUL();//li的总宽度
      		var divW = $(".mod-hd").width();
      		if(liW>divW&&p<0){//只有过宽，部分隐藏li，才能移动
      			p=p>0?0:p+=120;
      			$("#tabs_container").css('margin-left',p+"px");
      		}
       });   
       
       /**
        * 重新登录,异步验证
        */
       $("#reload_btn").click(function(){
    	    var jsonForm  = createParamForm("reloginForm");
    	    var url = "/login/relogin.json";
    	    AjaxJson(url,{"jsonForm":jsonForm}, function (data) {
    	    	alert(data.msg);
            });
       });
});   

//计算是否需要左右移动
function calculUL(){
	var ll = 0;
	$("#tabs_container li").each(function(){
		ll+=$(this).outerWidth(true); 
	});
	return ll;
}

function linkAddTabMenu() {
            $('.btn-nav-toggle').removeAttr('disabled');
            $('.btn-nav-toggle').removeClass('harvest');
            $('.btn-nav-toggle').trigger("click");
            //点击Tab事件
            $('#tabs_container li').click(function () {
                var id = $(this).attr('id');
                if (id == 'tabs_Imain') {
                    $('.btn-nav-toggle').attr('disabled', 'disabled');
                    //点击首页（显示导航菜单）
                    $(".navigation").css('position', '');
                    $(".navigation").css('width', '204');
                    $('.accordion').show();
                    $('.btn-nav-toggle').addClass('harvest');
                    $('.btn-nav-toggle').find('b').hide();
                    $('.btn-nav-toggle').find('i').show();
                    $('.btn-nav-toggle').attr('title', '');
                } else {
                    $('.btn-nav-toggle').removeAttr('disabled');
                    //（隐藏导航菜单）
                    $(".navigation").css('position', 'absolute');
                    $('.btn-nav-toggle').removeClass('harvest');
                    $('.btn-nav-toggle').trigger("click");
                }
            });
        }
        //初始化界面UI效果
        function InitializeImpact() {
            //设置自应高度
            resizeU();
            $(window).resize(resizeU);
            //手风琴效果
            var Accordion = function (el, multiple) {
                this.el = el || {};
                this.multiple = multiple || false;
                var links = this.el.find('.link');
                links.on('click', { el: this.el, multiple: this.multiple }, this.dropdown)
            }
            Accordion.prototype.dropdown = function (e) {
                //计算高度
                var accordionheight = ($("#accordion").children("ul li").length * 36);
                var navigationheight = $(".navigation").height()
                $('#accordion li').children('.b-children').height(navigationheight - accordionheight - 1);
                $(this).next().slideToggle();
                $(this).parent().toggleClass('open');
                if (!e.data.multiple) {
                    $(this).parent().parent().find('.submenu').not($(this).next()).slideUp().parent().removeClass('open');
                };
            }
            $(".submenu a").click(function () {
                $('.submenu a').removeClass('action');
                $(this).addClass('action');
            })
            var accordion = new Accordion($('#accordion'), false);
            $("#accordion li:first").find('div').trigger("click");//默认第一个展开
            $('.btn-nav-toggle').click(function () {
                if (!$('.btn-nav-toggle').attr('disabled') && !$(this).hasClass("harvest")) {
                    $(this).addClass('harvest');
                    $(".navigation").animate({ width: 0 }, 100);
                    $('.accordion').hide();
                    $(this).find('b').show();
                    $(this).find('i').hide();
                } else {
                    $(this).removeClass('harvest');
                    $(".navigation").animate({ width: 204 }, 100);
                    $('.accordion').show();
                    $(this).find('b').hide();
                    $(this).find('i').show();
                }
            }).hover(function () {
                if ($(this).hasClass("harvest")) {
                    $(this).attr('title', '隐藏导航');
                    $(this).removeClass('harvest');
                    $(".navigation").animate({ width: 204 }, 100);
                    $('.accordion').show();
                    $(this).find('b').hide();
                    $(this).find('i').show();
                    $(".navigation").css('position', 'absolute');
                }
            }, function () {
            });
        }
        /*导航菜单begin====================*/
        //导航一级菜单
        function GetAccordionMenu() {
            //var html = "<li title='系统管理' class=''><div class='link'><span class='icon icon-system'>系统管理</span><i class='chevron-down'></i></div><ul class='submenu b-children' style='height: 303px; display: block;'><li title=\"用户管理\" onclick=\"AddTabMenu('11', 'pages/system/userManager.jsp', '用户管理',  'icon-user','true');\"><a><span class='icon icon-user'>用户管理</span></a></li><li title=\"角色管理\" onclick=\"AddTabMenu('12', 'pages/system/roleManager.jsp', '角色管理',  'icon-group','true');\"><a><span class='icon icon-group'>角色管理</span></a></li></ul></li>";
            var html = "";
            AjaxJson("/menu/list.json",{}, function (data) {
                $.each(data, function (i) {
                    var node = data[i];
                    html+="<li title='"+node.nodeName+"' class=''>";
                    html+="<div class='link'><span class='icon "+node.iconCls+"'>"+node.nodeName+"</span><i class='chevron-down'></i></div>"
                    html+="<ul class='submenu b-children' style='height: 303px; display: block;'>";
                    for(var j=0;j<node.children.length;j++){
						var child = node.children[j];
	                    html+="<li title=\""+child.nodeName+"\" onclick=\"AddTabMenu('"+child.nodeId+"', '"+child.url+"', '"+child.nodeName+"',  '"+child.iconCls+"','true');\"><a><span class='icon "+child.iconCls+"'>"+child.nodeName+"</span></a></li>";
                    }
                    html+="</ul>";
                    html+="</li>";
                });
            });
		    $("#accordion").append(html);
        }
        //导航子菜单
        function GetSubmenu(ModuleId, _class) {
            var submenu = "<ul class=\"submenu " + _class + "\">";
            $.each(accordionJson, function (i) {
                if (accordionJson[i].ParentId == ModuleId) {
                    if (IsBelowMenu(accordionJson[i].ModuleId) > 0) {
                        submenu += "<li title=" + accordionJson[i].FullName + "><a class=\"link\"><img src='../Content/Images/Icon16/" + accordionJson[i].Icon + "'><span>" + accordionJson[i].FullName + "</span><i class=\"submenu-chevron-down\"></i></a>";
                        submenu += GetSubmenu(accordionJson[i].ModuleId, "c-children")
                        submenu += "</li>";
                    } else {
                        submenu += "<li title=" + accordionJson[i].FullName + " onclick=\"AddTabMenu('" + accordionJson[i].ModuleId + "', '" + RootPath() + accordionJson[i].Location + "', '" + accordionJson[i].FullName + "',  '" + accordionJson[i].Icon + "','true');linkAddTabMenu()\"><img src='../Content/Images/Icon16/" + accordionJson[i].Icon + "'><a><span>" + accordionJson[i].FullName + "</span></a></li>";
                    }
                }
            });
            submenu += "</ul>";
            return submenu;
        }
        //判断是否有子节点
        function IsBelowMenu(ModuleId) {
            var count = 0;
            $.each(accordionJson, function (i) {
                if (accordionJson[i].ParentId == ModuleId) {
                    count++;
                    return false;
                }
            });
            return count;
        }
        /*导航菜单end====================*/
        
        
        /*===========================alertify 控件==================================================*/
        var reset = function () {
            alertify.set({
                labels : {
                    ok     : "确认",
                    cancel : "取消"
                },
                delay :5500,			//提示持续时间（ms）
                buttonReverse : false,
                buttonFocus   : "ok"
            });
        };
        alertify.warning = alertify.extend('warning');
        var msg = {
        	alert:function(message){
        		alertify.alert(message);
        	},
            ok: function(message) {
        		reset();
                alertify.success(message);
            },
            error: function(message) {
            	reset();
                alertify.error(message);
            },
            warning: function(message) {
            	reset();
                alertify.warning(message);
            },
            confirm:function(message,handler){
            	reset();
            	alertify.confirm(message, function (e) {
            		if (e) {
            			//alertify.success("点击确认");
            			handler();
            		} else {
            			alertify.warning("点击取消");
            		}
            	});
            },
            prompt:function(message){
            	reset();
            	return alertify.prompt(message, function (e, str) {
            		if (e) {
            			alertify.success("点击确认，输入内容为: " + str);
            		} else {
            			alertify.warning("点击取消");
            		}
            	}, "默认值");
            }
        };
        
        
        /**
         主要是推荐这个函数。它将jquery系列化后的值转为name:value的形式。
         * @param o
         * @return
         */
        function convertArray(o) { 
            var v = {};
            for (var i in o) {
                if (o[i].name != '__VIEWSTATE') {
                    if (typeof (v[o[i].name]) == 'undefined')
                        v[o[i].name] = o[i].value;
                    else
                        v[o[i].name] += "," + o[i].value;
                }
            }
            return v;
        }
        /**
	        将form内容打包成为json格式数据，主要用于查询条件传值
         * @param formId 需要序列化的Form的id
         * @return
         */
        function createParamForm(formId){
            var o = {};
            var query = $('#'+formId).serializeArray();
            query = convertArray(query);
            o.jsonEntity = JSON.stringify(query);
            var result = {};
            result.jsonForm = encodeURIComponent(encodeURIComponent(JSON.stringify(o)));
            return result;
        }
        
        /**
	        自定义的Object对象打包为for吗样式
         * @param obj js Object对象 其中Key必须是实体类中你的属性
         * @return
         */
        function createParam(query){
        	var o = {};
        	o.jsonEntity = JSON.stringify(query);
        	var result = {};
        	result.jsonForm = encodeURIComponent(encodeURIComponent(JSON.stringify(o)));
        	return result;
        }
        
       Array.prototype.contains = function(item){
		    for(i=0;i<this.length;i++){
		        if(this[i]==item){return true;}
		    }
		    return false;
	  };
