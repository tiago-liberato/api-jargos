package org.tiagoliberato.assistente_virtual_jargos.infraestructure.http;

import org.springframework.ai.audio.transcription.TranscriptionModel;
import org.springframework.ai.audio.tts.TextToSpeechModel;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.tiagoliberato.assistente_virtual_jargos.application.transactions.dto.TransactionQuery;
import org.tiagoliberato.assistente_virtual_jargos.application.transactions.dto.TransactionResponse;
import org.tiagoliberato.assistente_virtual_jargos.application.transactions.usecase.ConsultTransactions;
import org.tiagoliberato.assistente_virtual_jargos.application.transactions.usecase.PersistTransactionUseCase;
import org.tiagoliberato.assistente_virtual_jargos.infraestructure.http.request.TransactionRequest;

import java.util.List;

@RestController
@RequestMapping("/Transacoes")
public class TransactionController {

    private final PersistTransactionUseCase persistTransactionUseCase;
    private final ConsultTransactions consultTransactions;
    private final TranscriptionModel transcriptionModel;
    private final ChatClient chatClientQuery;
    private final ChatClient chatClientAssistant;
    private final TextToSpeechModel textToSpeechModel;

    public TransactionController(
            ChatClient.Builder chatClienteQueryBuilder,
            ChatClient.Builder chatClientAssistantBuilder,
            PersistTransactionUseCase persistTransactionUseCase,
            ConsultTransactions consultTransactions,
            TranscriptionModel transcriptionModel,
            TextToSpeechModel textTospech,
            @Value("classpath:prompt/querySystemPrompt.st") Resource querySystemPrompt,
            @Value("classpath:prompt/assistantSystemPrompt.st") Resource assistantSystemPrompt) {

        this.persistTransactionUseCase = persistTransactionUseCase;
        this.consultTransactions = consultTransactions;
        this.transcriptionModel = transcriptionModel;

        this.chatClientQuery = chatClienteQueryBuilder
                .defaultSystem(querySystemPrompt)
                .build();

        this.chatClientAssistant = chatClientAssistantBuilder
                .defaultSystem(assistantSystemPrompt)
                .defaultTools(consultTransactions, persistTransactionUseCase)
                .build();

        this.textToSpeechModel = textTospech;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionResponse createTransaction(@RequestBody TransactionRequest request){
        var transaction = persistTransactionUseCase.execute(request.description(), request.amount(), request.category());
        return transaction;
    }


    @PostMapping(value = "/assistant", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<Resource> transcribe(@RequestParam("file") MultipartFile file){
        var audioResource = file.getResource();
        var transcription = transcriptionModel.transcribe(audioResource);

        var response = chatClientAssistant.prompt().user(transcription).call().content();

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

    @PostMapping(value = "/query", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    ResponseEntity<List<TransactionResponse>> consult(@RequestParam("file") MultipartFile file){
        var audioResource = file.getResource();
        var transcription = transcriptionModel.transcribe(audioResource);

        TransactionQuery query = chatClientQuery.prompt()
                .user(transcription)
                .call()
                .entity(TransactionQuery.class);

        List<TransactionResponse> transactions = consultTransactions.executeQuery(query);

        return ResponseEntity.ok(transactions);



    }
}
