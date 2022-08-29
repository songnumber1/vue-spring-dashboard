package com.dashboard.back.restController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.back.common.storage.Common;
import com.dashboard.back.dto.storage.StorageItemModel;
import com.dashboard.back.dto.storage.StorageRootItemModel;
import com.dashboard.back.dto.storage.StorageSelectContentItemModel;
import com.dashboard.back.dto.storage.StorageSelectItemModel;
import com.dashboard.back.response.ResponseEntityData;

@RestController
@RequestMapping("/Storage")
public class StorageController {

    @Autowired
    private Common storageCommon;

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
        StorageSelectItemModel storageSelectItemModels = StorageSelectItemModel.builder().build();

        storageSelectItemModels.setStorageSelectHeaderModels(storageCommon.getStorageSelectHeaderModel());

        List<StorageSelectContentItemModel> storageSelectContentItemModels = new ArrayList<StorageSelectContentItemModel>();

        File dir = new File(path);

        if (!dir.exists()) {
            return ResponseEntityData.CreateReponse(HttpStatus.OK.value(), "OK", storageSelectItemModels, null);
        }

        File files[] = dir.listFiles();

        for (int i = 0; i < files.length; i++) {
            long lastModified = files[i].lastModified();
            String pattern = "yyyy-MM-dd HH:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            Date lastModifiedDate = new Date(lastModified);

            String size = storageCommon.getFileSize(String.valueOf(files[i].length()));
             
            StorageSelectContentItemModel storageSelectContentItemModel = StorageSelectContentItemModel.builder()
                    .id(files[i].getAbsolutePath())
                    .name(files[i].getName())
                    .dir(files[i].isDirectory() == true ? "DIR" : "FILE")
                    .lastModified(simpleDateFormat.format(lastModifiedDate))
                    .path(files[i].getPath())
                    .size(size)
                    .build();

            storageSelectContentItemModels.add(storageSelectContentItemModel);
        }

        storageSelectItemModels.setStorageSelectContentItemModels(storageSelectContentItemModels);

        return ResponseEntityData.CreateReponse(HttpStatus.OK.value(), "OK", storageSelectItemModels, null);
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
