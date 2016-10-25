/**
 * 
 */
package com.hzy.scp;

import java.io.File;
import java.io.IOException;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.SCPClient;
import ch.ethz.ssh2.Session;

/**
 * @author HZY
 *
 */
public class ChannelSCP {
	
	private static ChannelSCP instance;
	
	private String ip;  
    private int port;  
    private String user;//登录用户名  
    private String password;//生成私钥的密码和登录密码，这两个共用这个密码  
    
    private String PRIVATEKEY = "";// 本机的私钥文件  
    
    // 使用用户名和密码来进行登录验证。如果为true则通过用户名和密码登录，false则使用rsa免密码登录
    private boolean usePassword;
    
    // SSH 连接
    private Connection connection;
    
    synchronized public static ChannelSCP getInstance(final String ipAddress, final String userName, final String userPassword) {
		if (instance == null) {
			instance = new ChannelSCP(ipAddress, userName, userPassword);
		}
		return instance;
	}
    
	private ChannelSCP(final String ipAddress, final String userName, final String userPassword) {
		this.ip   = ipAddress;
		this.port = 22;
		this.user = userName;
		this.password = userPassword;
		this.usePassword = true;
		
		this.connection = new Connection(this.ip, this.port); 
	}

	/** 
     * ssh用户登录验证，使用用户名和密码来认证 
     *  
     * @param user 
     * @param password 
     * @return 
     */  
    public boolean isAuthedWithPassword(String user, String password) {  
        try {  
            return connection.authenticateWithPassword(user, password);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return false;  
    } 
    
    /** 
     * ssh用户登录验证，使用用户名、私钥、密码来认证 其中密码如果没有可以为null，生成私钥的时候如果没有输入密码，则密码参数为null 
     *  
     * @param user 
     * @param privateKey 
     * @param password 
     * @return 
     */  
    public boolean isAuthedWithPublicKey(String user, File privateKey, String password) {  
        try {  
            return connection.authenticateWithPublicKey(user, privateKey, password);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return false;  
    } 
    
    public boolean isAuth() {  
        if (this.usePassword) {  
            return isAuthedWithPassword(this.user, this.password);  
        } else {  
            return isAuthedWithPublicKey(this.user, new File(PRIVATEKEY), this.password);  
        }  
    }  
    
    /**
     * Download File from Remote Server.
     * @param remoteFile
     * @param path
     */
    public void getFile(String remoteFile, String path) {  
        try {  
            connection.connect();  
             
            if (isAuth()) {  
                System.out.println("认证成功!");  
                SCPClient scpClient = connection.createSCPClient();  
                scpClient.get(remoteFile, path);  
            } else {  
                System.out.println("认证失败!");  
            }  
        } catch (IOException e) {  
            e.printStackTrace();  
        } finally {  
            connection.close();  
        }  
    }  
    
    /**
     * Upload File to Remote Server.
     * 
     * @param localFile
     * @param remoteTargetDirectory
     */
    public void putFile(String localFile, String remoteTargetDirectory) {  
        try {  
            connection.connect();
            
            if (isAuth()) {  
                SCPClient scpClient = connection.createSCPClient();  
                scpClient.put(localFile, remoteTargetDirectory);  
            } else {  
                System.out.println("认证失败!");  
            }  
        } catch (Exception ex) {  
            ex.printStackTrace();  
        } finally {  
            connection.close();  
        }  
    }  
    
    /**
     * Execute Shell Command.
     * 
     * @param command
     */
    public void executeCommand(final String cd) {
    	final String command = parseCommand(cd);
    	
		try {
			Session session = connection.openSession();
			
			session.execCommand(command);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
    
    /**
     * Parse Command.
     * 
     * @param cd
     * @return
     */
    private String parseCommand(final String cd) {
    	String afterParse = "";
    	
		return afterParse;
	}
    
    public static void main(String[] args) {  
    	ChannelSCP scp = new ChannelSCP("172.27.18.151", "dev", "Booking@0715");
    	
        try {
        	scp.putFile("C://Users//HZY//Desktop//PNRTimeMachine.jar", "/opt/app/prouser/Temp");  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
    }  
}
