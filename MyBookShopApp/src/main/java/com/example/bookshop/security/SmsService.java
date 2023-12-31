package com.example.bookshop.security;

import com.example.bookshop.data.entities.SmsCodeEntity;
import com.example.bookshop.repositories.SmsCodeRepository;
import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;

@Service
@RequiredArgsConstructor
public class SmsService {

    private final SmsCodeRepository smsCodeRepository;

    @Value("${twilio.ACCOUNT_SID}")
    private String accountSID;

    @Value("${twilio.AUTH_TOKEN}")
    private String authToken;

    @Value("${twilio.TWILIO_NUMBER}")
    private String tilioNumber;

    public String sendSecretCodeSms(String aContact) {
        Twilio.init(accountSID, authToken);
        String formatted = aContact.replaceAll("[()-]", "");
        String generatedCode = generateCode();
        Message.creator(
                        new PhoneNumber(formatted),
                        new PhoneNumber(tilioNumber),
                        "Your secret code it: " + generatedCode
                )
                .create();
        return generatedCode;
    }

    public String generateCode() {
        //nnn nnn - pattern
        StringBuilder sb = new StringBuilder();
        while (sb.length() < 6) {
            sb.append(new SecureRandom().nextInt(9));
        }
        sb.insert(3, " ");
        return sb.toString();
    }

    public void saveNewCode(SmsCodeEntity aSmsCode) {
        if (smsCodeRepository.findByCode(aSmsCode.getCode()) == null) {
            smsCodeRepository.save(aSmsCode);
        }
    }

    public boolean verifyCode(String code) {
        SmsCodeEntity smsCode = smsCodeRepository.findByCode(code);
        return (smsCode != null && !smsCode.isExpired());
    }

}
