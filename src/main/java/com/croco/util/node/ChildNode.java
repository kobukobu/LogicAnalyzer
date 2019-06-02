package com.croco.util.node;

import java.util.HashSet;
import java.util.Set;

public class ChildNode extends Node {

	public ChildNode(Node parentNode, String name) {
		super(parentNode, name);
	}

	public boolean hasChild() {
		return false;
	}
}
