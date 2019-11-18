package com.robosh;

import com.robosh.entity.Developer;
import com.robosh.entity.JuniorDeveloper;
import com.robosh.entity.MiddleDeveloper;
import com.robosh.entity.SeniorDeveloper;
import com.robosh.observer.Git;
import com.robosh.observer.Observer;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class Main {
    public static void main(String[] args) {
        Developer developer = new MiddleDeveloper(new JuniorDeveloper());
        log.info(developer.doWork());

        Git git = new Git();
        git.addRequest("ST-1 merge");
        git.addRequest("ST-2 merge");

        Observer mainAssignee = new SeniorDeveloper(new JuniorDeveloper());
        Observer additionalAssignee = new MiddleDeveloper(new JuniorDeveloper());

        git.addAssignee(mainAssignee);

        git.addRequest("St-3_BE merge");
        git.addAssignee(additionalAssignee);
        git.addRequest("St-1_FE merge");

    }
}
