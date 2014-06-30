package com.easeye.quartz.quartzmonitor.core;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;

import org.apache.log4j.Logger;

import com.easeye.quartz.quartzmonitor.conf.QuartzConfig;
import com.easeye.quartz.quartzmonitor.conf.SystemConfigFile;
import com.easeye.quartz.quartzmonitor.object.QuartzInstance;
import com.easeye.quartz.quartzmonitor.object.Scheduler;
import com.easeye.quartz.quartzmonitor.util.JMXUtil;
import com.easeye.quartz.quartzmonitor.util.PropertiesUtil;

/**
 * 处理应用与Quartz的连接（使用JMX）
 */
public class QuartzConnectServiceImpl implements QuartzConnectService {

	static Logger log = Logger.getLogger(QuartzConnectServiceImpl.class);
	
	@Override
	public QuartzInstance initInstance(QuartzConfig config) throws Exception {
		Map<String, String[]> env = new HashMap<String, String[]>();
		env.put(JMXConnector.CREDENTIALS, new String[] { config.getUserName(), config.getPassword() });
		JMXServiceURL jmxServiceURL = JMXUtil.createQuartzInstanceConnection(config);
		JMXConnector connector = JMXConnectorFactory.connect(jmxServiceURL, env);
		MBeanServerConnection connection = connector.getMBeanServerConnection();

//		String schedulerJmxObjectName = "com.easeye.ymail.service.timerservice:type=QuartzScheduler,name=EaseyeScheduler";
//		String schedulerJmxObjectName = "com.easeye.ymail.service.timerservice:type=QuartzScheduler,*";
		String schedulerJmxObjectName = PropertiesUtil.getPropertiesValue(SystemConfigFile.SYSCONFIG, "schedulerJmxObjectName");
		ObjectName mBName = new ObjectName(schedulerJmxObjectName);
		Set<ObjectName> names = connection.queryNames(mBName, null);
		QuartzInstance quartzInstance = new QuartzInstance();
		quartzInstance.setUuid(config.getUuid());
		quartzInstance.setMBeanServerConnection(connection);
		quartzInstance.setJmxConnector(connector);

		List<Scheduler> schList = new ArrayList<Scheduler>();
		for (ObjectName objectName : names) {  // for each scheduler.
			QuartzJMXAdapter jmxAdapter = QuartzJMXAdapterFactory.initQuartzJMXAdapter(objectName, connection);
			quartzInstance.setJmxAdapter(jmxAdapter);

			Scheduler scheduler = jmxAdapter.getSchedulerByJmx(quartzInstance, objectName);
			scheduler.setHost(config.getHost());
			scheduler.setPort(config.getPort());
			scheduler.setUserName(config.getUserName());
			scheduler.setPassword(config.getPassword());
			schList.add(scheduler);

			// attach listener
			// connection.addNotificationListener(objectName, listener, null, null);
			log.info("added listener " + objectName.getCanonicalName());
			// QuartzInstance.putListener(listener);
		}
		quartzInstance.setSchedulerList(schList);
		return quartzInstance;
	}
}