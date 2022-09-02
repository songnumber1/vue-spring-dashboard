package com.dashboard.back.dto.storage;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StorageRootItemModel {
    private String id;
    
    private String name;

    private String absolutePath;

    private String totalSize;

    private String useSize;

    private int useSizePercent;

    private List<StorageItemModel> children;
}
