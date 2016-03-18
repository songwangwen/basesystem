package org.car.common.model;

import java.util.List;


/**
 * JQTable（Jquery dataTable扩展）自定义控件的表格实体类，使用JSON进行数据传输
 * @author songwangwen
 *
 */
public class JQTableVO {
	private int pageIndex=1;//开始页
    private int pageSize=10;//分页大小
    private int dataCount=0;//总数据条数
    
    private List<?> dataList;
    private String sortType;//排序类型
    private String sortCol;//排序字段
    
	public JQTableVO() {
		super();
	}
	public JQTableVO(PageDTO page) {
		super();
		this.pageIndex = page.getjPageIndex();// 当前页码
		this.pageSize = page.getjPageSize();// 每页条数
		this.sortCol = page.getjSortCol();// 排序字段
		this.sortType = page.getjSortType();// 排序方式
	}
	public int getPageIndex() {
		return pageIndex;
	}
	public void setPageIndex(int pageIndex) {
		this.pageIndex = pageIndex;
	}
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}
	public int getDataCount() {
		return dataCount;
	}
	public void setDataCount(int dataCount) {
		this.dataCount = dataCount;
	}
	public List<?> getDataList() {
		return dataList;
	}
	public void setDataList(List<?> dataList) {
		this.dataList = dataList;
	}
	public String getSortType() {
		return sortType;
	}
	public void setSortType(String sortType) {
		this.sortType = sortType;
	}
	public String getSortCol() {
		return sortCol;
	}
	public void setSortCol(String sortCol) {
		this.sortCol = sortCol;
	}
}
