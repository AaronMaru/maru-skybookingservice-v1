package com.skybooking.eventservice.v1_0_0.ui.model.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class S3UploadRS {

    String file;
    String path;

}
