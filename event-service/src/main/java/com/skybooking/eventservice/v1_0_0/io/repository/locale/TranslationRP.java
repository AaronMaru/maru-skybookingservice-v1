package com.skybooking.eventservice.v1_0_0.io.repository.locale;

import com.skybooking.eventservice.v1_0_0.io.entity.locale.TranslationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TranslationRP extends JpaRepository<TranslationEntity, Long> {

}
