package com.skybooking.skyhotelservice.v1_0_0.io.repository.attachment;

import com.skybooking.skyhotelservice.v1_0_0.io.entity.attachment.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AttachmentRP extends JpaRepository<AttachmentEntity, Long> {
    @Query(value = "SELECT * FROM attachment WHERE reference = ?1 AND type = ?2 AND lang_code = ?3 LIMIT 1", nativeQuery = true)
    Optional<AttachmentEntity> findByReferenceAndTypeAndLangCode(String reference, String type, String local);
}
