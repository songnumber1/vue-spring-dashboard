package com.dashboard.back.auth.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageItemModel {
	// 아이디
	private String id;

	// 부모 아이디
	private String parendId;

	// 이름
	private String text;

	// 디렉토리 / 파일
	private boolean isDirectory;

	// 최종 수정일자
	private long lastModified;

	// 읽기 가능
	private boolean iscanRead;

	// 쓰기 가능
	private boolean iscanWrite;

	// 숨김 파일
	private boolean isHidden;

	// 크기
	private long length;

	// 상대 경로
	private String path;

	// 상대 경로
	private String absolutePath;

	// 부모 경로
	private String parent;
	
	// 드라이브
	private boolean isDrive;
}
