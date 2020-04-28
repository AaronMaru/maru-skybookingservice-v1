package com.skybooking.stakeholderservice.v1_0_0.io.repository.content.career;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.career.CareersLocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CareerLocaleRP extends JpaRepository<CareersLocaleEntity, Long> {

    CareersLocaleEntity findByCareerIdAndLocale(Long careerId, String locale);

}
