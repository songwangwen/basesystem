package org.car.system.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

/**
 * 主菜单实体类
 * @author songwangwen
 */
public class MenuNode implements Serializable{
	private static final long serialVersionUID = 1L;
	/**
	 */
	private int nodeId; // 节点ID
	private String nodeName; //节点名称
	private String nodeModel; //菜单项所属的模块
	private String iconCls; // 图标
    private String  url;//地址
    private String  nodeType;//节点类型
    private int  parentId;//父节点ID
    private String remark;//备注
    //批量查询
    private Set<Integer> nodeIdSet;
    /*********************一下属性不存储在数据库中**************************/
    private List<MenuNode> children;  //子节点
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getIconCls() {
		return iconCls;
	}
	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public int getParentId() {
		return parentId;
	}
	public void setParentId(int parentId) {
		this.parentId = parentId;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public List<MenuNode> getChildren() {
		if(children==null)
			children = new ArrayList<MenuNode>();
		return children;
	}
	public void setChildren(List<MenuNode> children) {
		this.children = children;
	}
	public Set<Integer> getNodeIdSet() {
		return nodeIdSet;
	}
	public void setNodeIdSet(Set<Integer> nodeIdSet) {
		this.nodeIdSet = nodeIdSet;
	}
	public String getNodeModel() {
		return nodeModel;
	}
	public void setNodeModel(String nodeModel) {
		this.nodeModel = nodeModel;
	}
}
