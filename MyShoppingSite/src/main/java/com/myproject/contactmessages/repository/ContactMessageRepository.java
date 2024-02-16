package com.myproject.contactmessages.repository;

import com.myproject.contactmessages.dto.ContactMessageResponse;
import com.myproject.contactmessages.entity.ContactMessage;
import net.bytebuddy.jar.asm.commons.Remapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ContactMessageRepository extends JpaRepository<ContactMessage,Long> {


    Page<ContactMessage> findByEmailEquals(String email, Pageable pageable);


    Page<ContactMessage> findBySubject(String subject, Pageable pageable);

    @Query("SELECT c FROM ContactMessage c WHERE FUNCTION('DATE' , c.dateTime) BETWEEN ?1 AND ?2")
    List<ContactMessage> findMessagesBetweenDates(LocalDate beginDate, LocalDate endDate);
}
