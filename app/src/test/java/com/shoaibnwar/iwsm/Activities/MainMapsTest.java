package com.shoaibnwar.iwsm.Activities;

import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Created by gold on 6/28/2018.
 */
public class MainMapsTest {
    @Test
    public void isNumberIs5() throws Exception {

        int number = 5;
        MainMaps mainMaps = new MainMaps();
        boolean isTrue = mainMaps.isNumberIs5(number);
        Assert.assertTrue(isTrue);
    }

}