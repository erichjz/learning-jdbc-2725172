package com.erich.lil.data.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import com.erich.lil.data.entity.Product;
import com.erich.lil.data.entity.Vendor;
import com.erich.lil.data.util.DatabaseUtils;

public class ProductDao implements Dao<Product, UUID> {

  private Logger LOGGER = Logger.getLogger(ProductDao.class.getName());
  private String GET_ALL = """
      select product_id, wisdom.products.name product_name, price,
             wisdom.products.vendor_id, wisdom.vendors.name vendor_name,
             wisdom.vendors.contact vendor_contact, wisdom.vendors.email vendor_email,
             wisdom.vendors.phone vendor_phone, wisdom.vendors.address vendor_address
      from wisdom.products
        join wisdom.vendors on wisdom.products.vendor_id=wisdom.vendors.vendor_id
      """;
  private String GET_BY_ID = """
      select product_id, wisdom.products.name product_name, price,
             wisdom.products.vendor_id, wisdom.vendors.name vendor_name,
             wisdom.vendors.contact vendor_contact, wisdom.vendors.email vendor_email,
             wisdom.vendors.phone vendor_phone, wisdom.vendors.address vendor_address
      from wisdom.products
      join wisdom.vendors on wisdom.products.vendor_id=wisdom.vendors.vendor_id
      where product_id=?
      """;
  private static final String GET_VENDOR_BY_EMAIL = """
      select vendor_id, name, contact, email, phone, address
      from wisdom.vendors
      where email = ?
      """;
  private static final String GET_VENDOR_BY_ID = """
      select vendor_id, name, contact, email, phone, address
      from wisdom.vendors
      where vendor_id = ?
      """;
  private static final String CREATE_PRODUCT = """
      insert into wisdom.products
        (product_id, name, price, vendor_id)
        values (?, ?, ?, ?)
      """;
  private static final String CREATE_VENDOR = """
      insert into wisdom.vendors
      (vendor_id, name, contact, email, phone, address)
      values (?, ?, ?, ?, ?, ?)
      """;

  private static final String UPDATE = "update wisdom.services set name = ?, price = ? where service_id = ?";
  private static final String DELETE = "delete from wisdom.services where service_id = ?";

  @Override
  public List<Product> getAll() {
    List<Product> products = new ArrayList<>();
    Connection connection = DatabaseUtils.getConnection();
    try (Statement statement = connection.createStatement()) {
      ResultSet rs = statement.executeQuery(GET_ALL);
      products = this.processResultSet(rs);
    } catch (SQLException e) {
      DatabaseUtils.handleSqlException("ProductDao.getAll", e, LOGGER);
    }
    return products;
  }

  @Override
  public Product create(Product entity) {
    UUID productId = UUID.randomUUID();
    // Check for existing vendor
    Vendor vendor = entity.getVendor();
    Optional<Vendor> checkVendor = getVendorByEmail(entity.getVendor().getEmail());
    if (!checkVendor.isPresent()) {
      // No matching Vendor
      vendor = createVendor(entity.getVendor());
    } else {
      // Vendor exists, reuse
      vendor = checkVendor.get();
    }
    UUID vendorId = vendor.getVendorId();
    Connection connection = DatabaseUtils.getConnection();
    try {
      connection.setAutoCommit(false);
      // Create the vendor first, then the product entry
      PreparedStatement statement = connection.prepareStatement(CREATE_PRODUCT);
      statement.setObject(1, productId);
      statement.setString(2, entity.getName());
      statement.setBigDecimal(3, entity.getPrice());
      statement.setObject(4, vendorId);
      statement.execute();
      connection.commit();
      statement.close();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException sqle) {
        DatabaseUtils.handleSqlException("ProductDao.create.rollback", sqle, LOGGER);
      }
      DatabaseUtils.handleSqlException("ProductDao.create", e, LOGGER);
    }
    Optional<Product> product = this.getOne(productId);
    if (!product.isPresent()) {
      return null;
    }
    return product.get();
  }

  private Vendor createVendor(Vendor entity) {
    UUID vendorId = UUID.randomUUID();
    Connection connection = DatabaseUtils.getConnection();
    try {
      connection.setAutoCommit(false);
      // Create the vendor first, then the product entry
      PreparedStatement statement = connection.prepareStatement(CREATE_VENDOR);
      statement.setObject(1, vendorId);
      statement.setString(2, entity.getName());
      statement.setString(3, entity.getContact());
      statement.setString(4, entity.getEmail());
      statement.setString(5, entity.getPhone());
      statement.setString(6, entity.getAddress());
      statement.execute();
      connection.commit();
      statement.close();
    } catch (SQLException e) {
      try {
        connection.rollback();
      } catch (SQLException sqle) {
        DatabaseUtils.handleSqlException("ProductDao.createVendor.rollback", sqle, LOGGER);
      }
      DatabaseUtils.handleSqlException("ProductDao.createVendor", e, LOGGER);
    }
    Optional<Vendor> vendor = this.getVendorById(vendorId);
    if (!vendor.isPresent()) {
      return null;
    }
    return vendor.get();
  }

  @Override
  public Optional<Product> getOne(UUID id) {
    try (PreparedStatement statement = DatabaseUtils.getConnection().prepareStatement(GET_BY_ID)) {
      statement.setObject(1, id);
      ResultSet rs = statement.executeQuery();
      List<Product> products = this.processResultSet(rs);
      if (products.isEmpty()) {
        return Optional.empty();
      }
      return Optional.of(products.get(0));
    } catch (SQLException e) {
      DatabaseUtils.handleSqlException("ProductDao.getOne", e, LOGGER);
    }
    return Optional.empty();
  }

  private Optional<Vendor> getVendorByEmail(String email) {
    try (PreparedStatement statement = DatabaseUtils.getConnection().prepareStatement(GET_VENDOR_BY_EMAIL)) {
      statement.setObject(1, email);
      ResultSet rs = statement.executeQuery();
      List<Vendor> vendors = this.processVendorResultSet(rs);
      if (vendors.isEmpty()) {
        return Optional.empty();
      }
      return Optional.of(vendors.get(0));
    } catch (SQLException e) {
      DatabaseUtils.handleSqlException("ProductDao.getOne", e, LOGGER);
    }
    return Optional.empty();
  }

  private Optional<Vendor> getVendorById(UUID id) {
    try (PreparedStatement statement = DatabaseUtils.getConnection().prepareStatement(GET_VENDOR_BY_ID)) {
      statement.setObject(1, id);
      ResultSet rs = statement.executeQuery();
      List<Vendor> vendors = this.processVendorResultSet(rs);
      if (vendors.isEmpty()) {
        return Optional.empty();
      }
      return Optional.of(vendors.get(0));
    } catch (SQLException e) {
      DatabaseUtils.handleSqlException("ProductDao.getVendorById", e, LOGGER);
    }
    return Optional.empty();
  }

  @Override
  public Product update(Product entity) {
    // To consider;
    // Since we have two tables, vendors and products, we may
    // only be updating one of those
    // The awkward thing about updating a Vendor is that it will update
    // for all products using that same vendor. Dangerous.
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'update'");
  }

  @Override
  public void delete(UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'delete'");
  }

  private List<Product> processResultSet(ResultSet rs) throws SQLException {
    List<Product> products = new ArrayList<>();
    while (rs.next()) {
      Product product = new Product();
      product.setProductId((UUID) rs.getObject("product_id"));
      product.setName(rs.getString("product_name"));
      product.setPrice(rs.getBigDecimal("price"));
      UUID vendorId = (UUID) rs.getObject("vendor_id");
      Vendor vendor = getVendorById(vendorId).get();
      product.setVendor(vendor);
      products.add(product);
    }
    return products;
  }

  private List<Vendor> processVendorResultSet(ResultSet rs) throws SQLException {
    List<Vendor> vendors = new ArrayList<>();

    while (rs.next()) {
      Vendor vendor = new Vendor();
      vendor.setVendorId((UUID) rs.getObject("vendor_id"));
      vendor.setName(rs.getString("name"));
      vendor.setContact(rs.getString("contact"));
      vendor.setEmail(rs.getString("email"));
      vendor.setPhone(rs.getString("phone"));
      vendor.setAddress(rs.getString("address"));
      vendors.add(vendor);
    }
    return vendors;
  }
}
