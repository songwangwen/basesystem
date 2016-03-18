package org.car.common.model;

/**
 * 封装查询相关字段
 */
public class PageDTO {

	private int jPageIndex=1;// 当前页码
	private int jPageSize=10;// 每页条数
	private String jSortCol;// 排序字段
	private String jSortType="asc";// 排序方式
	public int getjPageIndex() {
		return jPageIndex;
	}
	public void setjPageIndex(int jPageIndex) {
		this.jPageIndex = jPageIndex;
	}
	public int getjPageSize() {
		return jPageSize;
	}
	public void setjPageSize(int jPageSize) {
		this.jPageSize = jPageSize;
	}
	public String getjSortCol() {
		return jSortCol;
	}
	public void setjSortCol(String jSortCol) {
		this.jSortCol = jSortCol;
	}
	public String getjSortType() {
		return jSortType;
	}
	public void setjSortType(String jSortType) {
		this.jSortType = jSortType;
	}
	@Override
	public String toString() {
		return "PageDTO [jPageIndex=" + jPageIndex + ", jPageSize=" + jPageSize
				+ ", jSortCol=" + jSortCol + ", jSortType=" + jSortType + "]";
	}
}