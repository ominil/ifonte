package ch.ti.ifonte.roles;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Collection;

@Entity
@Getter
@Setter
@Table(name = "privileges")
public class Privilege {

    @Id
    @SequenceGenerator(
            name = "privileges_privilege_id_seq",
            sequenceName = "privileges_privilege_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "privileges_privilege_id_seq"
    )
    @Column(name = "privilege_id")
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "privileges")
    private Collection<Role> roles;

}
