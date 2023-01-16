package me.myblog.framework.service.impl;

import me.myblog.framework.config.MinioConfig;
import me.myblog.framework.enums.HttpCodeEnum;
import me.myblog.framework.exception.SystemException;
import me.myblog.framework.service.FileService;
import me.myblog.framework.utils.MinioUtils;
import me.myblog.framework.utils.PathUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * @author Karigen B
 * @create 2023-01-07 15:46
 */
@Service
public class FileServiceImpl implements FileService {
    @Autowired
    private MinioUtils minioUtils;

    @Autowired
    private MinioConfig minioConfig;

    @Override
    public String uploadPicture(MultipartFile picture) {
        // 判断文件类型或者文件大小
        // 获取原始文件名
        String originalFilename = picture.getOriginalFilename();
        // 对原始文件名进行判断
        if (originalFilename == null || !originalFilename.matches("^.+\\.(png|jpg|jpeg)$")) {
            throw new SystemException(HttpCodeEnum.FILE_TYPE_ERROR);
        }

        // SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        // 1.
        /*String[] split = Objects.requireNonNull(originalFilename).split("\\.");
        String extendName = split[split.length - 1];
        String fileName = dateFormat.format(new Date()) + "/" + IdUtil.fastSimpleUUID() + "." + extendName;*/

        // 2.
        /*int index = originalFilename.lastIndexOf(".");
        String fileName = dateFormat.format(new Date()) + "/" + IdUtil.fastSimpleUUID() + originalFilename.substring(index);*/

        // 3.
        /*StringBuilder stringBuilder = new StringBuilder();
        int index = originalFilename.lastIndexOf(".");
        String fileName = stringBuilder.append(dateFormat.format(new Date()))
                .append("/")
                .append(IdUtil.fastSimpleUUID())
                .append(originalFilename.lastIndexOf(index))
                .toString();*/

        // 4.
        String fileName = PathUtils.dateUuidPath(originalFilename);

        // 如果判断通过上传文件到OSS
        return minioUtils.uploadObject(
                minioConfig.getBucketName(),
                fileName,
                picture
        );
    }
}
