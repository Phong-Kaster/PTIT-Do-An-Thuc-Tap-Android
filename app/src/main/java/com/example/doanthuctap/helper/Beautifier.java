package com.example.doanthuctap.helper;

import android.annotation.SuppressLint;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Random;
import java.util.TimeZone;

/**
 * @author Phong-Kaster
 * this class contains functions helps us improve user-experience about text, message,..
 */
public class Beautifier {
    /*
     * 123456 -> 123,456
     * */
    public static String formatNumber(int input)
    {
        DecimalFormat formatter = new DecimalFormat("###,###,###");
        return formatter.format(input);
    }

    public static String shortenName(String input){
        String output = "";

        if( input.length() > 20 ){
            output = input.substring(0, 19) + "...";
        }

        return output;
    }

    /**
     * generate a random number and return a image URL for product Avatar
     * this function serves temporary because this application use local Restful API
     * @return String imageUrl
     */
    public static String generateRandomAvatar(){
        String avatars[] = {
                "https://salt.tikicdn.com/cache/400x400/ts/product/17/75/b7/91374184a6a42ead753b14abe27d3d17.jpg",
                "https://salt.tikicdn.com/cache/400x400/ts/product/6f/d0/a7/f3a9e187810b7ece661439126e2cb397.jpg",
                "https://salt.tikicdn.com/cache/400x400/ts/product/a8/82/14/1a0508df2dd29af618497af903cd08cf.png",
                "https://salt.tikicdn.com/cache/400x400/ts/product/50/1e/e5/c8d668f0fb213ee4b30ce27b82d7fdcd.jpg",
                "https://salt.tikicdn.com/cache/400x400/ts/product/2d/18/0d/4fc5b41686e47c3d66a979aa7bb8c5a5.jpg",
                "https://salt.tikicdn.com/cache/400x400/ts/product/b2/d1/87/1c284ee4ff1ba9a09ef04be62d3baea1.png",
                "https://salt.tikicdn.com/cache/400x400/ts/product/92/c7/37/7eab63b8567d56515a44613534847081.png",
                "https://salt.tikicdn.com/cache/400x400/ts/product/58/2e/82/ac91a989f593c18202e3e4300f7c75b9.png",
                "https://salt.tikicdn.com/cache/400x400/ts/product/68/31/8a/b6e152910ca58654cee9545098d66437.png",
                "https://salt.tikicdn.com/cache/400x400/ts/product/63/5d/09/34f031ec5a98d2030f82d363f20fcdec.png"
        };

        Random rand = new Random();
        int avatarMax = avatars.length - 1;

        int avatarMin = 0;
        int position = rand.nextInt(avatarMax - avatarMin + 1) + avatarMin;

        String output = avatars[position];
        return output;
    }

    /**
     * @author Phong-Kaster
     * shorten order id
     * For example: 5b9ad00c-b622-4bc6-9640-d6b02157abac => 5b9ad00c-b622-4b
     */
    public static String shortenOrderId(String input)
    {
        if( input.length() < 16 )
        {
            return input;
        }
        String output = input.substring(0, 16);
        return output;
    }

    @SuppressLint("SimpleDateFormat")
    public static String generateShippingDate(int input)
    {
        String output = "";

        /*fromDate*/
        Calendar fromDate = Calendar.getInstance();
        fromDate.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        /*toDate*/
        Calendar toDate = Calendar.getInstance();
        toDate.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        int shippingEconomical = 2131231333;
        int shippingStandard = 2131231334;
        int shippingRapid = 2131231335;


        if( input == shippingEconomical )
        {
            fromDate.add(Calendar.DATE, 3);
            toDate.add(Calendar.DATE, 6);
            output = "Nhận hàng từ " + convertCalendarToString(fromDate) +
                    " đến " + convertCalendarToString(toDate);
        }
        else if( input == shippingStandard )
        {
            fromDate.add(Calendar.DATE, 2);
            toDate.add(Calendar.DATE, 4);

            output = "Nhận hàng từ " + convertCalendarToString(fromDate) +
                    " đến " + convertCalendarToString(toDate);
        }
        else if( input == shippingRapid )
        {
            output = "Nhận hàng trước 11h ngày mai";
        }


        return output;
    }

    /**
     * @author Phong-Kaster
     *
     * @param cal
     * @return String date
     * for example converCalendarToString( today ) => 28/07 in String.
     */
    public static String convertCalendarToString(Calendar cal) {
        return "" + cal.get(Calendar.DATE) + "/" +
                (cal.get(Calendar.MONTH) + 1);
    }
}
