package com.eptitsyn.webapp;

public class MainDeadlock {

  public static final int THREAD_SLEEP = 1000;

  public static void main(String[] args) {
    Object lock1 = new Object();
    Object lock2 = new Object();

    Thread thread1 = new Thread(new SomeThread(lock1, lock2));
    thread1.start();
    Thread thread2 = new Thread(new SomeThread(lock2, lock1));
    thread2.start();
  }

  public static class SomeThread implements Runnable {

    private final Object lock1;
    private final Object lock2;

    public SomeThread(Object lock1, Object lock2) {
      this.lock1 = lock1;
      this.lock2 = lock2;
    }

    @Override
    public void run() {
      System.out.println("Thread " + Thread.currentThread().getName() + " started");
      synchronized (lock1) {
        System.out.println("Thread " + Thread.currentThread().getName() + " locked object "  + lock1);
        try {
          Thread.sleep(THREAD_SLEEP);
        } catch (InterruptedException e) {
          throw new RuntimeException(e);
        }
        System.out.println("Thread " + Thread.currentThread().getName() + " trying to lock "  + lock2);
        synchronized (lock2) {
          System.out.println("Thread " + Thread.currentThread().getName() + " locked object "  + lock2);
          System.out.println("thread 1");
        }
      }
      System.out.println("Thread " + Thread.currentThread().getName() + " finished");
    }
  }
}
