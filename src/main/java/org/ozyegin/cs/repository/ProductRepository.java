package org.ozyegin.cs.repository;

import java.util.List;
import javax.sql.DataSource;
import org.ozyegin.cs.entity.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends JdbcDaoSupport {

  @Autowired
  public void setDatasource(DataSource dataSource) {
    super.setDataSource(dataSource);
  }

  public Product find(int id) {
    return null;
  }

  public List<Product> findMultiple(List<Integer> ids) {
    return null;
  }

  public List<Product> findByBrandName(String brandName) {
    return null;
  }

  public List<Integer> create(List<Product> products) {
    return null;
  }

  public void update(List<Product> products) {

  }

  public void delete(List<Integer> ids) {

  }

  public void deleteAll() {

  }
}
