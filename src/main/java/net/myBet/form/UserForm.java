package net.myBet.form;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.Length;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserForm {

    @Length(min = 2,max = 30)
    private String name;

    @Length(min = 2,max = 40)
    private String surname;

    @Length(min = 10)
    private String email;

    @Length(min=7,max = 100)
    private String passwordCode;

    @Length(min = 2,max = 20)
    private String password;

    @Length(min = 2,max = 20)
    private String rePassword;
}