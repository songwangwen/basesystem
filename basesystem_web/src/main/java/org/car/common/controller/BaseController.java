package org.car.common.controller;

import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.car.common.model.JQTableVO;
import org.car.common.model.PageDTO;
import org.car.common.utils.JsonUtil;
import org.car.system.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
/**
 * 基础Controller类，用于定义通用方法
 * @author songwangwen
 */
@Controller  
public  class BaseController extends HandlerInterceptorAdapter{
//	protected static final Logger log = LogManager.getLogger(BaseController.class);
	public final static Logger log = LoggerFactory
			.getLogger(BaseController.class);
	//表格数据对象
	protected JQTableVO table;
	protected HttpServletRequest request;  
	protected HttpServletResponse response;
    protected HttpSession session;  
    protected User loginUser;//登录人员  
    protected Set<Integer> loginUserMenuSet;//登录人员可操作的菜单集合
    @Override
    public boolean preHandle(HttpServletRequest request,  
            HttpServletResponse response, Object handler) throws Exception {  
  
        request.setCharacterEncoding("UTF-8");  
        response.setCharacterEncoding("UTF-8");  
        response.setContentType("text/html;charset=UTF-8");  
  
        String uri = request.getRequestURI();  
       //设置不拦截的对象,
        String[] noFilters = new String[] { "/login","/utility/fieldExist"};  
        boolean beFilter = true; 
        for (String s : noFilters) {  
            if (uri.indexOf(s) != -1) {  
                beFilter = false;  
                break;  
            }  
        }
        // uri中包含.do时才进行过滤
        if (beFilter == true) {  
        	loginUser= (User) request.getSession().getAttribute("loginUser");  
             // 未登录  直接跳转至登录
             if (null == loginUser) {  
            	 response.sendRedirect("/login.jsp");      
                 return false;  
             }   
        }   
        return super.preHandle(request, response, handler);     
    }//end function preHandle
    /**
     * 初始化request response 对象
     * @param request
     * @param response
     */
    @ModelAttribute  
    public void setReqAndRes(HttpServletRequest request, HttpServletResponse response){  
        this.request = request;  
        this.response = response;  
        this.session = request.getSession();  
    }  
	/**
	 * 获取URL中参数
	 */
	protected String getParameter(String key) {
		return request.getParameter(key);
	}
	
	protected PageDTO getPage() {
		PageDTO dto = new PageDTO();
		if(request.getParameter("jPageIndex")!=null&&request.getParameter("jPageSize")!=null
				&&request.getParameter("jSortCol")!=null&&request.getParameter("jSortType")!=null){//如果不想要分页，则可以返回空
			dto.setjPageIndex(Integer.parseInt(request.getParameter("jPageIndex")));// 当前页码
			dto.setjPageSize(Integer.parseInt(request.getParameter("jPageSize")));// 每页条数
			dto.setjSortCol(request.getParameter("jSortCol"));// 排序字段
			dto.setjSortType(request.getParameter("jSortType"));// 排序方式
		}else{
			dto.setjPageIndex(1);// 当前页码
			dto.setjPageSize(10);// 每页条数
			dto.setjSortCol("id");// 排序字段
			dto.setjSortType("asc");// 排序方式
		}
		return dto;
	}
	
	/**
	 * 解析界面传过来的JSON数据，转化为实体类
	 */
	protected Object getProcessContent(String json, Class<?> pojoCalss) {
		Object obj = null;
		obj = JsonUtil.getObjectFromJsonString(json,"jsonEntity",pojoCalss);
		return obj;
	}
	public User getLoginUser() {
		return loginUser;
	}
}
