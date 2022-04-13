package com.eptitsyn.webapp;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainStreams {

  public static void main(String[] args) {
    System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
    System.out.println(minValue(new int[]{9,8}));
    System.out.println(oddOrEven(Arrays.asList(1, 2, 5, 6, 9)));
  }

  static int minValue(int[] values) {
    return Integer.parseInt(Arrays.stream(values).distinct().sorted()
        .collect(StringBuilder::new, StringBuilder::append, StringBuilder::append).toString());
  }

  static List<Integer> oddOrEven(List<Integer> integers) {
    AtomicInteger sum = new AtomicInteger();
    Map<Boolean, List<Integer>> result = integers.stream()
        .collect(Collectors.partitioningBy(x -> {
          sum.set((x + sum.get()) % 2);
          return x % 2 == 0;
        }));
    return result.get(((sum.get() % 2) != 0));
  }

}
