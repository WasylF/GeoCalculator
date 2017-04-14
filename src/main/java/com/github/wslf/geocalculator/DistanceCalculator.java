package com.github.wslf.geocalculator;

import com.github.wslf.datastructures.pair.Pair;
import static java.lang.Math.*;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Wsl_F
 */
public class DistanceCalculator {

    private static final double R = 6371000;

    public int getDistance(Address adr1, Address adr2) {
        double toRad = PI / 180;
        double t1 = sin(adr1.latitude * toRad) * sin(adr2.latitude * toRad);
        double delta = (adr1.longitude - adr2.longitude) * toRad;
        double t2 = cos(adr1.latitude * toRad) * cos(adr2.latitude * toRad) * cos(delta);
        double l = acos(t1 + t2);
        double d = R * l;
        return (int) d;
    }

    public ArrayList<Pair<Address, Integer>> getDistance(Address point, List<Address> list) {
        ArrayList<Pair<Address, Integer>> res = new ArrayList<>();
        for (Address item : list) {
            int d = getDistance(item, point);
            res.add(new Pair<>(item, d));
        }
        return res;
    }
}
