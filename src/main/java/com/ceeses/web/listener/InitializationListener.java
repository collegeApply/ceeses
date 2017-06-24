package com.ceeses.web.listener;

import com.ceeses.service.InitializationService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

/**
 * 初始化监听器
 * Spring容器初始化完成后开始初始化业务数据
 *
 * @author deng.zhang
 * @since 1.0 created on 2017/6/25
 */
@Component
public class InitializationListener implements ApplicationListener<ContextRefreshedEvent> {
    /**
     * logger
     */
    private static final Logger LOGGER = LoggerFactory.getLogger(InitializationListener.class);
    /**
     * 初始化服务
     */
    @Autowired
    private InitializationService initializationService;

    /**
     * 当Spring容器启动完成时会调用该方法
     *
     * @param contextRefreshedEvent Spring容器初始化完成事件
     */
    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //Root ApplicationContext容器初始化完成后开始调用初始化方法
        if (contextRefreshedEvent.getApplicationContext().getParent() == null) {
            new Thread() {
                @Override
                public void run() {
                    LOGGER.info("初始化业务数据开始");
                    initializationService.init();
                    LOGGER.info("初始化业务数据结束");
                }
            }.start();
        }
    }
}
