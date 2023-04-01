package ch.ti.ifonte.employer;


import jakarta.persistence.*;
import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
@Entity
@Table(
        name = "employer",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "employer_email_unique",
                        columnNames = "email"
                )
        }
)
public class Employer {

    @Id
    @SequenceGenerator(
            name = "employer_id_seq",
            sequenceName = "employer_id_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employer_id_seq"
    )
    private Integer id;
    @Column(
            nullable = false
    )
    private String name;
    @Column(
            nullable = false
    )
    private String email;

    public Employer(String name, String email) {
        this.name = name;
        this.email = email;
    }

}
