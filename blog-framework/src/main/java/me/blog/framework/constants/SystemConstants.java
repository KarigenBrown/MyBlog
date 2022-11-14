package me.blog.framework.constants;

/**
 * @author Karigen B
 * @create 2022-10-29 21:21
 */
public class SystemConstants {
    /**
     * 文章是草稿
     */
    public static final int ARTICLE_STATUS_DRAFT = 1;
    /**
     * 文章是正常分布状态
     */
    public static final int ARTICLE_STATUS_NORMAL = 0;

    public static final String CATEGORY_STATUS_NORMAL = "0";

    /**
     * 链接审核通过
     */
    public static final String LINK_STATUS_NORMAL = "0";

    public static final Long ROOT_COMMENT = -1L;

    public static final String ARTICLE_COMMENT = "0";
    public static final String LINK_COMMENT = "1";


    public static final String USER_STATUS_KEY_PREFIX = "loginUser:";

    public static final String VIEW_COUNT_KEY = "article:viewCount";
}
