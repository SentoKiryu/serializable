package com.example.serializable.serialize;

import java.util.Date;
import java.util.List;

public class Demo {

  private String name;
  private int age;
  private Date date;

  private List<Demolist> demolistList;

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public int getAge() {
    return age;
  }

  public void setAge(int age) {
    this.age = age;
  }

  public Date getDate() {
    return date;
  }

  public void setDate(Date date) {
    this.date = date;
  }

  public List<Demolist> getDemolistList() {
    return demolistList;
  }

  public void setDemolistList(List<Demolist> demolistList) {
    this.demolistList = demolistList;
  }
}
