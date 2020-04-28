package com.skybooking.stakeholderservice.v1_0_0.io.repository.content.page;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.page.FrontendPageLocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface FrontendPageLocaleRP extends JpaRepository<FrontendPageLocaleEntity, Long> {

    @Query(value = "SELECT * FROM frontend_page_locales WHERE page_id  = ?1 AND locale = ?2 LIMIT 1", nativeQuery = true)
    FrontendPageLocaleEntity getByPageIdAndLocale(Long pageId, String locale);

}
