package com.example.doanthuctap.helper;

import java.text.DecimalFormat;
import java.util.Random;

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
}
