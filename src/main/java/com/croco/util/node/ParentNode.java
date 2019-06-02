package com.croco.util.node;

import java.util.Optional;
import java.util.Set;

public class ParentNode extends Node {

	public ParentNode(String name) {
		super(null, name);
	}
	
	public ParentNode(Node parentNode, String name) {
		super(parentNode, name);
	}
	
	public Optional<Node> getNode(String name) {
		return nodes.stream().filter(node -> node.getName().equals(name)).findFirst();
	}

	public void addNode(Node node) {
		nodes.add(node);
	}
	
	public void removeNode(Node node) {
		nodes.remove(node);
	}

	public boolean hasChild() {
		return nodes.size() > 0;
	}
}
