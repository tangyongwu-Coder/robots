package com.sirius.robots.comm.constants;

/**
 * @author songsong_huang
 * @date on 2019/9/26 14:49
 * @description 网关认证 常量
 */
public interface HeaderConstants {
    /**token */
    String HEADER_USER_TOKEN = "token";

    /**用户ID */
    String HEADER_USER = "HEADER-USER";


    /**是否是管理员 */
    String HEADER_IS_MANAGER = "HEADER-IS-MANAGER";

    String HEADER_SYSTEM_TYPE = "HEADER-SYSTEM-TYPE";

    /** 前后端交互 请求api code */
    String HEADER_API_CODE = "apicode";


    /** 系统内部交互 http header 中包含的日志跟踪id */
    String HEADER_LOG_ID = "HEADER-LOG-ID";
}
