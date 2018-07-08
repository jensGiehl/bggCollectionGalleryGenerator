package de.agiehl.bggCollectionGalleryGenerator.ftp;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.net.ftp.FTP;
import org.apache.commons.net.ftp.FTPClient;
import org.springframework.beans.factory.annotation.Value;
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

	// TODO: Logging
	public void uploadFiles(File htmlFile) {
		FTPClient ftpClient = new FTPClient();
		try {

			ftpClient.connect(ftpServer, ftpPort);
			ftpClient.login(ftpUsername, ftpPassword);
			ftpClient.enterLocalPassiveMode();

			ftpClient.setFileType(FTP.BINARY_FILE_TYPE);

			InputStream inputStream = new FileInputStream(htmlFile);
			if (!remoteDir.endsWith("/")) {
				remoteDir += "/";
			}

			// TODO: Find a better way to mkdir
			ftpClient.makeDirectory(remoteDir);
			ftpClient.makeDirectory(remoteDir + "css");
			ftpClient.makeDirectory(remoteDir + "img");
			ftpClient.makeDirectory(remoteDir + "js");
			boolean done = ftpClient.storeFile(remoteDir + targetFileName, inputStream);
			inputStream.close();

			// Upload assets
			// TODO: DRY
			// TODO: Favicon
			ClassLoader cl = this.getClass().getClassLoader();
			ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver(cl);
			Resource[] resources = resolver.getResources("classpath:/static/css/*");
			for (Resource resource : resources) {
				ftpClient.storeFile(remoteDir + "css/" + resource.getFilename(), resource.getInputStream());
			}

			resources = resolver.getResources("classpath:/static/img/*");
			for (Resource resource : resources) {
				ftpClient.storeFile(remoteDir + "img/" + resource.getFilename(), resource.getInputStream());
			}

			resources = resolver.getResources("classpath:/static/js/*");
			for (Resource resource : resources) {
				ftpClient.storeFile(remoteDir + "js/" + resource.getFilename(), resource.getInputStream());
			}

			if (done) {
				System.out.println("The first file is uploaded successfully.");
			}
		} catch (IOException ex) {

		} finally {
			try {
				if (ftpClient.isConnected()) {
					ftpClient.logout();
					ftpClient.disconnect();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}
