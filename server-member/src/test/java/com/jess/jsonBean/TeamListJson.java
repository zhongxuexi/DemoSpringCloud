/**
  * Copyright 2018 bejson.com 
  */
package com.jess.jsonBean;
import java.util.List;

/**
 * Auto-generated: 2018-07-17 14:48:33
 *
 * @author bejson.com (i@bejson.com)
 * @website http://www.bejson.com/java2pojo/
 */
public class TeamListJson {

    private String name;
    private String introduce;
    private List<ManageListJson> managelist;
    private List<String> filelist;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public List<ManageListJson> getManagelist() {
        return managelist;
    }

    public void setManagelist(List<ManageListJson> managelist) {
        this.managelist = managelist;
    }

    public List<String> getFilelist() {
        return filelist;
    }

    public void setFilelist(List<String> filelist) {
        this.filelist = filelist;
    }
}