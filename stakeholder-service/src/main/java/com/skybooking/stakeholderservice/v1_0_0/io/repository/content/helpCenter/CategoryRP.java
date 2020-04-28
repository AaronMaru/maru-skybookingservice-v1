package com.skybooking.stakeholderservice.v1_0_0.io.repository.content.helpCenter;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.helpCenter.CategoryEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CategoryRP extends JpaRepository<CategoryEntity, Long> {

    List<CategoryEntity> findByStatus(Integer status);

}
