package app.model;


import app.Person;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "address")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Address {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false, unique = true)
    private long Id;

    @Column(name = "address", nullable = false)
    private String Address;

    @ManyToOne
    @JoinColumn(name = "zipcode_id", referencedColumnName = "zip")
    private ZipCode zipCode;

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

    public Address(String address) {
        Address = address;
    }

    public Address(String address, ZipCode zipCode, LocalDateTime updated) {
        Address = address;
        this.zipCode = zipCode;
        this.updated = updated;
    }

    public void addZipCode(ZipCode zipCode) {
        this.zipCode = zipCode;
        if (zipCode != null) {
            zipCode.setAddress(this);
        }
    }

}
