package ch.ti.ifonte.auth;

public record AuthenticationRequest(
        String username,
        String password
) {
}
