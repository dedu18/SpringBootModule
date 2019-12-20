package com.dedu.activitimodule.standalone;

import org.activiti.engine.*;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.repository.ProcessDefinition;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;

import java.text.MessageFormat;
import java.util.List;

/**
 * 独立运行模式
 */
public class HolidayRequestMain {

    public static void main(String[] args) {
        // 获得内存存储的工作流引擎
        ProcessEngine processEngine = ProcessEngineConfiguration.createStandaloneInMemProcessEngineConfiguration().buildProcessEngine();

        // 部署流程
        delopyProcess(processEngine);

        // 查询流程
        queryProcess(processEngine);

        // 流程删除
//        deleteProcess(processEngine);

        // 开启流程实例
        startProcessInstance(processEngine);

        // 查询已开启的实例
        queryProcessInstance(processEngine);

        // 查询任务
        querySelfTask(processEngine);

        // 完成任务
        completeSelfTask(processEngine);

        // 完成后再次查询任务
        querySelfTask(processEngine);
    }


    public static void delopyProcess(ProcessEngine processEngine) {
        // ②获得repositoryService并进行部署流程
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String filePath = "processes/Helloworld.bpmn";
        Deployment deploy = repositoryService.createDeployment().addClasspathResource(filePath).name("Helloworld").deploy();
        printMessage("部署流程：{0} -> {1} -> {2}.",deploy.getId(),deploy.getKey(), deploy.getName());
    }

    public static void queryProcess(ProcessEngine processEngine) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String processKey = "HelloworldProcess";
        printMessage("查询流程：{0}", processKey);
        List<ProcessDefinition> processDefinitionList = repositoryService.createProcessDefinitionQuery().processDefinitionKey(processKey).list();
        processDefinitionList.stream().forEach(t-> System.out.println(t));
    }

    public static void deleteProcess(ProcessEngine processEngine) {
        RepositoryService repositoryService = processEngine.getRepositoryService();
        String deploymentId = "1";
        printMessage("删除流程：{0}", deploymentId);
        repositoryService.deleteDeployment(deploymentId);
    }

    public static void startProcessInstance(ProcessEngine processEngine) {
        RuntimeService runtimeService = processEngine.getRuntimeService();
        String processKey = "HelloworldProcess";
        String businessKey = "关联的业务Id";
        ProcessInstance helloworldProcess = runtimeService.startProcessInstanceByKey(processKey, businessKey);
        printMessage("开启流程实例：{0} -> {1}", helloworldProcess.getId(), helloworldProcess.getProcessDefinitionId());
    }

    private static void queryProcessInstance(ProcessEngine processEngine) {

        RuntimeService runtimeService = processEngine.getRuntimeService();
        String processDefinitionKey = "HelloworldProcess";
        List<ProcessInstance> processInstanceList = runtimeService.createProcessInstanceQuery().processDefinitionKey(processDefinitionKey).list();
        processInstanceList.stream().forEach(t -> printMessage("查询到流程：{0} -> {1}", t.getId(), t.getBusinessKey()));
    }

    public static void querySelfTask(ProcessEngine processEngine) {
        String assignee = "zhangsan";
        String processDefinitionKey = "HelloworldProcess";
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().processDefinitionKey(processDefinitionKey).taskAssignee(assignee).list();
        printMessage("查询到任务:");
        taskList.stream().forEach(t -> {
            printMessage(MessageFormat.format("任务Id：{0}，任务名称：{1}，任务负责人：{2}",t.getId(), t.getName(), t.getAssignee()));
        });

        List<Task> sysTaskList = taskService.createTaskQuery().processDefinitionKey("HelloworldProcess").taskAssignee("system").list();
        printMessage("查询到任务:");
        sysTaskList.stream().forEach(t -> {
            printMessage("任务Id：{0}，任务名称：{1}，任务负责人：{2}",t.getId(), t.getName(), t.getAssignee());
        });
    }

    public static void completeSelfTask(ProcessEngine processEngine) {
        String taskId = "8";
        printMessage("完成任务：{0}", taskId);
        TaskService taskService = processEngine.getTaskService();
        taskService.complete(taskId);
    }

    private static void printMessage(String message, Object... args) {
        System.out.println(MessageFormat.format(message, args));
    }
}
