package com.stock_tracker.stock_tracker_ost.DataTransferObject;

public class CompanyConfigDTO {

    private String name;
    private String nip;
    private String address;
    private String city;
    private String postalCode;
    private String phone;
    private String email;
    private String bankAccount;

    public CompanyConfigDTO() {}

    public CompanyConfigDTO(String name, String nip, String address, String city,
                            String postalCode, String phone, String email, String bankAccount) {
        this.name = name;
        this.nip = nip;
        this.address = address;
        this.city = city;
        this.postalCode = postalCode;
        this.phone = phone;
        this.email = email;
        this.bankAccount = bankAccount;
    }

    public String getName() { return name; }
    public String getNip() { return nip; }
    public String getAddress() { return address; }
    public String getCity() { return city; }
    public String getPostalCode() { return postalCode; }
    public String getPhone() { return phone; }
    public String getEmail() { return email; }
    public String getBankAccount() { return bankAccount; }

    public void setName(String name) { this.name = name; }
    public void setNip(String nip) { this.nip = nip; }
    public void setAddress(String address) { this.address = address; }
    public void setCity(String city) { this.city = city; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }
    public void setPhone(String phone) { this.phone = phone; }
    public void setEmail(String email) { this.email = email; }
    public void setBankAccount(String bankAccount) { this.bankAccount = bankAccount; }
}
