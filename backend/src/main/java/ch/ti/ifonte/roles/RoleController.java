package ch.ti.ifonte.roles;

import io.swagger.v3.oas.annotations.Operation;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
@RequestMapping("api/v1/roles")
@AllArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @GetMapping()
    @Operation(summary = "Get a list of available roles")
    public List<Role> selectAllRoles() {
        return roleService.selectAllRoles();
    }

}
