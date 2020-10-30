package com.sirius.robots.comm.constants;


public interface ServiceConstants {
    String SYSTEM_NAME = "SYSTEM";

    String CHAR_SET = "UTF-8";


    String MSG_NULL_RES="暂时无法理解您说的。";

    String MSG_UNKNOWN="您输入的信息无法识别。";


    String NO_GROUP_OUT="当前未进入分组！";

    String REDIS_CHECK_SEND = "ROBOTS:RESET_CHECK_SEND_";

    String LOGIN_USER_REDIS_KEY = "ROBOTS:LOGIN:USER:";

    String LOGIN_USER_REFRESH_REDIS_KEY = "ROBOTS:LOGIN:USER:REFRESH";


    String SUCCESS = "SUCCESS";

}
