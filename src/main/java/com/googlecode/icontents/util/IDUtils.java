package com.googlecode.icontents.util;

public class IDUtils {

    public static synchronized long getAutoId(){
        return System.currentTimeMillis();
    }
}
