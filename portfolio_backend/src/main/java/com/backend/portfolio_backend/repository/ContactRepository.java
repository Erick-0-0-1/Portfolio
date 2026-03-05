package com.backend.portfolio_backend.repository;

import com.backend.portfolio_backend.model.ContactMessage;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContactRepository extends JpaRepository<ContactMessage, Long> {

    // Returns all messages sorted newest first
    List<ContactMessage> findAllByOrderBySentAtDesc();
}