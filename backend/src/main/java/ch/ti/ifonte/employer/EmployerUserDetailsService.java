package ch.ti.ifonte.employer;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class EmployerUserDetailsService implements UserDetailsService {

    private final EmployerDao employerDao;

    public EmployerUserDetailsService(@Qualifier("jpa") EmployerDao employerDao) {
        this.employerDao = employerDao;
    }
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return employerDao.selectUserByEmail(username).orElseThrow(
                () -> new UsernameNotFoundException("Username " + username + " not found")
        );
    }
}
