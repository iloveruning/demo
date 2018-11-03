package com.cll.demo.example;

import lombok.Getter;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.context.WebApplicationContext;

/**
 * @author chenliangliang
 * @date 2018/11/3
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@WebAppConfiguration
@Getter
public class BaseMvcTest {

    @Autowired
    protected WebApplicationContext context;


}
