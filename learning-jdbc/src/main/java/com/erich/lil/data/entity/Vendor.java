package com.erich.lil.data.entity;

import java.util.UUID;

public class Vendor {
  private UUID vendorId;
  private String name;
  private String contact;
  private String phone;
  private String email;
  private String address;

  public UUID getVendorId() {
    return vendorId;
  }

  public void setVendorId(UUID vendorId) {
    this.vendorId = vendorId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }

  public String getPhone() {
    return phone;
  }

  public void setPhone(String phone) {
    this.phone = phone;
  }

  public String getEmail() {
    return email;
  }

  public void setEmail(String email) {
    this.email = email;
  }

  public String getAddress() {
    return address;
  }

  public void setAddress(String address) {
    this.address = address;
  }

  @Override
  public String toString() {
    return "[id: " + vendorId + ", name: " + name + ", contact: " + contact +
           ", phone: " + phone + ", email: " + email + ", address: " + address + "]";
  }
}
