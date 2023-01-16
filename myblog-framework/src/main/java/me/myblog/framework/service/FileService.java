package me.myblog.framework.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author Karigen B
 * @create 2023-01-07 15:45
 */
public interface FileService {
    String uploadPicture(MultipartFile picture);
}
