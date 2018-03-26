package com.eyelake.cloud.admin.assist.dto.admin;

import com.yjh.framework.core.entity.Entity;

import java.util.Date;

/**
 *
 * @author  xudajan
 */
public class TreeNodeDataInfoDto extends Entity {

    private static final long serialVersionUID = 6039621565672177412L;

    private String warningStatus;

    private String snNumber;

    public String getWarningStatus() {
        return warningStatus;
    }

    public void setWarningStatus(String warningStatus) {
        this.warningStatus = warningStatus;
    }

    public String getSnNumber() {
        return snNumber;
    }

    public void setSnNumber(String snNumber) {
        this.snNumber = snNumber;
    }
}
