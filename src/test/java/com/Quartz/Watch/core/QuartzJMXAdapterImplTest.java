package com.Quartz.Watch.core;

import static org.junit.Assert.fail;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import com.easeye.quartz.quartzmonitor.conf.QuartzConfig;
import com.easeye.quartz.quartzmonitor.core.QuartzConnectService;
import com.easeye.quartz.quartzmonitor.core.QuartzConnectServiceImpl;
import com.easeye.quartz.quartzmonitor.object.JMXInput;
import com.easeye.quartz.quartzmonitor.object.QuartzInstance;
import com.easeye.quartz.quartzmonitor.object.Scheduler;
import com.easeye.quartz.quartzmonitor.util.JMXUtil;

public class QuartzJMXAdapterImplTest {
	
	
	@Test
	public void testGetVersion() {
		
	}
	
	
	QuartzConfig config = null;

	QuartzInstance quartzInstance = null;

	@Before
	public void setUpBeforeClass() throws Exception {
		try {
			config = new QuartzConfig("11", "127.0.0.1", 2911, "", "");
			QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
			quartzInstance = quartzConnectService.initInstance(config);
			System.out.println("------------");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Test
	public void testGetJobDetails() throws Exception {

		List<Scheduler> schedulers = quartzInstance.getSchedulerList();

		System.out.println(schedulers.size());
		JMXInput jmxInput = new JMXInput(quartzInstance, new String[] { String.class.getName() }, "AllJobDetails", new Object[] { schedulers.get(0).getUuidInstance() }, schedulers.get(0).getObjectName());

		Object o1 = JMXUtil.callJMXAttribute(jmxInput);
		Object o2 = JMXUtil.callJMXOperation(jmxInput);
		System.out.println(o1);
		System.out.println(o2);
	}

	@Test
	public void testGetScheduler() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetTriggersForJob() {
		fail("Not yet implemented");
	}

	@Test
	public void testAttachListener() {
		fail("Not yet implemented");
	}
	
	
}