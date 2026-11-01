package com.RAG_Imple.controller;

import com.RAG_Imple.helper.Helper;
import com.RAG_Imple.service.RagService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/rag")
public class RagController {
    private final RagService ragService;

    public RagController(RagService ragService) {
        this.ragService = ragService;
    }

    //load data from the helper list to the vector db
    @PostMapping("/load-data")
    public ResponseEntity<String> loadData() {
        try {
            ragService.saveData(Helper.getData());
            return ResponseEntity.ok("Documents saved to VectorStore successfully");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to save documents: " + e.getMessage());
        }
    }

    @GetMapping("/chat")
    public ResponseEntity<String> chat(@RequestParam(value = "q", required = true) String q ) {
        return ResponseEntity.ok(ragService.chatTemplate(q));
    }


    @GetMapping("/stream-chat")
    public ResponseEntity<Flux<String>> streamChat(@RequestParam("q") String query){
        return ResponseEntity.ok(this.ragService.streamChat(query));
    }


}
