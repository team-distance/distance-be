package io.festival.distance.auth.dto;

import javax.annotation.Nullable;
import lombok.*;

import javax.validation.constraints.NotNull;


@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LoginDto {
    @NotNull
    private String telNum;
    @NotNull
    private String password;
    @Nullable
    private String clientToken;
}
