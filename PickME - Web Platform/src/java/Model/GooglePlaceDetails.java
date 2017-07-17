package Model;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class GooglePlaceDetails {

    public static String findCity(String parameter) {
        String html = "NO_RES";
        try {
            String URL = "https://maps.googleapis.com/maps/api/place/autocomplete/json?input=" + parameter + "&components=country:lk&types=(cities)&key=AIzaSyCI0H9IN9W9P2JfSw2xH22JPdX34wi2TVA";
            Object response = gotoURL(URL);
            JSONObject object = (JSONObject) new JSONParser().parse(response.toString());
            if (object.get("status").toString().equalsIgnoreCase("OK")) {
                html = "";
                JSONArray predictions = (JSONArray) object.get("predictions");
                for (Object jsono : predictions) {
                    JSONObject city = (JSONObject) jsono;
                    html += "<a class='list-group-item btn-city-result' onclick='setTempCity(\"" + city.get("place_id") + "\",\"" + city.get("description") + "\")'>" + city.get("description") + "</a>";
                }
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            System.gc();
        }
        System.out.println("[ JSON OUT ]" + html);
        return html;
    }

    public static void main(String[] args) {
        String json = findCity("Kul");
        System.out.println(json);
    }

    private static Object gotoURL(String URL) throws Exception {

        URL obj = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    private GooglePlaceDetails() {
    }

    private static final String USER_AGENT = "Mozilla/5.0";

    public static String getCityById(String placeId) throws Exception {
        String URL = "https://maps.googleapis.com/maps/api/place/details/json?placeid=" + placeId + "&key=AIzaSyCI0H9IN9W9P2JfSw2xH22JPdX34wi2TVA";

        URL obj = new URL(URL);
        HttpURLConnection con = (HttpURLConnection) obj.openConnection();

        // optional default is GET
        con.setRequestMethod("GET");

        //add request header
        con.setRequestProperty("User-Agent", USER_AGENT);

        int responseCode = con.getResponseCode();

        BufferedReader in = new BufferedReader(
                new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        String json = response.toString();

        JSONParser parse = new JSONParser();
        JSONObject object = (JSONObject) parse.parse(json);
        if (object.get("status").toString().equalsIgnoreCase("OK")) {
            JSONObject result = (JSONObject) object.get("result");
            JSONArray address = (JSONArray) result.get("address_components");
            JSONObject city = (JSONObject) address.get(0);
            return city.get("short_name").toString();
        } else {
            return "Not Found";
        }
    }
//
//    public static void main(String[] args) throws Exception {
//        System.out.println(getCityById("ChIJXW6A4brZ4joRl664yLSq6g0"));
//    }

}
