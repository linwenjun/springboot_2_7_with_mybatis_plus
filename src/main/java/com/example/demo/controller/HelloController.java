package com.example.demo.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.conditions.query.LambdaQueryChainWrapper;
import com.example.demo.repository.User;
import com.example.demo.repository.mapper.RoleMapperService;
import com.example.demo.repository.mapper.RoleRepository;
import com.example.demo.repository.mapper.UserMapper;
import com.example.demo.repository.mapper.UserRepository;
import com.example.demo.stateProcessor.ProcessorFactoryAutoWireService;
import com.example.demo.stateProcessor.StateProcessor;
import lombok.extern.slf4j.Slf4j;
import org.mybatis.logging.Logger;
import org.mybatis.logging.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.LockSupport;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.Thread.sleep;

@RestController
@RequestMapping("/users")
@Slf4j
public class HelloController {

    @Autowired
    private UserMapper userMapper;

    @Autowired
    ProcessorFactoryAutoWireService processorFactoryAutoWireService;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("animals/{name}")
    public void wolf(@PathVariable String name) {
        StateProcessor stateProcessor = processorFactoryAutoWireService.getStateProcessor(name);
        stateProcessor.doAction();
    }

    @GetMapping
    public List<User> users() {
        LambdaQueryWrapper<User> eq = Wrappers.<User>lambdaQuery().likeRight(User::getId, "1");
        return userMapper.selectList(eq);
//        return userRepository.lambdaQuery().likeRight(User::getId, "1").list();
    }

    @GetMapping("/{id}")
    public User hello(@PathVariable String id) {
        User byId = userMapper.findById(id);

        log.trace(byId.toString());
        return byId;
    }

    @GetMapping("/parallel")
    public List<String> helloParams() {
        List<String> debugList = new ArrayList<>();

        List<Map<String, Integer>> values = List.of(
                Map.of("a", 1),
                Map.of("a", 2),
                Map.of("a", 3)
        );

        List<Map<String, Integer>> result = values.stream().parallel().peek(e -> {
            debugList.add(e.toString());
            log.trace(e.toString());
            LockSupport.parkNanos(TimeUnit.SECONDS.toNanos(1));
        }).collect(Collectors.toList());

        return debugList;
    }

    @GetMapping("/optional")
    public List<String> helloOptional() {
        return List.of("a");
    }

    @PostMapping
    public String createUser(@RequestBody User user) {
        userMapper.insert(user);
        log.trace(user.getId());
        user.getRoles().stream().forEach(it-> it.setUserId(user.getId()));
        roleRepository.saveBatch(user.getRoles());
        return user.getId();
    }

    @PostMapping
    @RequestMapping("/bulkInsert")
    public String createUserBulk(@RequestBody User user) {
        for(int i = 0; i<100; i++) {
            String tempId = String.valueOf(26+i);
            user.setId(tempId);
            userMapper.insert(user);
        }

        return "100";
    }
}
