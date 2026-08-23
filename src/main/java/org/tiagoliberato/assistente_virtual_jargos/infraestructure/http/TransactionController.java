package org.tiagoliberato.assistente_virtual_jargos.infraestructure.http;

import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.tomcat.autoconfigure.TomcatServerProperties;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tiagoliberato.assistente_virtual_jargos.application.ListTransactionByCategoryUseCase;
import org.tiagoliberato.assistente_virtual_jargos.application.PersistTransactionUseCase;
import org.tiagoliberato.assistente_virtual_jargos.domain.Category;
import org.tiagoliberato.assistente_virtual_jargos.domain.Transaction;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.http.request.TransactionRequest;

import java.util.List;

@RestController
@RequestMapping("/Transacoes")
public class TransactionController {

    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ListTransactionByCategoryUseCase listTransactionByCategoryUseCase;
    private final TranscriptionModel transcriptionModel;
    private final ChatClient chatClient;
    private final TextToSpeechModel textToSpeechModel;

    public TransactionController(
            ChatClient.Builder chatClienteBuilder,
            PersistTransactionUseCase persistTransactionUseCase,
            ListTransactionByCategoryUseCase listTransactionByCategoryUseCase,
            TranscriptionModel transcriptionModel,
            TextToSpeechModel textTospech) {

        this.persistTransactionUseCase = persistTransactionUseCase;
        this.listTransactionByCategoryUseCase = listTransactionByCategoryUseCase;
        this.transcriptionModel = transcriptionModel;

        this.chatClient = chatClienteBuilder
                .defaultTools(persistTransactionUseCase, listTransactionByCategoryUseCase)
                .build();

        this.textToSpeechModel = textTospech;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Transaction createTransaction(@RequestBody TransactionRequest request){
        var transaction = persistTransactionUseCase.execute(request.description(), request.amount(), request.category());
        return transaction;
    }

    @GetMapping("/{category}")
    public List<Transaction> findByAllCategory(@PathVariable Category category){
        return listTransactionByCategoryUseCase.execute(category.toString());

    }

    @PostMapping(value = "/ai", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Resource> transcribe(@RequestParam("file") MultipartFile file){
        var audioResource = file.getResource();
        var transcription = transcriptionModel.transcribe(audioResource);

        var response = chatClient.prompt().user(transcription).call().content();

        byte[] speechBytes = textToSpeechModel.call(response);
        Resource speechResource = new ByteArrayResource(speechBytes);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType("audio/mpeg"))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        ContentDisposition.attachment()
                                .filename("audio.mp3")
                                .build()
                                .toString())
                .body(speechResource);
    }
}
