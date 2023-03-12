package ch.ti.ifonte.employer;


import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
@EqualsAndHashCode
public class Employer {

    private Integer id;
    private String name;

    private String email;
}
