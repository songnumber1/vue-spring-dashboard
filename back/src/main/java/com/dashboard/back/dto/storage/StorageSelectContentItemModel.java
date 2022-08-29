package com.dashboard.back.dto.storage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageSelectContentItemModel {
    private String id;

    private String name;

    private String path;

    private String dir;

    private String lastModified;

    private String size;
}
