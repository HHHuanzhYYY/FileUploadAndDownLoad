/**
 * 
 */
package com.hzy.ftp;

import java.io.Closeable;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.SocketException;

import org.apache.commons.net.ftp.FTPReply;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author HZY
 *
 */
public class ChannelFTP implements Closeable {
	
	//@Autowired
	private String localDirectory = "C:/Users/HZY/Desktop/";
	
	@Autowired
	private RemoteServerInfo remoteServerInfo;

	@Autowired
	private FTPClient ftp;

	public ChannelFTP(FTPClient ftpClient, RemoteServerInfo info)
			throws SocketException, IOException {
		this.ftp = ftpClient;
		this.remoteServerInfo = info;
		
		initFTP();
	}
	
	private boolean initFTP() throws SocketException, IOException {
		//FTPClientConfig config = new FTPClientConfig();
		
		
		this.ftp.connect(remoteServerInfo.getServerAddress());
		
		if(!ftp.login(remoteServerInfo.getUserName(), remoteServerInfo.getUserPassword())) {
            ftp.logout();
            return false;
        }

        //FTPReply stores a set of constants for FTP reply codes. 
        if (!FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            ftp.disconnect();
            return false;
        }

        //enter passive mode
        ftp.enterLocalPassiveMode();
        //get system name
        System.out.println("Remote system is " + ftp.getSystemType());
        //change current directory
        ftp.changeWorkingDirectory(remoteServerInfo.getRemoteDirectory());
        System.out.println("Current directory is " + ftp.printWorkingDirectory());
		
		return true;
	}
	
	public void upload(final String fileToFTP) {
		File upload = new File(localDirectory + "/" + fileToFTP);
		try (InputStream is = new FileInputStream(upload)) {
			this.ftp.storeFile(upload.getName(), is);
		} catch (Throwable t) {
			t.printStackTrace();
		}
	}
	
	public void download() {
		
	}

	/* (non-Javadoc)
	 * @see java.io.Closeable#close()
	 */
	@Override
	public void close() throws IOException {
		if (this.ftp != null) {
			ftp.logout();
			ftp.disconnect();
		}
	}


	
	@SuppressWarnings("resource")
	public static void main(String[] args) {
		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("classpath:spring.xml");
		
		ChannelFTP ftp = (ChannelFTP) applicationContext.getBean("channelFTP");
		ftp.upload("test.txt");
	}
}
