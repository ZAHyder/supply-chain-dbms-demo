package org.ozyegin.cs.repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;

import org.ozyegin.cs.entity.Company;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class CompanyRepository extends JdbcDaoSupport {
  final String getNamePS = "SELECT * FROM company WHERE name = ?";
  final String emailPS = "SELECT email FROM email WHERE name = ?";
  final String cityZipPS = "SELECT city FROM city WHERE zip = ?";
  final String nameCountryPS = "SELECT name FROM company WHERE country = ?";
  final String deleteNamePS = "DELETE FROM company WHERE name = ?";
  final String deleteAllPS = "DELETE FROM company";
  final String cityCreatePS = "INSERT INTO city (zip, city) VALUES (?, ?)";
  final String createPS = "INSERT INTO company (name, country, zip, streetInfo, phoneNumber) VALUES (?, ?, ?, ?, ?)";
  final String emailCreatePS = "INSERT INTO email (name, email) VALUES (?,?)";

  @Autowired
  public void setDatasource(DataSource dataSource) {
    super.setDataSource(dataSource);
  }

  private final class CompanyMapper implements RowMapper<Company> {

    @Override
    public Company mapRow(ResultSet resultSet, int i) throws SQLException {
      Company company = new Company();
      company.setName(resultSet.getString("name"));
      company.setZip(resultSet.getInt("zip"));
      company.setCountry(resultSet.getString("country"));
      company.setStreetInfo(resultSet.getString("streetInfo"));
      company.setPhoneNumber(resultSet.getString("phoneNumber"));

      return company;
    }
  }

  private final RowMapper<String> stringRowMapper = (resultSet, i) -> resultSet.getString(1);

  public Company find(String name) {
    Company company = Objects.requireNonNull(getJdbcTemplate()).queryForObject(getNamePS, new Object[]{name}, new CompanyMapper());
    company.setE_mails(Objects.requireNonNull(getJdbcTemplate()).query(emailPS,
            new Object[]{company.getName()}, stringRowMapper));
    List<String> cityQuery = Objects.requireNonNull(getJdbcTemplate()).query(cityZipPS, new Object[]{company.getZip()}, stringRowMapper);
    if (cityQuery.size() < 1) {
      company.setCity("");
    } else {
      company.setCity(cityQuery.get(0));
    }
    return company;
  }


  public List<Company> findByCountry(String country) {
    List<String> names = Objects.requireNonNull(getJdbcTemplate()).query(nameCountryPS,
            new Object[]{country}, stringRowMapper);
    ArrayList<Company> company = new ArrayList<>();
    for (String cName : names) {
      company.add(find(cName));
    }
    return company;
  }

  public String create(Company company) throws Exception {
    try {
      String companyCity = Objects.requireNonNull(getJdbcTemplate()).
              queryForObject(cityZipPS, new Object[]{company.getZip()}, stringRowMapper);
      if (!companyCity.equals(company.getCity())) {
        throw new Exception("City values should be same");
      }
    } catch (EmptyResultDataAccessException e) {
      Objects.requireNonNull(getJdbcTemplate()).update(cityCreatePS, company.getZip(), company.getCity());
    }

    Objects.requireNonNull(getJdbcTemplate()).update(createPS, company.getName(), company.getCountry(), company.getZip(), company.getStreetInfo(),
            company.getPhoneNumber());


    for (String cEmail : company.getE_mails()) {
      Objects.requireNonNull(getJdbcTemplate()).update(emailCreatePS, company.getName(), cEmail);
    }

    return "Company " + company.getName() + " added";
  }

  public String delete(String name) throws Exception {
    if (Objects.requireNonNull(getJdbcTemplate()).update(deleteNamePS, name) != 1) {
      throw new Exception("Company delete failed!");
    } else {
      Objects.requireNonNull(getJdbcTemplate()).update(deleteNamePS, name);
      return "Company " + name + " deleted";
    }
  }

  public void deleteAll() {
    Objects.requireNonNull(getJdbcTemplate()).update(deleteAllPS);
  }
}