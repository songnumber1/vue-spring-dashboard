package com.dashboard.back.auth.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;
import org.springframework.util.FileCopyUtils;

@Component
public class FileDownloadService {
    public void fileDown(HttpServletRequest request, HttpServletResponse response, String absolutePath)
            throws IOException {

        File file = new File(absolutePath);

        if (file.exists() && file.isFile()) {
            String browser = getBrowser(request);
            String disposition = getDisposition(file.getName(), browser);

            response.setContentType("application/octet-stream; charset=utf-8");
            response.setContentLength((int) file.length());

            response.setHeader("Content-Disposition", disposition);
            response.setHeader("Content-Transfer-Encoding", "binary");

            OutputStream out = response.getOutputStream();
            FileInputStream fis = null;
            fis = new FileInputStream(file);
            FileCopyUtils.copy(fis, out);

            if (fis != null)
                fis.close();

            out.flush();
            out.close();
        }
    }

    private String getBrowser(HttpServletRequest request) {
        String header = request.getHeader("User-Agent");

        if (header.indexOf("MSIE") > -1 || header.indexOf("Trident") > -1)
            return "MSIE";
        else if (header.indexOf("Chrome") > -1)
            return "Chrome";
        else if (header.indexOf("Opera") > -1)
            return "Opera";

        return "Firefox";
    }

    private String getDisposition(String filename, String browser) throws UnsupportedEncodingException {
        String dispositionPrefix = "attachment;filename=";
        String encodedFilename = null;

        if (browser.equals("MSIE")) {
            encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll("\\+", "%20");
        } else if (browser.equals("Firefox")) {
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Opera")) {
            encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
        } else if (browser.equals("Chrome")) {
            StringBuffer sb = new StringBuffer();

            for (int i = 0; i < filename.length(); i++) {
                char c = filename.charAt(i);
                if (c > '~') {
                    sb.append(URLEncoder.encode("" + c, "UTF-8"));
                } else {
                    sb.append(c);
                }
            }

            encodedFilename = sb.toString();
        }
        return dispositionPrefix + encodedFilename;
    }
}
