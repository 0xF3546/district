package de.district.api.economy.bank;

import de.district.api.experimental.random.BitRandomSource;
import de.district.api.experimental.random.SingleThreadedRandomSource;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Locale;
import java.util.UUID;

/**
 * <p>
 * The {@code Iban} class represents an International Bank Account Number (IBAN) that is unique to each player.
 * This class provides functionality for generating and handling IBANs based on specific criteria.
 * It is also associated with a {@link Bic} which identifies the bank associated with the IBAN.
 * </p>
 *
 * <p>
 * The IBAN format is defined as:
 * <ul>
 *     <li>Country Code: 2 characters, fixed as "DT" for this application.</li>
 *     <li>Check Digits: 2 characters, fixed as "70".</li>
 *     <li>Bank Code: 8 characters, fixed as "74020060" for this application.</li>
 *     <li>Account Number: 10 characters, generated based on the player's UUID using seed-based random generation.</li>
 * </ul>
 * The final IBAN is formatted as "DT70 74020060 XXXXXXXXXX", where "XXXXXXXXXX" represents the account number.
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>{@code
 * Player player = ...; // Get the player object
 * Iban iban = Iban.createRandomIban(player);
 * Bic bic = new Bic("DEUTDEFF");
 * System.out.println("Generated IBAN: " + iban);
 * System.out.println("Associated BIC: " + bic);
 * }</pre>
 * </p>
 *
 * @author Erik Pförtner
 * @see Bic
 * @since 1.0.0
 */
public class Iban {

    private static final String COUNTRY_CODE = "DT";
    private static final String BANK_CODE = "74020060";
    private static final String CHECK_DIGITS = "70";
    private static final int ACCOUNT_NUMBER_LENGTH = 10;

    private final String iban;

    /**
     * Constructs a new {@code Iban} by copying an existing {@code Iban}.
     *
     * @param iban the existing {@code Iban} to copy.
     */
    public Iban(@NotNull final Iban iban) {
        this.iban = iban.iban;
    }

    /**
     * Constructs a new {@code Iban} with the specified IBAN string.
     *
     * @param iban the IBAN string.
     */
    private Iban(@NotNull final String iban) {
        this.iban = iban;
    }

    /**
     * Creates a random {@code Iban} that is unique to the specified player.
     *
     * <p>
     * The IBAN is generated based on the following criteria:
     * <ul>
     *     <li>Country Code: "DT"</li>
     *     <li>Check Digits: "70"</li>
     *     <li>Bank Code: "74020060"</li>
     *     <li>Account Number: 10 digits derived from the player's UUID using seed-based random generation</li>
     * </ul>
     * </p>
     *
     * @param player the player for whom the IBAN is generated.
     * @return a new {@code Iban} instance.
     */
    public static Iban createRandomDistrictIban(@NotNull final Player player) {
        // Use the player's UUID to seed the random number generator
        BitRandomSource randomSource = new SingleThreadedRandomSource(player.getUniqueId().getMostSignificantBits());

        // Generate the account number (10 characters) based on the seeded random source
        String accountNumber = generateAccountNumber(randomSource);

        // Combine all parts to form the IBAN
        String iban = String.format(Locale.US, "%s%s%s%s", COUNTRY_CODE, CHECK_DIGITS, BANK_CODE, accountNumber);

        return new Iban(iban);
    }

    /**
     * Creates a random {@code Iban} that is unique to the specified uuid.
     *
     * <p>
     * The IBAN is generated based on the following criteria:
     * <ul>
     *     <li>Country Code: "DT"</li>
     *     <li>Check Digits: "70"</li>
     *     <li>Bank Code: "74020060"</li>
     *     <li>Account Number: 10 digits derived from the player's UUID using seed-based random generation</li>
     * </ul>
     * </p>
     *
     * @param uuid the UUID for whom the IBAN is generated.
     * @return a new {@code Iban} instance.
     */
    public static Iban createRandomDistrictIban(@NotNull final UUID uuid) {
        // Use the player's UUID to seed the random number generator
        BitRandomSource randomSource = new SingleThreadedRandomSource(uuid.getMostSignificantBits());

        // Generate the account number (10 characters) based on the seeded random source
        String accountNumber = generateAccountNumber(randomSource);

        // Combine all parts to form the IBAN
        String iban = String.format(Locale.US, "%s%s%s%s", COUNTRY_CODE, CHECK_DIGITS, BANK_CODE, accountNumber);

        return new Iban(iban);
    }

    /**
     * Creates a random {@code Iban} with the specified country code, check digits, and bank code.
     *
     * <p>
     * The IBAN is generated based on the following criteria:
     * <ul>
     *     <li>Country Code: the specified country code.</li>
     *     <li>Check Digits: the specified check digits.</li>
     *     <li>Bank Code: the specified bank code.</li>
     *     <li>Account Number: 10 random digits generated using a seed-based random number generator.</li>
     *     <li>UUID: the specified UUID.</li>
     * </ul>
     * </p>
     *
     * @param countryCode the country code for the IBAN.
     * @param checkDigits the check digits for the IBAN.
     * @param bankCode the bank code for the IBAN.
     * @param uuid the UUID for whom the IBAN is generated.
     * @return a new {@code Iban} instance.
     */
    public static Iban createRandomIban(@NotNull final String countryCode, @NotNull final String checkDigits, @NotNull final String bankCode, @NotNull final UUID uuid) {
        // Use the player's UUID to seed the random number generator
        BitRandomSource randomSource = new SingleThreadedRandomSource(uuid.getMostSignificantBits());

        // Generate the account number (10 characters) based on the seeded random source
        String accountNumber = generateAccountNumber(randomSource);

        // Combine all parts to form the IBAN
        String iban = String.format(Locale.US, "%s%s%s%s", countryCode, checkDigits, bankCode, accountNumber);

        return new Iban(iban);
    }

    /**
     * Generates a 10-digit account number using a seed-based random number generator.
     *
     * @param randomSource the seeded random source used to generate the account number.
     * @return a 10-digit account number as a string.
     */
    private static String generateAccountNumber(final BitRandomSource randomSource) {
        StringBuilder accountNumber = new StringBuilder(ACCOUNT_NUMBER_LENGTH);
        for (int i = 0; i < ACCOUNT_NUMBER_LENGTH; i++) {
            accountNumber.append(randomSource.nextInt(10)); // Generate a digit (0-9)
        }
        return accountNumber.toString();
    }

    /**
     * Parses an IBAN string and constructs a new {@code Iban} instance.
     *
     * @param iban the IBAN string to parse.
     * @return a new {@code Iban} instance.
     * @throws IllegalArgumentException if the IBAN string is invalid.
     */
    public static Iban fromDistrictString(@NotNull final String iban) {
        if (!isValidIban(iban)) {
            throw new IllegalArgumentException("Invalid IBAN format: " + iban);
        }

        return new Iban(iban.replaceAll(" ", "")); // remove any spaces before storing the IBAN
    }

    public static Iban fromString(@NotNull final String iban) {
        if (!isValidGenericIban(iban)) {
            throw new IllegalArgumentException("Invalid IBAN format: " + iban);
        }

        return new Iban(iban.replaceAll(" ", "")); // remove any spaces before storing the IBAN
    }

    /**
     * Checks if the specified IBAN string is valid.
     *
     * @param iban the IBAN string to validate.
     * @return {@code true} if the IBAN is valid, otherwise {@code false}.
     */
    private static boolean isValidIban(@NotNull final String iban) {
        // Remove all spaces for validation
        String cleanedIban = iban.replaceAll(" ", "");

        // Check the basic length and structure
        if (cleanedIban.length() != 22) {
            return false;
        }

        // Check the country code
        if (!cleanedIban.startsWith(COUNTRY_CODE)) {
            return false;
        }

        // Check the check digits
        String checkDigits = cleanedIban.substring(2, 4);
        if (!checkDigits.equals(CHECK_DIGITS)) {
            return false;
        }

        // Check the bank code
        String bankCode = cleanedIban.substring(4, 12);
        if (!bankCode.equals(BANK_CODE)) {
            return false;
        }

        // Check that the account number consists only of digits and is 10 digits long
        String accountNumber = cleanedIban.substring(12);
        return accountNumber.matches("\\d+");
    }

    /**
     * Checks if the specified IBAN string is valid.
     *
     * @param iban the IBAN string to validate.
     * @return {@code true} if the IBAN is valid, otherwise {@code false}.
     */
    protected static boolean isValidGenericIban(@NotNull final String iban) {
        // Remove all spaces for validation
        String cleanedIban = iban.replaceAll(" ", "");

        // Check the basic length and structure
        if (cleanedIban.length() != 22) {
            return false;
        }

        // Check the country code
        if (!cleanedIban.substring(0, 2).matches("[A-Z]{2}")) {
            return false;
        }

        // Check the check digits
        if (!cleanedIban.substring(2, 4).matches("\\d{2}")) {
            return false;
        }

        // Check the bank code
        if (!cleanedIban.substring(4, 12).matches("\\d{8}")) {
            return false;
        }

        // Check that the account number consists only of digits and is 10 digits long
        return cleanedIban.substring(12).matches("\\d{10}");
    }

    @Override
    public String toString() {
        // Format the IBAN with spaces for readability
        return String.format("%s %s %s",
                iban.substring(0, 4),
                iban.substring(4, 12),
                iban.substring(12));
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Iban iban1 = (Iban) o;
        return iban.equals(iban1.iban);
    }

    @Override
    public int hashCode() {
        return iban.hashCode();
    }
}
