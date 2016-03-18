/**
Description: JQuery DataTable Plugin (datagridview)
Date:2014-7-13
@Author:Robin.Shen
@About: http://www.websxh.com
@Version:3.0
**/
(function ($) {
    $.extend({
        tableOptions: {},
        tableData:{}
    })
    $.extend($.fn, {
        JTables: function (options) {
            if (!filter(options)) { //参数格式错误
                return this;
            }

            var options = $.extend({}, $.fn.JTables.defaults, options); //覆盖原有参数
            var oId = $(this).attr("id"); //当前容器ID
            //$.JTablesOptions[oId] = options;
            var _$$ = {
                jFrm: $("#" + oId), //初始化的oId不会存在错误
                init: function () {
                    _$$.setBoxParms({ "PageIndex": options.oPageIndex, "PageSize": options.oPageSize, "SortType": options.oSortType, "SortCol": options.oSortCol ,"Form":{}});  //刚开始为Box设置参数
                    _$$.GetInitData(); //默认获取第一页数据 默认一页5条数据
                },
                //把关于Page的参数进行一次赋值
                setBoxParms: function (parms) {
                    if (parms) {
                        if (parms.PageIndex) {
                            _$$.jFrm.attr("PageIndex", parms.PageIndex);
                        }
                        if (parms.PageSize) {
                            _$$.jFrm.attr("PageSize", parms.PageSize);
                        }
                        if (parms.SortType) {
                            _$$.jFrm.attr("SortType", parms.SortType);
                        }
                        if (parms.SortCol) {
                            _$$.jFrm.attr("SortCol", parms.SortCol);
                        }
                    }
                },
                //初始化首次加载数据
                GetInitData: function () {
                    if (options.dataSource != null) {
                        _$$.SealTable(options.dataSource);
                    } else {
                        _$$.GetData();
                    }
                },
                //获取数据
                GetData: function () {
                	//_$$.jFrm.attr("style", "height:" + _$$.jFrm.height());
                	//_$$.jFrm.html(""); //清空
                    _$$.jFrm.attr("style", "min-height:" + options.mask_height);
                    if (options.bProcessing) {
                        _$$.jFrm.mask('数据加载中...'); //设置Loading
                    }
                    //拼装参数 把客户端参数和Table参数整合
                    var jTabParms = options.sAjaxParams; //拿到客户端参数
                    jTabParms.jPageIndex = _$$.jFrm.attr("PageIndex");
                    jTabParms.jPageSize = _$$.jFrm.attr("PageSize");
                    jTabParms.jSortType = _$$.jFrm.attr("SortType");
                    jTabParms.jSortCol = _$$.jFrm.attr("SortCol");
                    var timestamp=new Date().getTime();
                    if(options.sAjaxSource.indexOf("?")<0){
			    		options.sAjaxSource+="?v="+timestamp;
			    	}else{
			    		options.sAjaxSource+="&v="+timestamp;
			    	}
                    $.ajax({
                        url: options.sAjaxSource,
                        dataType: "json",
                        data: jTabParms,
                        type: "post",
                        cache: false,
                        success: function (data) {
                    		 //如果date==login，表示session过期，需要重新登录
                    		if("login"==data){
                    			top.login();
                    			return false;
                    		}
                    		_$$.jFrm.html(""); //清空
                            _$$.SealTable(data);
                        },
                        error: function (data) {
                        	if(data!=null&&data.responseText=="login"){//需要再次登录
                            	top.login();
                            }else{
                            	alertDialog("系统出错，请刷新重试，如果仍然无法解决，请联系管理员！错误信息:"+data.responseText, -1);
                            }
                            if (options.bProcessing) {
                                _$$.jFrm.unmask();
                            }
                        }
                    })
                },
                //封装表格
                SealTable: function (data) {
                    //重置参数
                    if (data != null && data.pageIndex != null) {
                        _$$.setBoxParms({ "PageIndex": data.pageIndex });
                    }
                    if (data != null && data.pageSize != null) {
                        _$$.setBoxParms({ "PageSize": data.pageSize });
                    }
                    if (data != null && data.sortType != null) {
                        _$$.setBoxParms({ "SortType": data.sortType });
                    }
                    if (data != null && data.sortCol != null) {
                        _$$.setBoxParms({ "SortCol": data.sortCol });
                    }
                    var oid = _$$.jFrm.attr("id");
                    var ths = _$$.GetThs(data.sortType, data.sortCol, oid); //组织THead
                    var trs = "";
                    if (data != null && data.dataList != null) { //返回有数据
                        if (data.dataList.length != 0) {
                            var dataListObj = eval(data.dataList);
                            if (typeof (dataListObj) == "undefined") {
                                _$$.HandlerNoData(ths);
                            } else {
                                if (dataListObj.length != 0) {
                                    for (var i = 0; i < dataListObj.length; i++) {
                                        var tds = _$$.GetTds(dataListObj[i]);
                                        if (tds) {
                                            trs += "<tr  class=\"jTablesTr\">" + tds + "</tr>";
                                        } else {
                                            alert($$.jtabLanguage.lang_formatError);
                                            return;
                                        }
                                    }
                                }
                                if (ths) {
                                    //_$$.jFrm.append('<table id="' + oId + '_tb" cellpadding="0" cellspacing="0" width="100%" class="M_Table"><tbody class="M_Tbody"><tr class="M_Thead">' + ths + '</tr>' + trs); //2014-07-11 修改
                        		    var hh = top.document.documentElement.clientHeight-300;
                                	if(options.sWidth==undefined){
                                		_$$.jFrm.append('<div id="div_'+oId+'" class="div_table" style="height:'+hh+'px;overflow:auto;"><table id="' + oId + '_tb" cellpadding="0" cellspacing="0"  class="M_Table"><thead><tr class="M_Thead">' + ths + '</tr></thead><tbody class="M_Tbody">' + trs);
                                	}else{
                                		var w = options.sWidth;
                                		_$$.jFrm.append('<div id="div_'+oId+'" class="div_table" style="height:'+hh+'px;overflow:auto;"><table id="' + oId + '_tb" cellpadding="0" cellspacing="0" style="width:'+w+'" class="M_Table"><thead><tr class="M_Thead">' + ths + '</tr></thead><tbody class="M_Tbody">' + trs);
                                	}
                                    if (options.bShowPage) { //开启分页
                                        var oid = _$$.jFrm.attr("id");
                                        _$$.jFrm.append(getPageHtml(data.dataCount, data.pageSize, data.pageIndex, options.bSelectPageSize, oid));
                                    }
                                } else {
                                    alert($$.jtabLanguage.lang_formatError);
                                }
                            }
                        } else { //没有数据
                            _$$.HandlerNoData(ths);
                        }
                    } else {  //没有数据
                        _$$.HandlerNoData(ths);
                    }
                    if (options.bProcessing) {
                        _$$.jFrm.unmask(); //移除遮罩效果
                    }
                    _$$.jFrm.removeAttr("style"); //移除min-height 属性
                    _$$.jFrm.hoverTable(); //隔行变色
                    //处理锁表头逻辑
                    if (options.bLockTableHead) {
                        ths = _$$.GetLockHeadThs(data.sortType, data.sortCol, oid); //组织THead;
                        var id = _$$.jFrm.attr("id");
                        $("#" + id + "FloatTH").remove();
                        var lockTH = "<div id='" + id + "FloatTH' class='jqMyTable_floatTH' style='left:" + _$$.jFrm.offset().left + "px;'><table cellpadding='0' cellspacing='0' width='100%' class='M_Table_TH'><tbody class='M_Tbody_TH'><tr>" + ths + "</tr></tbody></table></div></div>";
                        $("body").append(lockTH);
                        //注册鼠标全局滚动监听事件
                        $(window).resize(function () {
                            //处理绝对居左的定位
                            var leftP = _$$.jFrm.offset().left;
                            $(".jqMyTable_floatTH").css("left", leftP);
                        })
                        $(options.lockTHScrollC).bind("scroll", function () {
                            var topP = _$$.jFrm.find(".M_Thead").length == 0 ? 0 : _$$.jFrm.find(".M_Thead").offset().top;
                            var leftP = _$$.jFrm.find(".M_Thead").length == 0 ? 0 : _$$.jFrm.find(".M_Thead").offset().left;
                            var sTopP = $(options.lockTHScrollC).scrollTop();
                            var sLeftP = $(options.lockTHScrollC).scrollLeft(); //左侧距离
                            if ($(options.lockTHScrollC).offset() != undefined) {  //可能是内部滚动条
                                sTopP = $(options.lockTHScrollC).offset().top;
                            }
                            if (topP < sTopP) {
                                if ($(options.lockTHScrollC).offset() != undefined) {  //可能是内部滚动条
                                    $(".jqMyTable_floatTH").css("top", sTopP);
                                }
                                $(".jqMyTable_floatTH").css("left", leftP);
                                $(".jqMyTable_floatTH").show();
                            } else {
                                $(".jqMyTable_floatTH").hide();
                            }
                            if (leftP <= 1) {
                                if (sLeftP >= 0) {
                                    $(".jqMyTable_floatTH").css("left", -sLeftP);
                                }
                            }

                        })
                    }
                    //处理锁表逻辑
                    else if (options.bLockTable) {
                        _$$.HandlerLockTable(oId + "_tb");
                    }
                    //处理合并单元格逻辑
                    if (options.oMergeCells.length != 0) {
                        _$$.jFrm.find("table").mergeCell({
                            cols: options.oMergeCells
                        })
                    }
                    if (options.fnCallback != null) {
                        options.fnCallback(); //执行回调函数
                    }
                    if (options.checkSelected) {
                        $('.jTablesTr').each(function(){
                        	 $(this).children("td").each(function(index,element){
                        		 if(index!=0)
                        		 	$(this).click(function(){
                        		 		//点击任意一个td，选中该行
                        		 		var tr = $(this).parent();
                        		 		var c = $(tr).children(":first").children(":first");//获得checkbox对象
                        		 		if($(c).is(':checked')){//选中则取消
                        		 			$(c).removeAttr("checked");
                        		 			$(tr).removeClass("selected");
                        		 		}else{
                        		 			$(c).attr("checked",true);
                        		 			$(tr).addClass("selected");
                        		 		}
                        		 	});
                        	 });
                        });
                    }
                    if (options.selectedColor) {
                    	$('.jTablesTr').each(function(){
                    		 var tr = $(this);
                        	 $(this).children(":first").children(":first").change(function(){
	                    		if($(this).is(':checked')){//选中则取消
	            		 			$(tr).addClass("selected");
	            		 		}else{
	            		 			$(tr).removeClass("selected");
	            		 		}
                    	});
                     });
                    }
                },
                GetTds: function (dataObj) {
                    if (isArray(options.aoColumns)) {
                        var tds = "";
                        if (options.bSelectAll) {
                            if (options.bSelectCol != "") {
                                tds += "<td style=\"text-align: center;width:25px;\"><input class=\"jTabCheck\" type=\"checkbox\" cdata=\"" + dataObj[options.bSelectCol] + "\"  width=\"25px\" /></td>";
                            }
                            else {
                                tds += "<td style=\"text-align: center;width:25px;\"><input class=\"jTabCheck\" type=\"checkbox\" width=\"25px\"/></td>";
                            }
                        }
                        for (var i = 0; i < options.aoColumns.length; i++) {
                            var td = "";
                            var columnObj = options.aoColumns[i];
                            if (columnObj.cClass) {
                                td = "<td class='" + columnObj.cClass + "'>";
                            } else {
                                td = "<td>";
                            }
                            if (columnObj.cName) {
                                //HTML 编码
                                if (columnObj.cEncode) {
                                    if (columnObj.cEncode == true) {
                                        dataObj[columnObj.cName] = HtmlEncode(dataObj[columnObj.cName]);
                                    }
                                }
                                if (columnObj.fnRender) { //只要有fnRender 就按照fnRender来设置
                                    td += columnObj.fnRender(dataObj[columnObj.cName])?columnObj.fnRender(dataObj[columnObj.cName]):""; //带格式的
                                } else {
                                    td += dataObj[columnObj.cName]?dataObj[columnObj.cName]:"";
                                }
                            } else {  //没有Name，可能是操作
                                if (columnObj.fnRender) {
                                    td += columnObj.fnRender(); //带格式的
                                }
                            }
                            td += "</td>";
                            if (columnObj.cHide) {  //隐藏列 清空内容
                                td = "";
                            }
                            tds += td;
                        }
                        $.tableData[dataObj[options.bSelectCol]] =dataObj ;
                        return tds;
                    } else {
                        return false;
                    }
                },
                GetThs: function (SortType, SortCol, oid) {
                    if (isArray(options.aoColumns)) {
                        var ths = "";
                        if (options.bSelectAll) {
                            ths += "<th><input type=\"checkbox\" onclick=\"$$.checkAll(this,'" + oid + "')\" width=\"25px\" /></th>";
                        }
                        for (var i = 0; i < options.aoColumns.length; i++) {
                            var th = "<th";
                            var columnObj = options.aoColumns[i];
                            if (columnObj.cName) {
                                th += " columnname=" + columnObj.cName;
                            }
                            if (columnObj.cSort) { //当前列允许排序
                                if (SortCol != null) { //客户端程序是否指定排序列
                                    if (columnObj.cName == SortCol) {
                                        if (SortType == "asc") {
                                            th += " class=\"M_tablesortingasc\" onclick=\"$$.listSubmit(1,this,'" + oid + "')\"";
                                        } else {
                                            th += " class=\"M_tablesortingdesc\" onclick=\"$$.listSubmit(1,this,'" + oid + "')\"";
                                        }
                                    } else {
                                        th += " class=\"M_tablesorting\" onclick=\"$$.listSubmit(1,this,'" + oid + "')\"";
                                    }
                                } else {
                                    th += " class=\"M_tablesorting\" onclick=\"$$.listSubmit(1,this,'" + oid + "')\"";
                                }
                            }
                            if(columnObj.cWidth){
                            	th += ' style="width:'+columnObj.cWidth+'px"';
                            }
                            if (columnObj.cText) {
                                th += ">" + columnObj.cText;
                            }
                            th += "</th>";
                            if (columnObj.cHide) {  //隐藏列 这列不算
                                th = "";
                            }
                            ths += th;
                        }
                        return ths;
                    } else {
                        return false;
                    }
                },
                //处理锁表头的文本
                GetLockHeadThs: function (SortType, SortCol, oid) {
                    if (isArray(options.aoColumns)) {
                        //获取th的宽度
                        var thsWidth = new Array();
                        _$$.jFrm.find("th").each(function (i, e) {
                            thsWidth[i] = $(this).width();
                        })
                        var ths = "";
                        if (options.bSelectAll) {
                            ths += "<th style='width:" + thsWidth[0] + "px;'><input type=\"checkbox\" onclick=\"$$.checkAll(this,'" + oid + "')\" /></th>";
                        }
                        var index = 0; //循环宽度标量
                        for (var i = 0; i < options.aoColumns.length; i++) {
                            var th;
                            if (options.bSelectAll) {
                                th = "<th style='width:" + thsWidth[index + 1] + "px;'";
                            } else {
                                th = "<th style='width:" + thsWidth[index] + "px;'";
                            }
                            var columnObj = options.aoColumns[i];
                            if (columnObj.cName) {
                                th += " columnname=" + columnObj.cName;
                            }
                            if (columnObj.cSort) { //当前列允许排序
                                if (SortCol != null) { //客户端程序是否指定排序列
                                    if (columnObj.cName == SortCol) {
                                        if (SortType == "asc") {
                                            th += " class=\"M_tablesortingasc\" onclick=\"$$.listSubmit(1,this,'" + oid + "')\"";
                                        } else {
                                            th += " class=\"M_tablesortingdesc\" onclick=\"$$.listSubmit(1,this,'" + oid + "')\"";
                                        }
                                    } else {
                                        th += " class=\"M_tablesorting\" onclick=\"$$.listSubmit(1,this,'" + oid + "')\"";
                                    }
                                } else {
                                    th += " class=\"M_tablesorting\" onclick=\"$$.listSubmit(1,this,'" + oid + "')\"";
                                }
                            }
                            if (columnObj.cText) {
                                th += ">" + columnObj.cText;
                            }
                            th += "</th>";
                            if (columnObj.cHide) {  //隐藏列 这列不算
                                th = "";
                            } else {
                                index += 1;
                            }
                            ths += th;
                        }
                        return ths;
                    } else {
                        return false;
                    }
                },
                //锁表处理
                HandlerLockTable: function (tableId) {
                    FixTable(tableId, options.oLockTableParms[0], options.oLockTableParms[1], options.oLockTableParms[2], options.oLockTableParms[3]);
                },
                //没有数据处理
                HandlerNoData: function (ths) {
                    if (options.showTH) {
                        var nodataHtml = "<div style='padding-top:30px;margin: 0 auto;text-align: center;width: 100%;'><span style='line-height:40px;font-size:18px'>" + $$.jtabLanguage.lang_nodata + "</span></div>"; //没有数据
                        _$$.jFrm.append('<table cellpadding="0" cellspacing="0" width="100%" class="M_Table"><tbody class="M_Tbody"><tr class="M_Thead">' + ths + '</tr><tr><td colspan="' + options.aoColumns.length + '">' + nodataHtml + '</td></tr>');
                    } else {
                        _$$.jFrm.append("<div style='padding-top:10px;margin: 0 auto;text-align: center;width: 100%;'><span style='line-height:40px;color:red;font-size:18px'>" + $$.jtabLanguage.lang_nodata + "</span></div>");
                    }
                }
            }
            //插件共有成员，对外开放调用
            var $$ = window.$$ = {
                jtabLanguage: jTableLanguage[options.oLanguage],
                listSubmit: function (type, obj, oid) {  //操作符  对象
                    options = $.tableOptions[oid];

                    if (options.bLockTableHead) {  //清空锁定表头信息
                        $(".jqMyTable_floatTH").remove();
                    }
                    var parms = {};
                    if (type == 1) {   //1 表示 Sort操作 2表示Change Size操作  3 表示切换页 4.传递自定义参数操作，用于表格的搜索
                        var $obj = $(obj);
                        if ($obj.attr("class") == "M_tablesorting" || $obj.attr("class") == "M_tablesortingdesc") {
                            parms.SortType = "asc";
                            parms.SortCol = $obj.attr("columnname");
                            $obj.attr("class", "M_tablesortingasc"); //设置 class为 "M_tablesortingasc" 
                        } else {
                            parms.SortType = "desc";
                            parms.SortCol = $obj.attr("columnname");
                            $obj.attr("class", "M_tablesortingdesc");//
                        }
                    }
                    if (type == 2) {  //切换PageSize操作
                        var $obj = $(obj);
                        parms.PageSize = $obj.val();
                        parms.PageIndex = 1;
                    }
                    if (type == 3) { //换页操作
                        parms.PageIndex = obj;
                    }
                    if(type == 4){//传递自定义参数操作，用于表格的搜索
                    	options.sAjaxParams = obj;//用户自定义参数
                    }
                    _$$.jFrm = $("#" + oid);
                    _$$.setBoxParms(parms); //设置查询参数
                    _$$.GetData();
                },
                checkAll: function (obj, oid) {//勾选全部数据
                    var $th = $(obj);
                    _$$.jFrm = $("#" + oid); //改变jFrm的值
                    var checked = $th.attr("checked");
                    if (checked) {
                        _$$.jFrm.find(".jTabCheck").each(function () {
	                    	$(this).parent().parent().addClass("selected");
                            $(this).attr("checked", "checked");
                        });
                    } else {
                        _$$.jFrm.find(".jTabCheck").each(function () {
	                    	$(this).parent().parent().removeClass("selected");
                            $(this).removeAttr("checked");
                        });
                    }
                },
                getSelectedRows:function(oid){
                     var result = new Array();
                    _$$.jFrm = $("#" + oid); //改变jFrm的值
                	_$$.jFrm.find(".jTabCheck").each(function () {
                		if($(this).attr("checked")){//选中行
                			var cid = $(this).attr("cdata");//得到选中的行
                			var o = $.tableData[cid];
                			result.push(o);
                		}
                    });
                	return result;
                }
            }
            $.tableOptions[oId] = options; //多个表格的参数绑定
            //做一个页面多个JTable用
            _$$.init();
        }
    })
    //表格移动行变色
    $.fn.hoverTable = function () {
        $(this).find(".jTablesTr").each(function (i,obj) {
        	if(i%2==0)
        		$(this).addClass("hover");
        	/*
        	$(this).click(function () {
        		var radio = $(this).find("input:radio");
        		var cc = radio.attr("checked");
        		alert(radio+"---------"+cc);
        	});
        	*/
        	/*
            $(this).hover(function () {
	        	$(this).addClass("hover");
            }, function () {
                $(this).removeClass("hover");
            });*/
        })
    }
    //参数
    $.fn.JTables.defaults = { //默认参数设置
        showTH: false, //在没有数据的时候是否要显示表头
        dataSource: null, //指定Json格式数据源
        sAjaxSource: "", //Ajax数据源
        sAjaxParams: {}, //Ajax参数
        fnCallback: function () { }, //回调函数
        aoColumns: [], //列数据  包括的参数： cName  cText cSort fnRender cClass cEncode
        bSelectAll: true, //是否有全选功能
        bSelectCol: "", //如果有全选功能，则需要提供ID列
        bShowPage: true, //是否显示页码
        oPageIndex: 1, //默认显示第一页数据
        oPageSize: 10, //默认一页5条数据
        oSortType: "null", //排序类别 默认没有
        oSortCol: "null", //排序列 默认没有
        bProcessing: true, //是否启用等待功能
        mask_height: "320px", //Mask 界面的高度
        bSelectPageSize: true, //是否带选择每页的数据量
        bLockTableHead: false, //是否锁表头
        lockTHScrollC: window, //锁表头 滚动监听容器对象
        oMergeCells: [], //合并单元格列参数,默认没有
        bLockTable: false, //是否锁表（包含表头和列头）
        oLockTableParms: [0, 0, 0, 0], //锁表头参数[锁定几列，容器宽度，容器高度,表格宽度]
        oLanguage: "zh_cn",   //语言类型  zh-cn , en-us
        checkSelected:true,    //自定义，单机一行选中
        selectedColor:true    //自定义，单机一行选中
    }
    //验证是否为数组
    function isArray(obj) {  //私有函数  验证参数是否为数组
        return Object.prototype.toString.call(obj) === '[object Array]';
    }
    //过滤操作
    function filter(options) { //插件私有函数  验证参数是否正确
        return !options || (options && typeof options === "object") ? true : false;  //如果不存在，返回真，存在并且为对象也返回真，其余返回假
    }
    //对内容进行HTML编码
    function HtmlEncode(text) {
        if (isNaN(text)) {
            return text.replace(/&/g, '&amp').replace(/"/g, '&quot;').replace(/</g, '&lt;').replace(/>/g, '&gt;');
        } else {
            return text;
        }
    }
    //对内容进行HTML解码
    function HtmlDecode(text) {
        if (isNaN(text)) {
            return text.replace(/&amp;/g, '&').replace(/&quot;/g, '/"').replace(/&lt;/g, '<').replace(/&gt;/g, '>');
        } else {
            return text;
        }
    }
    //生成页码
    function getPageHtml(total, pageSize, pageIndex, isShowSelect, oid) {  //计算出分页
        var allPage = 0;
        var next = 0;
        var prev = 0;
        var pagestr = "";
        var startCount = 0;
        var endCount = 0;
        if (pageIndex < 1) { pageIndex = 1; }
        if (pageSize != 0) {
            allPage = parseInt(total / pageSize); //去掉小数
            allPage = ((total % pageSize) != 0 ? allPage + 1 : allPage);
            allPage = (allPage == 0 ? 1 : allPage);
        }
        next = pageIndex + 1;
        prev = pageIndex - 1;
        startCount = (pageIndex + 5) > allPage ? allPage - 9 : pageIndex - 4;
        endCount = pageIndex < 5 ? 10 : pageIndex + 5;
        if (startCount < 1) { startCount = 1; }
        if (allPage < endCount) { endCount = allPage; }
        pagestr += pageIndex > 1 ? "<a class=\"paginate_button\" href=\"javascript:$$.listSubmit(3,1,'" + oid + "')\">" + $$.jtabLanguage.lang_firstPage + "</a><a class=\"paginate_button\" href=\"javascript:$$.listSubmit(3," + prev + ",'" + oid + "')\">" + $$.jtabLanguage.lang_prevPage + "</a>" : "";
        for (var i = startCount; i <= endCount; i++) {
            pagestr += pageIndex == i ? "<a id=\"current\">" + i + "</a>" : "<a class=\"paginate_button\" href=\"javascript:$$.listSubmit(3," + i + ",'" + oid + "')\">" + i + "</a>";
        }
        pagestr += pageIndex != allPage ? "<a class=\"paginate_button\" href=\"javascript:$$.listSubmit(3," + next + ",'" + oid + "')\">" + $$.jtabLanguage.lang_nextPage + "</a><a class=\"paginate_button\" href=\"javascript:$$.listSubmit(3," + allPage + ",'" + oid + "')\">" + $$.jtabLanguage.lang_lastPage + "</a>" : "";
        var selectHtml = "";
        if (isShowSelect) {
            selectHtml = "<select onchange=\"$$.listSubmit(2,this,'" + oid + "')\"><option " + (pageSize == 5 ? "selected=\"selected\"" : "") + " >5</option><option " + (pageSize == 10 ? "selected=\"selected\"" : "") + ">10</option><option " + (pageSize == 30 ? "selected=\"selected\"" : "") + ">30</option><option " + (pageSize == 50 ? "selected=\"selected\"" : "") + ">50</option><option " + (pageSize == 100 ? "selected=\"selected\"" : "") + ">100</option></select>";
        }
        var totalMsg = "<div class='footer'><div class=\"M_pagination_left\"><span>" + $$.jtabLanguage.lang_total +"<font style='color:red'>"+ total+ "</font>"+ $$.jtabLanguage.lang_noteNum + "，每页显示" + selectHtml +"条"+ "</span></div>";
        return  totalMsg +"<div class=\"M_pagination\">"+ pagestr + "</div><footer>";
    }
    //获取锁表的左上头
    function FixTable(TableID, FixColumnNumber, FixWidth, FixHeight, TableWidth) {

        if ($("#" + TableID + "_tableLayout").length != 0) {
            $("#" + TableID + "_tableLayout").before($("#" + TableID));
            $("#" + TableID + "_tableLayout").empty();
        }
        else {
            var oldtable = $("#" + TableID);

            //oldtable.css({ "width": FixWidth, "min-width": 0 });
            oldtable.css({ "width": TableWidth, "min-width": TableWidth });

            var width = oldtable.width() + 2; //表格宽度
            width = FixWidth;
            var height = oldtable.height() + 2; //表格高度
            if (height > FixHeight) {
                height = FixHeight;
            }
            $("#" + TableID).after("<div id='" + TableID + "_tableLayout' style='overflow:hidden;height:" + height + "px; width:" + width + "px;'></div>");
        }
        $('<div id="' + TableID + '_tableFix"></div>'
			+ '<div id="' + TableID + '_tableHead"></div>'
			+ '<div id="' + TableID + '_tableColumn"></div>'
			+ '<div id="' + TableID + '_tableData"></div>').appendTo("#" + TableID + "_tableLayout");

        var tableFixClone = oldtable.clone(true);
        tableFixClone.attr("id", TableID + "_tableFixClone");
        $("#" + TableID + "_tableFix").append(tableFixClone);
        var tableHeadClone = oldtable.clone(true);
        tableHeadClone.attr("id", TableID + "_tableHeadClone");
        $("#" + TableID + "_tableHead").append(tableHeadClone);
        var tableColumnClone = oldtable.clone(true);
        tableColumnClone.attr("id", TableID + "_tableColumnClone");
        $("#" + TableID + "_tableColumn").append(tableColumnClone);
        $("#" + TableID + "_tableData").append(oldtable);

        $("#" + TableID + "_tableLayout table").each(function () {
            $(this).css("margin", "0");
        });
        var HeadHeight = $("#" + TableID + "_tableHead thead").height();
        HeadHeight += 2;

        console.log("HeadHight:" + HeadHeight);

        $("#" + TableID + "_tableHead").css("height", HeadHeight);
        $("#" + TableID + "_tableFix").css("height", HeadHeight);
        var ColumnsWidth = 0;
        var ColumnsNumber = 0;
        $("#" + TableID + "_tableColumn tr:last td:lt(" + FixColumnNumber + ")").each(function () {
            ColumnsWidth += $(this).outerWidth(true);
            ColumnsNumber++;
        });
        ColumnsWidth += 2;

        console.log("ColumnsWidth:" + ColumnsWidth);

        if ($.browser.msie) {
            switch ($.browser.version) {
                case "7.0":
                    if (ColumnsNumber >= 3) ColumnsWidth--;
                    break;
                case "8.0":
                    if (ColumnsNumber >= 2) ColumnsWidth--;
                    break;
            }
        }

        $("#" + TableID + "_tableColumn").css("width", ColumnsWidth);
        $("#" + TableID + "_tableFix").css("width", ColumnsWidth);
        $("#" + TableID + "_tableData").scroll(function () {
            $("#" + TableID + "_tableHead").scrollLeft($("#" + TableID + "_tableData").scrollLeft());
            $("#" + TableID + "_tableColumn").scrollTop($("#" + TableID + "_tableData").scrollTop());
        });
        $("#" + TableID + "_tableFix").css({ "overflow": "hidden", "position": "relative", "z-index": "50", "background-color": "Silver" });
        $("#" + TableID + "_tableHead").css({ "overflow": "hidden", "width": width - 17, "position": "relative", "z-index": "45", "background-color": "Silver" });
        $("#" + TableID + "_tableColumn").css({ "overflow": "hidden", "height": height - 17, "position": "relative", "z-index": "40", "background-color": "Silver" });
        $("#" + TableID + "_tableData").css({ "overflow": "scroll", "width": width, "height": height, "position": "relative", "z-index": "35" });

        $("#" + TableID + "_tableFix").offset($("#" + TableID + "_tableLayout").offset());
        $("#" + TableID + "_tableHead").offset($("#" + TableID + "_tableLayout").offset());
        $("#" + TableID + "_tableColumn").offset($("#" + TableID + "_tableLayout").offset());
        $("#" + TableID + "_tableData").offset($("#" + TableID + "_tableLayout").offset());

        if ($("#" + TableID + "_tableHead").width() > $("#" + TableID + "_tableFix table").width()) {
            $("#" + TableID + "_tableHead").css("width", $("#" + TableID + "_tableFix table").width());
            $("#" + TableID + "_tableData").css("width", $("#" + TableID + "_tableFix table").width() + 17);
        }
        if ($("#" + TableID + "_tableColumn").height() > $("#" + TableID + "_tableColumn table").height()) {
            $("#" + TableID + "_tableColumn").css("height", $("#" + TableID + "_tableColumn table").height());
            $("#" + TableID + "_tableData").css("height", $("#" + TableID + "_tableFix table").height() + 17);
        }
    }
    //多语言
    var jTableLanguage = {
        "en_us": {
            lang_nodata: "No Data",
            lang_formatError: "Column format error",
            lang_prevPage: "Previous",
            lang_firstPage: "First",
            lang_nextPage: "Next",
            lang_lastPage: "Last",
            lang_total: "Total ",
            lang_noteNum: " "
        },
        "zh_cn": {
            lang_nodata: "未查询到数据",
            lang_formatError: "列格式错误",
            lang_prevPage: "上一页",
            lang_firstPage: "首页",
            lang_nextPage: "下一页",
            lang_lastPage: "尾页",
            lang_total: "共",
            lang_noteNum: "条记录"
        }
    };
    //************************合并单元格插件 Start************************
    $.fn.mergeCell = function (options) {
        return this.each(function () {
            var cols = options.cols;
            for (var i = cols.length - 1; cols[i] != undefined; i--) {
                mergeCell($(this), cols[i]);
            }
            dispose($(this));
        });
    };
    // 如果对javascript的closure和scope概念比较清楚, 这是个插件内部使用的private方法 
    function mergeCell($table, colIndex) {
        $table.data('col-content', ''); // 存放单元格内容 
        $table.data('col-rowspan', 1); // 存放计算的rowspan值 默认为1 
        $table.data('col-td', $()); // 存放发现的第一个与前一行比较结果不同td(jQuery封装过的), 默认一个"空"的jquery对象 
        $table.data('trNum', $('tbody tr', $table).length); // 要处理表格的总行数, 用于最后一行做特殊处理时进行判断之用 
        // 我们对每一行数据进行"扫面"处理 关键是定位col-td, 和其对应的rowspan 
        $('tbody tr', $table).each(function (index) {
            // td:eq中的colIndex即列索引 
            var $td = $('td:eq(' + colIndex + ')', this);
            // 取出单元格的当前内容 
            var currentContent = $td.html();
            // 第一次时走此分支 
            if ($table.data('col-content') == '') {
                $table.data('col-content', currentContent);
                $table.data('col-td', $td);
            } else {
                // 上一行与当前行内容相同 
                if ($table.data('col-content') == currentContent) {
                    // 上一行与当前行内容相同则col-rowspan累加, 保存新值 
                    var rowspan = $table.data('col-rowspan') + 1;
                    $table.data('col-rowspan', rowspan);
                    // 值得注意的是 如果用了$td.remove()就会对其他列的处理造成影响 
                    $td.hide();
                    // 最后一行的情况比较特殊一点 
                    // 比如最后2行 td中的内容是一样的, 那么到最后一行就应该把此时的col-td里保存的td设置rowspan 
                    if (++index == $table.data('trNum'))
                        $table.data('col-td').attr('rowspan', $table.data('col-rowspan'));
                } else { // 上一行与当前行内容不同 
                    // col-rowspan默认为1, 如果统计出的col-rowspan没有变化, 不处理 
                    if ($table.data('col-rowspan') != 1) {
                        $table.data('col-td').attr('rowspan', $table.data('col-rowspan'));
                    }
                    // 保存第一次出现不同内容的td, 和其内容, 重置col-rowspan 
                    $table.data('col-td', $td);
                    $table.data('col-content', $td.html());
                    $table.data('col-rowspan', 1);
                }
            }
        });
    }
    // 同样是个private函数 清理内存之用 
    function dispose($table) {
        $table.removeData();
    }
    //************************合并单元格插件 End************************
})(jQuery);
