package com.dashboard.back.auth.restcontroller.data;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.back.auth.model.response.StorageItemModel;
import com.dashboard.back.auth.util.ResponseData;

@RestController
@RequestMapping("/data/api")
public class StorageRestController {
	@PostMapping("/storage/getdirinfo")
	public ResponseEntity<?> getDirInfo(String parentId, String path) {
		List<StorageItemModel> storageItemModels = new ArrayList<StorageItemModel>();

		File[] files = new File(path).listFiles();

		for (File file : files) {
			if (file.exists()) {
				StorageItemModel storageItemModel = StorageItemModel.builder().id(UUID.randomUUID().toString())
						.parendId(parentId).text(file.getName()).isDirectory(file.isDirectory())
						.lastModified(file.lastModified()).iscanRead(file.canRead()).iscanWrite(file.canWrite())
						.isHidden(file.isHidden()).length(file.length()).path(file.getPath()).isDrive(false)
						.absolutePath(file.getAbsolutePath()).parent(file.getParent()).build();

				storageItemModels.add(storageItemModel);
			}
		}

		// JSONObject jsonObjDrive = new JSONObject();
		// jsonObjDrive.put("nodes", storageItemModels);

		return ResponseData.CreateReponse(HttpStatus.OK.value(), "OK", storageItemModels, null);
	}
}
