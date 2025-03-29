package de.district.core.faction.service;

import de.district.core.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author Mayson1337
 * @version 1.0.0
 * @since 1.0.0
 */
@Service
public class FactionService {

    @Autowired
    private UserRepository userRepository;

    public void setCharacterFaction(Long characterId, Long factionId) throws Exception {
        setCharacterFaction(characterId, factionId, 0);
    }

    public void setCharacterFaction(Long characterId, Long factionId, int grade) throws Exception {

    }

    public void setCharacterFactionGrade(Long characterId, int grade) throws Exception {

    }

    public void setCharacterLeaderPermission(Long characterId, boolean permission)  {

    }

    public boolean removeFactionBankBalance(Long factionId, int balance, String reason) {

        return true;
    }

    public void addFactionBankBalance(Long factionId, int balance, String reason) {

    }

    public void setFactionBankBalance(Long factionId, int balance) {

    }
}
