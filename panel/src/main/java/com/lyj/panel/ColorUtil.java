package com.lyj.panel;

import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.Random;

/**
 * Created by yu on 2017/9/27.
 *
 */

public class ColorUtil {


    public static List<String> getColorList(String[] colors, int colorSum){
        int stepCount = colorSum/(colors.length-1);//每个阶梯有多少个

        String startColor = colors[0];
        String endColor = colors[1];

        List<String> resultColorList = new ArrayList<>();
        for(int i=0;i<colorSum;i++){
            if(i%stepCount == 0){
                startColor = colors[i/stepCount];
                endColor = colors[i/stepCount +1];
            }

            int r = getRed(startColor) + (getRed(endColor) - getRed(startColor))*(i%stepCount)/stepCount;
            int g = getGreen(startColor) + (getGreen(endColor) - getGreen(startColor))*(i%stepCount)/stepCount;
            int b = getBlue(startColor) + (getBlue(endColor) - getBlue(startColor))*(i%stepCount)/stepCount;

            String rStr = Integer.toHexString(r).toUpperCase();
            String gStr = Integer.toHexString(g).toUpperCase();
            String bStr = Integer.toHexString(b).toUpperCase();

            rStr = rStr.length() == 1 ? "0" + rStr : rStr;
            gStr = gStr.length() == 1 ? "0" + gStr : gStr;
            bStr = bStr.length() == 1 ? "0" + bStr : bStr;

            String resultColor = "#" + rStr + gStr + bStr;
            resultColorList.add(resultColor);
        }

        return resultColorList;
    }

    public int getAlpha(String color) {
        if(color.charAt(0) != '#'){
            throw new IllegalArgumentException("Unknown color");
        }
        String alphaStr;
        if(color.length() == 7){
            alphaStr = "FF";
        }else if(color.length() == 9){
            alphaStr = color.substring(1,3);
        }else {
            throw new IllegalArgumentException("Unknown color");
        }
        return Integer.parseInt(alphaStr,16);
    }

    public static int getRed(String color) {
        if(color.charAt(0) != '#'){
            throw new IllegalArgumentException("Unknown color");
        }
        String redStr;
        if(color.length() == 7){
            redStr = color.substring(1,3);
        }else if(color.length() == 9){
            redStr = color.substring(3,5);
        }else {
            throw new IllegalArgumentException("Unknown color");
        }
        return Integer.parseInt(redStr,16);
    }

    public static int getGreen(String color) {
        if(color.charAt(0) != '#'){
            throw new IllegalArgumentException("Unknown color");
        }
        String greenStr;
        if(color.length() == 7){
            greenStr = color.substring(3,5);
        }else if(color.length() == 9){
            greenStr = color.substring(5,7);
        }else {
            throw new IllegalArgumentException("Unknown color");
        }
        return Integer.parseInt(greenStr,16);
    }

    public static int getBlue(String color) {
        if(color.charAt(0) != '#'){
            throw new IllegalArgumentException("Unknown color");
        }
        String blueStr;
        if(color.length() == 7){
            blueStr = color.substring(5,7);
        }else if(color.length() == 9){
            blueStr = color.substring(7,9);
        }else {
            throw new IllegalArgumentException("Unknown color");
        }
        return Integer.parseInt(blueStr,16);
    }


    /**
     * 获取十六进制的颜色代码.例如  "#6E36B4" , For HTML ,
     *
     * @return String
     */
    public static String getRandColorCode() {
        String r, g, b;
        Random random = new Random();
        r = Integer.toHexString(random.nextInt(256)).toUpperCase();
        g = Integer.toHexString(random.nextInt(256)).toUpperCase();
        b = Integer.toHexString(random.nextInt(256)).toUpperCase();

        r = r.length() == 1 ? "0" + r : r;
        g = g.length() == 1 ? "0" + g : g;
        b = b.length() == 1 ? "0" + b : b;

        return "#" + r + g + b;
    }

}
