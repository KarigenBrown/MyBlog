package me.myblog.framework.utils;

import org.springframework.beans.BeanUtils;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Karigen B
 * @create 2022-10-29 21:24
 */
public class BeanCopyUtils {

    private BeanCopyUtils() {
    }

    public static <V> V copyBean(Object source, Class<V> clazz) {
        V result = null;
        try {
            // 创建目标对象
            result = clazz.getConstructor().newInstance();
            // 实现属性拷贝
            BeanUtils.copyProperties(source, result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        // 返回结果
        return result;
    }

    public static <V> List<V> copyBeanList(Collection<?> sourceCollection, Class<V> clazz) {
        return sourceCollection.stream()
                .map(source -> copyBean(source, clazz))
                .collect(Collectors.toList());
    }
}
