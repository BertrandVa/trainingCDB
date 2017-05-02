package com.excilys.cdb.core.mapper;

import com.excilys.cdb.core.dto.ComputerDTO;
import com.excilys.cdb.core.model.Computer;

public class ComputerMapperPojoDTO {

    /**
     * @param computer
     *                 l'ordinateur à mapper
     * @return
     *      l'ordinateur mappé
     */
    public static ComputerDTO mapper(Computer computer) {
        String name = computer.getName();
        String manufacturerId = String
                .valueOf(computer.getManufacturer().getId());
        String manufacturerName = computer.getManufacturer().getName();
        String introduceDate = null;
        if ((computer.getIntroduceDate() != null)) {
            introduceDate = computer.getIntroduceDate().toString();
        }
        String discontinuedDate = null;
        if (computer.getDiscontinuedDate() != null) {
            discontinuedDate = computer.getDiscontinuedDate().toString();
        }
        String id = String.valueOf(computer.getId());
        ComputerDTO computerDto = new ComputerDTO(name, manufacturerId,
                manufacturerName, introduceDate, discontinuedDate, id);
        return computerDto;
    }

}
