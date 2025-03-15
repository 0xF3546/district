package de.district.api.util;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
@Getter
@AllArgsConstructor
public enum Prefix {
    DEFAULT("§7"),
    INFO("§7[§bINFO§7] "),
    ERROR("§7[§cERROR§7] "),
    SUCCESS("§7[§aSUCCESS§7] "),
    WARNING("§7[§eWARNING§7] "),
    TICKET("§7[§bTICKET§7] ");

    private final String prefix;
}
