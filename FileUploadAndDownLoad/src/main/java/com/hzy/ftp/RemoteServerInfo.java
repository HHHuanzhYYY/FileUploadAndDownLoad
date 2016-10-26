/**
 * 
 */
package com.hzy.ftp;

/**
 * @author HZY
 *
 */
public final class RemoteServerInfo {
	
	private String serverAddress;
	private String userName;
	private String userPassword;
	private String remoteDirectory;

	/**
	 * 获取remoteDirectory.
	 *
	 * @return the remoteDirectory
	 */
	public String getRemoteDirectory() {
		return remoteDirectory;
	}

	/**
	 * 设置remoteDirectory.
	 *
	 * @param remoteDirectory the remoteDirectory to set
	 */
	public void setRemoteDirectory(String remoteDirectory) {
		this.remoteDirectory = remoteDirectory;
	}

	/**
	 * 获取serverAddress.
	 *
	 * @return the serverAddress
	 */
	public String getServerAddress() {
		return serverAddress;
	}

	/**
	 * 设置serverAddress.
	 *
	 * @param serverAddress the serverAddress to set
	 */
	public void setServerAddress(String serverAddress) {
		this.serverAddress = serverAddress;
	}

	/**
	 * 获取userName.
	 *
	 * @return the userName
	 */
	public String getUserName() {
		return userName;
	}

	/**
	 * 设置userName.
	 *
	 * @param userName the userName to set
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}

	/**
	 * 获取userPassword.
	 *
	 * @return the userPassword
	 */
	public String getUserPassword() {
		return userPassword;
	}

	/**
	 * 设置userPassword.
	 *
	 * @param userPassword the userPassword to set
	 */
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
}
