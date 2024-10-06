package edu.pnu.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class AccountDTO {
	private Long Ano;
	@Builder.Default
	private List<MultipartFile> files = new ArrayList<>();
	@Builder.Default
	private List<String> uploadFileNames=new ArrayList<>();
	private String uniqueId; // 고유번호
}
