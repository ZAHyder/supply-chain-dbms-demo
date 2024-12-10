package org.ozyegin.cs.repository;

import java.sql.Date;
import java.util.List;
import java.util.Objects;
import javax.sql.DataSource;
import org.ozyegin.cs.entity.Pair;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

@Repository
public class TransactionHistoryRepository extends JdbcDaoSupport {
  private final RowMapper<Pair> pairMapper = (resultSet, i) -> new Pair(
      resultSet.getString(1),
      resultSet.getInt(2)
  );

  private final RowMapper<String> stringMapper = (resultSet, i) -> resultSet.getString(1);

  @Autowired
  public void setDatasource(DataSource dataSource) {
    super.setDataSource(dataSource);
  }

  public List<Pair> query1() {
    return null;
  }

  public List<String> query2(Date start, Date end) {
    return null;
  }

  public void deleteAll() {
    Objects.requireNonNull(getJdbcTemplate()).update("DELETE FROM your_table_name");
  }
}
