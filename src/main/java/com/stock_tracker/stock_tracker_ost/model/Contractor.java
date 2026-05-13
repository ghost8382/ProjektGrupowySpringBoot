package com.stock_tracker.stock_tracker_ost.model;

import jakarta.persistence.*;

@Entity
@Table(name = "contractor")
public class Contractor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String nip;
    private String address;
    private String city;
    private String postalCode;
    private String phone;
    private String email;

    public Long getId() { return id; }
    public String getName() { return name; }
    public String getNip() { return nip; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getPostalCode() { return postalCode; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }

    public void setId(Long id) { this.id = id; }
    public void setName(String name) { this.name = name; }
    public void setNip(String nip) { this.nip = nip; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
}
