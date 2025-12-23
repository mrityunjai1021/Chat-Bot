package com.spring.ai.Controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.ai.Payload.FootballResponse;
import com.spring.ai.Service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;
import java.util.stream.Stream;

@Controller
@RequestMapping("/api/v1/chat")
public class ChatController {

    @Autowired
    private ChatService chatService;

    @GetMapping
    public ResponseEntity<String>generateResponse(
                @RequestParam(value = "inputText") String inputText){
           String responseText = chatService.generateResponse(inputText);
           return ResponseEntity.ok(responseText);
        }

    @GetMapping("/stream")
    public Flux<String> streamResponse(
            @RequestParam(value = "inputText") String inputText){
        Flux<String> response = chatService.streamResponse(inputText);
        return response;
    }

    @GetMapping("/football-bot")
    public ResponseEntity <FootballResponse> getFootballResponse(
            @RequestParam("inputText") String inputText
    ) throws JsonProcessingException{
        FootballResponse footballResponse = chatService.generateFootballResponse(inputText);
        return ResponseEntity.ok(footballResponse);
    }

    @GetMapping("/images")
    public ResponseEntity<List<String>> generateImages(
            @RequestParam("imageDescription") String imageDesc,
            @RequestParam(value = "numberOfImages",required = false,defaultValue = "2") int numbers
    ) throws IOException {
        return ResponseEntity.ok(chatService.generateImages(imageDesc,numbers));
    }
}
