package org.ozyegin.cs.repository;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import java.util.Objects;

@Repository
public class ProduceRepository extends JdbcDaoSupport {

  @Autowired
  public void setDatasource(DataSource dataSource) {
    super.setDataSource(dataSource);
  }
  final int batchSize = 10;
  final String createPS = "INSERT INTO produce (name, id, capacity) VALUES(?,?,?)";
  final String deletePS = "DELETE FROM produce WHERE id=?";
  final String deleteAllPS = "DELETE FROM produce";

  public Integer produce(String company, int product, int capacity) {
    return Objects.requireNonNull(getJdbcTemplate()).update(createPS, company, product, capacity);
  }

  public void delete(int produceId) throws Exception {
    if (Objects.requireNonNull(getJdbcTemplate()).update(deletePS, produceId) != 1) {
      throw new Exception("Produce Delete is failed!");
    } else {
      Objects.requireNonNull(getJdbcTemplate()).update(deletePS, produceId);
    }
  }

  public void deleteAll() {
    Objects.requireNonNull(getJdbcTemplate()).update(deleteAllPS);
  }
}
