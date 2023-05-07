package ch.ti.ifonte.customer;

import ch.ti.ifonte.roles.Role;

import java.util.List;

public record CustomerUserDetailsUpdate (
        boolean accountNonLocked,
        List<Role> roles
) {
}
