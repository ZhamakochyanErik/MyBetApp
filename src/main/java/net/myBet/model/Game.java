package net.myBet.model;

import lombok.*;
import net.myBet.model.enums.GameStatus;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String p1Name;

    private String p2Name;

    private String p1ImgUrl;

    private String p2ImgUrl;

    @Enumerated(EnumType.STRING)
    private GameStatus gameStatus;

    private String score;

    @Temporal(TemporalType.TIMESTAMP)
    private Date startDate;

    @ManyToOne
    private ChampionShip championShip;
}
