package com.osp.core.ext;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import com.osp.common.util.PropertiesUtil;

public class LoadExt {
	private static final String EXT_DESCRIPTION_FILE_PATH = "META-INFO/osp-ext-config-description";
	private static Class<?>[] CACHE= null;
	private static Class<FileControlled>[] fileClass = null;
	/**
	 * 得到所有扩展class
	 * @return
	 */
	public static Class<FileControlled>[] getFileClass (){
		if(fileClass==null){
			findExtConfigClass(true);
		}
		return fileClass;
	}
	/**
	 * 得到所有的配置class
	 * @return
	 */
	public static Class<?>[] findExtConfigClass(){
		if(CACHE!=null){
			return CACHE;
		}
		return findClass();
	}
	/**
	 * 得到所有的配置class
	 * @param refreshCache true强制刷新
	 * @return
	 */
	public static Class<?>[] findExtConfigClass(boolean refreshCache){
		if(refreshCache){
			return findClass();
		}
		return findExtConfigClass();
	}
	
	private static Class<?>[] findClass() {
		List<Class<?>> list = new ArrayList<>();
		//配置class
		List<Class<FileControlled>> fileList = new ArrayList<>(); 
		ClassLoader classLoader = LoadExt.class.getClassLoader();
		InputStream in = classLoader.getResourceAsStream(EXT_DESCRIPTION_FILE_PATH);
		if(in == null){
			System.out.println("缺少 META-INFO/osp-ext-config-description 配置文件，无法加载扩展项");
		}
		BufferedReader reader = new BufferedReader(new InputStreamReader(in));
		String line = null;
		try {
			while((line = reader.readLine())!=null){
				Class<?> tempClass = null;
				try {
					tempClass = Class.forName(line);
				} catch (ClassNotFoundException e) {
					e.printStackTrace();
					//TODO 抛出一个运行期异常
				}
				boolean assignableFrom = tempClass.getSuperclass().isAssignableFrom(FileControlled.class);
				if(assignableFrom){
					try {
						FileControlled c = (FileControlled) tempClass.newInstance();
						String value = PropertiesUtil.getValue(c.getEnableName());
						if(value!=null&&value.equals("true")){
							//添加配置class
							fileList.add((Class<FileControlled>) tempClass);
							Class<?> configClass = c.getConfigClass();
							if(configClass!=null){
								list.add(configClass);
							}
						}else{
							System.out.println("application.properties中没有开启"+c.getEnableName());
						}
					} catch (InstantiationException e) {
						e.printStackTrace();
					} catch (IllegalAccessException e) {
						e.printStackTrace();
					}
				}else{
					list.add(tempClass);
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}finally{
			if(reader!=null){
				try {
					reader.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		Class<?>[] retVal = list.toArray(new Class<?>[list.size()]);
		CACHE = retVal;
		//赋值pageClass
		fileClass = (Class<FileControlled>[]) fileList.toArray(new Class<?>[fileList.size()]);
		return retVal;
	}
}
