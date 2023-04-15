package ch.ti.ifonte.employer;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Service;

import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class EmployerDTOMapper implements Function<Employer, EmployerDTO> {
    @Override
    public EmployerDTO apply(Employer employer) {
        return new EmployerDTO(
                employer.getId(),
                employer.getName(),
                employer.getEmail(),
                employer.getAuthorities()
                        .stream()
                        .map(GrantedAuthority::getAuthority)
                        .collect(Collectors.toList()),
                employer.getUsername()
        );
    }
}
