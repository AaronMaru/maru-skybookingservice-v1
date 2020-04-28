package com.skybooking.stakeholderservice.v1_0_0.io.repository.content.page;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.page.FrontendPageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FrontendPageRP extends JpaRepository<FrontendPageEntity, Long> {

    List<FrontendPageEntity> getAllByAllowPublic(Integer allowPublic);

}
