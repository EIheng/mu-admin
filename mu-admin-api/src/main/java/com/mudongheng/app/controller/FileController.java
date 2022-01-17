package com.mudongheng.app.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import com.mudongheng.app.model.FileInfo;
import com.mudongheng.app.model.vo.FileListResVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;

/**
 * @author 幕冬
 * @since 2021年10月26日
 */
@Slf4j
@SaCheckLogin
@RestController
@RequestMapping("/api/file")
public class FileController {

    private final static String BASE_FILE_PATH = "/cache/mudongheng/";
    private final static File BASE_FILE = new File(BASE_FILE_PATH);

    private static File toBaseFile(String path) {
        return new File(BASE_FILE + path);
    }

    private static FileInfo toFileInfo(File file) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getName());
        fileInfo.setPath(file.getPath().substring(BASE_FILE.getPath().length()));
        fileInfo.setDirectory(file.isDirectory());
        fileInfo.setLength(file.length());
        fileInfo.setLastUpdateTime(DateUtil.format(new Date(file.lastModified()), "yyyy/MM/dd HH:mm:ss"));
        return fileInfo;
    }

    @GetMapping("/hello")
    public String hello() throws Exception {
        throw new Exception("123123");
    }

    @PostMapping("/create-file")
    public boolean createFile(String path, String fileName, boolean directory) {
        File file = toBaseFile(path + "/" + fileName);
        log.info("创建文件：" + file);
        if (directory) {
            return file.mkdir();
        } else {
            try {
                return file.createNewFile();
            } catch (IOException e) {
                log.error("创建文件异常");
                e.printStackTrace();
            }
        }
        return false;
    }


    @PostMapping("/delete-file")
    public boolean deleteFile(String path) {
        File file = toBaseFile(path);
        log.info("删除文件：" + file);
        return file.delete();
    }

    @PostMapping("/get-directory-info")
    public FileListResVO getDirectoryInfo(String path) {
        File file = new File(BASE_FILE_PATH + path);
        log.info("读取：" + file);

        if (!file.isDirectory()) {
            return null;
        }

        FileListResVO res = new FileListResVO();
        res.setCurFileInfo(toFileInfo(file));

        // 父文件列表，遍历到BASE_FILE_PATH
        ArrayList<FileInfo> fileInfoPathList = new ArrayList<>();
        File pFile = file;
        while (!pFile.getPath().equals(BASE_FILE.getPath())) {
            fileInfoPathList.add(toFileInfo(pFile));
            pFile = pFile.getParentFile();
        }
        Collections.reverse(fileInfoPathList);
        res.setFileInfoPathList(fileInfoPathList);

        // 获取当前文件列表
        File[] files = file.listFiles();

        ArrayList<FileInfo> fileInfoList = null;
        if (files != null) {
            fileInfoList = new ArrayList<>();
            for (File f : files) {
                fileInfoList.add(toFileInfo(f));
            }
        }
        res.setFileInfoList(fileInfoList);

        return res;
    }

    @PostMapping("/upload")
    public boolean upload(@RequestParam("file") MultipartFile file, String path) {
        if (file.isEmpty()) {
            log.error("文件为空");
            return false;
        }


        String fileName = file.getOriginalFilename();
        File out = toBaseFile(path + "/" + fileName);

        log.info("上传文件" + out);
        try {
            file.transferTo(out.getAbsoluteFile());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @PostMapping("/download")
    public void download(HttpServletResponse response, String path) {
        File file = toBaseFile(path);
        log.info("请求下载：" + file);
        try (ServletOutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/force-download");
            response.addHeader("Content-Disposition", "attachment;fileName=" + file.getName());

            outputStream.write(FileUtil.readBytes(file));
            outputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            log.error("连接异常");
        }
    }
}
