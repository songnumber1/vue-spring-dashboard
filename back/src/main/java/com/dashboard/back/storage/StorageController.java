package com.dashboard.back.storage;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.back.Response.ResponseEntityData;
import com.dashboard.back.dto.StorageItemModel;
import com.dashboard.back.dto.StorageRootItemModel;

@RestController
@RequestMapping("/Storage")
public class StorageController {
    @GetMapping("/root")
    public ResponseEntity<?> storage() {
        String drive, id, absolutePath;
        double totalSize, freeSize, useSize;

        File[] roots = File.listRoots();

        List<StorageRootItemModel> storageRootItemModels = new ArrayList<StorageRootItemModel>();

        for (File root : roots) {
            id = UUID.randomUUID().toString();
            drive = root.getAbsolutePath();
            absolutePath = root.getAbsolutePath();
            totalSize = root.getTotalSpace() / Math.pow(1024, 3);
            useSize = root.getUsableSpace() / Math.pow(1024, 3);
            freeSize = totalSize - useSize;

            List<StorageItemModel> storageItemModels = new ArrayList<StorageItemModel>();

            File[] files = new File(absolutePath).listFiles();

            for (File file : files) {
                if (file.exists()) {
                    StorageItemModel storageItemModel = StorageItemModel.builder().id(UUID.randomUUID().toString())
                            .parendId(id).text(file.getName()).isDirectory(file.isDirectory())
                            .lastModified(file.lastModified()).iscanRead(file.canRead()).iscanWrite(file.canWrite())
                            .isHidden(file.isHidden()).length(file.length()).path(file.getPath()).isDrive(false)
                            .absolutePath(file.getAbsolutePath()).parent(file.getParent()).build();

                    storageItemModels.add(storageItemModel);
                }
            }

            StorageRootItemModel storageRootItemModel = StorageRootItemModel.builder()
                    .id(id)
                    .drive(drive)
                    .absolutePath(absolutePath)
                    .totalSize(totalSize)
                    .useSize(useSize)
                    .freeSize(freeSize)
                    .storageItemModels(storageItemModels).build();

            storageRootItemModels.add(storageRootItemModel);
        }

        return ResponseEntityData.CreateReponse(HttpStatus.OK.value(), "OK", storageRootItemModels, null);
    }
}
