package com.easeye.quartz.quartzmonitor.util;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.configuration.AbstractConfiguration;
import org.apache.commons.configuration.CompositeConfiguration;
import org.apache.commons.configuration.ConfigurationException;
import org.apache.commons.configuration.PropertiesConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.easeye.quartz.quartzmonitor.exception.PropertiesNotFindException;

public class PropertiesUtil {

	protected static Logger logger = LoggerFactory.getLogger(PropertiesUtil.class);
	
	public static Pattern varPtn = Pattern.compile("\\$\\{\\s*([^\\}]+)\\s*\\}");
	
	public static void main(String[] args) {
		String path = "${	store_BaseFilePath }\\Attachments";
		
		Matcher m = varPtn.matcher(path);
		if (m.find()) {
			System.out.println(m.group(1));
		}
	}
	
	/** 
     * 功能: 获取某个properties文件中的某个key对应的value值 
     * @param fileName properties文件名
     * @param key properties文件中的key
     * @return 返回key说对应的value值 
     * */  
    public static String getPropertiesValue(String fileName, String key){  
        CompositeConfiguration config = new CompositeConfiguration();  
        PropertiesConfiguration pc = null;  
        try {  
            pc = new PropertiesConfiguration(fileName); 
            config.addConfiguration(pc);  
            
            String filevalue = config.getString(key).trim();  
            return filevalue;  
        } catch (ConfigurationException e) {  
        	logger.error(e.getMessage(), e);
            e.printStackTrace();  
        }  
  
        return null;  
    }  
  
    /** 
     * 功能: 获取某个properties文件中的某个key对应的value值(值是个数组) 
     * @param fileName properties文件名
     * @param key properties文件中的key
     * @param delimiter 分隔符(默认是逗号)
     * @return 返回key说对应的value数组值(使用时遍历数组值后要加.trim()) 
     * */  
    public static String[] getPropertiesValues(String fileName, String key, char delimiter){  
        CompositeConfiguration config = new CompositeConfiguration();  
        PropertiesConfiguration pc = null;  
        try {  
            if(!Character.isWhitespace(delimiter)){  
               AbstractConfiguration.setDefaultListDelimiter(delimiter);  
            }  
            pc = new PropertiesConfiguration(fileName);  
            config.addConfiguration(pc);  
  
            String[] filevalues = config.getStringArray(key);  
            return filevalues;  
        } catch (ConfigurationException e) {  
        	logger.error(e.getMessage(), e);
            e.printStackTrace();  
        }  
  
        return null;  
    }
    
    /** 
     * 功能: 获取某个properties文件中的所有key值 
     * @param fileName properties文件名
     * @return 返回文件中所有key的数组(使用时遍历数组值后要加.trim()) 
     * */
    public static String[] getPropertiesValues(String fileName){  
        CompositeConfiguration config = new CompositeConfiguration();  
        PropertiesConfiguration pc = null;  
        try {  
            pc = new PropertiesConfiguration(fileName);  
            config.addConfiguration(pc);  
            List<String> list = new ArrayList<String>();
            Iterator<String> item =  config.getKeys();
            for (Iterator<String> iterator = item; iterator.hasNext();) {
				String tmpkey = (String) iterator.next();
				list.add(tmpkey.trim());
			}
            
            String[] arrkeys = list.toArray(new String[list.size()]);
            
            return arrkeys;  
        } catch (ConfigurationException e) { 
        	logger.error(e.getMessage(), e);
            e.printStackTrace();  
        }  
  
        return null;  
    }
    
    /** 
     * 功能: 获取某个properties文件中的某个key对应的value值 
     * @param filePath properties文件所在的绝对路径
     * @param fileName properties文件名
     * @param key properties文件中的key
     * @return 返回key说对应的value值 
     * */  
    public static String getPropertiesValue(String filePath, String fileName, String key){  
        CompositeConfiguration config = new CompositeConfiguration();  
        PropertiesConfiguration pc = null;  
        try {  
            pc = new PropertiesConfiguration(filePath+fileName); 
            config.addConfiguration(pc);  
            
            String filevalue = config.getString(key).trim();  
            return filevalue;  
        } catch (ConfigurationException e) {  
        	logger.error(e.getMessage(), e);
            e.printStackTrace();  
        }  
  
        return null;  
    }  
  
    /** 
     * 功能: 获取某个properties文件中的某个key对应的value值(值是个数组) 
     * @param filePath properties文件所在的绝对路径
     * @param fileName properties文件名
     * @param key properties文件中的key
     * @param delimiter 分隔符(默认是逗号)
     * @return 返回key说对应的value数组值(使用时遍历数组值后要加.trim()) 
     * */  
    public static String[] getPropertiesValues(String filePath, String fileName, String key, char delimiter){  
        CompositeConfiguration config = new CompositeConfiguration();  
        PropertiesConfiguration pc = null;  
        try {  
            if(!Character.isWhitespace(delimiter)){  
               AbstractConfiguration.setDefaultListDelimiter(delimiter);  
            }  
            pc = new PropertiesConfiguration(filePath+fileName);  
            config.addConfiguration(pc);  
  
            String[] filevalues = config.getStringArray(key);  
            return filevalues;  
        } catch (ConfigurationException e) {  
        	logger.error(e.getMessage(), e);
            e.printStackTrace();  
        }  
  
        return null;  
    }
    
    /** 
     * 功能: 获取某个properties文件中的所有key值
     * @param filePath properties文件所在的绝对路径
     * @param fileName properties文件名
     * @return 返回文件中所有key的数组(使用时遍历数组值后要加.trim()) 
     * */
    public static String[] getPropertiesValues(String filePath, String fileName){  
        CompositeConfiguration config = new CompositeConfiguration();  
        PropertiesConfiguration pc = null;  
        try {  
            pc = new PropertiesConfiguration(filePath+fileName);  
            config.addConfiguration(pc);  
            List<String> list = new ArrayList<String>();
            Iterator<String> item =  config.getKeys();
            for (Iterator<String> iterator = item; iterator.hasNext();) {
				String tmpkey = (String) iterator.next();
				list.add(tmpkey.trim());
			}
            
            String[] arrkeys = list.toArray(new String[list.size()]);
            
            return arrkeys;  
        } catch (ConfigurationException e) { 
        	logger.error(e.getMessage(), e);
            e.printStackTrace();  
        }  
  
        return null;  
    }
	
	public static Object setObjectProperty(Object object, File c3p0file) throws PropertiesNotFindException {
		InputStream in;
		try {
			in = new BufferedInputStream(new FileInputStream(c3p0file));
			Properties property = new Properties();
			property.load(in);
			for (Object key : property.keySet()) {
				String functionName = "set" + key.toString().substring(0, 1).toUpperCase() + key.toString().substring(1, key.toString().length());
				for (int i = 0; i < 3; i++) {
					try {
						Class<?> class1 = null;
						switch (i) {
						case 0:
							class1 = int.class;
							break;
						case 1:
							class1 = String.class;
							break;
						case 2:
							class1 = boolean.class;
							break;
						}
						Method method = object.getClass().getDeclaredMethod(functionName, class1);
						switch (i) {
						case 0:
							method.invoke(object, Integer.parseInt(property.get(key).toString()));
							break;
						case 1:
							method.invoke(object, property.get(key).toString());
							break;
						case 2:
							method.invoke(object, Boolean.parseBoolean(property.get(key).toString()));
							break;

						default:
							break;
						}
						break;
					} catch (SecurityException e) {
						// logger.error("存在安全管理器 s，并满足下列任一条件： 调用 s.checkMemberAccess(this, Member.DECLARED) 拒绝访问已声明方法 调用方的类加载器不同于也不是该类的类加载器的一个祖先，并且对 s.checkPackageAccess() 的调用拒绝访问该类的包。"
						// + e.getMessage(), e);
					} catch (NoSuchMethodException e) {
						// logger.error(e.getMessage(), e);
					} catch (IllegalArgumentException e) {
						// logger.error("方法是实例方法，且指定对象参数不是声明基础方法的类或接口（或其中的子类或实现程序）的实例；如果实参和形参的数量不相同；如果基本参数的解包转换失败；如果在解包后，无法通过方法调用转换将参数值转换为相应的形参类型。 "
						// + e.getMessage(), e);
						// e.printStackTrace();
					} catch (IllegalAccessException e) {
						// logger.error("此 Method 对象强制执行 Java 语言访问控制，并且基础方法是不可访问的。"
						// + e.getMessage(), e);
						// e.printStackTrace();
					} catch (InvocationTargetException e) {
						// logger.error("如果基础方法抛出异常." + e.getMessage());
						// e.printStackTrace();
					}
				}
			}
			return object;
		} catch (FileNotFoundException e1) {
			throw new PropertiesNotFindException("配置文件不存在，文件路径：" + c3p0file.getAbsolutePath(), e1);
		} catch (IOException e) {
			throw new PropertiesNotFindException("配置文件读取是IO失败，文件路径：" + c3p0file.getAbsolutePath(), e);
		}
	}
}