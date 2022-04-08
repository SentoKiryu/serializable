package com.example.serializable.serialize;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class serializedemo {

  public static void main(String[] args){
    List<Map<String, Object>> users = new ArrayList<>();
    Map<String, Object> map = new HashMap<>();
    map.put("Name", "name");
    map.put("age", 123);
    map.put("date", new Date());
    Map<String, Object> mapinfo = new HashMap<>();
    mapinfo.put("OK",true);
    users.add(mapinfo);
    users.add(map);
    List<Map<String, Object>> messagelist = new ArrayList<>();
    Map<String, Object> messagemap = new HashMap<>();
    messagemap.put("float", 1L);
    messagelist.add(messagemap);
    map.put("message",messagelist);
    serialize serialize = new serialize();
    System.out.println(serialize.toJson(users));
  }
}
