package com.effective.java.ch3.item13;

public class Temp {

  public static void main(String[] args) throws CloneNotSupportedException {
    App a1 = new App(1, 2);

    App a2 = (App) a1.clone();

    System.out.println(a1 != a2);
    System.out.println(a1.getClass() == a2.getClass());
    System.out.println(a1.equals(a2));
  }

}
