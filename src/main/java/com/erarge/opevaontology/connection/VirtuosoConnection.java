package com.erarge.opevaontology.connection;



import org.apache.jena.atlas.logging.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;



@Component
@EnableConfigurationProperties(VirtuosoConfiguration.class)
public class VirtuosoConnection {

	@Autowired(required = false)
	private VirtuosoConfiguration virtConfig;
	
	public static String prefix;
	public static String ip;
	public static int port;
	public static String username;
	public static String password;
	
	@PostConstruct
	public void init() {
		if(virtConfig == null) {
			Log.info(VirtuosoConfiguration.class, "The configuration is not set!");
		} else {
			Log.info(VirtuosoConfiguration.class, virtConfig.toString());
			prefix = virtConfig.getPrefix();
			ip = virtConfig.getIp();
			port = virtConfig.getPort();
			username = virtConfig.getUsername();
			password = virtConfig.getPassword();
		}
	}
	
	public static String getHost() {
		return prefix + ip + ":" + port;
	}
	
	public static String getUsername() {
		return username;
	}
	
	public static String getPassword() {
		return password;
	}
	
}
