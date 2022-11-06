package me.blog.framework.utils;

import ch.qos.logback.core.util.ContentTypeUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.http.ContentType;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/**
 * @author Karigen B
 * @create 2022-11-02 20:06
 */
public class WebUtils {

    private WebUtils() {
    }

    public static void renderString(HttpServletResponse response, String string) {
        response.setStatus(200);
        response.setContentType(ContentType.JSON.toString());
        response.setCharacterEncoding(CharsetUtil.UTF_8);
        try {
            response.getWriter().print(string);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void setDownLoadHeader(String filename, ServletContext context, HttpServletResponse response) throws UnsupportedEncodingException {
        String mimeType = context.getMimeType(filename);
        response.setHeader("content-type", mimeType);
        String urlFilename = URLEncoder.encode(filename, StandardCharsets.UTF_8);
        response.setHeader("Content-disposition", "attachment; filename=" + urlFilename);
    }
}
