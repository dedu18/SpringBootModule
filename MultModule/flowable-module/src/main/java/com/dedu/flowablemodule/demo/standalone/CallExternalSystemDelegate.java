package com.dedu.flowablemodule.demo.standalone;

import org.flowable.engine.delegate.DelegateExecution;
import org.flowable.engine.delegate.JavaDelegate;

public class CallExternalSystemDelegate implements JavaDelegate {

    @Override
    public void execute(DelegateExecution delegateExecution) {
        System.out.println("任务完成后回调：");
        System.out.println(delegateExecution.getVariables());
    }
}
