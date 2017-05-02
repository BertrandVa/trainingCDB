package com.excilys.cdb.core.mapper;

import com.excilys.cdb.core.dto.CompanyDTO;
import com.excilys.cdb.core.model.Company;

public class CompanyMapperDTOPojo {

    /**
     * @param companyDto
     *        notre dto
     * @return company
     *         la compagnie
     */
    public Company mapper(CompanyDTO companyDto) {
        Long id = Long.parseLong(companyDto.getId());
        String name = companyDto.getName();
        Company company = new Company.CompanyBuilder(name).id(id).build();
        return company;
    }

}
