package com.dashboard.back.auth.controller.data;

import java.io.File;
import java.util.ArrayList;
import java.util.UUID;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.dashboard.back.auth.model.response.StorageItemModel;

@Controller
@RequestMapping("/storage")
public class StorageController {
    @GetMapping("/{menuid}/{menudept}")
    public String storage(Model menuModel, Model model, @PathVariable(value = "menuid") String menuid,
            @PathVariable(value = "menudept") String menudept) {
        String drive, id, absolutePath;

        double totalSize, freeSize, useSize;

        File[] roots = File.listRoots();

        JSONArray jsonArrDrive = new JSONArray();

        for (File root : roots) {
            id = UUID.randomUUID().toString();
            drive = root.getAbsolutePath();
            absolutePath = root.getAbsolutePath();
            totalSize = root.getTotalSpace() / Math.pow(1024, 3);
            useSize = root.getUsableSpace() / Math.pow(1024, 3);
            freeSize = totalSize - useSize;

            JSONObject jsonObjDrive = new JSONObject();

            jsonObjDrive.put("id", id);
            jsonObjDrive.put("parentId", "");
            jsonObjDrive.put("text", drive);
            jsonObjDrive.put("absolutePath", absolutePath);
            jsonObjDrive.put("totalSize", totalSize);
            jsonObjDrive.put("useSize", useSize);
            jsonObjDrive.put("freeSize", freeSize);
            jsonObjDrive.put("isDrive", true);

            jsonObjDrive.put("nodes", new ArrayList<StorageItemModel>());
            jsonArrDrive.put(jsonObjDrive);
        }

        ArrayList<JSONObject> arrayJson = new ArrayList<JSONObject>();

        for (int k = 0; k < jsonArrDrive.length(); k++) {
            JSONObject tempJson = jsonArrDrive.getJSONObject(k);
            arrayJson.add(tempJson);
        }

        model.addAttribute("reponseData", arrayJson);

        return "storage/storage";
    }

}
