package com.tec.anji.www.service.impl;

import com.tec.anji.www.service.HelloService;
import org.springframework.stereotype.Service;

@Service
public class HelloServiceImpl implements HelloService {

    @Override
    public String greeting(String name) {
        return "Hello " + name;
    }
}
