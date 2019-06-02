package com.croco.util.logic.analyzer;

public class StringProbe extends ProbeBase {
	private String val;
	static final String TYPE = "string";
	
	public StringProbe(String name) {
		super(TYPE, name);
		val = null;
	}
	
	public void setValue(String val) {
 		if (this.val != null && this.val.equals(val)) {
			return;
		}
 		this.val = val;
		onChange("s" + val);
	}
	
	@Override
	String getVCDHeaderInfo(String symbol) {
		return "$var " + TYPE + " 1 " + symbol + " " + shortName + " $end";
	}

}
