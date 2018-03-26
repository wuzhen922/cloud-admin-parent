package com.eyelake.cloud.admin.assist.dao.mybatis;

import com.eyelake.cloud.admin.assist.dao.CommonDao;
import com.yjh.framework.core.dao.Dao;
import com.yjh.framework.core.dao.mybatis.DaoMyBatis;
import com.yjh.framework.core.entity.Entity;
import com.yjh.framework.page.Page;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 公共Dao服务实现类
 */
@Dao("commonDao")
public class CommonDaoMybatis<T extends Entity> extends DaoMyBatis implements CommonDao<T> {

	@Override
	public int insert(T dmo) {
		return super.insert(dmo);
	}

	@Override
	public int delete(T dmo) {
		return super.delete(dmo);
	}

	@Override
	public int update(T dmo) {
		return super.update(dmo);
	}

	@Override
	public T selectOne(T con) {
		return super.selectOne(con);
	}

	@Override
	public List<T> selectList(T con) {
		return super.selectList(con);
	}

	@Override
	public List<T> selectList(String sql, T con) {

		return super.selectList(sql, con);
	}

	@Override
	public List<T> selectListByPage(String countSql, String querySql, Object con, Page page) {
		return super.selectByPage(countSql, querySql, con, page);
	}

	@Override
	public List<T> selectList(String sql, HashMap<String, String> con) {
		return super.selectList(sql, con);
	}

	@Override
	public int update(String sql, HashMap<String, String> con) {
		return super.update(sql, con);
	}

    @Override
    public int update(String sql, T con) {
        return super.update(sql, con);
    }

	@Override
	public int delete(String sql, HashMap<String, String> con) {
		return super.delete(sql, con);
	}
    @Override
    public int delete(String sql, Object con) {
        return super.delete(sql, con);
    }

	@Override
	public int insert(String sql, HashMap<String, String> con) {
		return super.insert(sql, con);
	}

	@Override
	public T selectOne(String sql, HashMap<String, String> con) {
		return super.selectOne(sql, con);
	}

    @Override
    public T selectOne(String sql, T con) {
        return super.selectOne(sql,con);
    }

    @Override
	public int insert(String sql, T con) {
		return super.insert(sql, con);
	}

    @Override
    public Long selectCount(String sql, HashMap<String, String> con) {
        return super.selectOne(sql,con);
    }

    @Override
    public Long selectCount(String sql, T con) {
        return super.selectOne(sql,con);
    }

    @Override
    public int batchInsert(String sql, Object con) {
        return super.insert(sql,con);
    }

    @Override
    public List<LinkedHashMap<String, String>> selectDownloadList(String sql, HashMap<String, String> con) {

        return super.selectList(sql, con);
    }

}
