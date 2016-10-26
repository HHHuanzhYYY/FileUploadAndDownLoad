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
    private String user;
    private String password;
    
    private String PRIVATEKEY = "";
    
    // 
    private boolean usePassword;
    
    // 
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
     * 
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
     * 
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
                System.out.println("��֤�ɹ�!");  
                SCPClient scpClient = connection.createSCPClient();  
                scpClient.get(remoteFile, path);  
            } else {  
                System.out.println("��֤ʧ��!");  
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
                System.out.println("��֤ʧ��!");  
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
