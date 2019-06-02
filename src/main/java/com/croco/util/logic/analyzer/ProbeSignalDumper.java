package com.croco.util.logic.analyzer;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.lang.System;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.apache.log4j.Logger;

import java.util.stream.Collector;
import java.util.stream.Collectors;

import com.croco.util.node.ChildNode;
import com.croco.util.node.Node;
import com.croco.util.node.ParentNode;

public class ProbeSignalDumper implements IValueChangeListener{

	private final Map<String, String> config;
	private final List<ProbeBase> probes = new LinkedList<>();
	private final StringProbe timeProbe;
	private final ParentNode scopeNode;
	private final Map<String, String> symbol = new HashMap<>();
	private final Map<String, ProbeBase> fullNameToProbe = new HashMap<>();
	private final long baseTime = System.currentTimeMillis();
	private long currentTime;
	private static final Logger logger = Logger.getLogger(ProbeSignalDumper.class);
	private int idx;
	
	private static String topNodeName = "TOP";
	
	public ProbeSignalDumper(Map<String, String> config, List<ProbeBase> probes) {
		this.config = config;
		this.timeProbe = new StringProbe("TimeStamp");
		this.probes.add(this.timeProbe);
		this.probes.addAll(probes);
		this.currentTime = baseTime;
		this.scopeNode = new ParentNode(topNodeName);
		this.idx = 0;
		
		this.timeProbe.addValueChangeListener(
			new IValueChangeListener() {
				@Override
				public void onChange(String key, String val) {
					logger.info(val + " " + symbol.get(topNodeName + "." + key));
				}
			}
		);
		this.probes.stream().filter(probe -> probe != this.timeProbe).forEach(probe -> probe.addValueChangeListener(this));
		this.probes.stream().forEach(probe -> registNode(new LinkedList<String>(Arrays.asList(probe.getName().split(ProbeBase.NAME_DELIMINATOR))), scopeNode));
		this.probes.stream().forEach(probe -> symbol.put(topNodeName + "." + probe.getName(), createSymbol(topNodeName + "." + probe.getName())));
		this.probes.stream().forEach(probe -> fullNameToProbe.put(topNodeName + "." + probe.getName(), probe));
		printVCDHeader(scopeNode);
	}
	
	private static String createSymbol(String name) {
		return name; // TODO
	}
	
	private void setTimeStamp() {
		long now = System.currentTimeMillis();
		if (now != currentTime) {
			currentTime = now;
			logger.info("#" + (currentTime - baseTime));
			timeProbe.setValue(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss.SSS")));
		}
	}
	
	private void registNode(LinkedList<String> scopeLaryers, ParentNode parentNode) {
		String first = scopeLaryers.removeFirst();
		
		Optional<Node> node = parentNode.getNode(first); 
		
		if(!node.isPresent()) {
			if(!scopeLaryers.isEmpty()) {
				ParentNode child = new ParentNode(parentNode, first);
				parentNode.addNode(child);
				registNode(scopeLaryers, child);
			} else {
				ChildNode child = new ChildNode(parentNode, first);
				parentNode.addNode(child);
			}
		} else {
			if(!scopeLaryers.isEmpty() && node.get().hasChild()) {
				registNode(scopeLaryers, (ParentNode)node.get());
			} else {
				throw new IllegalArgumentException(first + " node has already registered!. " + scopeLaryers.toString() + " is remaining layer.");
			}
		}
	}
	
	private void printVCDHeader(Node scopeNode) {
		logger.info("$timesacle 1ms $end");
		logger.info(getScopeInfo(scopeNode));
		logger.info("$enddefinitions $end");
		logger.info("$dumpvars");
	}
		
	private String getScopeInfo(Node scopeNode) {
		String ret;
		
		if (!scopeNode.hasChild()) {
			
			ret =  fullNameToProbe.get(scopeNode.getFullName()).getVCDHeaderInfo(symbol.get(scopeNode.getFullName())) + System.lineSeparator();
			
		} else {
			
			ret = "$scope module " + scopeNode.getName() + " $end" + System.lineSeparator();
			
			Iterator<Node> it = scopeNode.getChildNode().iterator();
			
			while (it.hasNext()) {
				Node node = it.next();
				ret += getScopeInfo(node);
			}
		
			ret += "$upscope $end" + System.lineSeparator();
			
		}
		
		return ret;
	}
	
	@Override
	public void onChange(String key, String val) {
		setTimeStamp();
		logger.info(val + " " + symbol.get(topNodeName + "." + key));
	}
	
}
