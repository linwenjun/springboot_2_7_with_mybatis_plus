package com.example.demo.stateProcessor;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProcessorFactoryAutoWireService {
    private final BeanFactory beanFactory;

    @Autowired
    public ProcessorFactoryAutoWireService(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

    public boolean isServerActive(String name, int serverId) {
        StateProcessor processor = beanFactory.getBean(name, StateProcessor.class);
        return processor.isServerActive();
    }

    public StateProcessor getStateProcessor(String name) {
        return beanFactory.getBean(name, StateProcessor.class);
    }
}
