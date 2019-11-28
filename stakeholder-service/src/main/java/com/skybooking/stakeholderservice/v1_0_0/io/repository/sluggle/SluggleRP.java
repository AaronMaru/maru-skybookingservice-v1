package com.skybooking.stakeholderservice.v1_0_0.io.repository.sluggle;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.sluggle.SluggleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SluggleRP extends JpaRepository<SluggleEntity, Long> {
    SluggleEntity findByKey(String key);
}
