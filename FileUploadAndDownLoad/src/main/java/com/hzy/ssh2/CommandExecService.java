/**
 * 
 */
package com.hzy.ssh2;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hzy.util.RemoteServerInfo;

import ch.ethz.ssh2.Connection;
import ch.ethz.ssh2.Session;

/**
 * @author HZY
 *
 */
@Service("commandExec")
public class CommandExecService {
	
	@Autowired
	private final RemoteServerInfo remoteServerInfo;
	
	@Autowired
	private Connection connection; 

	/**
	 * 
	 */
	public CommandExecService(RemoteServerInfo info, Connection conn) {
		this.remoteServerInfo = info;
		this.connection = conn; 
	}

	/** 
     * 
     *  
     * @param user 
     * @param password 
     * @return 
     */  
    public boolean authenticatedWithPassword(String user, String password) {  
        try {  
            return connection.authenticateWithPassword(user, password);  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
        return false;  
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
}
