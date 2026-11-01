
package com.RAG_Imple.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SafeGuardAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.memory.InMemoryChatMemoryRepository;
import org.springframework.ai.chat.memory.MessageWindowChatMemory;
import org.springframework.ai.google.genai.GoogleGenAiChatOptions;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class AiConfig {

    private final Logger logger = LoggerFactory.getLogger(AiConfig.class);

    @Bean
    public ChatMemory chatMemory() {
        InMemoryChatMemoryRepository repository = new InMemoryChatMemoryRepository();

        return MessageWindowChatMemory.builder()
                .maxMessages(10)
                .chatMemoryRepository(repository)
                .build();
    }

    @Bean
    public ChatClient chatClient(ChatClient.Builder builder, ChatMemory chatMemory) {

        logger.info("ChatMemoryImplementation class: {}", chatMemory.getClass().getName());

        MessageChatMemoryAdvisor messageChatMemoryAdvisor = MessageChatMemoryAdvisor.builder(chatMemory).build();

        return builder
                .defaultAdvisors(messageChatMemoryAdvisor, new SimpleLoggerAdvisor(), new SafeGuardAdvisor(List.of("games")))
                // .defaultSystem("You are a helpful coding assistant.")
                .build();
    }
}
