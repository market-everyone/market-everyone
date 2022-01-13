package web.register.domain;

import javax.persistence.*;

@Entity
public class Register {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "REGISTER_NO")
    private Long id;
}
