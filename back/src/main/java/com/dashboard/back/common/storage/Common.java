package com.dashboard.back.common.storage;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Component;

import com.dashboard.back.dto.storage.StorageSelectHeaderModel;

@Component
public class Common {
        public List<StorageSelectHeaderModel> getStorageSelectHeaderModel() {
                List<StorageSelectHeaderModel> storageSelectHeaderModels = new ArrayList<StorageSelectHeaderModel>();
                storageSelectHeaderModels
                                .add(StorageSelectHeaderModel.builder().id(UUID.randomUUID().toString()).sortable(true)
                                                .text("id").value("id").align(" d-none").build());

                storageSelectHeaderModels
                                .add(StorageSelectHeaderModel.builder().id(UUID.randomUUID().toString()).sortable(true)
                                                .text("Name").value("name").build());

                storageSelectHeaderModels
                                .add(StorageSelectHeaderModel.builder().id(UUID.randomUUID().toString()).sortable(true)
                                                .text("path").value("path").align(" d-none").build());

                storageSelectHeaderModels
                                .add(StorageSelectHeaderModel.builder().id(UUID.randomUUID().toString()).sortable(true)
                                                .text("Type").value("dir").build());

                storageSelectHeaderModels
                                .add(StorageSelectHeaderModel.builder().id(UUID.randomUUID().toString()).sortable(true)
                                                .text("Last Modified").value("lastModified").build());

                storageSelectHeaderModels
                                .add(StorageSelectHeaderModel.builder().id(UUID.randomUUID().toString()).sortable(true)
                                                .text("Size").value("size").build());

                return storageSelectHeaderModels;
        }
    
        public String getFileSize(String fileSize) {
                if (fileSize.equals("0"))
                        return "0 bytes";

                String result = "0";
                Double size = Double.parseDouble(fileSize);

                String[] sizes = { "bytes", "KB", "MB", "GB", "TB", "PB" };

                if (String.valueOf(fileSize) != "0") {
                        int idx = (int) Math.floor(Math.log(size) / Math.log(1024));
                        DecimalFormat df = new DecimalFormat("#,###.##");
                        double ret = ((size / Math.pow(1024, Math.floor(idx))));
                        result = df.format(ret) + " " + sizes[idx];
                } else {
                        result += " " + sizes[0];
                }

                return result;
        }
        
        public String byteCalculation(String bytes) {
                String retFormat = "0";
                Double size = Double.parseDouble(bytes);

                String[] s = { "bytes", "KB", "MB", "GB", "TB", "PB" };

                if (bytes != "0") {
                        int idx = (int) Math.floor(Math.log(size) / Math.log(1024));
                        DecimalFormat df = new DecimalFormat("#,###.##");
                        double ret = ((size / Math.pow(1024, Math.floor(idx))));
                        retFormat = df.format(ret) + " " + s[idx];
                } else {
                        retFormat += " " + s[0];
                }

                return retFormat;
        }
}
