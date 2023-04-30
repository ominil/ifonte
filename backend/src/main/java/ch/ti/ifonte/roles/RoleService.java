package ch.ti.ifonte.roles;

import ch.ti.ifonte.exception.ResourceNotFoundException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {

    private final RoleDAO roleDAO;

    public RoleService(@Qualifier("role_jpa") RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }


    public List<Role> selectAllRoles() {
        return roleDAO.selectAllRoles();
    }

    public Role selectRoleByName(String name) {
        return roleDAO.selectRoleByName(name).orElseThrow(
                () -> new ResourceNotFoundException(
                        "Role: " + name  + ", does not exists."
            )
        );
    }
}
