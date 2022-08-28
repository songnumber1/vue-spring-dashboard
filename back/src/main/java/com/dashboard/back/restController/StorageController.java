package com.dashboard.back.restController;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.back.dto.storage.StorageItemModel;
import com.dashboard.back.dto.storage.StorageRootItemModel;
import com.dashboard.back.dto.storage.StorageSelectHeaderModel;
import com.dashboard.back.dto.storage.StorageSelectItemModel;
import com.dashboard.back.response.ResponseEntityData;

@RestController
@RequestMapping("/Storage")
public class StorageController {
    @GetMapping("/rootNode")
    public ResponseEntity<?> rootNode() {
        String name, id, absolutePath;
        double totalSize, freeSize, useSize;

        File[] roots = File.listRoots();

        List<StorageRootItemModel> storageRootItemModels = new ArrayList<StorageRootItemModel>();

        for (File root : roots) {
            id = root.getAbsolutePath();
            name = root.getAbsolutePath();
            absolutePath = root.getAbsolutePath();
            totalSize = root.getTotalSpace() / Math.pow(1024, 3);
            useSize = root.getUsableSpace() / Math.pow(1024, 3);
            freeSize = totalSize - useSize;

            List<StorageItemModel> storageItemModels = new ArrayList<StorageItemModel>();

            File[] files = new File(absolutePath).listFiles();

            for (File file : files) {
                if (file.exists()) {
                    StorageItemModel storageItemModel = StorageItemModel.builder().id(file.getAbsolutePath())
                            .parendId(id).name(file.getName()).isDirectory(file.isDirectory())
                            .lastModified(file.lastModified()).iscanRead(file.canRead()).iscanWrite(file.canWrite())
                            .isHidden(file.isHidden()).length(file.length()).path(file.getPath())
                            .absolutePath(file.getAbsolutePath()).build();

                    storageItemModels.add(storageItemModel);
                }
            }

            StorageRootItemModel storageRootItemModel = StorageRootItemModel.builder()
                    .id(id)
                    .name(name)
                    .absolutePath(absolutePath)
                    .totalSize(totalSize)
                    .useSize(useSize)
                    .freeSize(freeSize)
                    .children(storageItemModels).build();

            storageRootItemModels.add(storageRootItemModel);
        }

        return ResponseEntityData.CreateReponse(HttpStatus.OK.value(), "OK", storageRootItemModels, null);
    }

    @GetMapping("openNode")
    public ResponseEntity<?> openNode(@RequestParam(required = true) String path) {
        File dir = new File(path);
        File files[] = dir.listFiles();

        List<StorageItemModel> storageItemModels = new ArrayList<StorageItemModel>();

        for (int i = 0; i < files.length; i++) {
            if (files[i].isDirectory()) {
                StorageItemModel storageItemModel = StorageItemModel.builder().id(files[i].getAbsolutePath())
                        .parendId(path).name(files[i].getName()).isDirectory(true)
                        .lastModified(files[i].lastModified()).iscanRead(files[i].canRead())
                        .iscanWrite(files[i].canWrite())
                        .isHidden(files[i].isHidden()).length(files[i].length()).path(files[i].getPath())
                        .absolutePath(files[i].getAbsolutePath())
                        .children(getSubFile(files[i].getAbsolutePath(), true))
                        .build();

                storageItemModels.add(storageItemModel);
            }
        }

        return ResponseEntityData.CreateReponse(HttpStatus.OK.value(), "OK", storageItemModels, null);
    }
    
    @GetMapping("selectNode")
    public ResponseEntity<?> selectNode(@RequestParam(required = true) String path) {
        List<StorageSelectHeaderModel> storageSelectHeaderModels = new ArrayList<StorageSelectHeaderModel>();

        storageSelectHeaderModels.add(StorageSelectHeaderModel.builder().id(UUID.randomUUID().toString()).value("name").text("name").isSort(true).build());
        storageSelectHeaderModels.add(StorageSelectHeaderModel.builder().id(UUID.randomUUID().toString()).value("type").text("type").isSort(true).build());
        storageSelectHeaderModels.add(StorageSelectHeaderModel.builder().id(UUID.randomUUID().toString()).value("lastModified").text("lastModified").isSort(true).build());
        storageSelectHeaderModels.add(StorageSelectHeaderModel.builder().id(UUID.randomUUID().toString()).value("size").text("size").isSort(true).build());
        
        List<StorageItemModel> storageItemModels = getSubFile(path, false);

        StorageSelectItemModel storageSelectItemModel = StorageSelectItemModel.builder().storageSelectHeaderModels(storageSelectHeaderModels).storageSelectItemModels(storageItemModels).build();

        return ResponseEntityData.CreateReponse(HttpStatus.OK.value(), "OK", storageSelectItemModel, null);
    }
    
    private List<StorageItemModel> getSubFile(String path, boolean isOnlyDir) {        
        List<StorageItemModel> storageItemModels = new ArrayList<StorageItemModel>();

        File dir = new File(path);

        if (!dir.exists()) {
            return storageItemModels;
        }

        File files[] = dir.listFiles();


        if (files == null)
            return storageItemModels;

        if (isOnlyDir) {
            for (int i = 0; i < files.length; i++) {
                if (files[i].isDirectory()) {
                    StorageItemModel storageItemModel = StorageItemModel.builder().id(files[i].getAbsolutePath())
                            .parendId(path).name(files[i].getName()).isDirectory(true)
                            .lastModified(files[i].lastModified()).iscanRead(files[i].canRead())
                            .iscanWrite(files[i].canWrite())
                            .isHidden(files[i].isHidden()).length(files[i].length()).path(files[i].getPath())
                            .absolutePath(files[i].getAbsolutePath()).build();

                    storageItemModels.add(storageItemModel);
                }
            }
        }
        else {
            for (int i = 0; i < files.length; i++) {
                Boolean isDir = files[i].isDirectory();

                StorageItemModel storageItemModel = StorageItemModel.builder().id(files[i].getAbsolutePath())
                            .parendId(path).name(files[i].getName()).isDirectory(isDir)
                            .lastModified(files[i].lastModified()).iscanRead(files[i].canRead())
                            .iscanWrite(files[i].canWrite())
                            .isHidden(files[i].isHidden()).length(files[i].length()).path(files[i].getPath())
                            .absolutePath(files[i].getAbsolutePath()).build();

                    storageItemModels.add(storageItemModel);
            }
        }

        return storageItemModels;
    }
}
