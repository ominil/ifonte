package ch.ti.ifonte.roles;

import ch.ti.ifonte.customer.Customer;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Table(name = "roles")
@Getter
@Setter
public class Role {

    @Id
    @SequenceGenerator(
            name = "roles_role_id_seq",
            sequenceName = "roles_role_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "roles_role_id_seq"
    )
    @Column(name = "role_id")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "roles")
    private Collection<Customer> customers;

    @ManyToMany
    @JoinTable(
            name = "roles_privileges",
            joinColumns = @JoinColumn(
                    name = "role_id", referencedColumnName = "role_id"
            ),
            inverseJoinColumns = @JoinColumn(
                    name = "privilege_id",referencedColumnName = "privilege_id"
            )
    )
    private Collection<Privilege> privileges;
}
