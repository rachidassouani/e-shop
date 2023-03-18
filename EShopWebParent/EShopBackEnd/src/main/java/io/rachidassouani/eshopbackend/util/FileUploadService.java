package io.rachidassouani.eshopbackend.util;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

import org.springframework.web.multipart.MultipartFile;

public class FileUploadService {
	
	public static void saveFile(String uploadDir, String fileName, MultipartFile multipartFile) throws IOException {
		
		Path path = Paths.get(uploadDir);
		
		if (!Files.exists(path)) {
			Files.createDirectories(path);
		}
		
		try (InputStream inputStream = multipartFile.getInputStream()){
			Path filePath = path.resolve(fileName);
			Files.copy(inputStream, filePath, StandardCopyOption.REPLACE_EXISTING);
		
		} catch (IOException e) {
			throw new IOException("Couldn't save file" + fileName, e);
		}
	}
}
