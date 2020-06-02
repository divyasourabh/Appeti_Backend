package com.appeti.database.table.node.wrap;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import org.apache.log4j.Logger;

import com.appeti.database.table.node.Node;
import com.appeti.database.table.node.NodeImage;
import com.appeti.database.utils.Image;

public class NodeTree {
	private long nodeId;
	private String nodeName;
	private Image image;
	List<NodeTree> childNodes = null;
	private static final Logger logger = Logger.getLogger(NodeTree.class);
	
	public static NodeTree prepareNodeTree(long nodeId, int level){
		if(level < 0)
			return null;
		Node node = Node.getNodeById(nodeId);
		if(node == null)
			return null;
		NodeTree treeNode = new NodeTree();
		treeNode.setNodeId(node.getNodeId());
		treeNode.setNodeName(node.getNodeName());
		Image image = NodeImage.getDefaultImageByNodeId(node.getNodeId());
		treeNode.setImage(image);
		Set<Long> children = Node.getImediateChildNodeIds(node.getNodeId(), Node.headerExtraWhereClause);
		Iterator<Long> iterator = children.iterator();
		while(iterator.hasNext()){
			NodeTree childTreeNode = prepareNodeTree(iterator.next(), level-1);
			if(childTreeNode != null){
				if(treeNode.getChildNodes() == null)
					treeNode.setChildNodes(new ArrayList<NodeTree>());
				
				treeNode.getChildNodes().add(childTreeNode);
			}
		}
		return treeNode;
	}
	
	
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public List<NodeTree> getChildNodes() {
		return childNodes;
	}
	public void setChildNodes(List<NodeTree> childNodes) {
		this.childNodes = childNodes;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
	
}
