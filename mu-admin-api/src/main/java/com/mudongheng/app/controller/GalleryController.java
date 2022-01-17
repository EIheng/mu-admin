package com.mudongheng.app.controller;

import cn.dev33.satoken.annotation.SaCheckLogin;
import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.io.file.FileNameUtil;
import com.mudongheng.app.model.PictureInfo;
import com.mudongheng.app.util.MyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 幕冬
 * @since 2022年01月11日
 */
@Slf4j
@SaCheckLogin
@RestController
@RequestMapping("/api/gallery")
public class GalleryController {

    private final static String BASE_FILE_PATH = "/cache/mudongheng/画廊/";
    private final static File BASE_FILE = new File(BASE_FILE_PATH);

    private static File toBaseFile(String path) {
        return new File(BASE_FILE + path);
    }

    private static boolean isImage(String extName) {
        return extName.equals(ImgUtil.IMAGE_TYPE_GIF)
                || extName.equals(ImgUtil.IMAGE_TYPE_JPG)
                || extName.equals(ImgUtil.IMAGE_TYPE_JPEG)
                || extName.equals(ImgUtil.IMAGE_TYPE_PNG);
    }

    @PostMapping(value = "/get-image")
    public byte[] getImage(String path) {
        File file = toBaseFile(path);
        return MyUtil.getThumbnailImage(file);
    }

    @PostMapping("/list-picture-info")
    public List<PictureInfo> listPictureInfo() {
        ArrayList<PictureInfo> res = new ArrayList<>();
        File[] files = BASE_FILE.listFiles();
        if (files == null) {
            return res;
        }

        for (File file : files) {
            // 不为图片
            if (file.isDirectory() || !isImage(FileNameUtil.extName(file))) {
                continue;
            }
            PictureInfo pictureInfo = new PictureInfo();
            pictureInfo.setName(file.getName());
            pictureInfo.setPath(file.getPath().substring(BASE_FILE.getPath().length()));
            res.add(pictureInfo);
        }
        return res;
    }



}
