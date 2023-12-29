package com.dashboard.back.auth.restcontroller.summary;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.FileTime;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dashboard.back.auth.model.response.MovieTableItemModel;
import com.dashboard.back.auth.util.ResponseData;

@RestController
@RequestMapping("/summary/api/movie")
public class MovieRestController {
    @PostMapping("/getdirinfo")
    public ResponseEntity<?> getDirInfo(String absolutePath) throws IOException {
        List<MovieTableItemModel> movieTableItemModels = new ArrayList<MovieTableItemModel>();

        File[] files = new File(absolutePath).listFiles();

        for (File file : files) {
            if (file.isFile()) {
                int pos = file.getName().lastIndexOf(".");
                String ext = file.getName().substring(pos + 1);

                if (ext.toLowerCase().equals("mp4")) {
                    MovieTableItemModel movieTableItemModel = MovieTableItemModel.builder()
                            .id(UUID.randomUUID().toString()).absolutePath(file.getAbsolutePath()).name(file.getName())
                            .isDir(file.isDirectory()).writeDate(fileCreatedInfo(file.getAbsolutePath())).build();

                    movieTableItemModels.add(movieTableItemModel);
                }
            } else {
                MovieTableItemModel movieTableItemModel = MovieTableItemModel.builder().id(UUID.randomUUID().toString())
                        .absolutePath(file.getAbsolutePath()).name(file.getName()).isDir(file.isDirectory())
                        .writeDate(fileCreatedInfo(file.getAbsolutePath())).build();

                movieTableItemModels.add(movieTableItemModel);
            }
        }

        return ResponseData.CreateReponse(HttpStatus.OK.value(), "OK", movieTableItemModels, null);
    }

    public String fileCreatedInfo(String path) throws IOException {
        Path file = Paths.get(path);

        FileTime creationTime = (FileTime) Files.getAttribute(file, "creationTime");
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        String dateCreated = df.format(creationTime.toMillis());

        return dateCreated;
    }
}