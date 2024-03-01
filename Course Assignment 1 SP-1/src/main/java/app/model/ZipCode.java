package app.model;

import app.model.Address;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "zipcode")
@NoArgsConstructor
@Getter
@Setter
@AllArgsConstructor
@ToString

public class ZipCode {

    @Id
    @Column(name = "zip", nullable = false, unique = true)
    private int zip;

    @Column(name = "city_name", nullable = false)
    private String cityName;

    @Column(name = "region_name", nullable = false)
    private String RegionName;

    @Column(name = "municipality_name", nullable = false)
    private String MunicipalityName;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    public ZipCode(int zip, String cityName, String regionName, String municipalityName) {
        this.zip = zip;
        this.cityName = cityName;
        RegionName = regionName;
        MunicipalityName = municipalityName;
    }

    public void setAddress(Address address){
        this.address= address;

    }

}

