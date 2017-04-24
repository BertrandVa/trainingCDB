package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper implements RowMapper<Computer>{
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        String companyName = rs.getString("company.name");
        Long companyId = rs.getLong("company.id");
        String computerName = rs.getString("computer.name");
        Long computerId = rs.getLong("computer.id");
        LocalDate introduce = null;
        if(rs.getTimestamp("computer.introduced") != null){
            introduce = rs.getTimestamp("computer.introduced").toLocalDateTime().toLocalDate();
        }
        LocalDate discontinued = null;
        if(rs.getTimestamp("computer.discontinued") != null){
            discontinued = rs.getTimestamp("computer.discontinued").toLocalDateTime().toLocalDate();
        }
        
        Company company = new Company.CompanyBuilder(companyName).id(companyId).build();
        Computer computer = new Computer.ComputerBuilder(computerName).id(computerId).manufacturer(company)
                .introduceDate(introduce).discontinuedDate(discontinued)
                .build();
        return computer;
     }
}
