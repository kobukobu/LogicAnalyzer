package com.croco.util.logic.analyzer;


public class DoubleProbe extends ProbeBase {
	private Double val;
	static final String TYPE = "real";
	
	public DoubleProbe(String name) {
		super(TYPE, name);
		val = Double.NaN;
	}
	
	public void setValue(Double val) {
 		if (this.val == val) {
			return;
		}
		this.val = val;
		onChange("r" + val.toString());
	}
	
	public String getVCDHeaderInfo(String symbol) {		
		return "$var " + TYPE + " 1 " + symbol + " " + shortName + " $end";
	}
}
