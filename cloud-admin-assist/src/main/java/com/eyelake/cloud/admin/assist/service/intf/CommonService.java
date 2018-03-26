package com.eyelake.cloud.admin.assist.service.intf;

import com.yjh.framework.core.entity.Entity;
import com.yjh.framework.lang.Result;
import com.yjh.framework.page.Page;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

public interface CommonService<T extends Entity> {

	/**
	 * 单表操作新增数据
	 * 
	 * @param dmo
	 * @return
	 */
	public Result insert(T dmo);
	
	/**
	 * 单表操作删除数据
	 * 
	 * @param dmo
	 * @return
	 */
	public Result delete(T dmo);

	/**
	 * 单表操作修改数据
	 * 
	 * @param dmo
	 * @return
	 */
	public Result update(T dmo);

	/**
	 * 单表操作查询单条数据
	 * 
	 * @param con
	 * @return
	 */
	public T selectOne(T con);
	
	/**
	 * 单表操作查询列表数据
	 * @param con
	 * @return
	 */
	public List<T> selectList(T con);
	
	/**
	 * 根据对应SQL和条件查询列表
	 * 
	 * @param sql
	 * @param con
	 * @return
	 */
	public List<T> selectList(String sql, T con);
	
	/**
	 * 根据对应SQL和条件查询列表
	 * 
	 * @param sql
	 * @param con
	 * @return
	 */
	public List<T> selectList(String sql, HashMap<String, String> con);
	
	/**
	 * 分页查询
	 * 
	 * @param countSql
	 * @param querySql
	 * @param con
	 * @return
	 */
	public List<T> selectListByPage(String countSql, String querySql, Object con, Page page);
	
	/**
	 * 根据对应SQL和条件更新数据
	 * 
	 * @param sql
	 * @param con
	 * @return
	 */
	public Result update(String sql, HashMap<String, String> con);

    /**
     * 根据对应SQL和条件更新数据
     *
     * @param sql
     * @param con
     * @return
     */
    public Result update(String sql, T con);
	
	/**
	 * 根据对应SQL和条件删除数据
	 * @param sql
	 * @param con
	 * @return
	 */
	public Result delete(String sql, HashMap<String, String> con);

    /**
     * 根据对应SQL和条件删除数据
     * @param sql
     * @param con
     * @return
     */
    public Result delete(String sql, Object con);
	
	/**
	 * 根据对应SQL和条件插入数据
	 * @param sql
	 * @param con
	 * @return
	 */
	public Result insert(String sql, HashMap<String, String> con);

	/**
	 * 根据对应的SQL和条件查询一条记录
	 * @param sql
	 * @param con
     * @return
     */
	public T selectOne(String sql, HashMap<String, String> con);

    /**
     * 根据对应的SQL和条件查询一条记录
     * @param sql
     * @param con
     * @return
     */
    public T selectOne(String sql, T con);

	/**
	 * 根据对应的SQL和对象新增数据
	 * @param sql
	 * @param con
     * @return
     */
	public Result insert(String sql, T con);


    /**
     * 根据对应的SQL和条件查询记录数
     * @param sql
     * @param con
     * @return
     */
    public Long selectCount(String sql, HashMap<String, String> con);


    /**
     * 根据对应的SQL和条件查询记录数
     * @param sql
     * @param con
     * @return
     */
    public Long selectCount(String sql, T con);

    /**
     * 根据对应的SQL和条件批量插入数据
     * @param sql
     * @param con
     * @return
     */
    public Result batchInsert(String sql, Object con);

    /**
     * 查询下载列表
     *
     * @param sql
     * @param con
     * @return
     */
    public List<LinkedHashMap<String, String>> selectDownloadList(String sql, HashMap<String, String> con);
}
