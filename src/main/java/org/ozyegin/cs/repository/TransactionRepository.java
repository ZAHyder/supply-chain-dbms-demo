package org.ozyegin.cs.repository;

import java.util.Date;
import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionRepository extends JdbcDaoSupport {
  @Autowired
  public void setDatasource(DataSource dataSource) {
    super.setDataSource(dataSource);
  }

  public Integer order(String company, int product, int amount, Date createdDate) {
    return null;
  }

  public void delete(int transactionId) throws Exception {

  }

  public void deleteAll() {

  }
}
