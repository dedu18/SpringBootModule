package com.dedu.activitimodule.service.impl;

import com.dedu.activitimodule.module.SysProcessDefinition;
import com.dedu.activitimodule.module.SysTask;
import com.dedu.activitimodule.service.ActivitiCoreService;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.ProcessEngine;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

@Service
public class ActivitiCoreServiceImpl implements ActivitiCoreService {

    @Autowired
    private ProcessEngine processEngine;

    @Autowired
    private RepositoryService repositoryService;

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private ProcessRuntime processRuntime;

    @Autowired
    private TaskRuntime taskRuntime;

    /**
     * 部署流程
     * TODO 文件名和图片名写死的
     */
    @Override
    public void delopyProcess() {
        // ②获得repositoryService并进行部署流程
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String filePath = "processes/Helloworld.bpmn";
        String imagePath = "processes/Helloworld.png";
        Deployment deploy = repositoryService.createDeployment().addClasspathResource(filePath).addClasspathResource(imagePath).name("Helloworld").deploy();
        printMessage("部署流程：{0} -> {1} -> {2}.",deploy.getId(),deploy.getKey(), deploy.getName());
    }

    /**
     * 查询流程
     * @return
     */
    @Override
    public List<SysProcessDefinition> queryProcess(String processKey) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        printMessage("查询流程：{0}", processKey);
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).list();
        List<SysProcessDefinition> sysProcessDefinitionList = new ArrayList<>();
        processDefinitionList.stream().forEach(t-> {
            SysProcessDefinition sysProcessDefinition = new SysProcessDefinition();
            BeanUtils.copyProperties(t, sysProcessDefinition);
            sysProcessDefinitionList.add(sysProcessDefinition);
        });
        return sysProcessDefinitionList;
    }

    /**
     *
     * @param processKey ACT_RE_DEPLOYMENT表主键Id.
     * @return 图片输入流.
     */
    @Override
    public InputStream queryProcessImage(String processKey) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        List<String> deploymentResourceNames = repositoryService.getDeploymentResourceNames(processKey);
        InputStream resourceAsStream = null;
        if (!deploymentResourceNames.isEmpty()) {
            String imageResourceName =deploymentResourceNames.stream().filter(t -> t.indexOf(".png") >= 0).findFirst().get();
            resourceAsStream = repositoryService.getResourceAsStream(processKey, imageResourceName);
        }
        return resourceAsStream;
    }

    @Override
    public void deleteProcess(String deploymentId) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        repositoryService.deleteDeployment(deploymentId);
        printMessage("删除流程：{0}", deploymentId);
    }

    @Override
    public void startProcessInstance(String processKey, String businessKey) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        ProcessInstance helloworldProcess = runtimeService.startProcessInstanceByKey(processKey, businessKey);
        printMessage("开启流程实例：{0} -> {1}", helloworldProcess.getId(), helloworldProcess.getProcessDefinitionId());
    }

    @Override
    public List<SysProcessDefinition> queryProcessInstance(String processDefinitionKey) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).list();
        List<SysProcessDefinition> sysProcessDefinitionList = new ArrayList<>();
        processInstanceList.stream().forEach(t-> {
            SysProcessDefinition sysProcessDefinition = new SysProcessDefinition();
            BeanUtils.copyProperties(t, sysProcessDefinition);
            sysProcessDefinitionList.add(sysProcessDefinition);
        });
        return sysProcessDefinitionList;
    }

    @Override
    public List<SysTask> querySelfTask(String processDefinitionKey, String assignee) {
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).taskAssignee(assignee).list();
        printMessage("查询到任务:");
        List<SysTask> sysTaskList = new ArrayList<>();
        taskList.stream().forEach(t -> {
            printMessage(MessageFormat.format("任务Id：{0}，任务名称：{1}，任务负责人：{2}",t.getId(), t.getName(), t.getAssignee()));
            SysTask sysTask = new SysTask();
            BeanUtils.copyProperties(t, sysTask);
            sysTaskList.add(sysTask);
        });
        return sysTaskList;
    }

    @Override
    public void completeTaskByTaskId(String taskId) {
        printMessage("完成任务：{0}", taskId);
        TaskService taskService = processEngine.getTaskService();
        taskService.complete(taskId);
    }

    private void printMessage(String message, Object... args) {
        System.out.println(MessageFormat.format(message, args));
    }
}
