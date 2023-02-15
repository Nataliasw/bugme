package com.finalproject.bugme.mail;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Mail {
    private String recipient;
    private String subject;
    private String content;
}
