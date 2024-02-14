package com.myproject.contactmessages.repository;

import com.myproject.contactmessages.dto.ContactMessageResponse;
import com.myproject.contactmessages.entity.ContactMessage;
import net.bytebuddy.jar.asm.commons.Remapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> {


    Page<ContactMessage> FindByEmailEquals(String email, Pageable pageable);


    Page<ContactMessage> FindBySubject(String subject, Pageable pageable);
}
