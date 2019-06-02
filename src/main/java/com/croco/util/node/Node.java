package com.croco.util.node;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.EqualsExclude;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.HashCodeExclude;

public abstract class Node {
	@EqualsExclude
	@HashCodeExclude
	protected final LinkedHashSet<Node> nodes;
	private final String name;
	private final Node parentNode;
		
	public Node(Node parentNode, String name) {
		this.parentNode = parentNode;
		this.name = name;
		nodes = new LinkedHashSet<>();
	}
	
	public abstract boolean hasChild();
	
	public Node getParentNode() {
		return parentNode;
	}
	
	public Set<Node> getChildNode(){
		return nodes;
	}
	
	public String getName() {
		return name;
	}
	
	public String getFullName() {
		if (parentNode == null) {
			return name;
		} else {
			return parentNode.getFullName() + "." + name;
		}
	}

	@Override
	public boolean equals(Object obj) {
		return EqualsBuilder.reflectionEquals(this, obj);
	}

	@Override
	public int hashCode() {
		return HashCodeBuilder.reflectionHashCode(this);
	}

	@Override
	public String toString() {
		return getFullName();
	}
}
