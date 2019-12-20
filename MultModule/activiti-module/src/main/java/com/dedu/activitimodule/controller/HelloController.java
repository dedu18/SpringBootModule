package com.dedu.activitimodule.controller;

import com.alibaba.fastjson.JSON;
import com.dedu.activitimodule.service.ActivitiCoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;

@Controller
public class HelloController {

    @Autowired
    private ActivitiCoreService activitiCoreService;

    @GetMapping("/hello")
    @ResponseBody
    public String hello() {
        return "hello";
    }

    @GetMapping("/delopyProcess")
    @ResponseBody
    public String delopyProcess() {
        activitiCoreService.delopyProcess();
        return "部署成功！";
    }

    @GetMapping("/process/{key}")
    @ResponseBody
    public String queryProcess(@PathVariable(value = "key") String processKey) {
        return JSON.toJSONString(activitiCoreService.queryProcess(processKey));
    }

    @GetMapping(value = "/processImage/{key}", produces = MediaType.IMAGE_JPEG_VALUE)
    public void queryProcessImage(@PathVariable(value = "key") String processKey, HttpServletResponse response) throws IOException {
        OutputStream os = response.getOutputStream();
        ImageIO.write(ImageIO.read(activitiCoreService.queryProcessImage(processKey)), "png", os);
    }

    @GetMapping("/deleteProcess/{id}")
    @ResponseBody
    public String deleteProcess(@PathVariable(value = "id") String deploymentId) {
        activitiCoreService.deleteProcess(deploymentId);
        return "删除成功！";
    }

    @GetMapping("/startProcess")
    @ResponseBody
    public String startProcessInstance(@RequestParam String processKey, @RequestParam String businessKey) {
        activitiCoreService.startProcessInstance(processKey, businessKey);
        return "已开启流程实例！";
    }

    @GetMapping("/queryProcessInstance")
    @ResponseBody
    public String queryProcessInstance(@RequestParam String processInstanceKey) {
        return JSON.toJSONString(activitiCoreService.queryProcessInstance(processInstanceKey));
    }

    @GetMapping("/queryTaskByProcessAndAssignee")
    @ResponseBody
    public String queryTaskByProcessAndAssignee(@RequestParam String processDefinitionKey,@RequestParam String assignee) {
        return JSON.toJSONString(activitiCoreService.querySelfTask(processDefinitionKey, assignee));
    }

    @GetMapping("/completeTask")
    @ResponseBody
    public String completeTask(@RequestParam String taskId) {
        activitiCoreService.completeTaskByTaskId(taskId);
        return "完成任务！";
    }

}
