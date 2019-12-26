package com.robosh.second_variant;

import com.robosh.first_variant.Light;
import java.util.concurrent.locks.ReentrantLock;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.SneakyThrows;

@AllArgsConstructor
@Data
public class TrafficLight implements Runnable {

  private ReentrantLock reentrantLock;
  private Light light;

  public void switchLight() {
    switch (light) {
      case RED:
        light = Light.GREEN;
        break;
      default:
        light = Light.RED;
        break;
    }
  }

  @SneakyThrows
  @Override
  public void run() {
    while (true) {
      reentrantLock.lock();
      switchLight();
      System.out.println(Thread.currentThread().getName()+ " just turned on: " + getLight());
      Thread.sleep((long) (Math.random() * 2000));
      switchLight();
      System.out.println(Thread.currentThread().getName() + " is turned on: " + getLight());
      reentrantLock.unlock();
      Thread.sleep((long) (Math.random() * 2000));
    }
  }

}