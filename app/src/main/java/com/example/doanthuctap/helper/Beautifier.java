package com.example.doanthuctap.helper;

import android.annotation.SuppressLint;
import android.content.Context;

import com.example.doanthuctap.R;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
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
        String output = input;

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
    public static String generateShippingDate(String input, Context context)
    {
        String output = "";

        /*fromDate*/
        Calendar fromDate = Calendar.getInstance();
        fromDate.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        /*toDate*/
        Calendar toDate = Calendar.getInstance();
        toDate.setTimeZone(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));

        String economical = context.getResources().getString(R.string.economical);// tiết kiệm - economical
        String standard = context.getResources().getString(R.string.standard);// tiêu chuẩn - standard
        String rapid = context.getResources().getString(R.string.rapid);// siêu tốc - rapid


        if( input.equals(economical ) )
        {
            fromDate.add(Calendar.DATE, 3);
            toDate.add(Calendar.DATE, 6);
            output = "Nhận hàng từ " + convertCalendarToString(fromDate) +
                    " đến " + convertCalendarToString(toDate);
        }
        else if(input.equals(standard))
        {
            fromDate.add(Calendar.DATE, 2);
            toDate.add(Calendar.DATE, 4);

            output = "Nhận hàng từ " + convertCalendarToString(fromDate) +
                    " đến " + convertCalendarToString(toDate);
        }
        else if(input.equals(rapid))
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


    /**
     * @author Phong-Kaster
     * convert server-side date to application date
     * for example 2022-05-12 -> 12-05-2022
     * */
    public static String convertStringToReadableDate(String input)
    {
        if( input.length() == 0)
        {
            Date date = new Date();
            @SuppressLint("SimpleDateFormat") SimpleDateFormat forrmatter =
                    new SimpleDateFormat("yyyy-MM-dd");
            return forrmatter.format(date);
        }
        String day = input.substring(8,10);
        String month = input.substring(5,7);
        String year = input.substring(0,4);

        return day + "-" + month + "-" + year;
    }

    /**
     * @author Phong-Kaster
     * @param input is the current status of the order
     * @return readable status depend on language vn/en
     */
    public static String convertStatusToReadableStatus(Context context, String input)
    {
        String output = "";

        String processing = context.getString(R.string.to_pay);
        String verified = context.getString(R.string.verified);
        String packed = context.getString(R.string.packed);
        String beingTransported = context.getString(R.string.being_transported);
        String delivered = context.getString(R.string.done);
        String cancel = context.getString(R.string.cancel);


        if( input.equals("processing") )
        {
            output = processing;
        }
        else if(input.equals("verified") )
        {
            output = verified;
        }
        else if( input.equals("packed"))
        {
            output = packed;
        }
        else if( input.equals("being transported") )
        {
            output = beingTransported;
        }
        else if( input.equals("delivered") )
        {
            output = delivered;
        }
        else if( input.equals("cancel") )
        {
            output = cancel;
        }
        else
        {
            output = context.getString(R.string.unknown);
        }
        return output;
    }

    /**
     * @return String the root url
     */
    public static String getRootURL()
    {
        return "http://192.168.1.2:8080/PTIT-Do-An-Thuc-Tap/assets/uploads/";
    }
}
