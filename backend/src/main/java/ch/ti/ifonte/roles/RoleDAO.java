package ch.ti.ifonte.roles;

import java.util.List;
import java.util.Optional;

public interface RoleDAO {

    List<Role> selectAllRoles();
    Optional<Role> selectRoleByName(String name);
}
