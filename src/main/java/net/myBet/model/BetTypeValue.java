package net.myBet.model;

import lombok.*;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString(exclude = "userBetList")
public class BetTypeValue {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private double coefficient;

    private int makeCoefficientCount;

    @ManyToOne
    private BetType betType;

    @ManyToOne
    private Game game;

    @ManyToMany(mappedBy = "betList")
    private List<UserBet> userBetList;
}