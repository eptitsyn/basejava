package com.eptitsyn.webapp;

import com.eptitsyn.webapp.model.Resume;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MainReflection {
    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Resume r = new Resume("John Doe");
        Method toStringMethod = r.getClass().getMethod("toString");
        System.out.println(toStringMethod.invoke(r));
    }
}
