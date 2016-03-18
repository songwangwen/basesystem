package org.car.common.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.car.system.model.MenuNode;
public class TreeNode {
	private String id;// id值
	private String parentId;// 父ID值
	private String text;// 文本
	//树打开或关闭   open   closed
	private String state;
	//子节点个数
	private List<TreeNode> children;
	//存储自定义节点属性

	private String iconCls;//自定义图标
	private Map<?,?> attributes;//存储自定义属性
	private String checked;//是否勾选节点 true 自动勾选

	public TreeNode(){}
	public TreeNode(MenuNode nemuNode) {
		super();
		this.id = String.valueOf(nemuNode.getNodeId());
		this.parentId = String.valueOf(nemuNode.getParentId());
		this.text = nemuNode.getNodeName();
		this.iconCls = nemuNode.getIconCls();
	}


	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}


	public String getParentId() {
		return parentId;
	}


	public void setParentId(String parentId) {
		this.parentId = parentId;
	}


	public String getText() {
		return text;
	}


	public void setText(String text) {
		this.text = text;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public List<TreeNode> getChildren() {
		if(children==null)
			children = new ArrayList<TreeNode>();
		return children;
	}


	public void setChildren(List<TreeNode> children) {
		this.children = children;
	}


	public String getIconCls() {
		return iconCls;
	}


	public void setIconCls(String iconCls) {
		this.iconCls = iconCls;
	}


	public Map<?, ?> getAttributes() {
		return attributes;
	}
	public void setAttributes(Map<?, ?> attributes) {
		this.attributes = attributes;
	}
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
}
