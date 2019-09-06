package com.tec.anji.www.web.async;

import lombok.Data;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.async.DeferredResult;

import java.util.HashMap;
import java.util.Map;

@Component
@Data
public class DeferredResultHolder {

    private Map<String, DeferredResult<String>> holder = new HashMap<>();
}
