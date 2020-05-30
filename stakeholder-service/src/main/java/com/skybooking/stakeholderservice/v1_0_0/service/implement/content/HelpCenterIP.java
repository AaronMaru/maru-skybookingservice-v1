package com.skybooking.stakeholderservice.v1_0_0.service.implement.content;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.helpCenter.CategoryEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.helpCenter.CategoryLocaleEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.helpCenter.QuestionEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.helpCenter.QuestionLocaleEntity;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.content.helpCenter.CategoryLocaleRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.content.helpCenter.CategoryRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.content.helpCenter.QuestionLocaleRP;
import com.skybooking.stakeholderservice.v1_0_0.io.repository.content.helpCenter.QuestionRP;
import com.skybooking.stakeholderservice.v1_0_0.service.interfaces.content.HelpCenterSV;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.CategoryRS;
import com.skybooking.stakeholderservice.v1_0_0.ui.model.response.content.QuestionRS;
import com.skybooking.stakeholderservice.v1_0_0.util.header.HeaderBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

@Service
public class HelpCenterIP implements HelpCenterSV {

    @Autowired
    private CategoryRP categoryRP;

    @Autowired
    private CategoryLocaleRP categoryLocaleRP;

    @Autowired
    private QuestionRP questionRP;

    @Autowired
    private QuestionLocaleRP questionLocaleRP;

    @Autowired
    private HeaderBean headerBean;


    public List<CategoryRS> getCategories() {

        List<CategoryRS> categoriesRS = new ArrayList<>();
        List<CategoryEntity> categoryEntities = categoryRP.findByStatus(1);

        categoryEntities.forEach(data -> {
            CategoryRS categoryRS = new CategoryRS();

            CategoryLocaleEntity catLocale = categoryLocaleRP.findByCategoryIdAndLocale(data.getId(), headerBean.getLocalization());
            if (catLocale == null) {
                catLocale = categoryLocaleRP.findByCategoryIdAndLocale(data.getId(), "en");
            }

            categoryRS.setId(data.getId());
            categoryRS.setName(catLocale.getName());
            categoryRS.setOrder(data.getOrder());

            categoriesRS.add(categoryRS);

        });

        return categoriesRS;
    }


    public List<QuestionRS> getQuestions(Long catId) {

        List<QuestionEntity> questionEntities = questionRP.findByCategoryIdAndStatus(catId, 1);
        List<QuestionRS> questionsRS = new ArrayList<>();

        questionEntities.forEach(data -> {
            QuestionLocaleEntity questionLocale = questionLocaleRP.findByQuestionIdAndLocale(data.getId(), headerBean.getLocalization());
            if (questionLocale == null) {
                questionLocale = questionLocaleRP.findByQuestionIdAndLocale(data.getId(), "en");
            }

            QuestionRS questionRS = new QuestionRS();
            questionRS.setOrder(data.getOrder());
            questionRS.setTitle(questionLocale.getTitle());
            questionRS.setBody(questionLocale.getBody());

            questionsRS.add(questionRS);
        });

        return questionsRS;

    }

}
