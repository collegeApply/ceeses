package com.ceeses;

import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 系统启动器
 * Created by deng.zhang on 2017/5/29.
 */
@MapperScan("com.ceeses.dao.mapper")
@SpringBootApplication
public class Application {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(Application.class);

    public static void main(String[] args) throws Exception {
        LOGGER.info("CEESES starting......");
        SpringApplication.run(Application.class, args);
        LOGGER.info("CEESES started!");
    }
}
