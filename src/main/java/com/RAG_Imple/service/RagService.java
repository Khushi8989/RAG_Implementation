package com.RAG_Imple.service;

import reactor.core.publisher.Flux;

import java.util.List;

public interface RagService {
    String chatTemplate(String query);

    Flux<String> streamChat(String query);

    void saveData(List<String> list);
}
