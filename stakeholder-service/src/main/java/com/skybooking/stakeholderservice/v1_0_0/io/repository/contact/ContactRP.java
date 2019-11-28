package com.skybooking.stakeholderservice.v1_0_0.io.repository.contact;

import com.skybooking.stakeholderservice.v1_0_0.io.enitity.contact.ContactEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import javax.transaction.Transactional;
import java.util.List;

public interface ContactRP extends JpaRepository<ContactEntity, Long> {


    /**
     * Save contacts
     */
    @Modifying
    @Transactional
    @Query(value = "INSERT INTO contacts (contactable_id, value, type, contactable_type, priority) VALUES (?1, ?2, ?3, ?4, 0)", nativeQuery = true)
    void createContact(Long id, String value, String type, String skyType);


    /**
     * Get contacts of company
     */
    @Query(value = "SELECT * FROM contacts WHERE contactable_id  = ?1", nativeQuery = true)
    List<ContactEntity> getContactCM(Long stkId);


    /**contactable_id
     * Check contacts exists
     */
    @Query(value = "SELECT CASE WHEN COUNT(value) > 0 THEN 'true' ELSE 'false' END FROM contacts WHERE type IN ('p', 'e') AND value = ?1 AND (CASE WHEN ?2 THEN contactable_id NOT IN (?2) ELSE contactable_id IS NOT NULL END)", nativeQuery = true)
    String existsContact(String value, Long id);


}
