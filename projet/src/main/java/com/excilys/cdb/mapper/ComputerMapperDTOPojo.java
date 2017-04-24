package com.excilys.cdb.mapper;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

import org.apache.commons.lang3.StringUtils;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;

public class ComputerMapperDTOPojo {

    /**
     * @param computerDto
     *                  notre dto
     * @return
     *         l'ordinateur
     */
    public static Computer mapper(ComputerDTO computerDto) {
        Long id;
        String name = computerDto.getName();
        String companyName = computerDto.getManufacturerName();
        Long companyId = Long.parseLong(computerDto.getManufacturerId());
        Company company = new Company.CompanyBuilder(companyName).id(companyId)
                .build();
        LocalDate introduceDate = toDate(computerDto.getIntroduceDate());
        LocalDate discontinuedDate = toDate(computerDto.getDiscontinuedDate());
        if (computerDto.getId() != null) {
            id = Long.parseLong(computerDto.getId());
        } else {
            id = 0L;
        }
        Computer computer = new Computer.ComputerBuilder(name)
                .manufacturer(company).introduceDate(introduceDate)
                .discontinuedDate(discontinuedDate).id(id).build();
        return computer;
    }

    /**
     * @param string
     *              La date en chaine de caractères
     * @return
     *        La date en localdate
     */
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
