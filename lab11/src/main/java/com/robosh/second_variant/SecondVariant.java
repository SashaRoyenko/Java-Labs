package com.robosh.second_variant;

import com.robosh.first_variant.Light;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

public class SecondVariant {


  public static void main(String[] args) {
    ExecutorService executor = Executors.newFixedThreadPool(2);
    ReentrantLock reentrantLock = new ReentrantLock();

    executor.execute(new TrafficLight(reentrantLock, Light.GREEN));
    executor.execute(new TrafficLight(reentrantLock, Light.RED));
  }
}
