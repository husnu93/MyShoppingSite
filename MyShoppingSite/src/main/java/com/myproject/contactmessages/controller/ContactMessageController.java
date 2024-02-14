package com.myproject.contactmessages.controller;

import com.myproject.contactmessages.dto.ContactMessageRequest;
import com.myproject.contactmessages.dto.ContactMessageResponse;
import com.myproject.contactmessages.entity.ContactMessage;
import com.myproject.contactmessages.service.ContactMessageService;
import com.myproject.payload.response.business.ResponseMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;



@RestController
@RequestMapping("/contactMessages")
@RequiredArgsConstructor
public class ContactMessageController {

    private final ContactMessageService contactMessageService;

    @PostMapping("/save") // http://localhost:8084/contactMessages/save + POST + JSON
     public ResponseMessage<ContactMessageResponse> save(@Valid @RequestBody ContactMessageRequest contactMessageRequest){

            return contactMessageService.save(contactMessageRequest);

       }


       @GetMapping("/getAll") //http://localhost:8084/contactMessages/getAll + GET
       public Page<ContactMessageResponse> getAll(
               @RequestParam(value = "page",defaultValue = "0") int page,
               @RequestParam(value = "size",defaultValue = "10") int size ,
               @RequestParam(value = "sort",defaultValue = "dateTime") String sort,
               @RequestParam(value = "type",defaultValue = "desc") String type
       ){

            return contactMessageService.getAll(page,size,sort,type);

       }


       @GetMapping("/searchByEmail")  //http://localhost:8084/contactMessages/searchByEmail + GET
       public Page<ContactMessageResponse> searchByEmail(
                    @RequestParam(value = "email") String email,
                    @RequestParam(value = "page",defaultValue = "0") int page,
                    @RequestParam(value = "size",defaultValue = "10") int size ,
                    @RequestParam(value = "sort",defaultValue = "dateTime") String sort,
                    @RequestParam(value = "type",defaultValue = "desc") String type
            ){

            return contactMessageService.searchByEmail(email,page,size,sort,type);

    }
    // bunu ödev verdi asıl burada yaptığım hali burada kalacak diğer tarafta hocadan aldığımı yapıştıracağım kendi yazdığım


    @GetMapping("/searchBySubject")  //http://localhost:8084/contactMessages/searchBySubject + GET
    public Page<ContactMessageResponse> searchBySubject(
            @RequestParam(value = "subject") String subject,
            @RequestParam(value = "page",defaultValue = "0") int page,
            @RequestParam(value = "size",defaultValue = "10") int size ,
            @RequestParam(value = "sort",defaultValue = "dateTime") String sort,
            @RequestParam(value = "type",defaultValue = "desc") String type
    ){

        return contactMessageService.searchBySubject(subject,page,size,sort,type);

    }

            @DeleteMapping("/deleteById/{contactMessageId}") //http://localhost:8084/contactMessages/deleteById/2
            public ResponseEntity<String> deleteByIdPath(@PathVariable Long contactMessageId){
            return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId));
            }

                     //not: deleteByIdParam

                @DeleteMapping("/deleteById") //http://localhost:8084/contactMessages/deleteById?contactMessageId=2
                public ResponseEntity<String> deleteByIdParam(@RequestParam Long contactMessageId){
                return ResponseEntity.ok(contactMessageService.deleteById(contactMessageId));
    }


    }
