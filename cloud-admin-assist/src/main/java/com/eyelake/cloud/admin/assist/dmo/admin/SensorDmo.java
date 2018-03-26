package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 传感器信息表
 * @author j_cong
 *
 */
@Table(name= "t_sensor_info")
public class SensorDmo extends Entity {

    private static final long serialVersionUID = 3403143382834199166L;

    /**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;

	/**
	 * 生产商名称（天宝，司南等）枚举类型
	 */
	@Column(name = "product_name_enum")
	private String productNameEnum;
	
	/**
	 * 传感器名称
	 */
	@Column(name = "sensor_name")
	private String sensorName;
	
	/**
	 * 传感器类型
	 */
	@Column(name = "sensor_type_enum")
	private String sensorTypeEnum;
	
	/**
	 * 传感器型号
	 */
    @Column(name = "sensor_model_enum")
	private String sensorModelEnum;
	
	/**
	 * 传感器编号
	 */
	@Column(name = "sensor_number")
	private String sensorNumber;

    /**
     * 集成商企业ID
     */
    @Column(name = "integrator_id")
    private Long integratorId;

    /**
     * RTU ID
     */
    @Column(name = "rtu_id")
    private Long rtuId;

    /**
     * RTU SN码
     */
    @Column(name = "sn_number")
    private String snNumber;

    /**
     * 通道号
     */
    @Column(name = "tunnel_number")
    private String tunnelNumber;

    /**
     * 传感器唯一编号
     */
    @Column(name = "uuid")
    private String uuid;

    /**
     * 备注
     */
    @Column(name = "remark")
    private String remark;

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

	/**
	 * 状态
     *
     * 10：正常
     * 20：禁用
     * 99：已删除
	 */
	@Column(name = "status")
    private String status;


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProductNameEnum() {
        return productNameEnum;
    }

    public void setProductNameEnum(String productNameEnum) {
        this.productNameEnum = productNameEnum;
    }

    public String getSensorName() {
        return sensorName;
    }

    public void setSensorName(String sensorName) {
        this.sensorName = sensorName;
    }

    public String getSensorTypeEnum() {
        return sensorTypeEnum;
    }

    public void setSensorTypeEnum(String sensorTypeEnum) {
        this.sensorTypeEnum = sensorTypeEnum;
    }

    public String getSensorModelEnum() {
        return sensorModelEnum;
    }

    public void setSensorModelEnum(String sensorModelEnum) {
        this.sensorModelEnum = sensorModelEnum;
    }

    public String getSensorNumber() {
        return sensorNumber;
    }

    public void setSensorNumber(String sensorNumber) {
        this.sensorNumber = sensorNumber;
    }

    public Long getIntegratorId() {
        return integratorId;
    }

    public void setIntegratorId(Long integratorId) {
        this.integratorId = integratorId;
    }

    public Long getRtuId() {
        return rtuId;
    }

    public void setRtuId(Long rtuId) {
        this.rtuId = rtuId;
    }

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
    }

    public String getTunnelNumber() {
        return tunnelNumber;
    }

    public void setTunnelNumber(String tunnelNumber) {
        this.tunnelNumber = tunnelNumber;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
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

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
