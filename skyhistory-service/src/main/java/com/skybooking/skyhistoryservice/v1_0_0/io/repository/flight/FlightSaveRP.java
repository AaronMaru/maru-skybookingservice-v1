package com.skybooking.skyhistoryservice.v1_0_0.io.repository.flight;

import com.skybooking.skyhistoryservice.v1_0_0.io.enitity.flight.FlightSaveEntity;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface FlightSaveRP extends JpaRepository<FlightSaveEntity, Long> {


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Query to get flight save list by user's id and keyword
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param userId
     * @param keyword
     * @param pageable
     * @return page result
     */
    @Query(value = "SELECT * FROM flight_saves WHERE user_id = :userId AND (class_name LIKE CONCAT('%',:keyword,'%') OR class_code LIKE CONCAT('%',:keyword,'%'))",
            countQuery = "SELECT COUNT(*) FROM flight_saves WHERE user_id = :userId AND (class_name LIKE CONCAT('%',:keyword,'%') OR class_code LIKE CONCAT('%',:keyword,'%'))",
            nativeQuery = true)
    Page<FlightSaveEntity> findAllByUserAndKeyword(long userId, String keyword, Pageable pageable);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Query to get flight save list by user's id, keyword and flight save's id list
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param userId
     * @param keyword
     * @param ids
     * @param pageable
     * @return page result
     */
    @Query(value = "SELECT * FROM flight_saves WHERE user_id = :userId AND ( id IN (:ids) OR class_name LIKE CONCAT('%',:keyword,'%') OR class_code LIKE CONCAT('%',:keyword,'%'))",
            countQuery = "SELECT COUNT(*) FROM flight_saves WHERE user_id = :userId AND ( id IN (:ids) OR class_name LIKE CONCAT('%',:keyword,'%') OR class_code LIKE CONCAT('%',:keyword,'%'))",
            nativeQuery = true)
    Page<FlightSaveEntity> findAllByUserAndKeywordInIds(long userId, String keyword, List<Long> ids, Pageable pageable);


    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Query to get single flight save by id and user's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param userId
     * @return entity
     */
    FlightSaveEntity findFirstByIdAndUserId(long id, Integer userId);
    FlightSaveEntity findFirstByIdAndUserIdAndCompanyId(long id, Integer userId, Integer companyId);
    FlightSaveEntity findFirstByIdAndCompanyId(long id, Integer companyId);

    /**
     * -----------------------------------------------------------------------------------------------------------------
     * Query to delete flight save by id and user's id
     * -----------------------------------------------------------------------------------------------------------------
     *
     * @param id
     * @param userId
     */
    @Transactional
    void deleteByIdAndUserId(Long id, int userId);

}

