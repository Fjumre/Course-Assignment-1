package app;


import app.model.Address;
import app.model.Hobby;
import app.model.Password;
import app.model.PhoneNumber;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDate;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "person")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString
public class Person {

    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name= "id", nullable = false, unique = true)
    private long Id;

    @Column(name = "firstname", nullable = false)
    private String firstName;

    @Column(name = "lastname", nullable = false)
    private String lastName;

    @Column(name = "birthday", nullable = false)
    private LocalDate Birthday;

    @Column(name = "email", nullable = false)
    private String email;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    @OneToMany(mappedBy = "person",cascade = CascadeType.ALL)
    private Set<PhoneNumber> phoneNumberSet = new HashSet<>();

    @Column(name = "updated", nullable = false)
    private LocalDateTime updated;

    @ManyToOne(cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "address_id", nullable = false)
    private Address address;

    @ManyToMany(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(name = "person_hobby",
            joinColumns = @JoinColumn(name = "person_id"),
            inverseJoinColumns = @JoinColumn(name = "hobby_id"))
    private Set<Hobby> hobbySet = new HashSet<>();

    @OneToOne(cascade = CascadeType.ALL, optional = false, fetch = FetchType.LAZY)
    private Password password;

    public enum Gender{

        MALE,
        FEMALE,
        IT

    }

    @PrePersist
    protected void onCreate() {
        updated = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updated = LocalDateTime.now();
    }

    public Person(String firstName, String lastName, LocalDate birthday, String email, Gender gender) {
        this.firstName = firstName;
        this.lastName = lastName;
        Birthday = birthday;
        this.email = email;
        this.gender = gender;
    }

    public Person(String firstName, String lastName, LocalDate birthday, String email, Gender gender, Set<PhoneNumber> phoneNumberSet, LocalDateTime updated, Address address, Set<Hobby> hobbySet, Password password) {
        this.firstName = firstName;
        this.lastName = lastName;
        Birthday = birthday;
        this.email = email;
        this.gender = gender;
        this.phoneNumberSet = phoneNumberSet;
        this.updated = updated;
        this.address = address;
        this.hobbySet = hobbySet;
        this.password = password;
    }


    public void addHobby(Hobby hobby){
        hobbySet.add(hobby);
        hobby.getPerson().add(this);
    }
    public void removeHobby(Hobby hobby) {
        hobbySet.remove(hobby);
        hobby.getPerson().remove(this);
    }
    public void addAddress(Address address) {
        this.address = address;
        if (address != null) {
            address.addPerson(this);
        }
    }

    public void addPhoneNumber(PhoneNumber phoneNumber){

        this.phoneNumberSet.add(phoneNumber);
        if (phoneNumber!= null){
            phoneNumber.setPerson(this);
        }
    }
    public void removePhoneNumber(PhoneNumber phoneNumber) {
        phoneNumberSet.remove(phoneNumber);
        if (phoneNumber != null) {
            phoneNumber.setPerson(null);
        }
    }
    public void addPassword(Password password){
        this.password= password;
        if (password!= null){
            password.setPerson(this);
        }
    }
}
