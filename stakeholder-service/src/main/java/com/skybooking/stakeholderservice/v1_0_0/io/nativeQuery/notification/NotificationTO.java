package com.skybooking.stakeholderservice.v1_0_0.io.nativeQuery.notification;


import java.sql.Date;

public class NotificationTO {

    private Integer id;
    private String urlKey;
    private String title;
    private String description;
    private String notiIcon;
    private Integer readable;
    private Integer status;
    private Date createdAt;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrlKey() {
        return urlKey;
    }

    public void setUrlKey(String urlKey) {
        this.urlKey = urlKey;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getNotiIcon() {
        return notiIcon;
    }

    public void setNotiIcon(String notiIcon) {
        this.notiIcon = notiIcon;
    }

    public Integer getReadable() {
        return readable;
    }

    public void setReadable(Integer readable) {
        this.readable = readable;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
