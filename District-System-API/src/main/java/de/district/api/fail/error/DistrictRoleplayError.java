package de.district.api.fail.error;

public class DistrictRoleplayError extends Error {
    public DistrictRoleplayError() {
    }

    public DistrictRoleplayError(final String message) {
        super(message);
    }

    public DistrictRoleplayError(final Throwable cause) {
        super(cause);
    }

    public DistrictRoleplayError(final String message, final Throwable cause) {
        super(message, cause);
    }
}
