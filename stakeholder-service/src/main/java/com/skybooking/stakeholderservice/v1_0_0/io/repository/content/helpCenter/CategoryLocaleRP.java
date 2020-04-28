package com.skybooking.stakeholderservice.v1_0_0.io.repository.content.helpCenter;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.helpCenter.CategoryLocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryLocaleRP extends JpaRepository<CategoryLocaleEntity, Long> {

    CategoryLocaleEntity findByCategoryIdAndLocale(Long id, String locale);

}
