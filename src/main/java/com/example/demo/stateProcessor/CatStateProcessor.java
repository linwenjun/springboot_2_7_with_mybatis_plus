package com.example.demo.stateProcessor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component("cat-processor")
@Slf4j
public class CatStateProcessor implements StateProcessor{
    @Override
    public void doAction() {
        log.trace("this is a cat");
    }

    @Override
    public boolean isServerActive() {
        return true;
    }
}
