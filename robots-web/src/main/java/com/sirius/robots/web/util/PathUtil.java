package com.sirius.robots.web.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.util.Arrays;
import java.util.List;

/**
 * 路径工具类
 *
 * @author 孟星魂
 * @version 5.0 createTime: 2019/7/29
 */
@Slf4j
public class PathUtil {

    public static final String XIE = "/";

    public static void main(String[] args) {
        String path = "/auth/aa/bb";
        String[] excludePatterns = {"/auth/**",""};
        String[] includePatterns = {};
        Boolean aBoolean = commProcess(path, Arrays.asList(excludePatterns), Arrays.asList(includePatterns));
        System.out.println(aBoolean);
    }


    public static Boolean commProcess(String path, List<String> excludePatterns, List<String> includePatterns){
        if(StringUtils.isEmpty(path)){
            return Boolean.FALSE;
        }
        if(!path.startsWith(XIE)){
            path = XIE + path;
        }

        AntPathMatcher antPathMatcher = new AntPathMatcher();
        if (!CollectionUtils.isEmpty(excludePatterns)) {
            for (String pattern : excludePatterns) {
                if (antPathMatcher.match(pattern, path)) {
                    return Boolean.FALSE;
                }
            }
        }
        if (ObjectUtils.isEmpty(includePatterns)) {
            return Boolean.TRUE;
        }
        else {
            for (String pattern : includePatterns) {
                if (antPathMatcher.match(pattern, path)) {
                    return Boolean.TRUE;
                }
            }
            return Boolean.FALSE;
        }
    }
}
