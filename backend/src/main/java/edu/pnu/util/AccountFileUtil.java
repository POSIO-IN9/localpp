package edu.pnu.util;
//파일처리
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;

@Component
@Log4j2
@RequiredArgsConstructor
public class AccountFileUtil {
	@Value("${org.zerock.upload.path}")
	private String uploadPath;

	@PostConstruct
	public void init() {
		File tempFolder = new File(uploadPath);
		if(!tempFolder.exists()) {
			boolean mkdir = tempFolder.mkdir();
		}
		uploadPath=tempFolder.getAbsolutePath();
		log.info("--------------------------------");
		log.info(uploadPath);
	}

	public List<String> saveFiles(@RequestPart("files") List<MultipartFile> files) throws RuntimeException{
		if(files==null || files.isEmpty()) {
			return List.of();
		}
		List<String> uploadNames=new ArrayList<>();
		for(MultipartFile multipartFile : files) {
			String savedName =multipartFile.getOriginalFilename();
			if(savedName == null){
				continue;
			}

			Path savePath = Paths.get(uploadPath, savedName);

			try {
				Files.copy(multipartFile.getInputStream(), savePath);
				uploadNames.add(savedName);
				System.out.println("File saved:" + savePath.toString());
			}catch(IOException e) {
				throw new RuntimeException(e.getMessage());
			}
		}
		return uploadNames;
	}

	public ResponseEntity<Resource> getFile(String fileName){
		Resource resource = new FileSystemResource(uploadPath+ File.separator + fileName);
		if(!resource. isReadable() ) {
			resource = new FileSystemResource(uploadPath+File.separator + "default.pdf");
		}
		HttpHeaders headers = new HttpHeaders();

		try {
			headers.add("Content-Type", Files.probeContentType( resource.getFile().toPath()));
		}catch(Exception e) {
			return ResponseEntity.internalServerError().build();
		}
		return ResponseEntity.ok().headers(headers).body(resource);
	}
	public boolean deleteFile(String fileName) {
		try {
			Path filePath = Paths.get(uploadPath, fileName);
			return Files.deleteIfExists(filePath);
		} catch (IOException e) {
			log.error("Failed to delete file: " + fileName, e);
			return false;
		}
	}
}