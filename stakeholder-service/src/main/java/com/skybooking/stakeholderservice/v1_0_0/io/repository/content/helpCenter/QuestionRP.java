package com.skybooking.stakeholderservice.v1_0_0.io.repository.content.helpCenter;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.helpCenter.QuestionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface QuestionRP extends JpaRepository<QuestionEntity, Long> {

    List<QuestionEntity> findByCategoryIdAndStatus(Long catId, Integer status);

}
