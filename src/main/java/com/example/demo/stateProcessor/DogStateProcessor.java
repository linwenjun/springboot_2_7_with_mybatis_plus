package com.example.demo.stateProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("dog-processor")
@Slf4j
public class DogStateProcessor implements StateProcessor{
    @Override
    public void doAction() {
        log.trace("this is a dog");
    }

    @Override
    public boolean isServerActive() {
        return true;
    }
}
