package com.jess.order.entity;

import java.util.Date;
import javax.persistence.*;

@Table(name = "com_version")
public class Version {
    /**
     * 版本主键id
     */
    @Id
    private Long id;

    /**
     * 版本名称
     */
    private String name;

    /**
     * 版本号
     */
    @Column(name = "version_no")
    private String versionNo;

    /**
     * 创建时间
     */
    @Column(name = "create_time")
    private Date createTime;

    /**
     * 更新时间
     */
    @Column(name = "update_time")
    private Date updateTime;

    public Version(Long id, String name, String versionNo, Date createTime, Date updateTime) {
        this.id = id;
        this.name = name;
        this.versionNo = versionNo;
        this.createTime = createTime;
        this.updateTime = updateTime;
    }

    public Version() {
        super();
    }

    /**
     * 获取版本主键id
     *
     * @return id - 版本主键id
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置版本主键id
     *
     * @param id 版本主键id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取版本名称
     *
     * @return name - 版本名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置版本名称
     *
     * @param name 版本名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取版本号
     *
     * @return version_no - 版本号
     */
    public String getVersionNo() {
        return versionNo;
    }

    /**
     * 设置版本号
     *
     * @param versionNo 版本号
     */
    public void setVersionNo(String versionNo) {
        this.versionNo = versionNo == null ? null : versionNo.trim();
    }

    /**
     * 获取创建时间
     *
     * @return create_time - 创建时间
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置创建时间
     *
     * @param createTime 创建时间
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * 获取更新时间
     *
     * @return update_time - 更新时间
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * 设置更新时间
     *
     * @param updateTime 更新时间
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}