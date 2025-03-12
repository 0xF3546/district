package de.district.api.economy.bank;

/**
 * <p>
 * The {@code Bic} class represents a Bank Identifier Code (BIC), also known as SWIFT code.
 * A BIC is a standard format identifier used to uniquely identify a bank or financial institution.
 * </p>
 *
 * <p>
 * This class is immutable and is used in conjunction with the {@link Iban} class to provide complete bank details.
 * </p>
 *
 * <p>
 * <b>Usage Example:</b>
 * <pre>{@code
 * Bic bic = new Bic("DEUTDEFF");
 * System.out.println("BIC: " + bic);
 * }</pre>
 * </p>
 *
 * @author Erik Pförtner
 * @since 1.0.0
 */
public class Bic {

    private final String bic;

    /**
     * Constructs a new {@code Bic} instance.
     *
     * @param bic the BIC string.
     * @throws IllegalArgumentException if the BIC is invalid.
     */
    public Bic(final String bic) {
        if (!isValidBic(bic)) {
            throw new IllegalArgumentException("Invalid BIC format: " + bic);
        }
        this.bic = bic;
    }

    /**
     * Validates the BIC format.
     *
     * @param bic the BIC string to validate.
     * @return {@code true} if the BIC is valid, otherwise {@code false}.
     */
    private static boolean isValidBic(final String bic) {
        // A valid BIC is either 8 or 11 characters long, and only contains uppercase letters and digits.
        return bic != null && (bic.length() == 8 || bic.length() == 11) && bic.matches("^[A-Z0-9]+$");
    }

    @Override
    public String toString() {
        return bic;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Bic bic1 = (Bic) o;
        return bic.equals(bic1.bic);
    }

    @Override
    public int hashCode() {
        return bic.hashCode();
    }
}
