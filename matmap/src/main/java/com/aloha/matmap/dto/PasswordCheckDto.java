package com.aloha.matmap.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

// 비밀번호 확인 폼
@Data 
public class PasswordCheckDto {
    
    @NotBlank (message = "현재 비밀번호를 입력하세요.")
    private String password;
}
