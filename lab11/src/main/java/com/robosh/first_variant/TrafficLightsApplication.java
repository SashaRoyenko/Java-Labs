package com.robosh.first_variant;

public class TrafficLightsApplication {

  public static void main(String[] args) {
    new Thread(new TrafficLight(Light.GREEN)).start();
    new Thread(new TrafficLight(Light.RED)).start();
  }
}
