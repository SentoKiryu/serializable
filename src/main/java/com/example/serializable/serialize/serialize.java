package com.example.serializable.serialize;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class serialize {

  public String toJson(Object obj)  {
    try {
      return toJsons(obj);
    } catch (IOException e) {
      throw new IllegalArgumentException(e.getMessage());
    }
  }

  private String toJsons(Object obj) throws IOException {
    if(obj instanceof List){
      @SuppressWarnings("unchecked")
      List<Map<String, Object>> objlist = (List<Map<String, Object>>)obj;
      return toJsonList(objlist);
    }else if(primitive(obj)){
      return toJsonPrimitive(obj);
    }else{
      throw new IllegalArgumentException();
    }
  }

  private String toJsonList(List<Map<String, Object>> list) throws IOException {
    clear();
    return toJsonLists(list);
  }

  private String toJsonLists(List<Map<String, Object>> list) throws IOException {
    sb.append("{");
    sb.append("\"" + "data" + "\"");
    sb.append(":");
    sb.append("[");
    append(list);
    sb.append("]");
    sb.append("}");
    return sb.toString();
  }

  private String toJsonPrimitive(Object obj) throws IOException {
    clear();
    sb.append("{");
    sb.append("\"");
    sb.append("data");
    sb.append("\"");
    sb.append(":");
    appendValue(obj.toString());
    sb.append("}");
    return sb.toString();
  }

  private void append(List<Map<String, Object>> list) throws IOException {
    for (int i = 0; i < list.size(); i++) {
      sb.append("{");
      Map<String, Object> map = list.get(i);
      Set<String> keys = map.keySet();
      for (Iterator<String> iterator = keys.iterator(); iterator.hasNext();) {
        String key = iterator.next();
        Object value = map.get(key);
        sb.append("\"");
        sb.append(key);
        sb.append("\"");
        sb.append(":");
        if (primitive(value)) {
          appendValue(value.toString());
        } else if(value instanceof byte[]){
          try {
            appendValue(new String((byte[])value, "GB2312"));
          } catch (UnsupportedEncodingException e) {
            throw new IllegalArgumentException();
          }
        }else if (value instanceof List) {
          sb.append("[");
          try {
            @SuppressWarnings("unchecked")
            List<Map<String, Object>> value2 = (List<Map<String, Object>>) value;
            append(value2);
          } catch (Exception e) {
            throw new IllegalArgumentException();
          }
          sb.append("]");
        } else {
          throw new IllegalArgumentException();
        }

        if (iterator.hasNext()) {
          sb.append(",");
        }
      }
      sb.append("}");
      if (i != list.size() - 1) {
        sb.append(",");
      }
    }
  }

  private void appendValue(Object value) throws IOException {
    sb.append("\"");
    sb.append((CharSequence) value);
    sb.append("\"");
  }

  private boolean primitive(Object value) {
    return value instanceof String || value instanceof Integer || value instanceof Double || value instanceof Float
        || value instanceof Long || value instanceof Boolean || value instanceof Date
        || value instanceof BigDecimal;
  }

  public static String json(Object obj) throws Exception {
    if(obj == null){
        throw new Exception();
    }
    if(obj.getClass().isArray() || obj instanceof List){
      return  toJSONArray(obj).toString();
    }else {
      return toJSONObject(obj).toString();
    }
  }

  private static JSONArray toJSONArray(Object array) {

    JSONArray jsonArray = new JSONArray();

    return jsonArray;
  }

  private static JSONObject toJSONObject(Object obj) throws IllegalAccessException, JSONException {
    StringBuilder string = new StringBuilder();
    string.append("{");
    JSONObject jsonObject = new JSONObject();
    Class<?> getclass = obj.getClass();
    List<Field> fields = new ArrayList<>();
    Collections.addAll(fields, getclass.getDeclaredFields());
    for(Field field : fields){
      String names = field.getName();
      Object values = field.get(obj);
      if(values != null){
        try {
          Class<?> nameClass = names.getClass();
          jsonObject.put(names, values);
          if (nameClass.isArray() || obj instanceof List) {
            JSONArray jsonArray = toJSONArray(field);
            jsonObject.put(names, jsonArray);
          } else {
            jsonObject.put(names, toJSONObject(field));
          }
        }catch (JSONException e){
          throw new IllegalArgumentException(e);
        }
      }else {
        jsonObject.put(names, JSONObject.NULL);
      }
    }
    return jsonObject;
  }

  protected Appendable sb;

  protected boolean safe = false;

  public serialize(){
    sb = new StringBuilder();
  }

  public serialize(boolean safe){
    this.safe=safe;
    sb = new StringBuffer();
  }

  private void clear() {
    if(safe){
      sb=new StringBuffer();
    }else{
      sb=new StringBuilder();
    }
  }

  public static void main(String [] args) throws Exception {

    Demo demo = new Demo();
    demo.setName("姓名");
    demo.setAge(123);
    List<Map<String, Object>> users = new ArrayList<Map<String, Object>>();
    Map<String, Object> map = new HashMap<String, Object>();
    map.put("Name", "name");
    map.put("age", "123");
    map.put("date", new Date());
    users.add(map);
//    System.out.println(toJson(users));
    String json = json(demo);
    System.out.println(json);
  }

}
