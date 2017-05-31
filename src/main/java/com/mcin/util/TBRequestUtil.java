package com.mcin.util;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Mcin on 2017/5/10.
 */
public class TBRequestUtil {

    /**
     * 淘宝登录所需参数
     * @param userName
     * @param password
     * @return
     */
    public static Map<String,String > tbFromData (String userName,String password){
        Map<String,String > map = new HashMap<String ,String >();
        map.put("TPL_username", userName);
        map.put("TPL_password", password);
        map.put("ncoSig", "");
        map.put("ncoSessionid", "");
        map.put("ncoToken", ""); // R如果有token的话 需要在登录的时候获取token
        map.put("slideCodeShow", "false");
        map.put("useMobile", "false");
        map.put("lang","zh_CN");
        map.put("loginsite", "0");
        map.put("newlogin", "0");
        map.put("TPL_redirect_url", "https://www.taobao.com/");
        map.put("from", "tbTop");
        map.put("fc", "default");
        map.put("style", "");
        map.put("css_style", "");
        map.put("keyLogin", "false");
        map.put("qrLogin", "true");
        map.put("newMini", "false");
        map.put("newMini2", "false");
        map.put("tid:", "");
        map.put("loginType", "3");
        map.put("minititle", "");
        map.put("minipara", "");
        map.put("pstrong", "");
        map.put("sign", "");
        map.put("need_sign", "");
        map.put("isIgnore", "");
        map.put("full_redirect", "");
        map.put("sub_jump", "");
        map.put("popid", "");
        map.put("callback", "");
        map.put("guf", "");
        map.put("not_duplite_str", "");
        map.put("need_user_id", "");
        map.put("poy", "");
        map.put("gvfdcname", "10");
        map.put("gvfdcre", "");
        map.put("from_encoding", "");
        map.put("sub", "false");
        map.put("TPL_password_2", "");//         * TPL_password_2:6d851b6a4a411efd6e8f5054703f91cf340413e4e1dde3afc58789ce85ba4164255163dfd15046b83d9ae597fea8eb88a3b92ec27f337365f58982739a5b39c34bf6a9532cdafa2d12ee1945a8b3bf0aeb80f2b4dfa50f4bdbb514309bbef90409ce93e6c673da0dec47c867493e22c0aef870cd78ba7f9f479a8740281e485f
        map.put("loginASR", "1");
        map.put("loginASRSuc", "1");
        map.put("allp", "");
        map.put("oslanguage", "zh-CN");
        map.put("sr", "1920*1080");
        map.put("osVer", "");
        map.put("naviVer", "chrome|58.0302996");
        map.put("osACN", "Mozilla");
        map.put("osAV", "5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36");
        map.put("osPF", "Win32");
        map.put("miserHardInfo", "");
        map.put("appkey", "");
        map.put("nickLoginLink", "");
        map.put("mobileLoginLink", "https://login.taobao.com/member/login.jhtml?spm=a21bo.50862.754894437.1.VelFzV&f=top&redirectURL=https://www.taobao.com/&useMobile=true");
        map.put("showAssistantLink", "false");
        map.put("um_token", "HV01PAAZ0be3fb96700ae8ad59132a6c008ed20f");
        map.put("ua", "");//090#qCQXO4XRX4nXITi0XXXXXQk63oUYjG9nzSXyOF9QGG11z5/5cYhGZtsUKZFhjUZsRb443ehrAEdPWgdtsPpAbQkP3ZFAZfxiXXfUZ8wl2TQXutXTXT34BHdvXQ5VcilKKaDz01NkirqgqQJFHXQX/vEgFglu6vTEN86joZyAvaByxp2gxmxiXXfUZ8wlHXQX/vVPFglu6v4EN86joZyAvaByxp2gx1TiXivpXXD/KeKTUQCWd5LCHwONL5BxIx4iXiw3XgysijXxPy/IoIykLwLXFt3BorBCSvBm5XQX/XYiivSTo5Xnz/EZKoupvCdmK/TyqTQXiP+0gzvRXvXgDakrvJdQIyXXu/kx2B8e3os6wsXY3G4iXXMvyOTaHTX2iTAXNwdcL55JXvX3lIzQXAOXi4KGP/12FbqA+XQXrErDcTUSXXLufTiRBZEm004iXXMvy6va8vX2iG+TNwdcL55JXvX3lIfnXAOXi4LUv/12FbqAkXQXinaik9BrxAXiXXtRcwM+fiCm6llgXvXnIZdsF07cXvQbK9DT+9VQnodtANuc+4D1IZR8L07ciBQbK9SC+9V/QxYiXXkRpGbZ0/pUZjXiXXF6liQvLg7fXvr+vCZMpa73kML4RUUjzZRDMfyJEJStM16EFfu5k5SN8cJkqE0jzZRDMfyJSH03S17TOfWyBtVNwpvgQ2fr6Y72EoitlTp/EsFwdtXDXtZS4xLlIfRfHMRLMjPswhStSEFwdtXDIn73TMi1F0qZKmR0EulZC0FME1mmPaZun8LNtc45OUqZKmR0EulnIcKNzVmMOm7rCNyywW1sdePotNbd9tKZOlpIHLrbFNpf/Bd3sLVSKi0rHZRrAzQtEDKM/1rw/kilTm73kML4KEPrkWFg12K4/XqE8Lu2IrDtwnMbt3Yj6K68KyWg12K4/axiXXfUZ8wl3XQXiPR22a45XvXQjsAKIiYiXiA4PaL53lxbDXXBlEOr/tJ3T4h3MaXABzz9jfxiXXfUZ8wlHXQXuvViFgluP3OHDDbObg/mB4ihLE2OI2/Si2P5XvXQjsAKIqTiXivpXvD/KZk7qyIyKuJ+jgLOxNBcKx4iXiD3DuysijlP3S1fYJEs0DXwdgrky5O=


        /**
         osAV:5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.96 Safari/537.36
         osPF:Win32
         miserHardInfo:
         appkey:
         nickLoginLink:
         mobileLoginLink:https://login.taobao.com/member/login.jhtml?spm=a21bo.50862.754894437.1.VelFzV&f=top&redirectURL=https://www.taobao.com/&useMobile=true
         showAssistantLink:false
         um_token:HV01PAAZ0be3fb96700ae8ad59132a6c008ed20f
         */













        return map;
    }

}
