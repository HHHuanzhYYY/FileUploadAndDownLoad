/**
 * Copyright (c) 2015,TravelSky. 
 * All Rights Reserved.
 * TravelSky CONFIDENTIAL
 * 
 * Project Name:CTR Synchronize
 * Package Name:com.travelsky.ngad.bkg.util
 * File Name:ConfigInfo.java
 * Date:2015-10-12 2:03:26
 * 
 */
package com.hzy.util;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

/**
 * ClassName: Config.
 * Description: 
 * Date: 2016年9月20日 上午9:12:28
 * 
 * @author HZY
 */

public final class Config {
	
	private Properties property;
	
    public Config(String cfgFileName) throws IOException {
        readConfigFile(cfgFileName);
    }

    /**
     * 读取配置文件.
     * 配置文件默认目录为 "./config"
     * 
     * @param fileName 配置文件名
     * @throws IOException
     */
    private void readConfigFile(String fileName) throws IOException {
        File cfg = new File(new File(new File("."), "config"), fileName);
        this.property = read(cfg);
    }
    
    public static Properties readConfigFile(Path absolutePath) throws IOException {
    	return read(absolutePath.toFile());
	}
    
    private static Properties read(File file) throws IOException {
    	Properties p = new Properties();
    	try (FileReader fr = new FileReader(file)) {
            p.load(fr);
        }
    	return p;
    }

    public Properties getConfigInfo() {
        return property;
    }

}
