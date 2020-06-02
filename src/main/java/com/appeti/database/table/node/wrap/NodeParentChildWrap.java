package com.appeti.database.table.node.wrap;

import com.appeti.database.table.node.Node;
import com.appeti.database.table.node.NodeImage;
import com.appeti.database.utils.Image;

public class NodeParentChildWrap {
	long nodeId;
	long parentId;
	String nodeName;
	String parentName;
	Image image;
	
	public NodeParentChildWrap(Node node){
		this.nodeId = node.getNodeId();
		this.parentId = node.getParentNodeId();
		this.nodeName = node.getNodeName();
		this.parentName = Node.getNodeById(node.getParentNodeId()).getNodeName();
		this.image = NodeImage.getDefaultImageByNodeId(node.getNodeId());
	}
	
	public long getNodeId() {
		return nodeId;
	}
	public void setNodeId(long nodeId) {
		this.nodeId = nodeId;
	}
	public long getParentId() {
		return parentId;
	}
	public void setParentId(long parentId) {
		this.parentId = parentId;
	}
	public String getNodeName() {
		return nodeName;
	}
	public void setNodeName(String nodeName) {
		this.nodeName = nodeName;
	}
	public String getParentName() {
		return parentName;
	}
	public void setParentName(String parentName) {
		this.parentName = parentName;
	}
	public Image getImage() {
		return image;
	}
	public void setImage(Image image) {
		this.image = image;
	}
}
