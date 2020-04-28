package com.skybooking.stakeholderservice.v1_0_0.io.repository.content.helpCenter;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.helpCenter.QuestionLocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface QuestionLocaleRP extends JpaRepository<QuestionLocaleEntity, Long> {

    QuestionLocaleEntity findByQuestionIdAndLocale(Long id, String locale);

}
