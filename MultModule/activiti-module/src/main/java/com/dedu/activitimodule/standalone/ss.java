//package com.dedu.activitimodule.standalone;
//
//public class ss {
//
//    public void getProcessResources() throws IOException {
//// 流程定义id
//        String processDefinitionId = "";
//// 获取repositoryService
//        RepositoryService repositoryService = processEngine
//                .getRepositoryService();
//// 流程定义对象
//        ProcessDefinition processDefinition = repositoryService
//                .createProcessDefinitionQuery()
//                .processDefinitionId(processDefinitionId).singleResult();
////获取bpmn
//        String resource_bpmn = processDefinition.getResourceName();
////获取png
//        String resource_png =
//                processDefinition.getDiagramResourceName();
//// 资源信息
//        System.out.println("bpmn：" + resource_bpmn);
//        System.out.println("png：" + resource_png);
//        File file_png = new File("d:/purchasingflow01.png");
//        File file_bpmn = new File("d:/purchasingflow01.bpmn");
//// 输出bpmn
//        InputStream resourceAsStream = null;
//        resourceAsStream = repositoryService.getResourceAsStream(
//                processDefinition.getDeploymentId(), resource_bpmn);
//        FileOutputStream fileOutputStream = new
//                FileOutputStream(file_bpmn);
//        byte[] b = new byte[1024];
//        int len = -1;
//        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
//            fileOutputStream.write(b, 0, len);
//        }
//// 输出图片
//        resourceAsStream = repositoryService.getResourceAsStream(
//                processDefinition.getDeploymentId(), resource_png);
//        fileOutputStream = new FileOutputStream(file_png);
//// byte[] b = new byte[1024];
//// int len = -1;
//        while ((len = resourceAsStream.read(b, 0, 1024)) != -1) {
//            fileOutputStream.write(b, 0, len);
//        }
//    }
//}
