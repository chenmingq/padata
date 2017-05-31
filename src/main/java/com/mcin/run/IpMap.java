package com.mcin.run;

import java.util.Hashtable;
import java.util.Map;

/**
 * Created by Mcin on 2017/5/17.
 */
public class IpMap {
    public static Map<String ,Integer> getIp (Map<String ,Integer> map){
        Map<String ,Integer> ipMap = new Hashtable<String, Integer>();
        for (String key: map.keySet()) {
            System.out.println("ip==   "+key+":"+map.get(key));
        }
        return ipMap;
    }
}
