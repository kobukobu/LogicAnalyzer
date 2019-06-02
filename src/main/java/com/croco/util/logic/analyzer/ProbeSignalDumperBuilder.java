package com.croco.util.logic.analyzer;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ProbeSignalDumperBuilder {

	private final List<ProbeBase> probes = new LinkedList<>();
	
	public ProbeSignalDumperBuilder() {}
	
	public ProbeSignalDumperBuilder registProbe(ProbeBase probe){
		if (probes.contains(probe)) {
			throw new IllegalArgumentException("ProbeBase(" + probe.getName() + ") has already registed");
		}
		probes.add(probe);
		return this;
	}
	
	public ProbeSignalDumper build() {
		return new ProbeSignalDumper(new HashMap<>(), probes);
	}
}
