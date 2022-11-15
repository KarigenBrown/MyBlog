package me.myblog;

import cn.hutool.core.util.IdUtil;
import me.myblog.framework.utils.MinioUtils;
import me.myblog.frontend.MyBlogFrontendApplication;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Karigen B
 * @create 2022-11-09 21:01
 */
@SpringBootTest(classes = MyBlogFrontendApplication.class)
public class OssTest {
    @Autowired
    private MinioUtils minioUtils;

    @Test
    public void testOss() {
        // System.out.println("url = " + minioUtils.getUploadObjectUrl("myblog", "photo.jpg", 7 * 24 * 60 * 60));

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
        String fileName = dateFormat.format(new Date()) + "/" + IdUtil.fastSimpleUUID();
        System.out.println("fileName = " + fileName);
    }
}
