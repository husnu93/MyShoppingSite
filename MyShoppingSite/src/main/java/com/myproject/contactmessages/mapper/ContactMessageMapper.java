package com.myproject.contactmessages.mapper;

import com.myproject.contactmessages.dto.ContactMessageRequest;
import com.myproject.contactmessages.dto.ContactMessageResponse;
import com.myproject.contactmessages.entity.ContactMessage;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ContactMessageMapper {
    //!!!!!
    public ContactMessage requestToContactMessage(ContactMessageRequest contactMessageRequest){

        return ContactMessage.builder()
                .contactName(contactMessageRequest.getName())
                .subject(contactMessageRequest.getSubject())
                .message(contactMessageRequest.getMessage())
                .contactEMail(contactMessageRequest.getEmail())
                .dateTime(LocalDateTime.now())
                .build() ;

    }
    // pojo ->> response
    public ContactMessageResponse contactMessageToResponse(ContactMessage contactMessage){



        return ContactMessageResponse.builder().
                name(contactMessage.getContactName())
                .subject(contactMessage.getMessage())
                .email(contactMessage.getContactEMail())
                .message(contactMessage.getMessage())
                .dateTime(LocalDateTime.now())
                .build();
    }



}
