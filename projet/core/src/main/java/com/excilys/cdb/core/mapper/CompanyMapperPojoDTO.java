package com.excilys.cdb.core.mapper;

import com.excilys.cdb.core.dto.CompanyDTO;
import com.excilys.cdb.core.model.Company;

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
