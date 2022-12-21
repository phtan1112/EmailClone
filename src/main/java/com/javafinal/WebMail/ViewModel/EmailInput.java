package com.javafinal.WebMail.ViewModel;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@ToString
@AllArgsConstructor
public class EmailInput {
    public static final String REPLY_MSG = "REPLY_MSG";
    public static final String NEW_MSG = "NEW_MSG";

    private String receiverEmail;
    private String title;
    private String body;
    private String archive;
}
