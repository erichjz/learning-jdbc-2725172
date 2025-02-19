package com.erich.lil.data.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.logging.Logger;

import com.erich.lil.data.entity.Customer;
import com.erich.lil.data.entity.Product;
import com.erich.lil.data.util.DatabaseUtils;

public class ProductDao implements Dao<Product, UUID> {

  private Logger LOGGER = Logger.getLogger(ProductDao.class.getName());
  private String GET_ALL = null;

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
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'create'");
  }

  @Override
  public Optional<Product> getOne(UUID id) {
    // TODO Auto-generated method stub
    throw new UnsupportedOperationException("Unimplemented method 'getOne'");
  }

  @Override
  public Product update(Product entity) {
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
      // Tricky: pull out the correct column names (product name vs vendor name)
      product.setProductId((UUID) rs.getObject("product_id"));
      product.setName(null);
      product.setPrice(new BigDecimal(0));
      product.setVendorName(null);
      product.setVendorContact(null);
      product.setVendorEmail(null);
      product.setVendorPhone(null);
      product.setVendorAddress(null);

      products.add(product);
    }
    return products;
  }

}
