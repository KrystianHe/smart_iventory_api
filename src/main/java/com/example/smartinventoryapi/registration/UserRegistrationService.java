package com.example.smartinventoryapi.registration;

import com.example.smartinventoryapi.services.SendEmailService;
import com.example.smartinventoryapi.utils.EmailCodeGenerator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserRegistrationService {

    @Autowired
    private SendEmailService sendEmailService;

    public void sendVerificationEmail(String userEmail){

        EmailCodeGenerator.VerificationCode verificationCode = EmailCodeGenerator.generateCode();

        sendEmailService.sendVerificationCode(userEmail,verificationCode.getCode());

        System.out.println("Wysłano kod weryfikacyjny: " + verificationCode.getCode());
        System.out.println("Kod wygasa: " + verificationCode.getExpirationTime());


    }
}
