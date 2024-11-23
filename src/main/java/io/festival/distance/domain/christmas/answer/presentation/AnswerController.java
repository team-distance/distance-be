package io.festival.distance.domain.christmas.answer.presentation;

import io.festival.distance.domain.christmas.answer.dto.request.AnswerRequest;
import io.festival.distance.domain.christmas.answer.dto.request.AnswerUpdateRequest;
import io.festival.distance.domain.christmas.answer.service.AnswerService;
import io.festival.distance.domain.christmas.answer.dto.response.CurrentResponse;
import java.security.Principal;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    /**
     * NOTE
     * 질문 현황체크
     */
    @GetMapping("/{questionId}")
    public ResponseEntity<List<CurrentResponse>> showQuestionStatus(@PathVariable Long questionId) {
        return ResponseEntity.ok(answerService.find(questionId));
    }

    /**
     * NOTE
     * 답변 등록 API
     */
    @PostMapping
    public ResponseEntity<Void> writeAnswer(@RequestBody AnswerRequest answerRequest,
        Principal principal) {
        answerService.write(answerRequest, principal.getName());
        return ResponseEntity.ok().build();
    }

    /**
     * NOTE
     * 답변 수정 API
     */
    @PatchMapping("/{answerId}")
    public ResponseEntity<Void> modifyAnswer(@PathVariable Long answerId,
        @RequestBody AnswerUpdateRequest answerUpdateRequest, Principal principal) {
        answerService.update(
            answerUpdateRequest.answer(),
            principal.getName(),
            answerId
        );
        return ResponseEntity.ok().build();
    }
}
