package com.dedu.flowablemodule.demo;

import lombok.extern.slf4j.Slf4j;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.DeploymentBuilder;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DemoService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private RepositoryService repositoryService;

    @Transactional
    public void startProcess(String process) {
        runtimeService.startProcessInstanceByKey(process);
    }

    @Transactional
    public List<Task> getTasks(String assignee) {
        return taskService.createTaskQuery().taskAssignee(assignee).list();
    }

    public Deployment deployFlow(String filePath) {
        try {
            DeploymentBuilder deploymentBuilder = repositoryService
                    .createDeployment()
                    .addClasspathResource(filePath);
            Deployment deployment = deploymentBuilder.deploy();
            log.info("成功：部署工作流成：" + filePath);
            return deployment;
        } catch (Exception e) {
            log.error("失败：部署工作流：" + e);
            return null;
        } // end catch
    }

}