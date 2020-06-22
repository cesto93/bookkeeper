package org.apache.bookkeeper.client;

import org.junit.Test;

import java.util.Arrays;

import static org.junit.Assert.assertEquals;

public class WeightedRandomSelectionImplTest {

    @Test
    public void testSetMaxProbabilityMultiplier() {
        int max = 3;
        WeightedRandomSelectionImpl<Object> randomSelection = new WeightedRandomSelectionImpl<>();
        randomSelection.setMaxProbabilityMultiplier(max);
        assertEquals(max, randomSelection.maxProbabilityMultiplier);
    }

    @Test(expected=UnsupportedOperationException.class)
    public void testGetNextRandom() {
        WeightedRandomSelectionImpl<Object> randomSelection = new WeightedRandomSelectionImpl<>();
        randomSelection.getNextRandom(Arrays.asList(new int[]{2}));
    }
}
