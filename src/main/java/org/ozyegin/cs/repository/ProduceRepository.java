package org.ozyegin.cs.repository;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class ProduceRepository extends JdbcDaoSupport {

  @Autowired
  public void setDatasource(DataSource dataSource) {
    super.setDataSource(dataSource);
  }

  public Integer produce(String company, int product, int capacity) {
    return null;
  }

  public void delete(int produceId) throws Exception {

  }

  public void deleteAll() {

  }
}
