package com.bitsvalley.micro.domain;

import javax.persistence.*;

/**
 * @author Fru Chifen
 * 11.08.2021
 */
@Entity
@Table(name = "runtimeproperties")
public class RuntimeProperties {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;

    private String propertyName;
    private String propertyValue;

    private long orgId;

    public long getOrgId() {
        return orgId;
    }

    public void setOrgId(long orgId) {
        this.orgId = orgId;
    }

    public String getPropertyName() {
        return propertyName;
    }

    public void setPropertyName(String propertyName) {
        this.propertyName = propertyName;
    }

    public String getPropertyValue() {
        return propertyValue;
    }

    public void setPropertyValue(String propertyValue) {
        this.propertyValue = propertyValue;
    }
}
