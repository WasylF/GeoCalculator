package com.github.wslf.geocalculator;

import com.github.wslf.datastructures.pair.Pair;
import com.github.wslf.helpers.file.Reader;
import com.github.wslf.helpers.file.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *
 * @author Wsl_F
 */
public class ClosestMetro {

    public void findClosest(String input, String output) {
        Locator locator = new Locator();
        ArrayList<String> result = new ArrayList();
        ArrayList<Address> addresses = readAddresses(input);
        System.out.println("good addresses: " + addresses.size());
        ArrayList<Address> metro = locator.findMetro("Киев");
        int i = 0;
        for (Address address : addresses) {
            if (i % 1000 == 0) {
                System.out.println("Processing item#" + i);
            }
            i++;
            ArrayList<Pair<Address, Integer>> closest = findClosest(address, metro, 3);
            int d1 = closest.get(0).getSecond();
            int d2 = closest.get(1).getSecond();
            int d3 = closest.get(2).getSecond();

            result.add(address.text + "," + d1 + "," + d2 + "," + d3);
        }
        Writer writer = new Writer();
        writer.write(result, output, "\n");
    }

    ArrayList<Pair<Address, Integer>> findClosest(Address address, List<Address> metro, int k) {
        DistanceCalculator dc = new DistanceCalculator();
        ArrayList<Pair<Address, Integer>> all = dc.getDistance(address, metro);
        Collections.sort(all, (p1, p2) -> Integer.compare(p1.getSecond(), p2.getSecond()));
        return new ArrayList<>(all.subList(0, Math.min(k, all.size())));
    }

    ArrayList<Address> readAddresses(String input) {
        Reader reader = new Reader();
        ArrayList<String> lines = reader.getLines(input);
        System.out.println("total addresses: " + lines.size());
        ArrayList<Address> addresses = new ArrayList<>();
        ArrayList<String> bad = new ArrayList<>();
        for (String line : lines) {
            String[] record = line.split(",");
            double lat = 0;
            double lng = 0;
            try {
                lat = Double.parseDouble(record[16]);
                lng = Double.parseDouble(record[17]);
                Address address = new Address(line, lat, lng);
                addresses.add(address);
            } catch (Exception ex) {
                bad.add(line);
            }
        }
        
        Writer writer = new Writer();
        writer.write(bad, "bad.csv", "\n");

        return addresses;
    }

    public static void main(String[] args) {
        ClosestMetro closestMetro = new ClosestMetro();
        closestMetro.findClosest("data.csv", "data+metro.csv");
    }
}
