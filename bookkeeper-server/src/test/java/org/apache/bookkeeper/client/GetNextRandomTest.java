package org.apache.bookkeeper.client;

import org.apache.commons.collections4.Get;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.*;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class GetNextRandomTest {

    private Double randomMax;
    private TreeMap<Double, Object> map;
    private Object max;

    public GetNextRandomTest(Double randomMax, TreeMap<Double,Object> map) {
        this.randomMax = randomMax;
        this.map = map;
    }

    @Parameterized.Parameters
    public static Collection param() {
        TreeMap<Double, Object> map1 = new TreeMap<>();
        TreeMap<Double, Object> map2 = new TreeMap<>();
        TreeMap<Double, Object> map3 = new TreeMap<>();
        TreeMap<Double, Object> map4 = new TreeMap<>();

        map2.put(0.0, "1");

        map3.put(0.0, "1");
        map3.put(1.0, "2");

        map4.put(0.0, "1");
        map4.put(1.0 / 6.0, "2");
        map4.put(1.0 / 6.0 + 2.0 / 6.0, "3");

        return Arrays.asList(new Object[][] {
                {0.0, map1},
                {0.0, map2},
                {1.0, map3},
                {1.0 / 6.0 + 2.0 / 6.0, map4}
        });
    }

    @Test
    public void getNextRandom() {
        WeightedRandomSelectionImpl<Object> randomSelection = new WeightedRandomSelectionImpl<>();
        randomSelection.randomMax = randomMax;
        randomSelection.cummulativeMap = map;
        if (!map.values().isEmpty())
            assertTrue(map.values().contains(randomSelection.getNextRandom()));
        else
            try {
                randomSelection.getNextRandom();
                fail();
            } catch (Exception e) {

            }
    }
}
