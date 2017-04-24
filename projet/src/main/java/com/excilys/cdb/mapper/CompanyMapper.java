package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;

public class CompanyMapper implements RowMapper<Company> {
   public Company mapRow(ResultSet rs, int rowNum) throws SQLException {
      Company company = new Company.CompanyBuilder(rs.getString("company.name")).id(rs.getLong("company.id")).build();
      return company;
   }
}