package de.district.core.faction.domain;

import de.splatgames.generators.annotation.dto.Dto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.bukkit.Color;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
@Table(name = "factions")
public class Faction {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;

    @Dto(order = 1)
    @Column(name = "name", unique = true, nullable = false)
    private String name;

    @Dto(order = 2)
    @Column(name = "fullName", unique = true, nullable = false)
    private String fullName;

    @Dto(order = 3)
    @Column(name = "primaryColor", nullable = false)
    private Color primaryColor;

    @Dto(order = 4)
    @Column(name = "secondaryColor", nullable = false)
    private Color secondaryColor;

    @Dto(order = 5)
    @Column(name = "bank", nullable = false)
    private int bank;

    @Dto(order = 6)
    @Column(name = "maxMember", nullable = false)
    private int maxMember;

    @Dto(order = 7)
    @Column(name = "motd", nullable = false)
    private String motd;

    @Dto(order = 8)
    @Column(name = "description", nullable = false)
    private String description;

    @Dto(order = 9)
    @Column(name = "imageURL", nullable = false)
    private String imageURL;

    @Dto(order = 10)
    @Column(name = "isPermittedForGangwar")
    private boolean isPermittedForGangwar;

    @Dto(order = 11)
    @Column(name = "isPermittedForLaboratory")
    private boolean isPermittedForLaboratory;

    @Dto(order = 12)
    @Column(name = "isPermittedForBlacklist")
    private boolean isPermittedForBlacklist;
}
