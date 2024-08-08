package com.apress.prospring6.thirteen;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;

@RestController
@Slf4j
public class LetterSenderController {

    private final RestTemplate webClient;
    private final String correspondentAddress;
    private final String sender;

    public LetterSenderController(RestTemplate webClient,
                                  @Value("#{senderApplication.correspondentAddress}") String correspondentAddress,
                                  @Value("#{senderApplication.sender}")String sender) {
        this.webClient = webClient;
        this.correspondentAddress = correspondentAddress;
        this.sender = sender;
    }

    @PostMapping(path = "send",consumes = MediaType.APPLICATION_JSON_VALUE)
    public void setSender(@RequestBody Letter letter){
        letter.setSender(sender);
        letter.setSentOn(LocalDate.now());
        var request = new HttpEntity<>(letter);
        webClient.exchange(correspondentAddress + "/letters", HttpMethod.POST,request,Letter.class);
    }

    @GetMapping(path = "misc",produces = MediaType.APPLICATION_JSON_VALUE)
    public String getMiscData(){
        var response = webClient.getForObject("https://jsonplaceholder.typeicode.com/users",String.class);
        log.info("Random info from non-java application: {}",response);
        return response;
    }
}
