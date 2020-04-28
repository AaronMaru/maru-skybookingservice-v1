package com.skybooking.stakeholderservice.v1_0_0.service.interfaces.content;

import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.CategoryRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.QuestionRS;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface HelpCenterSV {

    List<CategoryRS> getCategories();
    List<QuestionRS> getQuestions(Long catId);

}
