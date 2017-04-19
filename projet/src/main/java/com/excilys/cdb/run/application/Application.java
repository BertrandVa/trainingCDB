package com.excilys.cdb.run.application;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.persistence.ComputerDAO;

public class Application {
    
    public static void main( String[] args )
    {
        ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Modules.xml");
        ComputerDAO computerDAO = (ComputerDAO) context.getBean("computerDAO");       
    }

}
