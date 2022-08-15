package com.dashboard.back.restController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class testRestController {
    @GetMapping(value = "/RestTest")
    public String getPostList() {
        return "Rest-API-Test";
    }

    @GetMapping(value = "/csvReader")
    public Object csvReader() {
        HashMap<String, Object> result = new HashMap<String, Object>();

        // Read
        try {
            String path = "C:\\Users\\song min woo\\Desktop\\test.csv";
            File file = new File(path);
            FileInputStream fis = new FileInputStream(file);
            byte[] data = new byte[(int) file.length()];
            fis.read(data);
            fis.close();

            result.put("result", data);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return result;
    }
}
