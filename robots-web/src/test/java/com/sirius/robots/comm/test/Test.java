package com.sirius.robots.comm.test;

import com.sirius.robots.manager.helps.RedisCacheHelper;
import com.sirius.robots.web.DemoApplication;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2018/4/12
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class Test {
    @Autowired
    private RedisCacheHelper redisCacheHelper;
    @org.junit.Test
   public void test(){
        redisCacheHelper.insertString("test","test",22222L);
        String test = redisCacheHelper.queryString("test");
        System.out.println(test);
    }
}