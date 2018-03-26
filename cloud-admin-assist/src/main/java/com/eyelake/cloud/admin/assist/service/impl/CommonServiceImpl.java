/**
 * 
 */
package com.eyelake.cloud.admin.assist.service.impl;

import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.eyelake.cloud.admin.assist.service.intf.CommonService;

import com.eyelake.cloud.common.utils.SqlAssertUtils;
import com.yjh.framework.core.entity.Entity;
import com.yjh.framework.core.trace.ServiceTrace;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author dell
 * 
 */
@Service
@ServiceTrace
public class CommonServiceImpl<T extends Entity> implements CommonService<T> {

	@Autowired
	private CommonDao<T> commonDao;

	@Override
	public Result insert(T dmo) {
		int i = commonDao.insert(dmo);
		return SqlAssertUtils.insertAssert(i);
	}

	@Override
	public Result delete(T dmo) {
		int i = commonDao.delete(dmo);
		return SqlAssertUtils.deleteAssert(i);
	}

	@Override
	public Result update(T dmo) {
		int i = commonDao.update(dmo);
		return SqlAssertUtils.updateAssert(i);
	}

	@Override
	public T selectOne(T con) {

		return commonDao.selectOne(con);
	}

	@Override
	public List<T> selectList(T con) {

		return commonDao.selectList(con);
	}

	@Override
	public List<T> selectList(String sql, T con) {

		return commonDao.selectList(sql, con);
	}

	@Override
	public List<T> selectList(String sql, HashMap<String, String> con) {

		return commonDao.selectList(sql, con);
	}

	@Override
	public List<T> selectListByPage(String countSql, String querySql, Object con, Page page) {

		return commonDao.selectListByPage(countSql, querySql, con, page);
	}

	@Override
	public Result update(String sql, HashMap<String, String> con) {
		int i = commonDao.update(sql, con);
		return SqlAssertUtils.updateAssert(i);
	}

	@Override
	public Result update(String sql, T con) {
		int i = commonDao.update(sql, con);
		return SqlAssertUtils.updateAssert(i);
	}

	@Override
	public Result delete(String sql, HashMap<String, String> con) {
		int i = commonDao.delete(sql, con);
		return SqlAssertUtils.deleteAssert(i);
	}

	@Override
	public Result delete(String sql, Object con) {
		int i = commonDao.delete(sql, con);
		return SqlAssertUtils.deleteAssert(i);
	}

	@Override
	public Result insert(String sql, HashMap<String, String> con) {
		int i = commonDao.insert(sql, con);
		return SqlAssertUtils.insertAssert(i);
	}

	@Override
	public T selectOne(String sql, HashMap<String, String> con) {

		return commonDao.selectOne(sql, con);
	}

	@Override
	public T selectOne(String sql, T con) {

		return commonDao.selectOne(sql, con);
	}

	@Override
	public Result insert(String sql, T con) {
		int i = commonDao.insert(sql, con);
		return SqlAssertUtils.insertAssert(i);
	}

	@Override
	public Long selectCount(String sql, HashMap<String, String> con) {

		return commonDao.selectCount(sql, con);
	}

	@Override
	public Long selectCount(String sql, T con) {

		return commonDao.selectCount(sql, con);
	}

	@Override
	public Result batchInsert(String sql, Object con) {
		int i = commonDao.batchInsert(sql, con);
		return SqlAssertUtils.insertAssert(i);
	}

	@Override
	public List<LinkedHashMap<String, String>> selectDownloadList(String sql, HashMap<String, String> con) {

		return commonDao.selectDownloadList(sql, con);
	}

}
