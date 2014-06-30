package com.easeye.quartz.quartzmonitor.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.easeye.quartz.quartzmonitor.object.Trigger;

public class TriggerContainer {
	
	private static Map<String, Trigger> triggerMap = new ConcurrentHashMap<String, Trigger>();
	
	public static  void addTrigger(String uuid,Trigger trigger){
		triggerMap.put(uuid, trigger);
	}
	public static Trigger getTriggerById(String uuid){
		return triggerMap.get(uuid);
	}
	public static void removeTriggerById(String uuid){
		triggerMap.remove(uuid);
	}
}