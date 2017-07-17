package Controller;

import java.io.IOException;
import java.util.HashMap;
import org.json.simple.JSONArray;
import org.json.simple.parser.ParseException;

public class JSON {

    public static void main(String[] args) throws ParseException, IOException {
        JSONArray array = new JSONArray();
        HashMap hashMap = new HashMap();

        hashMap.put("status", "OK");

        HashMap map = new HashMap();
        map.put("name", "Deepth");
        map.put("age", "20");

        hashMap.put("data", map);

        array.add(hashMap);

        String json = array.toJSONString();

        System.out.println(json);

    }
}
