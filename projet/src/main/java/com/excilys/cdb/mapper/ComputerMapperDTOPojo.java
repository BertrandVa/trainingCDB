package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapperDTOPojo {

    public static Computer mapper(ComputerDTO computerDto) {
        String name = computerDto.getName();
        String companyName = computerDto.getManufacturerName();
        Long companyId = Long.parseLong(computerDto.getManufacturerId());
        Company company = new Company.CompanyBuilder(companyName).id(companyId)
                .build();
        LocalDate introduceDate = toDate(computerDto.getIntroduceDate());
        LocalDate discontinuedDate = toDate(computerDto.getDiscontinuedDate());
        Long id = Long.parseLong(computerDto.getId());
        Computer computer = new Computer.ComputerBuilder(name)
                .manufacturer(company).introduceDate(introduceDate)
                .discontinuedDate(discontinuedDate).id(id).build();
        return computer;
    }

    private static LocalDate toDate(String string) {
        LocalDate date = null;
        if (StringUtils.isNotEmpty(string)) {
            DateTimeFormatter formatter = DateTimeFormatter
                    .ofPattern("yyyy-MM-dd");
            formatter = formatter.withLocale(Locale.FRANCE);
            date = LocalDate.parse(string, formatter);
        }
        return date;
    }
}
