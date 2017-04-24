package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.CompanyDTO;
import com.excilys.cdb.model.Company;

public class CompanyMapperPojoDTO {

    /**
     * @param company
     *         la compagnie
     * @return
     *        la DTO
     */
    public static CompanyDTO mapper(Company company) {
        String id = String.valueOf(company.getId());
        String name = company.getName();
        CompanyDTO compdto = new CompanyDTO(name, id);
        return compdto;
    }
}
