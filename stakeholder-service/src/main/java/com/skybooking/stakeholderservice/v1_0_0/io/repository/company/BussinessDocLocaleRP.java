package com.skybooking.stakeholderservice.v1_0_0.io.repository.company;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.company.BussinessDocLocaleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BussinessDocLocaleRP extends JpaRepository<BussinessDocLocaleEntity, Long> {

    BussinessDocLocaleEntity findByBusinessDocIdAndLocaleId(Long bizId, Long localeId);

    List<BussinessDocLocaleEntity> findByBusinessDocId(Long id);

}
