package egovframework.ragchat.service;

import dev.langchain4j.service.TokenStream;
import egovframework.ragchat.dto.ChatRequest;
import reactor.core.publisher.Flux;

public interface ChatService {

     /**
      * RAG를 사용하여 사용자 질의에 대한 응답을 생성.
      * 
      * @param chatRequest 사용자 질의 요청
      * @return AI 모델의 응답
      */
     public String generateRagResponse(ChatRequest chatRequest);

     /**
      * RAG 없이 일반 채팅 응답을 생성.
      * 
      * @param chatRequest 사용자 질의 요청
      * @return AI 모델의 응답
      */
     public String generateSimpleResponse(ChatRequest chatRequest);

     /**
      * RAG를 사용하여 사용자 질의에 대한 스트리밍 응답을 생성.
      * 
      * @param chatRequest 사용자 질의 요청
      * @return AI 모델의 응답
      */
     public Flux<String> generateStreamingRagResponse(ChatRequest chatRequest);

     /**
      * RAG 없이 일반 채팅 스트리밍 응답을 생성.
      * 
      * @param chatRequest 사용자 질의 요청
      * @return AI 모델의 응답
      */
     public Flux<String> generateStreamingSimpleResponse(ChatRequest chatRequest);
}
