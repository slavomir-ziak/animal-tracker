package com.wecode.animaltracker.util;

import android.location.Location;
import android.support.test.runner.AndroidJUnit4;

import junit.framework.TestCase;

import org.junit.*;
import org.junit.Assert;
import org.junit.runner.RunWith;

/**
 * Created by SZIAK on 8/28/2016.
 */

@RunWith(AndroidJUnit4.class)
public class LocationUtilTest extends TestCase {

    @Test
    public void testLocationFormatting() {
        //
        String converted = LocationUtil.formatLocation(0.0, 0.0);
        Assert.assertEquals("0°0'0.00\"S, 0°0'0.00\"W", converted);

        converted = LocationUtil.formatLocation(90.0, 180.0);
        Assert.assertEquals("90°0'0.00\"N, 180°0'0.00\"E", converted);

        converted = LocationUtil.formatLocation(-90.0, -180.0);
        Assert.assertEquals("90°0'0.00\"S, 180°0'0.00\"W", converted);

        converted = LocationUtil.formatLocation(45.63545, -175.638434);
        Assert.assertEquals("45°38'7.62\"N, 175°38'18.36\"W", converted);

        converted = LocationUtil.formatLocation(54.196108, 100.315324);
        Assert.assertEquals("54°11'45.99\"N, 100°18'55.17\"E", converted);


        converted = LocationUtil.formatLocation(48.194674, 17.293048);
        Assert.assertEquals("48°11'40.83\"N, 17°17'34.97\"E", converted);
    }

    @Test
    public void testLocationParsing() {


        double[] parsed = LocationUtil.parseLocation("0°0'0.00\"S, 0°0'0.00\"W");
        Assert.assertArrayEquals(new double[]{0.0, 0.0}, parsed, 0);

        parsed = LocationUtil.parseLocation("90°0'0.00\"N, 180°0'0.00\"E");
        Assert.assertArrayEquals(new double[]{90.0, 180.0}, parsed, 0);

        parsed = LocationUtil.parseLocation("90°0'0.00\"S, 180°0'0.00\"W");
        Assert.assertArrayEquals(new double[]{-90.0, -180.0}, parsed, 0);

        parsed = LocationUtil.parseLocation("45°38'7.62\"N, 175°38'18.36\"W");
        Assert.assertArrayEquals(new double[]{45.635449, -175.638433}, parsed, 0);

        parsed = LocationUtil.parseLocation("54°11'45.99\"N, 100°18'55.17\"E");
        Assert.assertArrayEquals(new double[]{54.196108,  100.315325}, parsed, 0);

        parsed = LocationUtil.parseLocation("48°11'40.83\"N, 17°17'34.98\"E");
        Assert.assertArrayEquals(new double[]{48.194674,   17.29305}, parsed, 0);

    }



}