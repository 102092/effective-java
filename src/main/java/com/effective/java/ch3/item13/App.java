package com.effective.java.ch3.item13;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class App implements Cloneable {

  private int a;
  private int b;


  public App(int a, int b) {
    this.a = a;
    this.b = b;
  }

  @Override
  public App clone() throws CloneNotSupportedException {
    return (App) super.clone();
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    App app = (App) o;

    if (a != app.a) {
      return false;
    }
    return b == app.b;
  }

  @Override
  public int hashCode() {
    int result = a;
    result = 31 * result + b;
    return result;
  }
}
