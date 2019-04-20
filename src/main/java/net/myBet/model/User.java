package net.myBet.model;

import lombok.*;
import net.myBet.model.enums.UserStatus;
import net.myBet.model.enums.UserType;

import javax.persistence.*;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String name;

    private String surname;

    private String email;

    private String password;

    private String passwordCode;

    private String token;

    @Enumerated(EnumType.STRING)
    private UserStatus userStatus;

    @Enumerated(EnumType.STRING)
    private UserType userType;

    private double account;
}