package com.sirius.robots.comm.util;

import java.util.UUID;
import org.apache.commons.lang.StringUtils;
import org.slf4j.MDC;
/**
 * Created by Administrator on 2018/10/31 0031.
 */
public class LogUtil {
    private static final String LOG_UUID = "LOG_UUID";

    public LogUtil() {
    }

    public static void updateLogId(String logID) {
        if(StringUtils.isBlank(logID)) {
            MDC.put(LOG_UUID, createLogId());
        } else {
            MDC.put(LOG_UUID, logID);
        }

    }

    public static String createLogId() {
        return UUID.randomUUID().toString();
    }

    public static String getLogId() {
        return MDC.get(LOG_UUID);
    }

    public static void main(String[] args) {
        updateLogId("111");
    }

}
