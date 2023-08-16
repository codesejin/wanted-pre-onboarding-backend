package wantedpreonboardingbackend.assignment.util.constants;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ErrorMessages {

    public static final String REQUIRE_AUTHORITY = "REQUIRE_AUTHORITY";
    public static final String BAD_TOKEN = "BAD_TOKEN";
    public static final String NOT_FOUND_USER = "NOT_FOUND_USER";
    public static final String CHECK_YOUR_EMAIL = "CHECK_YOUR_EMAIL";
    public static final String CHECK_YOUR_PASSWORD = "CHECK_YOUR_PASSWORD";
    public static final String NO_MATCH_PASSWORD = "NO_MATCH_PASSWORD";

    public static final String EXPIRED_TOKEN = "EXPIRED_TOKEN";
    public static final String WRONG_TOKEN = "WRONG_TOKEN";
    public static final String NOT_SAME_AUTHORITY = "NOT_SAME_AUTHORITY";
}
