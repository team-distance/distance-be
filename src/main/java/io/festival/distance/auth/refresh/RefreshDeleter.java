package io.festival.distance.auth.refresh;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class RefreshDeleter {
    private final RefreshRepository refreshRepository;
    @Transactional
    public void deleteRefreshToken(String telNum){
        refreshRepository.deleteBySubject(telNum);
    }
}
