package com.dedu.flowablemodule.demo.standalone;

import org.flowable.engine.*;
import org.flowable.engine.impl.cfg.StandaloneProcessEngineConfiguration;
import org.flowable.engine.repository.Deployment;
import org.flowable.engine.repository.ProcessDefinition;
import org.flowable.task.api.Task;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 创建一个独立运行的Flowable引擎
 */
public class HolidayRequestMain {
    public static void main(String[] args) {
        ProcessEngineConfiguration cfg = new StandaloneProcessEngineConfiguration()
                .setJdbcUrl("jdbc:h2:mem:flowable;DB_CLOSE_DELAY=-1")
                .setJdbcUsername("sa")
                .setJdbcPassword("")
                .setJdbcDriver("org.h2.Driver")
                .setDatabaseSchemaUpdate(ProcessEngineConfiguration.DB_SCHEMA_UPDATE_TRUE);

        ProcessEngine processEngine = cfg.buildProcessEngine();

        RepositoryService repositoryService = processEngine.getRepositoryService();
        Deployment deploy = repositoryService.createDeployment().addClasspathResource("processes/one-task-process.bpmn20.xml").deploy();
        ProcessDefinition processDefinition = repositoryService.createProcessDefinitionQuery().deploymentId(deploy.getId()).singleResult();
        System.out.println("流程定义: " + processDefinition.getName());

        String employee = "dedu";
        Integer holidayNumber = 5;
        String reason = "tensorflow";

        //根据process id 创建实例
        String processId = "holidayRequest";
        RuntimeService runtimeService = processEngine.getRuntimeService();
        // 开启实例并传入参数
        Map<String, Object> conditions = new HashMap<>();
        conditions.put("employee", employee);
        conditions.put("holidayNumber", holidayNumber);
        conditions.put("reason", reason);
        runtimeService.startProcessInstanceByKey(processId, conditions);

        // 查询任务
        TaskService taskService = processEngine.getTaskService();
        List<Task> taskList = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("你有"+ taskList.size() + "个任务！");
        Map<String, Object> variables = taskService.getVariables(taskList.get(0).getId());
        System.out.println(variables);

        // 完成任务
        variables = new HashMap<>();
        variables.put("approved", true);
        taskService.complete(taskList.get(0).getId(), variables);

        //查询 任务数量
        List<Task> endTaskList = taskService.createTaskQuery().taskCandidateGroup("managers").list();
        System.out.println("你有"+ endTaskList.size() + "个任务！");
    }
}
