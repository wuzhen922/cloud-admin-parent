package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * rtu套餐关联表dmo
 * @author xudajan
 *
 */
@Table(name= "t_rtu_pack")
public class RtuPackDmo extends Entity {

    private static final long serialVersionUID = -7522458294151440859L;
    /**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

    /**
     * 绑在rtu上卡的ccid
     */
    @Column(name = "sn_number")
    private String snNumber;

    /**
     * 套餐编码
     */
    @Column(name = "pack_num")
    private String packNum;

    /**
     * 状态 10：正常 99：删除
     */
    @Column(name = "status")
    private String status;

	/**
	 * 创建时间
	 */
	@Column(name = "create_time")
	private Date  createTime;

	/**
	 * 最后一次更新时间
	 */
	@Column(name = "last_update_time")
	private Date  lastUpdateTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
    }

    public String getPackNum() {
        return packNum;
    }

    public void setPackNum(String packNum) {
        this.packNum = packNum;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }
}

