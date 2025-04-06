package de.district.core.character.service;

import de.district.api.DistrictAPI;
import de.district.api.entity.PlayerCharacter;
import de.district.api.entity.PluginPlayer;
import de.district.core.character.domain.Character;
import de.district.core.character.domain.dto.CharacterDto;
import de.district.core.character.repository.CharacterRepository;
import de.district.core.character.util.Gender;
import de.district.core.entity.CorePluginPlayer;
import de.district.core.user.domain.User;
import de.district.core.user.domain.dto.UserDto;
import de.district.core.user.repository.UserRepository;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class CharacterService {

    private final CharacterRepository characterRepository;

    private final UserRepository userRepository;

    public CharacterService(final CharacterRepository characterRepository,
                            final UserRepository userRepository) {
        this.characterRepository = characterRepository;
        this.userRepository = userRepository;
    }

    public void createCharacter(@NotNull final PluginPlayer player,
                                @NotNull final String firstName,
                                @NotNull final String lastName,
                                @NotNull final Gender gender,
                                @NotNull final LocalDateTime dateOfBirth) {
        Optional<User> user = userRepository.findByUuid(player.getUniqueId().toString())
                .or(() -> {
                    User newUser = new User(new UserDto(player.getUniqueId().toString(), System.currentTimeMillis(), "germany", false));
                    userRepository.save(newUser);
                    return Optional.of(newUser);
                });

        if (user.isEmpty()) {
            throw new IllegalStateException("User not found");
        }

        final CharacterDto characterDto = new CharacterDto(
                user.get(),
                firstName,
                lastName,
                gender,
                dateOfBirth,
                LocalDateTime.now()
        );

        final Character character = new Character(characterDto);

        characterRepository.save(character);
    }

    public @Nullable PlayerCharacter findCharacterByPlayer(@NotNull final CorePluginPlayer corePluginPlayer) {
        Optional<User> user = userRepository.findByUuid(corePluginPlayer.getUniqueId().toString());

        if (user.isEmpty()) {
            DistrictAPI.getLogger().warning("User not found for player " + corePluginPlayer.getName());
            return null;
        }

        Optional<Character> character = characterRepository.findByUser(user.get());

        if (character.isEmpty()) {
            DistrictAPI.getLogger().warning("Character not found for player " + corePluginPlayer.getName());
            return null;
        }

        return character.get();
    }
}
