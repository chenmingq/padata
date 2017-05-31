package com.mcin.dao.impl;

import com.mcin.dao.InfoMapper;
import com.mcin.model.Info;
import com.mcin.util.SqlSessionUtil;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.SqlSession;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * mybatis 实现
 */
public class InfoDaoImpl implements InfoMapper {

	private static final Logger logger = Logger.getLogger(InfoDaoImpl.class);

	private InfoMapper mapper;

	/**
	 * 添加数据
	 * @param info
	 * @return
	 */
	public int insertData(Info info) {
		int result = 0 ;
		int num = selectNum(info.getUserInfo());
		if (num < 1){
			//获取sqlSession会话
			SqlSession session = SqlSessionUtil.openSqlSession();
			mapper = session.getMapper(InfoMapper.class);
			try {
				result = mapper.insertData(info);
				logger.info("***数据添加成功***");
				session.commit();//提交事务
			} finally {
				session.close();
			}
		}
		return result;
	}


	/**
	 * 查询数据是否被添加
	 * @param userInfo
	 * @return
	 */
	public int selectNum(@Param("userInfo") String userInfo) {
		//获取sqlSession会话
		SqlSession session = SqlSessionUtil.openSqlSession();
		mapper = session.getMapper(InfoMapper.class);
		int result = 0 ;
		try {
			result = mapper.selectNum(userInfo);
		} finally {
			session.close();
		}
		return result;
	}

	/**
	 * 查询出所有数据 保存excel
	 * @return
	 */
	public List<Info> selectInfo() {
		List<Info> list = new ArrayList<Info>();
        SqlSession session = SqlSessionUtil.openSqlSession();
        mapper = session.getMapper(InfoMapper.class);
        list = mapper.selectInfo();
		return list;
	}

	/**
	 * 添加ip
	 * @param map
	 */
	public void insertIp(Map<String, Object> map) {
		int num = selectIpNum(map);
		if ( num < 1){
			SqlSession session = SqlSessionUtil.openSqlSession();
			mapper = session.getMapper(InfoMapper.class);
			mapper.insertIp(map);
			session.commit();
		}
	}


	/**
	 *
	 * @param map
	 * @return
	 */
	public int selectIpNum(Map<String, Object> map) {
		SqlSession session = SqlSessionUtil.openSqlSession();
		mapper = session.getMapper(InfoMapper.class);
		int result = mapper.selectIpNum(map);
		return result;
	}

	/**
	 * 随机获取ip
	 * @return
	 */
	public Map<String,String> roandIp(){
		Map<String,String> resultMap = new HashMap<String, String>();
		SqlSession session = SqlSessionUtil.openSqlSession();
		mapper = session.getMapper(InfoMapper.class);
		resultMap = mapper.roandIp();
		return resultMap;
	}
}
