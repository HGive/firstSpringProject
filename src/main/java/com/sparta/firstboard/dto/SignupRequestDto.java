package com.sparta.firstboard.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SignupRequestDto {
    @Pattern(regexp="^[a-z0-9]+$")
    @NotBlank
    @Size(min=4, max=10)
    private String username;

    @Pattern(regexp="^[a-zA-Z0-9]+$")
    @NotBlank
    @Size(min=8, max=15)
    private String password;

}