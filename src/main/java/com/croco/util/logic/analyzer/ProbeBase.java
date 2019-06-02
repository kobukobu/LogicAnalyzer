package com.croco.util.logic.analyzer;

import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.Set;

public abstract class ProbeBase {
	protected final String name;
	protected final String shortName;
	protected final String type;
	protected final Set<IValueChangeListener> listeners = new LinkedHashSet<>();
	public static final String NAME_DELIMINATOR = "\\.";
	
	public ProbeBase(String type, String name) {
		this.type = type;
		this.name = name;
		this.shortName = new LinkedList<String>(Arrays.asList(name.split(NAME_DELIMINATOR))).getLast(); 
	}
	
	public String getName() {
		return name;
	}
	
	public void addValueChangeListener(IValueChangeListener lister) {
		listeners.add(lister);
	}
	
	protected void onChange(String val) {
		listeners.stream().forEach(listener -> listener.onChange(name, val));
	}
	
	abstract String getVCDHeaderInfo(String symbol); 
}
