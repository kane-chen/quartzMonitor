package com.easeye.quartz.quartzmonitor.core;

import java.io.FileNotFoundException;
import java.util.List;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.log4j.Logger;

import com.easeye.quartz.quartzmonitor.conf.QuartzConfig;
import com.easeye.quartz.quartzmonitor.object.QuartzInstance;
import com.easeye.quartz.quartzmonitor.object.Scheduler;
import com.easeye.quartz.quartzmonitor.service.SchedulerService;
import com.easeye.quartz.quartzmonitor.service.impl.SchedulerServiceImpl;

public class InitListener implements ServletContextListener {
	
	private static Logger logger = Logger.getLogger(InitListener.class);

	private SchedulerService schedulerService = new SchedulerServiceImpl();
	
	public void contextInitialized(ServletContextEvent event) {
		logger.info("load scheduler to scheduler container");
		
		try {
			List<Scheduler> list = schedulerService.getALLSchedulers();
			for (Scheduler scheduler : list) {
				QuartzConfig config = new QuartzConfig();
				config.setUuid(scheduler.getQuartzInstanceUUID());
				config.setHost(scheduler.getHost());
				config.setPort(scheduler.getPort());
				config.setName(scheduler.getName());
				config.setUserName(scheduler.getUserName());
				config.setPassword(scheduler.getPassword());
				
				try {
					QuartzInstanceContainer.addQuartzConfig(config);
					QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
					QuartzInstance quartzInstance = quartzConnectService.initInstance(config);
					QuartzInstanceContainer.addQuartzInstance(config.getUuid(), quartzInstance);
				} catch (FileNotFoundException e) {
					logger.error(e.getMessage(), e);
					e.printStackTrace();
				} catch (Exception e) {
					logger.error(e.getMessage(), e);
					e.printStackTrace();
				}
			}
		} catch (Exception ex) {
			logger.error(ex.getMessage(), ex);
			ex.printStackTrace();
		}
		
		/*String path = Thread.currentThread().getContextClassLoader().getResource("").getPath() + "quartz-config";
		String path = PropertiesUtil.getPropertiesValue(SystemConfigFile.SYSCONFIG, "configfilepath");
		File file = new File(path);
		if (!file.exists()) {
			file.mkdirs();
		} else {
			File[] fileList = file.listFiles();
			logger.info("find " + fileList.length + " configs of quartz config!");
			for (int i = 0; i < fileList.length; i++) {
				if (!fileList[i].isDirectory() && fileList[i].getName().startsWith("quartz-config-")) {
					try {
						QuartzConfig config = XstreamUtil.xml2Object(fileList[i].getAbsolutePath());
						QuartzInstanceContainer.addQuartzConfig(config);
						QuartzConnectService quartzConnectService = new QuartzConnectServiceImpl();
						QuartzInstance quartzInstance = quartzConnectService.initInstance(config);
						QuartzInstanceContainer.addQuartzInstance(config.getUuid(), quartzInstance);
					} catch (FileNotFoundException e) {
						logger.error(e.getMessage(), e);
						e.printStackTrace();
					} catch (Exception e) {
						logger.error(e.getMessage(), e);
						e.printStackTrace();
					}
				}
			}
		}*/
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {
		// TODO Auto-generated method stub

	}
}