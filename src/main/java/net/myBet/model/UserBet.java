package net.myBet.model;

import lombok.*;
import net.myBet.model.enums.UserBetStatus;

import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "betList")
public class UserBet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double awardPrise;

    @Enumerated(EnumType.STRING)
    private UserBetStatus userBetStatus;

    @ManyToMany
    @JoinTable(name="game_bet",joinColumns = @JoinColumn(name = "user_bet_id"),
    inverseJoinColumns = @JoinColumn(name = "bet_type_value_id"))
    private List<BetTypeValue> betList;
}