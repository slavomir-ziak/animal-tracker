package com.wecode.animaltracker.util;

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
        String converted = LocationUtil.formatLocationToMinutesAndSeconds( 0.0, 0.0, 0.0);
        Assert.assertEquals("0°0'0.00\"S, 0°0'0.00\"W", converted);

        converted = LocationUtil.formatLocationToMinutesAndSeconds( 90.0, 180.0, 0.0);
        Assert.assertEquals("90°0'0.00\"N, 180°0'0.00\"E", converted);

        converted = LocationUtil.formatLocationToMinutesAndSeconds( -90.0, -180.0, 0.0);
        Assert.assertEquals("90°0'0.00\"S, 180°0'0.00\"W", converted);

        converted = LocationUtil.formatLocationToMinutesAndSeconds( 45.63545, -175.638434, 0.0);
        Assert.assertEquals("45°38'7.62\"N, 175°38'18.36\"W", converted);

        converted = LocationUtil.formatLocationToMinutesAndSeconds( 54.196108, 100.315324, 0.0);
        Assert.assertEquals("54°11'45.99\"N, 100°18'55.17\"E", converted);


        converted = LocationUtil.formatLocationToMinutesAndSeconds( 48.194674, 17.293048, 0.0);
        Assert.assertEquals("48°11'40.83\"N, 17°17'34.97\"E", converted);
    }

    @Test
    public void testLocationParsing() {


        double[] parsed = LocationUtil.parseLocationDMS("0°0'0.00\"S, 0°0'0.00\"W");
        Assert.assertArrayEquals(new double[]{0.0, 0.0}, parsed, 0);

        parsed = LocationUtil.parseLocationDMS("90°0'0.00\"N, 180°0'0.00\"E");
        Assert.assertArrayEquals(new double[]{90.0, 180.0}, parsed, 0);

        parsed = LocationUtil.parseLocationDMS("90°0'0.00\"S, 180°0'0.00\"W");
        Assert.assertArrayEquals(new double[]{-90.0, -180.0}, parsed, 0);

        parsed = LocationUtil.parseLocationDMS("45°38'7.62\"N, 175°38'18.36\"W");
        Assert.assertArrayEquals(new double[]{45.635449, -175.638433}, parsed, 0);

        parsed = LocationUtil.parseLocationDMS("54°11'45.99\"N, 100°18'55.17\"E");
        Assert.assertArrayEquals(new double[]{54.196108,  100.315325}, parsed, 0);

        parsed = LocationUtil.parseLocationDMS("48°11'40.83\"N, 17°17'34.98\"E");
        Assert.assertArrayEquals(new double[]{48.194674,   17.29305}, parsed, 0);

    }



}