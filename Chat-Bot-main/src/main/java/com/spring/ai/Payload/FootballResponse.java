package com.spring.ai.Payload;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class FootballResponse {

    private  String content;
    public String getContent(){
        return  content;
    }
    public void setContent(String content){
        this.content=content;
    }

}
