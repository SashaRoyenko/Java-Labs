package com.robosh.first_variant;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

@AllArgsConstructor
@Data
public class TrafficLight implements Runnable{

  private Light light;


  public void switchLight() {
    switch (light) {
      case RED:
        light = Light.GREEN;
        break;
      default:
        light =  Light.RED;
        break;
    }
  }

  @SneakyThrows
  @Override
  public void run() {
    synchronized (this){
      while(true){
        System.out.println("Traffic light " + Thread.currentThread().getName() + " : " + getLight());
        wait((long) (Math.random() * 2000));
        switchLight();
        notifyAll();
      }
    }
  }
}
