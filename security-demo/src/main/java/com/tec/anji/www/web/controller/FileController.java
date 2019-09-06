package com.tec.anji.www.web.controller;

import com.tec.anji.www.model.FileInfo;
import org.apache.commons.io.IOUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Date;

@RestController
@RequestMapping("/file")
public class FileController {

    private Log log = LogFactory.getLog(getClass());

    private static final String FOLDER = "C:\\Users\\admin\\IdeaProjects\\spring-security\\security-demo\\src\\main\\java\\com\\tec\\anji\\www\\web\\controller";

    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        log.info(file.getName());
        log.info(file.getOriginalFilename());
        log.info(file.getSize() + "bytes");
        File localFile = new File(FOLDER, new Date().getTime() + ".txt");
        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }

    @GetMapping("/{filename}")
    public void download(@PathVariable String filename, HttpServletRequest request, HttpServletResponse response) {
        try (InputStream inputStream = new FileInputStream(new File(FOLDER, filename + ".txt"));
             OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/x-download");
            response.addHeader("Content-Disposition", "attachment;filename=test.txt");
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
