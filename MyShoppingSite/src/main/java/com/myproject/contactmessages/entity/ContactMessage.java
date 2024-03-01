package com.myproject.contactmessages.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)

@Entity
public class ContactMessage implements Serializable {

    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long contactId;     // burada hoca id yaptı ama best practice buymuş //

    @NotNull
    private String contactName;// arkadaki kodlar değişecek findByid ve name değişti yaparken hata alırsın
                                    // !!!!! Dikkat et

    @NotNull
    private String contactEMail;


    @NotNull
    private String subject;

    @NotNull
    private  String message;


    @JsonFormat(shape = JsonFormat.Shape.STRING,pattern = "yyyy/MM/dd HH:mm",timezone = "US")
    private LocalDateTime dateTime;


}
