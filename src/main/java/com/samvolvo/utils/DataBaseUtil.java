package com.samvolvo.utils;

public class DataBaseUtil {

    public static boolean checkBool(int result){
        if(result == 1){return true;}
        else{return false;}
    }

    public static int setBool(boolean result){
        if(result){return 1;}
        else{return 0;}
    }

}
