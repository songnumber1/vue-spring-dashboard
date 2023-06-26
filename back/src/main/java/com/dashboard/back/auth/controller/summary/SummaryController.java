package com.dashboard.back.auth.controller.summary;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.dashboard.back.auth.common.FinalVariable;
import com.dashboard.back.auth.model.response.MovieTreeItemModel;
import com.dashboard.back.auth.service.MultipartFileService;

@Controller
@RequestMapping("/summary")
public class SummaryController {
    @GetMapping("/movie/{menuid}/{menudept}")
    public String movie(Model menuModel, Model model, @PathVariable(value = "menuid") String menuid,
            @PathVariable(value = "menudept") String menudept) {
        File[] files = new File(FinalVariable.MOVIE_PATH).listFiles();

        List<File> fileList = Arrays.asList(files);
        List<String> folderList = new ArrayList<String>();

        fileList.stream().filter(x -> x.isDirectory()).forEach(s -> {
            MovieTreeItemModel movieTreeItemModel = MovieTreeItemModel.builder().id(UUID.randomUUID().toString())
                    .text(s.getName()).tags(Arrays.asList(s.getAbsolutePath())).nodes(ListFile(s.getAbsolutePath()))
                    .build();

            Gson gson = new GsonBuilder().setPrettyPrinting().create();
            folderList.add(gson.toJson(movieTreeItemModel));
        });

        model.addAttribute("reponseTreeData", folderList);
        model.addAttribute("reponseTableData", "");

        return "movie/movie";
    }

    private List<MovieTreeItemModel> ListFile(String strDirPath) {
        System.out.println(strDirPath);

        List<MovieTreeItemModel> movieTreeItemModels = new ArrayList<MovieTreeItemModel>();

        File path = new File(strDirPath);

        File[] fList = path.listFiles();

        if (fList == null) {
            return null;
        }

        for (int i = 0; i < fList.length; i++) {
            if (fList[i].isDirectory()) {
                MovieTreeItemModel movieTreeItemModel = MovieTreeItemModel.builder().id(UUID.randomUUID().toString())
                        .text(fList[i].getName()).tags(Arrays.asList(fList[i].getAbsolutePath()))
                        .nodes(ListFile(fList[i].getPath())).build();

                movieTreeItemModels.add(movieTreeItemModel);
            }
        }

        if (movieTreeItemModels.size() == 0) {
            return null;
        } else {
            return movieTreeItemModels;
        }
    }

    @GetMapping("/movie/player")
    public String player(Model menuModel, Model model,
            @RequestParam(value = "absolutePath", required = true) String absolutePath)
            throws UnsupportedEncodingException {
        File file = new File(absolutePath);

        if (file.exists()) {
            model.addAttribute("absolutePath", absolutePath);
            model.addAttribute("fileName", file.getName());
        }

        return "movie/player";
    }

    @GetMapping("/movie/play")
    public void play(Model menuModel, @RequestParam(value = "absolutePath", required = true) String absolutePath,
            HttpServletResponse response, HttpServletRequest request) {
        File file = new File(absolutePath);

        if (!file.exists()) {
            return;
        }

        try {
            MultipartFileService.fromFile(file).with(request).with(response).serveResource();
        } catch (Exception e) {

        }
    }
}
