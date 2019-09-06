package com.tec.anji.www.web.async;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

@Component
public class QueueListener implements ApplicationListener<ContextRefreshedEvent> {

    private Log log = LogFactory.getLog(getClass());

    private MockQueue mockQueue;

    private DeferredResultHolder deferredResultHolder;

    @Autowired
    public QueueListener(MockQueue mockQueue, DeferredResultHolder deferredResultHolder) {
        this.mockQueue = mockQueue;
        this.deferredResultHolder = deferredResultHolder;
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        new Thread(() -> {
            while (true) {
                String orderNumber = mockQueue.getCompleteOrder();
                if (StringUtils.isNotBlank(orderNumber)) {
                    deferredResultHolder.getHolder().get(orderNumber).setResult("success");
                    log.info("订单结果返回: " + orderNumber);
                    mockQueue.setCompleteOrder(null);
                } else {
                    try {
                        Thread.sleep(100);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }
}
