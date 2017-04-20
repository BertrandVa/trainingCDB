package com.excilys.cdb.mapper;

import com.excilys.cdb.dto.ComputerDTO;
import com.excilys.cdb.model.Computer;

public class ComputerMapperPojoDTO {
    
    public ComputerDTO Mapper(Computer computer){
        String name = computer.getName();
        String manufacturerId = String.valueOf(computer.getManufacturer().getId());
        String manufacturerName = computer.getManufacturer().getName();
        String introduceDate = computer.getIntroduceDate().toString();
        String discontinuedDate = computer.getDiscontinuedDate().toString();
        String id = String.valueOf(computer.getId());
        ComputerDTO computerDto = new ComputerDTO(name, manufacturerId, manufacturerName, introduceDate, discontinuedDate, id);
        return computerDto;
    }

}
