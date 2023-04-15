package ch.ti.ifonte.auth;

import ch.ti.ifonte.employer.Employer;
import ch.ti.ifonte.employer.EmployerDTO;
import ch.ti.ifonte.employer.EmployerDTOMapper;
import ch.ti.ifonte.jwt.JWTUtil;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class AuthenticationService {

    private final AuthenticationManager authenticationManager;
    private final EmployerDTOMapper employerDTOMapper;
    private final JWTUtil jwtUtil;


    public AuthenticationResponse login(AuthenticationRequest authenticationRequest) {
        Authentication authenticate = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        authenticationRequest.username(),
                        authenticationRequest.password()
                )
        );

        Employer principal = (Employer) authenticate.getPrincipal();
        EmployerDTO employerDTO = employerDTOMapper.apply(principal);
        String jwtToken = jwtUtil.issueToken(employerDTO.username(), employerDTO.roles());

        return new AuthenticationResponse(jwtToken, employerDTO.id());
    }
}
