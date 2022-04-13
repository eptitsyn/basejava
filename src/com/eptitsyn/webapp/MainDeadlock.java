package com.eptitsyn.webapp;

public class MainDeadlock {

  public static final int THREAD_SLEEP = 1000;

  public static void main(String[] args) {
    Object lock1 = new Object();
    Object lock2 = new Object();

    Thread thread1 = new Thread(() -> {
      synchronized (lock1) {
        try {
          Thread.sleep(THREAD_SLEEP);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        synchronized (lock2) {
          System.out.println("thread 1");
        }
      }
    });
    thread1.start();
    Thread thread2 = new Thread(() -> {
      synchronized (lock2) {
        try {
          Thread.sleep(THREAD_SLEEP);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        synchronized (lock1) {
          System.out.println("thread 2");
        }
      }
    });
    thread2.start();
  }
}
