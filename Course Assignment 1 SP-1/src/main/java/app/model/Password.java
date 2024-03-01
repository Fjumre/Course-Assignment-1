package app.model;

import app.Person;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "password")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString

public class Password {



    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", nullable = false, unique = true)
    private long Id;

    @Column(name = "password", nullable = false)
    private String password;

    @OneToOne(mappedBy = "password")
    private Person person;

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @PrePersist
    protected void onCreate() {
        updated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = LocalDateTime.now();
    }

    public Password(String password) {
        this.password = password;
    }
}
