package com.tec.anji.www.web.async;

import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.concurrent.Callable;

@RestController
public class AsyncController {

    private Log log = LogFactory.getLog(getClass());

    private MockQueue mockQueue;

    private DeferredResultHolder deferredResultHolder;

    @Autowired
    public AsyncController(MockQueue mockQueue, DeferredResultHolder deferredResultHolder) {
        this.mockQueue = mockQueue;
        this.deferredResultHolder = deferredResultHolder;
    }

    @GetMapping("/sync")
    public String sync() throws InterruptedException {
        log.info("主线程开始");
        Thread.sleep(1000);
        log.info("主线程结束");
        return "success";
    }

    @GetMapping("/async")
    public Callable<String> async() {
        log.info("主线程开始");
        Callable<String> callable = () -> {
            log.info("副线程开始");
            Thread.sleep(1000);
            log.info("副线程结束");
            return "success";
        };
        log.info("主线程结束");
        return callable;
    }

    @GetMapping("/async2")
    public DeferredResult<String> async2() {
        log.info("主线程开始");
        DeferredResult<String> deferredResult = new DeferredResult<>();
        String orderNumber = RandomStringUtils.randomNumeric(8);
        mockQueue.setPlaceOrder(orderNumber);
        deferredResultHolder.getHolder().put(orderNumber, deferredResult);
        log.info("主线程结束");
        return deferredResult;
    }
}
