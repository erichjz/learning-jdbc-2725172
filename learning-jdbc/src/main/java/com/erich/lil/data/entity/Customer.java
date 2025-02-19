package com.erich.lil.data.entity;

import java.util.UUID;

public class Customer {
  private UUID customerId;
  private String firstName;
  private String lastName;
  private String emailAddress;
  private String phoneNumber;
  private String mailingAddress;

  public UUID getCustomerId() {
    return customerId;
  }

  public void setCustomerId(UUID customerId) {
    this.customerId = customerId;
  }

  public String getFirstName() {
    return firstName;
  }

  public void setFirstName(String firstName) {
    this.firstName = firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public void setLastName(String lastName) {
    this.lastName = lastName;
  }

  public String getEmailAddress() {
    return emailAddress;
  }

  public void setEmailAddress(String emailAddress) {
    this.emailAddress = emailAddress;
  }

  public String getPhoneNumber() {
    return phoneNumber;
  }

  public void setPhoneNumber(String phoneNumber) {
    this.phoneNumber = phoneNumber;
  }

  public String getMailingAddress() {
    return mailingAddress;
  }

  public void setMailingAddress(String mailingAddress) {
    this.mailingAddress = mailingAddress;
  }

  @Override
  public String toString() {
    return "Customer [customerId=" + customerId + ", name=" + firstName + " " + lastName + ", email=" +
        emailAddress + ", phone=" + phoneNumber + ", address=" + mailingAddress + "]";
  }
}
