package com.jess.member.entity;

import javax.persistence.*;

@Table(name = "t_girl")
public class Girl {
    @Id
    private Integer id;

    private Integer age;

    @Column(name = "cup_size")
    private String cupSize;

    private String name;

    public Girl(Integer id, Integer age, String cupSize, String name) {
        this.id = id;
        this.age = age;
        this.cupSize = cupSize;
        this.name = name;
    }

    public Girl() {
        super();
    }

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return age
     */
    public Integer getAge() {
        return age;
    }

    /**
     * @param age
     */
    public void setAge(Integer age) {
        this.age = age;
    }

    /**
     * @return cup_size
     */
    public String getCupSize() {
        return cupSize;
    }

    /**
     * @param cupSize
     */
    public void setCupSize(String cupSize) {
        this.cupSize = cupSize == null ? null : cupSize.trim();
    }

    /**
     * @return name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    @Override
    public String toString() {
        return "Girl{" +
                "id=" + id +
                ", age=" + age +
                ", cupSize='" + cupSize + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}