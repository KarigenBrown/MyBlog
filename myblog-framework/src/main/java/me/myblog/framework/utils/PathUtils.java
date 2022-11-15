package me.myblog.framework.utils;

import cn.hutool.core.util.IdUtil;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Karigen B
 * @create 2022-11-13 14:38
 */
public class PathUtils {
    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");

    public static String dateUuidPath(String originalFilename) {
        int index = originalFilename.lastIndexOf(".");
        String extendFileName = originalFilename.substring(index);
        return dateFormat.format(new Date()) + "/" + IdUtil.fastSimpleUUID() + extendFileName;
    }
}
