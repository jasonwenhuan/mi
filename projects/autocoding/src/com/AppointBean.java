package com;

import java.util.Date;

public class AppointBean{

    private Integer id;
    private String name;
    private Date createDate;
    private Date modifiDate;
    private String Detail;

    public AppointBean(){

    }

    public Integer getId(){
        return id;
    }

    public void setId(Integer id){
        this.id = id;
    }

    public String getName(){
        return name;
    }

    public void setName(String name){
        this.name = name;
    }

    public Date getCreatedate(){
        return createDate;
    }

    public void setCreatedate(Date createDate){
        this.createDate = createDate;
    }

    public Date getModifidate(){
        return modifiDate;
    }

    public void setModifidate(Date modifiDate){
        this.modifiDate = modifiDate;
    }

    public String getDetail(){
        return Detail;
    }

    public void setDetail(String Detail){
        this.Detail = Detail;
    }


}