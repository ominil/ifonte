package ch.ti.ifonte.roles;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository("role_jpa")
@AllArgsConstructor
public class RoleJPAADataAccessService implements RoleDAO {

    private final RoleRepository roleRepository;
    @Override
    public List<Role> selectAllRoles() {
        return roleRepository.findAll();
    }

    @Override
    public Optional<Role> selectRoleByName(String name) {
        return roleRepository.findRoleByName(name);
    }
}
