package com.tec.anji.www.web.async;

import lombok.Data;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

@Component
@Data
public class MockQueue {

    private Log log = LogFactory.getLog(getClass());

    private String placeOrder;

    private String completeOrder;

    void setPlaceOrder(String placeOrder) {
        this.placeOrder = placeOrder;
        new Thread(() -> {
            log.info("订单处理开始");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            MockQueue.this.completeOrder = MockQueue.this.placeOrder;
            log.info("订单处理完成");
        }).start();
    }
}
