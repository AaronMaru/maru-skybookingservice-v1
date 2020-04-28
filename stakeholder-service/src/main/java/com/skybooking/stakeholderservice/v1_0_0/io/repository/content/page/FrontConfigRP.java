package com.skybooking.stakeholderservice.v1_0_0.io.repository.content.page;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.page.FrontConfigEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FrontConfigRP extends JpaRepository<FrontConfigEntity, Long> {
    FrontConfigEntity findByName(String name);
}
