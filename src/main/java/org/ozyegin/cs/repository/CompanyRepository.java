package org.ozyegin.cs.repository;

import java.util.List;
import javax.sql.DataSource;
import org.ozyegin.cs.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository extends JdbcDaoSupport {

  @Autowired
  public void setDatasource(DataSource dataSource) {
    super.setDataSource(dataSource);
  }

  public Company find(String name) {
    return null;
  }

  public List<Company> findByCountry(String country) {
    return null;
  }

  public String create(Company company) throws Exception {
    return null;
  }

  public String delete(String name) {
    return null;
  }

  public void deleteAll() {

  }
}
