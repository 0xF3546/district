package de.district.core.faction.domain;

import de.splatgames.generators.annotation.dto.Dto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

/**
 * Faction Rank Entity
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "faction_ranks")
public class FactionRank {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Dto(order = 1)
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Dto(order = 2)
    @Column(name = "rank", nullable = false)
    private int rank;

    @Dto(order = 3)
    @Column(name = "salary", nullable = false)
    private int salary;

    @ManyToOne
    @JoinColumn(name = "faction_id", nullable = false)
    private Faction faction;
}
