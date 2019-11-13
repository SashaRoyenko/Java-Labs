package com.robosh.entities;

import static org.junit.jupiter.api.Assertions.assertEquals;

import com.robosh.annotations.TestAnnotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;



@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class SmartphoneTest {

  private Smartphone smartphone;

  @BeforeAll
  private void init() {
    smartphone = getSmartphone();
  }

  @Test
  public void shouldReturnClassName() {
    assertEquals("Smartphone", smartphone.getClass().getSimpleName());
  }

  @Test
  public void shouldReturnSuperclassName() {
    assertEquals("Phone", smartphone.getClass().getSuperclass().getSimpleName());
  }

  @Test
  public void shouldReturnMethodsParameters() {
    Arrays.stream(smartphone.getClass().getMethods())
        .map(method ->
            method.getReturnType().getName() + " " + method.getName() + " " + Arrays
                .toString(method.getParameterTypes()))
        .forEach(System.out::println);
  }

  @Test
  @SneakyThrows
  public void shouldIncreaseMemoryTwice() {
    for (Method method : smartphone.getClass().getMethods()) {
      if (method.isAnnotationPresent(TestAnnotation.class)) {
        method.invoke(smartphone);
      }
    }
    assertEquals(128, smartphone.getMemory());

  }


  private Smartphone getSmartphone() {
    return Smartphone.builder()
        .name("Xiaomi")
        .memory(64)
        .camera(true)
        .cores(8)
        .diagonal(15.6f)
        .frequency(2)
        .ram(4)
        .weight(160)
        .build();
  }

}