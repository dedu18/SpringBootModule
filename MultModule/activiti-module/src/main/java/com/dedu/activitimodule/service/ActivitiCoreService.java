package com.dedu.activitimodule.service;

import com.dedu.activitimodule.module.SysProcessDefinition;
import com.dedu.activitimodule.module.SysTask;

import java.io.InputStream;
import java.util.List;

public interface ActivitiCoreService {

    void delopyProcess();

    List<SysProcessDefinition> queryProcess(String processKey);

    InputStream queryProcessImage(String processDefinitionId);

    void deleteProcess(String deploymentId);

    void startProcessInstance(String processKey, String businessKey);

    List<SysProcessDefinition> queryProcessInstance(String processDefinitionKey);

    List<SysTask> querySelfTask(String processDefinitionKey, String assignee);

    void completeTaskByTaskId(String taskId);
}
