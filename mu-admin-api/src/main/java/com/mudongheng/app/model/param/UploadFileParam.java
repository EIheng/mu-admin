package com.mudongheng.app.model.param;

import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author 幕冬
 * @since 2022年01月16日
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class UploadFileParam extends BaseFileParam {

    private MultipartFile file;

}
