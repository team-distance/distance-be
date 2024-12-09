package io.festival.distance.domain.christmas.question.presentation;

import io.festival.distance.domain.christmas.question.dto.request.QuestionRequest;
import io.festival.distance.domain.christmas.question.dto.response.QuestionResponse;
import io.festival.distance.domain.christmas.question.service.QuestionService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/question")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    /**
     * NOTE
     * 질문 생성 API
     */
    @PostMapping
    public ResponseEntity<QuestionResponse> generateQuestion(
        @RequestBody QuestionRequest questionRequest
    ){
        return ResponseEntity.ok(questionService.create(questionRequest));
    }

    /**
     * NOTE
     * 질문 List API
     */
    @GetMapping("/{chatRoomId}")
    public ResponseEntity<List<QuestionResponse>> getQuestions(
        @PathVariable Long chatRoomId
    ){
        return ResponseEntity.ok(questionService.findAll(chatRoomId));
    }
}
