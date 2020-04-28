package com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content;

import lombok.Data;

@Data
public class PagesRS {

    private String title;
    private Object body;
    private String metaTitle;
    private String metaDescription;
    private String metaKeyword;
    private String code;
    private String awsUrl = "https://skybooking.s3.amazonaws.com/cms/frontend_page/";

}
