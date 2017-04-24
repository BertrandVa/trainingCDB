package com.excilys.cdb.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import org.springframework.jdbc.core.RowMapper;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapper implements RowMapper<Computer> {

    /**
     * @param rs
     *          le resultset
     * @param rowNum
     *          le numéro de colonne
     * @throws SQLException
     *          L'exception
     * @return
     *        l'ordinateur
     */
    public Computer mapRow(ResultSet rs, int rowNum) throws SQLException {
        String companyName = rs.getString("company.name");
        Long companyId = rs.getLong("company.id");
        String computerName = rs.getString("computer.name");
        Long computerId = rs.getLong("computer.id");
        LocalDate introduce = null;
        if (rs.getString("introduced") != "NULL") {
            if (rs.getTimestamp("introduced") != null) {
                introduce = rs.getTimestamp("introduced").toLocalDateTime()
                        .toLocalDate();
            }
        }
        LocalDate discontinued = null;
        if (rs.getTimestamp("discontinued") != null) {
            discontinued = rs.getTimestamp("discontinued").toLocalDateTime()
                    .toLocalDate();
        }

        Company company = new Company.CompanyBuilder(companyName).id(companyId)
                .build();
        Computer computer = new Computer.ComputerBuilder(computerName)
                .id(computerId).manufacturer(company).introduceDate(introduce)
                .discontinuedDate(discontinued).build();
        return computer;
    }
}
