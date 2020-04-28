package com.skybooking.stakeholderservice.v1_0_0.io.repository.content.career;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.content.career.CareersEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface CareerRP extends JpaRepository<CareersEntity, Long> {

    List<CareersEntity> findByStatus(Integer status);


    Optional<CareersEntity> findByIdAndStatus(Long id, Integer status);

}
