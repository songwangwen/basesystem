package org.car.common.utils;

import java.util.ArrayList;
import java.util.List;

import org.car.common.model.TreeNode;
import org.car.system.model.MenuNode;

/**
 * 该工具类用于构建树形结构，主要是将线性的数据通过parentId等关联条件构造成为树形
 * @author songwangwen
 *
 */
public class TreeUtil {
	/**
		查找线性数据集合中的根节点（或根节点集合）
	 * 
	 * */
	public static List<MenuNode> getRoot(List<MenuNode> list){  
		List<MenuNode> treeList = new ArrayList<MenuNode>();
		for(MenuNode node:list){
			if(node.getParentId()==0){  
				treeList.add(node);
				initChild(node,list);  
			}  
		}
        return treeList;  
    }
	
	/**递归查找所有节点，组装成为一个树
	 * @param root 根节点
	 * @param list 全部节点
	 */
	public static void initChild(MenuNode root,List<MenuNode> list){  
        int parentId = root.getNodeId();
        	for(MenuNode node:list){
        		if(node.getParentId()==parentId){  
        			initChild(node,list);  
        			root.getChildren().add(node);  
        		}  
        }
    }  
	
	/**
		查找线性数据集合中的根节点（或根节点集合）
	 * 
	 * */
	public static List<TreeNode> getTreeNodeRoot(List<TreeNode> list){  
		List<TreeNode> treeList = new ArrayList<TreeNode>();
		for(TreeNode node:list){
			if("0".equals(node.getParentId())){  
				treeList.add(node);
				initChild(node,list);  
			}  
		}
	    return treeList;  
	}
	
	/**递归查找所有节点，组装成为一个树
	 * @param root 根节点
	 * @param list 全部节点
	 */
	public static void initChild(TreeNode root,List<TreeNode> list){  
			String parentId = root.getId();
	    	for(TreeNode node:list){
	    		if(parentId.equals(node.getParentId())){  
	    			initChild(node,list);  
	    			root.getChildren().add(node);  
	    		}  
	    }
	}  
}
