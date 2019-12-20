package com.dedu.activitimodule;

import org.activiti.engine.ProcessEngines;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ActivitiModuleApplicationTests {


    @Autowired
    private ProcessEngines processEngines;

    @Test
    public void contextLoads() {
        System.out.println(null == processEngines);
    }

}
