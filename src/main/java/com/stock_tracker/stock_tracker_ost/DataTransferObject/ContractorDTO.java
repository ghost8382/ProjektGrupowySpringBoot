package com.stock_tracker.stock_tracker_ost.DataTransferObject;

public class ContractorDTO {

    private Long id;
    private String name;
    private String nip;
    private String address;
    private String city;
    private String postalCode;
    private String phone;
    private String email;

    public ContractorDTO() {}

    public ContractorDTO(Long id, String name, String nip, String address,
                         String city, String postalCode, String phone, String email) {
        this.id = id;
        this.name = name;
        this.nip = nip;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.email = email;
    }

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
