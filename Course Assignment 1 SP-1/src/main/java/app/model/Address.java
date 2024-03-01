package app.model;


import app.Person;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

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


    @OneToMany(mappedBy = "address")
    private Set<Person> personSet= new HashSet<>();

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

    public void addPerson(Person person) {
        if (personSet == null) {
            personSet = new HashSet<>();
        }
        personSet.add(person);
        person.setAddress(this);
    }
}
