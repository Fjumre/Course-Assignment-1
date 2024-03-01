package app.model;


import app.Person;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "phonenumber")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class PhoneNumber {


    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", nullable = false, unique = true)
    private long Id;

    @Column(name= "phonenumber", nullable = false)
    private int PhoneNumber;

    @ManyToOne
    @JoinColumn(name = "person_id")
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

    public PhoneNumber(int phoneNumber) {
        this.PhoneNumber = phoneNumber;

    }
}
