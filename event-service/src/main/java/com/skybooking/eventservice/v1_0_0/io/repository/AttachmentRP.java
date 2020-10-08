package com.skybooking.eventservice.v1_0_0.io.repository;

import com.skybooking.eventservice.v1_0_0.io.entity.AttachmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AttachmentRP extends JpaRepository<AttachmentEntity, Long> {
}
