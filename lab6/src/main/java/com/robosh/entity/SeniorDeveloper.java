package com.robosh.entity;

import com.robosh.decorator.DeveloperDecorator;
import com.robosh.observer.Observer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class SeniorDeveloper extends DeveloperDecorator implements Observer {

    public SeniorDeveloper(Developer developer) {
        super(developer);
    }

    public String resolveTasks(){
        return "Resolve hard tasks";
    }

    @Override
    public String doWork() {
        return super.doWork() + "; " + resolveTasks();
    }

    @Override
    public void mergeRequest(List<String> requests) {
        log.info("We have new merge requests : " + requests);
    }
}
