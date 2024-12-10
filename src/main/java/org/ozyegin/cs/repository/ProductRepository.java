package org.ozyegin.cs.repository;

import java.util.*;
import javax.sql.DataSource;
import org.ozyegin.cs.entity.Product;
import org.ozyegin.cs.entity.Sample;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class ProductRepository extends JdbcDaoSupport {
  final int batchSize = 10;
  final String createPS = "INSERT INTO product (name, description, brandName) VALUES (?,?,?) ";
  final String updatePS = "UPDATE product SET name=?, description=?, brandName=? WHERE id=?";
  final String getPS = "SELECT * FROM product WHERE id IN (:ids)";
  final String findPS = "SELECT * FROM product WHERE id=?";
  final String deleteAllPS = "DELETE FROM product";
  final String deletePS = "DELETE FROM product WHERE id=?";
  final String getAllIdsPS = "SELECT * FROM product";
  final String getBrandPS = "SELECT * FROM product WHERE brandName =?";

  @Autowired
  public void setDatasource(DataSource dataSource) {
    super.setDataSource(dataSource);
  }

  public Product find(int id) {
    Product product;
      product = Objects.requireNonNull(getJdbcTemplate()).queryForObject(findPS,
              new Object[]{id},
              productRowMapper);
    return product;
  }

  private final RowMapper<Product> productRowMapper = (resultSet, i) ->
    new Product()
            .id(resultSet.getInt("id"))
            .name(resultSet.getString("name"))
            .description(resultSet.getString("description"))
            .brandName(resultSet.getString("brandName"));


  public List<Product> findMultiple(List<Integer> ids) {
    if (ids == null || ids.isEmpty()) {
      return new ArrayList<>();
    } else {
      Map<String, List<Integer>> params = new HashMap<>() {
        {
          this.put("ids", new ArrayList<>(ids));
        }
      };
      var template = new NamedParameterJdbcTemplate(Objects.requireNonNull(getJdbcTemplate()));
      return template.query(getPS, params, productRowMapper);
    }
  }

  public List<Product> findByBrandName(String brandName) {
    return getJdbcTemplate().query(getBrandPS, new Object[] {brandName}, productRowMapper);
  }

  public List<Integer> create(List<Product> products) {
    List<Product> createList =(Objects.requireNonNull(getJdbcTemplate()).query(getAllIdsPS, productRowMapper));

    Objects.requireNonNull(getJdbcTemplate()).batchUpdate(createPS, products,
            products.size(),
            (ps, product) -> {
              ps.setString(1, product.getName());
              ps.setString(2, product.getDescription());
              ps.setString(3,product.getBrandName());
            });
    List<Product> productList =(Objects.requireNonNull(getJdbcTemplate()).query(getAllIdsPS, productRowMapper));
    productList.removeAll(createList);

    List<Integer> ids =new ArrayList<Integer>();

    for(int i=0 ; i<productList.size() ;i++){
      ids.add(productList.get(i).getId());
    }
    return ids;
  }

  public void update(List<Product> products) {
    Objects.requireNonNull(getJdbcTemplate()).batchUpdate(updatePS, products,
            products.size(),
            (ps, product) -> {
              ps.setString(1, product.getName());
              ps.setString(2, product.getDescription());
              ps.setString(3, product.getBrandName());
              ps.setInt(4, product.getId());
            });
  }

  public void delete(List<Integer> ids) {
//    for (Integer id : ids) {
//      if (Objects.requireNonNull(getJdbcTemplate()).update(deletePS, ids) != 1) {
//      } else {
//        Objects.requireNonNull(getJdbcTemplate()).update(deletePS, ids);
//      }
//    }

    assert getJdbcTemplate() != null;
    getJdbcTemplate().batchUpdate(deletePS, ids, ids.size(), (ps, id) -> ps.setInt(1, id));
  }

  public void deleteAll() {
    Objects.requireNonNull(getJdbcTemplate()).update(deleteAllPS);
  }
}
