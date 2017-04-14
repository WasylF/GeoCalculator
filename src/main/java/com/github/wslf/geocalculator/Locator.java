package com.github.wslf.geocalculator;

import com.github.wslf.helpers.json.JsonReader;
import java.nio.*;
import java.nio.file.*;
import java.util.*;
import java.util.stream.*;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 *
 * @author Wsl_F
 */
public class Locator {

    private static final String API_PREFIX = "https://geocode-maps.yandex.ru/1.x/?format=json&geocode=";

    public ArrayList<Address> getLocation(String querryText) {
        JsonReader jsonReader = new JsonReader();
        try {
            JSONObject respond = jsonReader.read(API_PREFIX + querryText);
            JSONArray results = respond.getJSONObject("response").getJSONObject("GeoObjectCollection").getJSONArray("featureMember");
            ArrayList<Address> addresses = new ArrayList<>();
            for (Object r : results) {
                JSONObject result = (JSONObject) r;
                String pos = result.getJSONObject("GeoObject").getJSONObject("Point").getString("pos");
                addresses.add(new Address(querryText, pos));
            }
            return addresses;
        } catch (Exception ex) {
            return null;
        }

    }

    public static void main(String[] args) {
        Locator locator = new Locator();
        ArrayList<Address> metro = locator.findMetro("Киев");

        for (Address adr : metro) {
            System.out.println(adr.text + "\t" + adr.latitude + "\t" + adr.longitude);
        }

    }

    ArrayList<Address> findMetro(String city) {
        ArrayList<Address> metro = new ArrayList<>();

        String fileName = "metro.txt";
        List<String> stantions = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stantions = stream.collect(Collectors.toList());

        } catch (Exception e) {
            e.printStackTrace();
        }

        for (String stantion : stantions) {
            if (stantion.length() > 3) {
                ArrayList<Address> addresses = getLocation("город " + city + ", метро " + stantion);
                metro.addAll(addresses);
            }
        }

        return metro;
    }
}
