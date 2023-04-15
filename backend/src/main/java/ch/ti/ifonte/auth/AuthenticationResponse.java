package ch.ti.ifonte.auth;

public record AuthenticationResponse(
        String token,
        Integer id
) {
}
