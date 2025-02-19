package com.erich.lil.data.entity;

import java.math.BigDecimal;
import java.util.UUID;

public class Product {

  private UUID productId;
  private String name;
  private BigDecimal price;
  private String vendorName;
  private String vendorContact;
  private String vendorPhone;
  private String vendorEmail;
  private String vendorAddress;

  public UUID getProductId() {
    return productId;
  }

  public void setProductId(UUID productId) {
    this.productId = productId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public BigDecimal getPrice() {
    return price;
  }

  public void setPrice(BigDecimal price) {
    this.price = price;
  }

  public String getVendorName() {
    return vendorName;
  }

  public void setVendorName(String vendorName) {
    this.vendorName = vendorName;
  }

  public String getVendorContact() {
    return vendorContact;
  }

  public void setVendorContact(String vendorContact) {
    this.vendorContact = vendorContact;
  }

  public String getVendorPhone() {
    return vendorPhone;
  }

  public void setVendorPhone(String vendorPhone) {
    this.vendorPhone = vendorPhone;
  }

  public String getVendorEmail() {
    return vendorEmail;
  }

  public void setVendorEmail(String vendorEmail) {
    this.vendorEmail = vendorEmail;
  }

  public String getVendorAddress() {
    return vendorAddress;
  }

  public void setVendorAddress(String vendorAddress) {
    this.vendorAddress = vendorAddress;
  }

  @Override
  public String toString() {
    throw new UnsupportedOperationException("Product::toString");
  }
}
