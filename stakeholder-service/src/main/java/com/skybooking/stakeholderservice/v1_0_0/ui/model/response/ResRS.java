package com.skybooking.stakeholderservice.v1_0_0.ui.model.response;

public class ResRS {

    private String message;
    private Object data;

    public ResRS(String message, Object data) {
        this.message = message;
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }


}
