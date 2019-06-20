package com.cj.test.httputil;

/**
 * 常量类 [一句话功能简述]
 * 
 * @author myuting
 * @version [版本号, 2018年3月30日]
 */
public class ExtranetConstant
{
    public static final String KEYWORD = "keyWord";
    public static final String USER = "user";
    public static final String GET = "GET";
    public static final String POST = "post";
    public static final String USERNAME = "userName";
    public static final String MOBILEPHONE = "mobilePhone";
    public static final String NOTE = "note";
    public static final String NAME = "name";
    public static final String IMAGE = "image";
    public static final String ALL = "all";
    public static final String RESOURCE = "resource";
    public static final String SERVICE = "service";
    public static final String APPLICATION = "application";
    public static final String NOTICE = "notice";
    public static final String PATCH = "patch";
    public static final String DELETE = "delete";
    public static final String FILE = "file";
    public static final String NULL = "null";

    public static final String RESULT_SCORE = "resultScore";
    public static final String AUDIT_STATUS = "auditStatus";
    public static final String LINK_MAN = "linkMan";
    public static final String RESOURCES_TYPE = "resourcesType";
    public static final String RESOURCE_SUBJECT = "resourceSubject";
    public static final String NATIVE_SHARE_DIRECTORY = "NativeShareDirectory";
    public static final String FILE_TYPE = "fileType";
    public static final String NEED_OTHER = "needOther";
    public static final String REGIST_DEPART_GUID = "registDepartGuid";

    public static final String IS_AUTOLOGIN = "isAutoLogin";
    public static final String IS_OPEN = "isOpen";
    public static final String IS_DEVELOPER = "isDeveloper";
    public static final String IS_BIND_EMAIL = "isBindEmail";
    public static final String IS_BIND_MOBILEPHONE = "isBindMobilePhone";

    public static final String HTTP = "http://";

    public static final String RESOURCESUBJECT = "资源主题";
    public static final String SERVEROBJECT = "服务对象";
    public static final String UPDATEFREQUENCY = "更新频率";

    public static final String NO_PERMISSION_TIPS = "dataopen query no permission ";

    public static final String ERROR_TIPS = "系统出错，请联系管理员！ ";

    /**
     * 评论类型
     */
    public static final Integer COMMENTTYPE_RESOURCE = 0;
    public static final Integer COMMENTTYPE_APPLICATION = 1;

    /****************************************************************** PageView ******************************************************************/

    /**
     * 访问量redis key
     */
    public static final String PV_KEY = "extranetpv";

    /**
     * 目录、api接口、栏目、信息
     */
    public static final String PV_CATALOG_ = "catalog_";
    public static final String PV_API_ = "api_";
    public static final String PV_CATEGORY_ = "category_";
    public static final String PV_INFORMATION_ = "information_";
    public static final String PV_APP_ = "app_";
    public static final String PV_EXTRANET_ALL = "extranet_all";
    public static final String PV_HOME_PAGE = "home_page";
    public static final String PV_EXTRANET_HOME_PAGE = "extranet_home_page";

    /**
     * 开放门户站总访问量
     */
    public static final String PV_ALL = "all";

    /**
     * 栏目 站内公告
     */
    public static final String PV_CATEGORY_ZNGG = "站内公告";

    /**
     * 栏目 帮助中心
     */
    public static final String PV_CATEGORY_BZZX = "帮助中心";

    /**
     * 缓存类型
     */
    public static final String CACHE_REFRESH_TYPE_CATALOG = "1";
    public static final String CACHE_REFRESH_TYPE_VISITCOUNT = "2";
    public static final String CACHE_REFRESH_TYPE_USECOUNT = "3";
    public static final String CACHE_REFRESH_TYPE_API = "4";
    public static final String CACHE_REFRESH_TYPE_SERVEROBJECT = "5";
    public static final String CACHE_REFRESH_TYPE_RESOURCESUBJECT = "6";

    /**
     * 目录数量 缓存key
     */
    public static final String EXTRANET_CATALOG_COUNT = "extranet_catalog_count";
    public static final String EXTRANET_VISIT_COUNT = "extranet_visit_count";
    public static final String EXTRANET_USE_COUNT = "extranet_use_count";
    public static final String EXTRANET_API_COUNT = "extranet_api_count";
    public static final String EXTRANET_SERVEROBJECT_COUNT = "extranet_serverobject_count";
    public static final String EXTRANET_RESOURCESUBJECT_COUNT = "extranet_resourcesubject_count";
}
