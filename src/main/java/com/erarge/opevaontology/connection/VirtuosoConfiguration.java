package com.erarge.opevaontology.connection;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Component;

@Primary
@Component
@ConfigurationProperties(prefix = "connection")
public class VirtuosoConfiguration {
    private final String prefix = "jdbc:virtuoso://";
	private String ip;
	private int port;
	private String username;
	private String password;
	/**
	 * @return the ip
	 */
	public String getIp() {
		return ip;
	}
	/**
	 * @return the prefix
	 */
	public String getPrefix() {
		return prefix;
	}
	/**
	 * @param ip the ip to set
	 */
	public void setIp(String ip) {
		this.ip = ip;
	}
	/**
	 * @return the port
	 */
	public int getPort() {
		return port;
	}
	/**
	 * @param port the port to set
	 */
	public void setPort(int port) {
		this.port = port;
	}
	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}
	/**
	 * @param username the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}
	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "VirtuosoConfiguration{"+
				"prefix=" + prefix +
				"ip=" + ip +
				"port=" + port +
				"username=" + username +
				"password=" + password +
				"}";
	}
	
}
