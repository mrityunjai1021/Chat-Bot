package com.spring.ai.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.spring.ai.Payload.FootballResponse;
import org.apache.tomcat.util.bcel.classfile.ClassParser;
import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.model.StreamingChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.image.*;
import org.springframework.ai.openai.OpenAiImageModel;
import org.springframework.ai.openai.OpenAiImageOptions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ChatService {

    @Autowired
    private ChatModel chatModel;

    @Autowired
    private ImageModel imageModel;

    @Autowired
    private StreamingChatModel streamingChatModel;


    public String generateResponse(String inputText) {
        String response=chatModel.call(inputText);
        return response;
    }

    public Flux<String> streamResponse(String inputText){
        Flux<String> response=chatModel.stream(inputText);
        return response;
    }

    public FootballResponse generateFootballResponse(String inputText) throws JsonProcessingException {
        String promptString="";
        ChatResponse footballResponse = chatModel.call(
               new Prompt(promptString)
       );

        String responseString = footballResponse.getResult().getOutput().getText();
        ObjectMapper mapper=new ObjectMapper();
        FootballResponse footballResponse1 = mapper.readValue(responseString,FootballResponse.class);
        return footballResponse1;
    }

    public List<String> generateImages(String imageDesc, int numbers) throws IOException{
        String template = this.loadPromptTemplate("prompts/image_bot.txt");
        String promptString = this.putValuesInPromptTemplate(template,Map.of(
                "numberOfImages",numbers+"",
                "description",imageDesc
        ));

        ImageResponse imageResponse = imageModel.call(new ImagePrompt(
                promptString, OpenAiImageOptions.builder()
                .model("dall-e-3")
                .N(1)
                .height(512)
                .width(512)
                .quality("standard")
                .build()));
        List<String> imageUrls = imageResponse.getResults().stream().map(generation -> generation.getOutput().getUrl()).collect(Collectors.toList());
        return imageUrls;
    }

    public String loadPromptTemplate(String fileName) throws IOException {
        Path filePath = new ClassPathResource(fileName).getFile().toPath();
        return Files.readString(filePath);
    }

    public String putValuesInPromptTemplate(String template, Map<String,String> variables){
        for(Map.Entry<String,String>entry:variables.entrySet()){
            template=template.replace("{"+entry.getKey()+"}",entry.getValue());
        }
        return template;
    }
}
