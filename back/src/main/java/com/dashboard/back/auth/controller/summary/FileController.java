package com.dashboard.back.auth.controller.summary;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.dashboard.back.auth.service.FileDownloadService;

@Controller
@RequestMapping("/file")
public class FileController {
    @Autowired
    private FileDownloadService fileDownloadService;

    @GetMapping(value = "fileDownload")
    public void fileDownload(@RequestParam("absolutePath") String absolutePath, HttpServletRequest req,
            HttpServletResponse res, ModelAndView mav) throws Throwable {
        try {
            fileDownloadService.fileDown(req, res, absolutePath);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
