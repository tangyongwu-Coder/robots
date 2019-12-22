package com.sirius;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ImportResource;

/**
 * @author 孟星魂
 * @version 5.0 createTime: 2018/4/16
 */
@SpringBootApplication
@ImportResource(locations={"classpath:ApplicationContext.xml"})
@MapperScan(basePackages="com.sirius.dal.mapper")
public class DemoApplication{

    public static void main(String[] args)
    {
        SpringApplication.run(DemoApplication.class, args);
    }
}
