package com.piggie.controller.admin;

import com.piggie.constant.MessageConstant;
import com.piggie.result.Result;
import com.piggie.utils.AliOssUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

/**
 * ClassName: CommonController
 * Package: com.piggie.controller.admin
 * Description:
 *
 * @Author Piggie
 * @Create 8/02/2024 4:42 pm
 * @Version 1.0
 */
@RestController
@RequestMapping("/admin/common/")
@Slf4j
@Api(tags = "common interface")
public class CommonController {
    //  parameter name has to remain the same as what front end names it to, otherwise
    //  springboot will not be able to auto encapsulate it
    @Autowired
    private AliOssUtil aliOssUtil;

    @ApiOperation("file upload")
    @PostMapping("/upload")
    public Result<String> upload(MultipartFile file) {
        log.info("file upload: {}", file);
        String originalName = file.getOriginalFilename();
        String extension = originalName.substring(originalName.lastIndexOf("."));
        String newName = UUID.randomUUID().toString().concat(extension);
        try {
            String url = aliOssUtil.upload(file.getBytes(), newName);
            return Result.success(url);
        } catch (Exception e) {
            log.error("file upload failed {}", e);
            e.printStackTrace();
        }
        return Result.error(MessageConstant.UPLOAD_FAILED);
    }
}
