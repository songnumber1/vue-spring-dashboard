package com.dashboard.back.dto.storage;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StorageSelectItemModel {
    private List<StorageSelectHeaderModel> storageSelectHeaderModels;

    private List<StorageSelectContentItemModel> StorageSelectContentItemModels;
}
