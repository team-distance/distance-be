package io.festival.distance.domain.christmas.answer.presentation;

import io.festival.distance.domain.christmas.answer.dto.request.AnswerRequest;
import io.festival.distance.domain.christmas.answer.dto.request.AnswerUpdateRequest;
import io.festival.distance.domain.christmas.answer.dto.response.CurrentResponse;
import io.festival.distance.domain.christmas.answer.service.AnswerService;
import java.security.Principal;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@CrossOrigin
@RequestMapping("/api/answer")
@RequiredArgsConstructor
public class AnswerController {

    private final AnswerService answerService;

    /**
     * NOTE
     * 크리스마스 트리 내 질문 현황체크
     */
    @GetMapping("/{questionId}")
    public ResponseEntity<CurrentResponse> showQuestionInTree(@PathVariable Long questionId) {
        return ResponseEntity.ok(answerService.find(questionId));
    }

    /**
     * NOTE
     * 채팅방 내 질문 현황 체크
     */
    @GetMapping
    public ResponseEntity<CurrentResponse> showQuestionInRoom(
        @RequestParam(name = "chatRoomId") Long chatRoomId,
        @RequestParam(name = "tikiTakaCount") Long tikiTakaCount
    ){
        return ResponseEntity.ok(answerService.find(chatRoomId, tikiTakaCount));
    }

    /**
     * NOTE
     * 답변 등록 API
     */
    @PostMapping
    public ResponseEntity<Boolean> writeAnswer(
        @RequestBody AnswerRequest answerRequest,
        Principal principal
    ) {
        return ResponseEntity.ok(
            answerService.write(answerRequest, principal.getName())
        );
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
