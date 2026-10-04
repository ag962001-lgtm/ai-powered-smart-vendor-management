package com.smart.vendor_management.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "Vendors")
public class Vendor{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String vendorName;
    private String contactPerson;
    private String email;
    private String phone;

   public Vendor(){

   }
   public Long getId(){
       return id;
   }
   public String getVendorName(){
       return vendorName;
   }
   public void setVendorName(String vendorName){
       this.vendorName = vendorName;
   }
   public String getContactPerson(){
       return contactPerson;
   }
   public void setContactPerson(String contactPerson){
       this.contactPerson = contactPerson;
   }
    public String getEmail() {
       return email;
   }

    public void setEmail(String email) {
       this.email = email;
   }

    public String getPhone() {
       return phone;
   }

    public void setPhone(String phone) {
       this.phone = phone;
   }

}