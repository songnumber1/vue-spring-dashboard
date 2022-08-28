package com.dashboard.back.restController;

import java.io.Console;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.back.Response.ResponseEntityData;
import com.dashboard.back.dto.StorageItemModel;
import com.dashboard.back.dto.StorageRootItemModel;

@RestController
@RequestMapping("/Storage")
public class StorageController {
    @GetMapping("/root")
    public ResponseEntity<?> storage() {
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

    @GetMapping("getFiles")
    public ResponseEntity<?> getFiles(@RequestParam(required = true) String path) {
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
                        .children(getSubFile(files[i].getAbsolutePath()))
                        .build();
                                
                storageItemModels.add(storageItemModel);
            }
        }

        return ResponseEntityData.CreateReponse(HttpStatus.OK.value(), "OK", storageItemModels, null);
    }
    
    private List<StorageItemModel> getSubFile(String path) {        
        File dir = new File(path);
        File files[] = dir.listFiles();

        List<StorageItemModel> storageItemModels = new ArrayList<StorageItemModel>();

        if (files == null)
            return storageItemModels;

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

        return storageItemModels;
    }
}
