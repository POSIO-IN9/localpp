package edu.pnu.controller;

import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import edu.pnu.dto.AccountDTO;
import edu.pnu.util.AccountFileUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/api/account")
@CrossOrigin(origins = "*") // CORS 설정을 클래스 레벨에서 적용
public class AccountController {

	private final AccountFileUtil fileUtil;
	private final Map<String, String> accountData = new HashMap<>();

	@PostMapping("/")
	public Map<String, String> register(@RequestParam("files") List<MultipartFile> files) {
		Map<String, String> response = new HashMap<>();
		try {
			log.info("Received files: {}", files);

			if (files.isEmpty()) {
				response.put("RESULT", "FAILURE");
				response.put("MESSAGE", "No files were uploaded.");
				return response;
			}

			List<String> uploadFileNames = fileUtil.saveFiles(files);

			// 업로드된 파일 이름 저장
			String uniqueId = UUID.randomUUID().toString();
			if (!uploadFileNames.isEmpty()) {
				String fileName = uploadFileNames.getFirst(); // 첫 번째 파일 이름 가져오기
				log.info("File name: {}", fileName);
				accountData.put(uniqueId, fileName);

				response.put("RESULT", "SUCCESS");
				response.put("uniqueId", uniqueId);
			} else {
				response.put("RESULT", "FAILURE");
				response.put("MESSAGE", "Failed to save files.");
			}
		} catch (Exception e) {
			log.error("Error processing file upload", e);
			response.put("RESULT", "FAILURE");
			response.put("MESSAGE", "An error occurred while processing the request.");
		}

		log.info("Response: {}", response);
		return response;
	}
	@GetMapping("/data/{uniqueId}")
	public ResponseEntity<Map<String, String>> getFilename(@PathVariable String uniqueId) {
		String fileName = accountData.get(uniqueId);
		Map<String, String> response = new HashMap<>();
		if (fileName != null) {
			response.put("fileName", fileName);
			return ResponseEntity.ok(response);
		} else {
			response.put("error", "File not found");
			return ResponseEntity.status(404).body(response);
		}
	}

	@GetMapping("/view/{fileName}")
	public ResponseEntity<Resource> viewFileGET(@PathVariable String fileName) {
		ResponseEntity<Resource> responseEntity = fileUtil.getFile(fileName);

		if (responseEntity.getStatusCode() != HttpStatus.OK || responseEntity.getBody() == null) {
			return ResponseEntity.notFound().build();
		}

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM); // 파일 다운로드를 위한 설정
		headers.add("Content-Disposition", "attachment;");
		return ResponseEntity.ok().headers(headers).body(responseEntity.getBody());
	}

	@DeleteMapping("/delete/{uniqueId}")
	public ResponseEntity<Map<String, String>> delete(@PathVariable String uniqueId) {
		String fileName = accountData.get(uniqueId);
		Map<String, String> response = new HashMap<>();
		if (fileName != null) {
			boolean isDeleted = fileUtil.deleteFile(fileName);
			if (isDeleted) {
				accountData.remove(uniqueId); // 데이터스토어에서 항목 삭제
				response.put("RESULT", "SUCCESS");
				response.put("message", "File " + fileName + " deleted successfully.");
				return ResponseEntity.ok(response);
			} else {
				response.put("RESULT", "FAIL");
				response.put("message", "Failed to delete file " + fileName);
				return ResponseEntity.status(500).body(response);
			}
		} else {
			response.put("RESULT", "FAIL");
			response.put("message", "File not found for uniqueId " + uniqueId);
			return ResponseEntity.status(404).body(response);
		}
	}
}
