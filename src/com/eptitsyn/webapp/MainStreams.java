package com.eptitsyn.webapp;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

public class MainStreams {

    public static void main(String[] args) {
        System.out.println(minValue(new int[]{1, 2, 3, 3, 2, 3}));
        System.out.println(minValue(new int[]{9, 8}));
        System.out.println(oddOrEven(Arrays.asList(1, 2)));
    }

    static int minValue(int[] values) {
        return Arrays.stream(values).distinct().sorted().reduce(0, (a, b) -> a * 10 + b);
    }

    static List<Integer> oddOrEven(List<Integer> integers) {
        Map<Boolean, List<Integer>> result = integers.stream()
            .collect(Collectors.partitioningBy(x -> x % 2 == 0));
        return result.get(result.get(false).size() % 2 != 0);
    }
}
