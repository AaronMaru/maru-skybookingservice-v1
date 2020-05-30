package com.skybooking.stakeholderservice.v1_0_0.io.repository.users;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.user.UserLanguageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLanguageRP extends JpaRepository<UserLanguageEntity, Long> {

    UserLanguageEntity findBySkyuserIdAndDeviceIdAndCompanyIdIsNull(Long skyuserId, String deviceId);
    UserLanguageEntity findBySkyuserIdAndCompanyIdAndDeviceId(Long skyuserId, Long companyId, String deviceId);
}
