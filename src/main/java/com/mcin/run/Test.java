package com.mcin.run;

import com.mcin.dao.InfoMapper;
import com.mcin.dao.impl.InfoDaoImpl;
import com.mcin.model.Info;

import java.util.Date;

/**
 * Created by Mcin on 2017/5/16.
 */
public class Test {
    public static void main(String[] args) {
        InfoMapper mapper = new InfoDaoImpl();
        Info info = new Info();
        info.setCompanyName("123");
        info.setUserInfo("3212312");
        info.setCreateTime(new Date());
        mapper.insertData(info);
    }
}
