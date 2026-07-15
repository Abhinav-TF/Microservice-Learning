package com.tnf.common_dto.dto;

import java.time.LocalDateTime;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MessageDto {
    @NotBlank(message = "Sender can not be empty")
    private String sender;

    @NotBlank(message = "Receiver can not be empty")
    private String receiver;

    @NotBlank(message = "Message can not be empty")
    private String message;

    @NotBlank(message = "subject can not be empty")
    private String subject;

    private LocalDateTime timestamp;

}
