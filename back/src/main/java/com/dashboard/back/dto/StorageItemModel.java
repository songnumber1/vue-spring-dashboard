package com.dashboard.back.dto;

import java.util.List;

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
    private String name;

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

    // 자식 객체
    private List<StorageItemModel> children;
}
