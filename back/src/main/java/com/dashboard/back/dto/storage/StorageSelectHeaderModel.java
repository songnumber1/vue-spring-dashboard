package com.dashboard.back.dto.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Builder.Default;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageSelectHeaderModel {
    // id
    private String id;

    // text
    private String text;

    // 값
    private String value;

    // 정렬
    @Default
    private boolean sortable = true;

    // 글자 정렬
    private String align;
}
