package com.skybooking.stakeholderservice.v1_0_0.io.enitity.notification;

import javax.persistence.*;

@Entity
public class OneSignalPushNotification {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String userName;

    private String idOneSignal;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getIdOneSignal() {
        return idOneSignal;
    }

    public void setIdOneSignal(String idOneSignal) {
        this.idOneSignal = idOneSignal;
    }

}
