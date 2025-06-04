package com.ust.FMCG.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;

@Document(collection = "otps")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OTP {

    @Id
    private String mobile; // OTP sent to this number

    private String otpCode;

    private LocalDateTime generatedAt;

    private boolean isUsed;

    // Constructors, getters, setters
}
