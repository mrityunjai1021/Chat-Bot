package com.spring.ai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.spring.ai.Payload.FootballResponse;
import com.spring.ai.Service.ChatService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.Map;

@SpringBootTest
class SpringBootAiApplicationTests {

    @Autowired
    private ChatService chatService;

	@Test
	void testResponse() throws JsonProcessingException {
        FootballResponse footballResponse = chatService.generateFootballResponse("Who is Messi?");
        System.out.println(footballResponse.getContent());
	}

    @Test
    void testTemplate() throws IOException {
       String s = chatService.loadPromptTemplate("prompts/cricket_bot.text");
       String prompt = chatService.putValuesInPromptTemplate(s, Map.of("inputText","what is football"));
       System.out.println(s);
    }

}
