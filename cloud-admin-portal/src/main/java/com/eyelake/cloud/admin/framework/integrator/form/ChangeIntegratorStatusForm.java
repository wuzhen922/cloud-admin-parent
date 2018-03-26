package com.eyelake.cloud.admin.framework.integrator.form;

/**
 * 集成商信息表单
 *
 * @author eyelake
 */
public class ChangeIntegratorStatusForm {

    private String id;

    private String status;

    private String disabledReason;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getDisabledReason() {
        return disabledReason;
    }

    public void setDisabledReason(String disabledReason) {
        this.disabledReason = disabledReason;
    }
}
