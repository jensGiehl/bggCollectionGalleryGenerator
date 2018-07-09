package de.agiehl.bggCollectionGalleryGenerator.ftp;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.stereotype.Component;

@Component
public class FileUploader {

	@Value("${ftp.server}")
	private String ftpServer;

	@Value("${ftp.port:21}")
	private int ftpPort;

	@Value("${ftp.user}")
	private String ftpUsername;

	@Value("${ftp.password}")
	private String ftpPassword;

	@Value("${ftp.remoteDir:/}")
	private String remoteDir;

	@Value("${ftp.targetFile:index.html}")
	private String targetFileName;

	private static final Logger logger = LoggerFactory.getLogger(FileUploader.class);

	public void uploadFiles(File htmlFile) {
		if (!remoteDir.endsWith("/")) {
			remoteDir += "/";
		}

		FTPClient ftpClient = new FTPClient();
		try {
			logger.info("Beginn upload to {} (directory: {})", ftpServer, remoteDir);

			ftpClient.connect(ftpServer, ftpPort);
			ftpClient.login(ftpUsername, ftpPassword);
			ftpClient.enterLocalPassiveMode();
			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			Resource htmlResource = new FileSystemResource(htmlFile);
			upload(ftpClient, htmlResource, remoteDir, targetFileName);

			uploadAllResources(ftpClient, "css");
			uploadAllResources(ftpClient, "js");
			uploadAllResources(ftpClient, "img");

		} catch (IOException e) {
			logger.error("Error while connection to FTP " + ftpServer, e);
		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				logger.error("Error while closing connection to " + ftpServer, ex);
			}
		}
	}

	private void uploadAllResources(FTPClient ftpClient, String dirName) {
		try {
			ClassLoader cl = this.getClass().getClassLoader();
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
			Resource[] resources = resolver.getResources("classpath:/static/" + dirName + "/*");
			for (Resource resource : resources) {
				logger.info("Upload " + resource.getFilename());
				upload(ftpClient, resource, remoteDir + dirName + "/", resource.getFilename());
			}
		} catch (IOException e) {
			logger.error("Error while uploading resources in directory " + dirName, e);
		}
	}

	private void upload(FTPClient ftpClient, Resource resource, String directory, String filename) {
		try (InputStream inputStream = resource.getInputStream()) {
			String pathAndFilename = directory + filename;

			ftpClient.makeDirectory(directory);

			ftpClient.storeFile(pathAndFilename, inputStream);
			inputStream.close();

			logger.info("File-Upload {} done", pathAndFilename);
		} catch (IOException e) {
			logger.error("Error while uploading " + resource.getFilename(), e);
		}
	}

}
