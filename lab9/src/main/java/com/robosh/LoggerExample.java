package com.robosh;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.Marker;
import org.apache.logging.log4j.MarkerManager;

public class LoggerExample {

  private static final Logger LOGGER = LogManager
      .getLogger(LoggerExample.class.getName());
  private static final Marker ADMIN_USER = MarkerManager.getMarker("ADMIN");
  private static final Marker GENERAL_USER = MarkerManager.getMarker("USER");
  private static final Random random = new Random();
  public static void main(String[] args) {
    for (; ; ) {
      getLogMessage(random.nextInt(4));
    }
  }

  public static void getLogMessage(int number) {
    switch (number) {
      case 0:
        LOGGER.debug(getMarker(random.nextInt(2)),"Debug message");
        break;
      case 1:
        LOGGER.info(getMarker(random.nextInt(2)), "Info message");
        break;
      case 2:
        LOGGER.error(getMarker(random.nextInt(2)), "Error message");
        break;
      case 3:
        LOGGER.warn(getMarker(random.nextInt(2)), "Warn message");
        break;
      default:
        LOGGER.fatal("Fatal message");
        break;
    }
  }

  public static Marker getMarker(int number){
    return number == 0 ? ADMIN_USER : GENERAL_USER;
  }
}
