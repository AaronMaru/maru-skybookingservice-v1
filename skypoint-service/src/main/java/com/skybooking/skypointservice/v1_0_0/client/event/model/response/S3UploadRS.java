package com.skybooking.skypointservice.v1_0_0.client.event.model.response;

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
