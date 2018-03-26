package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 传感器数据类型表
 * @author j_cong
 *
 */
@Table(name= "t_sensor_datatype_info")
public class SensorDataTypeDmo extends Entity {

    private static final long serialVersionUID = 362566848450143063L;
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
	 * 厂商名
	 */
	@Column(name = "product_name")
	private String productName;
	
	/**
	 * 传感器类型枚举
	 */
	@Column(name = "sensor_type_enum")
	private String sensorTypeEnum;
	
	/**
	 * 传感器类型
	 */
    @Column(name = "sensor_type")
	private String sensorType;
	
	/**
	 * 传感器型号枚举
	 */
	@Column(name = "sensor_model_enum")
	private String sensorModelEnum;

    /**
     * 传感器型号
     */
    @Column(name = "sensor_model")
    private Long sensorModel;

    /**
     * 数据类型枚举
     */
    @Column(name = "data_type_enum")
    private Long dataTypeEnum;

    /**
     * 数据类型
     */
    @Column(name = "data_type")
    private String dataType;

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

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getSensorTypeEnum() {
        return sensorTypeEnum;
    }

    public void setSensorTypeEnum(String sensorTypeEnum) {
        this.sensorTypeEnum = sensorTypeEnum;
    }

    public String getSensorType() {
        return sensorType;
    }

    public void setSensorType(String sensorType) {
        this.sensorType = sensorType;
    }

    public String getSensorModelEnum() {
        return sensorModelEnum;
    }

    public void setSensorModelEnum(String sensorModelEnum) {
        this.sensorModelEnum = sensorModelEnum;
    }

    public Long getSensorModel() {
        return sensorModel;
    }

    public void setSensorModel(Long sensorMode) {
        this.sensorModel = sensorMode;
    }

    public Long getDataTypeEnum() {
        return dataTypeEnum;
    }

    public void setDataTypeEnum(Long dataTypeEnum) {
        this.dataTypeEnum = dataTypeEnum;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
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
