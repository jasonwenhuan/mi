package com.chinaunicom.filterman.backend.ftp;

import java.io.FileOutputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.io.IOUtils;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;

import com.chinaunicom.filterman.utilities.Logging;

/**
 * @author wenhuan
 * @date Dec 25, 2013 
 * @time 5:40:07 PM
 */
public class FTPService {
	private String ftpHost;
	private String ftpUserName;
	private String ftpPassword;
	private String remoteFilePath;
	private String downloadFilePath;

	private String remoteFileName;
	private String downloadFileName;

	public void formatSummaryFileName() {
		Date now = new Date();
		SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd");
		String datePrefix = dateformat.format(now);
		setDownloadFileName(downloadFilePath + datePrefix + "-dailysum.csv");
		setRemoteFileName(datePrefix + "-dailysum.csv");
	}

	public boolean download() {
		
		formatSummaryFileName();

		boolean downloadSuccess = true;

		FTPClient ftpClient = new FTPClient();
		FileOutputStream fos = null;

		try {
			ftpClient.connect(ftpHost);
			ftpClient.login(ftpUserName, ftpPassword);

			FTPFile[] ftpFiles = ftpClient.listFiles(remoteFileName);

			if (ftpFiles.length > 0) {
				fos = new FileOutputStream(downloadFileName);
				ftpClient.setBufferSize(1024);
				ftpClient.setFileType(FTPClient.BINARY_FILE_TYPE);
				System.out.println("downloading " + remoteFileName + " ...");
				ftpClient.retrieveFile(remoteFileName, fos);
				System.out.println("downloading " + remoteFileName
						+ " finished");
			} else {
				downloadSuccess = false;
				Logging.logError("daily summary file does not exist");
			}
		} catch (IOException e) {
			downloadSuccess = false;
			Logging.logError("Doing download dailysummary.csv error occured", e);
		} finally {
			IOUtils.closeQuietly(fos);
			try {
				ftpClient.disconnect();
			} catch (IOException e) {
				Logging.logError(
						"Doing download dailysummary.csv disconnect ftp client occured",
						e);
			}
		}
		return downloadSuccess;
	}
	
	public String getFtpHost() {
		return ftpHost;
	}

	public void setFtpHost(String ftpHost) {
		this.ftpHost = ftpHost;
	}

	public String getFtpUserName() {
		return ftpUserName;
	}

	public void setFtpUserName(String ftpUserName) {
		this.ftpUserName = ftpUserName;
	}

	public String getFtpPassword() {
		return ftpPassword;
	}

	public void setFtpPassword(String ftpPassword) {
		this.ftpPassword = ftpPassword;
	}

	public String getRemoteFileName() {
		return remoteFileName;
	}

	public void setRemoteFileName(String remoteFileName) {
		this.remoteFileName = remoteFileName;
	}

	public String getDownloadFileName() {
		return downloadFileName;
	}

	public void setDownloadFileName(String downloadFileName) {
		this.downloadFileName = downloadFileName;
	}

	public String getRemoteFilePath() {
		return remoteFilePath;
	}

	public void setRemoteFilePath(String remoteFilePath) {
		this.remoteFilePath = remoteFilePath;
	}

	public String getDownloadFilePath() {
		return downloadFilePath;
	}

	public void setDownloadFilePath(String downloadFilePath) {
		this.downloadFilePath = downloadFilePath;
	}
}
