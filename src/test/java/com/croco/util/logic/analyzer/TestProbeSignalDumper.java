package com.croco.util.logic.analyzer;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class TestProbeSignalDumper {

	@Test
	public void test() throws InterruptedException {
		ProbeSignalDumperBuilder builder = new ProbeSignalDumperBuilder();
		DoubleProbe probe1 = new DoubleProbe("hehe.foo.fuga");
		DoubleProbe probe2 = new DoubleProbe("hehe.foo.hoge");
		DoubleProbe probe3 = new DoubleProbe("hehe.fuga");
		builder.registProbe(probe1);
		builder.registProbe(probe2);
		builder.registProbe(probe3);
		builder.build();
		
		probe1.setValue(2d);
		probe2.setValue(7d);
		
		Thread.sleep(100l);
		probe1.setValue(3d);
		probe3.setValue(1d);
		
		Thread.sleep(100l);
		probe2.setValue(4d);
		probe3.setValue(6d);
		
		Thread.sleep(200l);
		probe1.setValue(5d);
		probe2.setValue(-1d);
	}
	
}

