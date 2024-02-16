package com.myproject.contactmessages.service;

import com.myproject.contactmessages.dto.ContactMessageRequest;
import com.myproject.contactmessages.dto.ContactMessageResponse;
import com.myproject.contactmessages.entity.ContactMessage;
import com.myproject.contactmessages.mapper.ContactMessageMapper;
import com.myproject.contactmessages.messages.Messages;
import com.myproject.contactmessages.repository.ContactMessageRepository;
import com.myproject.exception.ConflictException;
import com.myproject.exception.ResourceNotFoundException;
import com.myproject.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContactMessageService {


    private final ContactMessageRepository contactMessageRepository;
    private final ContactMessageMapper contactMessageMapper;
    public ResponseMessage<ContactMessageResponse> save(ContactMessageRequest contactMessageRequest) {

          //  contactMessageRepository.save(contactMessageRequest);//içerisinde eksik datalar var bunu repo ile kaydedemiyorum burada tür dönüşümü yapmam lazım
            // sürekli tür dönüşümü yapılacak burası şişecek bu gibi kısımları yardımcı bir sınıf üzerinden package ekliyoruz.

       ContactMessage contactMessage =  contactMessageMapper.requestToContactMessage(contactMessageRequest);
          ContactMessage savedData =   contactMessageRepository.save(contactMessage); // saved datada yukarıdaki contactmessageden farklı olarak id de var

        return ResponseMessage.<ContactMessageResponse> builder()
                .message("Contact message Created Successfully")
                .httpStatus(HttpStatus.CREATED)
                .object(contactMessageMapper.contactMessageToResponse(savedData)).build();
    }

    public Page<ContactMessageResponse> getAll(int page, int size, String sort, String type) {
       Pageable pageable =  PageRequest.of(page,size, Sort.by(sort).ascending());

       if(Objects.equals(type,"desc")){
         pageable =   PageRequest.of(page,size, Sort.by(sort).descending());
       }

       return contactMessageRepository.findAll(pageable).map(contactMessageMapper::contactMessageToResponse);

    }

    public Page<ContactMessageResponse> searchByEmail(String email, int page, int size, String sort, String type) {
        Pageable pageable =  PageRequest.of(page,size, Sort.by(sort).ascending());

        if(Objects.equals(type,"desc")){
            pageable =   PageRequest.of(page,size, Sort.by(sort).descending());
        }
        return contactMessageRepository.findByEmailEquals(email , pageable)
                .map(contactMessageMapper::contactMessageToResponse);
    }


    public Page<ContactMessageResponse> searchBySubject(String subject, int page, int size, String sort, String type) {

        Pageable pageable = PageRequest.of(page, size, Sort.by(sort).ascending());
        if (Objects.equals(type, "desc")) {
            pageable = PageRequest.of(page, size, Sort.by(sort).descending());
        }
        return contactMessageRepository.findBySubject(subject, pageable). // Derived
                map(contactMessageMapper::contactMessageToResponse);
    }
    public String deleteById(Long contactMessageId) {
            getContactMessageById(contactMessageId);
            contactMessageRepository.deleteById(contactMessageId);
                return Messages.CONTACT_MESSAGE_DELETED_SUCCESSFULLY;
    }


    public ContactMessage getContactMessageById(Long id){
        return contactMessageRepository.findById(id).orElseThrow(()->
                new ResourceNotFoundException(Messages.NOT_FOUND_MESSAGE));
    }

    public List<ContactMessage> searchBeetwenDates(String beginDateString, String endDateString) {

        try {
            LocalDate beginDate =    LocalDate.parse(beginDateString);
            LocalDate endDate =    LocalDate.parse(endDateString);
            return contactMessageRepository.findMessagesBetweenDates(beginDate,endDate);
        } catch (DateTimeParseException e) {
            throw new ConflictException(Messages.WRONG_DATE_MESSAGE);
        }

    }
}
