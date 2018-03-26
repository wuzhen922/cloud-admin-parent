package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 套餐信息表
 * @author :xudajan
 *
 */
@Table(name= "t_pack_info")
public class PackInfoDmo extends Entity {

    private static final long serialVersionUID = -7522458294151440859L;
    /**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	/**
	 * 套餐编码
	 */
	@Column(name = "pack_num")
	private String packNum;

	/**
	 * 套餐名称
	 */
	@Column(name = "pack_name")
	private String packName;

	/**
	 * 套餐流量值（M）
	 */
	@Column(name = "pack_traffic")
	private String packTraffic;


    /**
     * 运营商枚举
     * 中国移动：00
     * 中国联通：01
     * 中国电信：03
     */
    @Column(name = "carrier_operator_enum")
    private String carrierOperatorEnum;

    /**
     * 套餐类型枚举01：固定套餐02：加油包
     */
    @Column(name = "pack_type")
    private String packType;


    /**
     * 状态 10：正常 20：失效 99：删除
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

    public String getPackNum() {
        return packNum;
    }

    public void setPackNum(String packNum) {
        this.packNum = packNum;
    }

    public String getPackName() {
        return packName;
    }

    public void setPackName(String packName) {
        this.packName = packName;
    }

    public String getPackTraffic() {
        return packTraffic;
    }

    public void setPackTraffic(String packTraffic) {
        this.packTraffic = packTraffic;
    }

    public String getCarrierOperatorEnum() {
        return carrierOperatorEnum;
    }

    public void setCarrierOperatorEnum(String carrierOperatorEnum) {
        this.carrierOperatorEnum = carrierOperatorEnum;
    }

    public String getPackType() {
        return packType;
    }

    public void setPackType(String packType) {
        this.packType = packType;
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

