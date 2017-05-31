package com.mcin.dao;

import com.mcin.model.Info;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 数据库操作
 */
public interface InfoMapper {

	/**
	 * 新增 用户
	 * @param info
	 * @return 返回数据库受影响的行数
	 */
	int insertData(Info info);

	/**
	 * 查询用户信息是否存在
	 * @param userInfo
	 * @return
	 */
	int selectNum (@Param("userInfo") String userInfo);

	/**
	 * 查询出所有的数据
	 * @return
	 */
	List<Info> selectInfo ();

	/**
	 * ip添加
	 * @param map
	 */
	void insertIp (Map<String ,Object> map);

	/**
	 * 查看ip是否存在
	 * @param map
	 * @return
	 */
	int selectIpNum (Map<String ,Object> map);

    /**
     * 随机获取ip
     * @return
     */
	Map<String ,String > roandIp ();



}
