package net.myBet.form;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@EqualsAndHashCode(exclude = {"text"})
public class MailMessageForm {

    private String subject;

    private String text;

    private String to;
}