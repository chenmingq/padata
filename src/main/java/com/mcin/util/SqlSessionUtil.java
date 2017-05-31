package com.mcin.util;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

import java.io.IOException;
import java.io.Reader;
import java.nio.charset.Charset;

public class SqlSessionUtil {
	
	private SqlSessionUtil(){}
	
	private static SqlSessionFactory factory;
	private static String mapperPath = "config/mybatis.xml";

	static{
		try {
			//设置输入流的编码格式
			Resources.setCharset(Charset.forName("utf-8"));
			//读取mybatis配置文件 路径是bin目录
			Reader reader = Resources.getResourceAsReader(mapperPath);
			//创建SqlSessionFactory对象  第二个参数为加载的数据库环境id 缺省情况下默认加载的为default中设置的环境
			factory = new SqlSessionFactoryBuilder().build(reader,"info");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	/**
	 * 获取一个SqlSession对象
	 * @return
	 */
	public static SqlSession openSqlSession(){
		return factory.openSession();
	}
}
