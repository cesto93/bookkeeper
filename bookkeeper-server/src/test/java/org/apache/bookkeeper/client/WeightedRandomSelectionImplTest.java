package org.apache.bookkeeper.client;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.Mockito.*;

import java.util.*;

import org.apache.bookkeeper.client.WeightedRandomSelection.WeightedObject;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

@RunWith(Parameterized.class)
public class WeightedRandomSelectionImplTest {
    Map<Object, WeightedObject> map;
    Map<Object, Double> res;

    public WeightedRandomSelectionImplTest(Map<Object, WeightedObject> map, Map<Object, Double> res) {
        this.map = map;
        this.res = res;
    }

    private static WeightedObject getWeightedObject(long weight) {
        WeightedObject mock = mock(WeightedObject.class);
        when(mock.getWeight()).thenReturn(weight);
        return mock;
    }

    @Parameterized.Parameters
    public static Collection param() {
        Map<Object, WeightedObject> map1 = new HashMap<>();
        Map<Object, WeightedObject> map2 = new HashMap<>();
        Map<Object, WeightedObject> map3 = new HashMap<>();
        Map<Object, WeightedObject> map4 = new HashMap<>();
        Map<Object, Double> res1 = new HashMap<>();
        Map<Object, Double> res2 = new HashMap<>();
        Map<Object, Double> res3 = new HashMap<>();
        Map<Object, Double> res4 = new HashMap<>();

        map1.put("1", getWeightedObject(1));
        res1.put("1", 0.0);

        map2.put("1", getWeightedObject(0));
        map2.put("2", getWeightedObject(1));
        res2.put("1", 0.0);
        res2.put("2", 1.0);

        map3.put("1", getWeightedObject(2));
        map3.put("2", getWeightedObject(2));
        map3.put("3", getWeightedObject(2));
        res3.put("1", 0.0);
        res3.put("2", 0.3333333333333333);
        res3.put("3", 0.6666666666666666);

        map4.put("1", getWeightedObject(1));
        map4.put("2", getWeightedObject(2));
        map4.put("3", getWeightedObject(3));
        res4.put("1", 0.0 );
        res4.put("2", 1.0 / 6.0);
        res4.put("3", 1.0 / 6.0 + 2.0 / 6.0);

        return Arrays.asList(new Object[][] {
                {map1, res1},
                {map2, res2},
                {map3, res3},
                {map4, res4}
        });
    }

    @Test
    public void testUpdateMap() {
        WeightedRandomSelectionImpl<Object> randomSelection = new WeightedRandomSelectionImpl<>();
        randomSelection.updateMap(map);

        System.out.println(randomSelection.cummulativeMap.keySet());

        for (Object key : map.keySet()) {
            assertTrue(randomSelection.cummulativeMap.containsValue(key));
            assertEquals(key, randomSelection.cummulativeMap.get(res.get(key)));
        }
    }
}
