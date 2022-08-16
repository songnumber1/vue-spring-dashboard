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
public class StorageRootItemModel {
    private String id;
    
    private String drive;

    private String absolutePath;

    private double totalSize;

    private double useSize;

    private double freeSize;

    private List<StorageItemModel> storageItemModels;
}