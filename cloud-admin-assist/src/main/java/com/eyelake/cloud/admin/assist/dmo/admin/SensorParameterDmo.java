package com.eyelake.cloud.admin.assist.dmo.admin;

import com.yjh.framework.core.dao.mybatis.torm.sql.annotation.*;
import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 * 传感器参数数值表
 * @author j_cong
 *
 */
@Table(name= "t_sensor_parameter")
public class SensorParameterDmo extends Entity {

    private static final long serialVersionUID = 1820342404458734198L;
    /**
	 * 主键
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "id")
	private Long id;
	
	/**
	 * 传感器 ID
	 */
	@Column(name = "sensor_id")
	private Long sensorId;
	
	/**
	 * 数据类型枚举
	 */
	@Column(name = "datatype_enum")
	private String dataTypeEnum;
	
	/**
	 * 数据类型名称
	 */
    @Column(name = "data_type")
	private String dataType;
	
	/**
	 * 参数的json字符串
	 */
	@Column(name = "param")
	private String param;

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

    public Long getSensorId() {
        return sensorId;
    }

    public void setSensorId(Long sensorId) {
        this.sensorId = sensorId;
    }

    public String getDataTypeEnum() {
        return dataTypeEnum;
    }

    public void setDataTypeEnum(String dataTypeEnum) {
        this.dataTypeEnum = dataTypeEnum;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getParam() {
        return param;
    }

    public void setParam(String param) {
        this.param = param;
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
