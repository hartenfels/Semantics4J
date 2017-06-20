package semantics;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import semantics.model.DescriptionLogic;


public class Util {
  public static JsonElement toTaggedArray(String tag, JsonElement... es) {
    JsonArray arr = new JsonArray();
    arr.add(tag);

    for (JsonElement e : es) {
      arr.add(e);
    }

    return arr;
  }


  public static JsonElement allToJson(DescriptionLogic... elems) {
    JsonArray arr = new JsonArray();

    for (DescriptionLogic e : elems) {
      arr.add(e.toJson());
    }

    return arr;
  }


  public static <T> String join(String sep, T[] objects) {
    String[] strings = new String[objects.length];

    for (int i = 0; i < objects.length; ++i) {
      strings[i] = objects[i].toString();
    }

    return String.join(sep, strings);
  }
}
