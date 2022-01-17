package com.mudongheng.app.model.vo;

import com.mudongheng.app.model.FileInfo;
import lombok.Data;

import java.util.List;

/**
 * @author 幕冬
 * @since 2022年01月10日
 */
@Data
public class FileListResVO {

    /**
     * 当前路径
     */
    private FileInfo curFileInfo;

    /**
     * 当前文件夹下所有文件集合
     */
    private List<FileInfo> fileInfoList;

    /**
     * 该文件夹的父路径集合
     */
    private List<FileInfo> fileInfoPathList;

}