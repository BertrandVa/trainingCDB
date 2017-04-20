package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyMapperDTOPojo {

    public Company mapper(CompanyDTO companyDto){
        Long id = Long.parseLong(companyDto.getId());
        String name = companyDto.getName();
        Company company = new Company.CompanyBuilder(name).id(id).build();       
        return company;
    }

}
