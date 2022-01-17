package com.mudongheng.app.model;

import lombok.Data;

/**
 * @author 幕冬
 * @since 2022年01月08日
 */
@Data
public class FileInfo {

    private String fileName;

    private String path;

    private boolean directory;

    private long length;

    private String lastUpdateTime;

}