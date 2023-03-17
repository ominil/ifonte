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
public class Employer {

    @Id
    @SequenceGenerator(
            name = "employer_id_sequence",
            sequenceName = "employer_id_sequence"
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "employer_id_sequence"
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
