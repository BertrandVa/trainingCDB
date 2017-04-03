package servicesTest;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.powermock.core.classloader.annotations.PrepareForTest;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.CompanyDAO;
import com.excilys.cdb.persistence.ComputerDAO;
import com.excilys.cdb.services.ClientActions;

import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.reflect.Whitebox;

import static org.powermock.api.mockito.PowerMockito.mock;
import static org.powermock.api.mockito.PowerMockito.when;

@RunWith(PowerMockRunner.class)
@PrepareForTest({ComputerDAO.class, CompanyDAO.class})
public class ClientActionsTest {
    
    /*
     * Les tests suivants ont pour but de tester la lecture de plusieurs
     * ordinateurs dans la couche de services. Plusieurs cas s'offrent à nous :
     * L'utilisateur souhaite afficher 1 seul ordinateur 
     * testé avec findsAndReadsExistingComputerByPage.
     * L'utilisateur souhaite afficher plusieurs ordinateurs
     * findsAndReadsExistingComputersByPage.
     * L'id de départ n'existe pas
     * testé avec findsAndReadsExistingCompaniesByPageWithFirstIdNotInDB
     *  findsAndReadsExistingCompaniesByPageGoingTooFar
     */
    
    
    @Test
    public void findsAndReadsExistingComputerByPage() throws Exception {
        List<Computer> list = new ArrayList<Computer>();
        Computer computer = new Computer.ComputerBuilder(null).build();
        List<Computer> liste = new ArrayList<Computer>();
        Computer computer1 = new Computer.ComputerBuilder("iPhone 4S").id(574).build();  
        liste.add(computer1);
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);
        when(compDAO.readAll(574, 1, "computer.id","")).thenReturn(liste);
        list = ClientActions.listComputers(574, 1, "computer.id", "'%'");
        computer = list.get(0);
        assertEquals(computer.getId(), 574);
        assertEquals(computer.getName(), ("iPhone 4S"));
        assertNull(computer.getManufacturer());
        assertNull(computer.getIntroduceDate());
        assertNull(computer.getDiscontinuedDate());
    }

    @Test
    public void findsAndReadsExistingComputersByPage() throws Exception {
        List<Computer> list = new ArrayList<Computer>();
        Computer computer1 = new Computer.ComputerBuilder(null).build();
        Computer computer2 = new Computer.ComputerBuilder(null).build();
        Computer computer3 = new Computer.ComputerBuilder(null).build();
        List<Computer> liste = new ArrayList<Computer>();
        Company company1 = new Company.CompanyBuilder(null).id(1).build();
        Company company2 = new Company.CompanyBuilder(null).id(2).build();
        Computer computer4 = new Computer.ComputerBuilder("Dell Vostro").id(572).introduceDate(LocalDate.of(2011, 11, 04)).discontinuedDate(LocalDate.of(2013, 10, 12)).manufacturer(company1).build();
        Computer computer5 = new Computer.ComputerBuilder("Gateway LT3103U").id(573).introduceDate(LocalDate.of(2011, 11, 04)).discontinuedDate(LocalDate.of(2013, 10, 12)).manufacturer(company2).build();  
        Computer computer6 = new Computer.ComputerBuilder("iPhone4S").id(574).introduceDate(LocalDate.of(2011, 11, 04)).build();  
        liste.add(computer4);
        liste.add(computer5);
        liste.add(computer6);
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);
        when(compDAO.readAll(572, 3, "computer.id","")).thenReturn(liste);
        list = compDAO.readAll(572, 3, "computer.id","");
        computer1 = list.get(0);
        computer2 = list.get(1);
        computer3 = list.get(2);
        assertEquals(computer1.getId(), 572);
        assertEquals(computer2.getId(), 573);
        assertEquals(computer3.getId(), 574);
        assertEquals(computer1.getName(), ("Dell Vostro"));
        assertEquals(computer2.getName(), ("Gateway LT3103U"));
        assertEquals(computer3.getName(), ("iPhone4S"));
        assertEquals(computer1.getManufacturer().getId(), 1);
        assertEquals(computer2.getManufacturer().getId(), 2);
        assertNull(computer3.getManufacturer());
        assertEquals(computer1.getIntroduceDate(), LocalDate.of(2011, 11, 04));
        assertEquals(computer2.getIntroduceDate(), LocalDate.of(2011, 11, 04));
        assertEquals(computer1.getIntroduceDate(), LocalDate.of(2011, 11, 04));
        assertEquals(computer1.getDiscontinuedDate(),
                LocalDate.of(2013, 10, 12));
        assertEquals(computer2.getDiscontinuedDate(),
                LocalDate.of(2013, 10, 12));
        assertNull(computer3.getDiscontinuedDate());
    }

    @Test
    public void findsAndReadsExistingComputersByPageWithFirstIdNotInDB()
            throws Exception {
        List<Computer> list = new ArrayList<Computer>();
        Computer computer1 = new Computer.ComputerBuilder(null).build();
        Computer computer2 = new Computer.ComputerBuilder(null).build();
        Computer computer3 = new Computer.ComputerBuilder(null).build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        List<Computer> liste = new ArrayList<Computer>();
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);
        when(compDAO.readAll(5000, 3, "computer.id","")).thenReturn(liste);
        list = compDAO.readAll(5000, 3, "computer.id","");
        if (list.size() != 0) {
            computer1 = list.get(0);
            computer2 = list.get(1);
            computer3 = list.get(2);
        }
        assertEquals(computer1.getId(), 0);
        assertEquals(computer2.getId(), 0);
        assertEquals(computer3.getId(), 0);
        assertNull(computer1.getName());
        assertNull(computer2.getName());
        assertNull(computer3.getName());
        assertNull(computer1.getManufacturer());
        assertNull(computer2.getManufacturer());
        assertNull(computer3.getManufacturer());
        assertNull(computer1.getIntroduceDate());
        assertNull(computer2.getIntroduceDate());
        assertNull(computer3.getIntroduceDate());
        assertNull(computer1.getDiscontinuedDate());
        assertNull(computer2.getDiscontinuedDate());
        assertNull(computer3.getDiscontinuedDate());
    }
    
    @Test
    public void findsAndReadsExistingComputersByPageGoingTooFar()
            throws Exception {
        List<Computer> list = new ArrayList<Computer>();
        Computer computer1 = new Computer.ComputerBuilder(null).build();
        Computer computer2 = new Computer.ComputerBuilder(null).build();
        Computer computer3 = new Computer.ComputerBuilder(null).build();
        List<Computer> liste = new ArrayList<Computer>();
        Company company2 = new Company.CompanyBuilder(null).id(1).build();
        Computer computer6 = new Computer.ComputerBuilder(null).build();
        Computer computer4 = new Computer.ComputerBuilder("Gateway LT3103U").id(573).introduceDate(LocalDate.of(2011, 11, 04)).discontinuedDate(LocalDate.of(2013, 10, 12)).manufacturer(company2).build();  
        Computer computer5 = new Computer.ComputerBuilder("iPhone4S").id(574).introduceDate(LocalDate.of(2011, 11, 04)).build();  
        liste.add(computer4);
        liste.add(computer5);
        liste.add(computer6);
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);
        when(compDAO.readAll(573, 3, "computer.id","")).thenReturn(liste);
        list = compDAO.readAll(573, 3, "computer.id","");
        if (list.size() > 0) {
            computer1 = liste.get(0);
        }
        if(list.size() > 1){
            computer2 = liste.get(1);
        }
        if (list.size() > 2){
            computer3 = liste.get(2);
        }
        assertEquals(computer1.getId(), 573);
        assertEquals(computer2.getId(), 574);
        assertEquals(computer3.getId(), 0);
        assertEquals(computer1.getName(),"Gateway LT3103U");
        assertEquals(computer2.getName(), ("iPhone4S"));
        assertNull(computer3.getName());
        assertEquals(computer1.getManufacturer().getId(), 1);
        assertNull(computer2.getManufacturer());
        assertNull(computer3.getManufacturer());
        assertEquals(computer1.getIntroduceDate(), LocalDate.of(2011, 11, 04));
        assertEquals(computer2.getIntroduceDate(), LocalDate.of(2011, 11, 04));
        assertNull(computer3.getIntroduceDate());
        assertEquals(computer1.getDiscontinuedDate(),
                LocalDate.of(2013, 10, 12));
        assertNull(computer2.getDiscontinuedDate());
        assertNull(computer3.getDiscontinuedDate());
    }

    
    /*
     * Les tests suivants ont pour but de tester la lecture de plusieurs
     * compagnies dans la couche de services. Plusieurs cas s'offrent à nous :
     * L'utilisateur souhaite afficher 1 seul compagnie 
     * testé avec findsAndReadsExistingCompanyByPage.
     * L'utilisateur souhaite afficher plusieurs ordinateurs
     * findsAndReadsExistingCompaniesByPage.
     * L'id de départ n'existe pas dans la BDD
     * testé avec findsAndReadsExistingCompaniesByPageWithFirstIdNotInDB
     *  La liste dépasse la fin de la BDD
     *  findsAndReadsExistingCompaniesByPageGoingTooFar
     */
    
    @Test
    public void findsAndReadsExistingCompanyByPage() throws Exception {
        List<Company> list = new ArrayList<Company>();
        Company company = new Company.CompanyBuilder(null).build();
        List<Company> liste = new ArrayList<Company>();
        Company company1 = new Company.CompanyBuilder("Apple Inc.").id(1).build();  
        liste.add(company1);
        CompanyDAO compDAO = mock(CompanyDAO.class);
        Whitebox.setInternalState(CompanyDAO.class,"INSTANCE", compDAO);
        when(compDAO.readAll(1, 1)).thenReturn(liste);
        list = compDAO.readAll(1, 1);
        company = list.get(0);
        assertEquals(company.getId(), 1);
        assertEquals(company.getName(), ("Apple Inc."));
    }

    @Test
    public void findsAndReadsExistingCompanysByPage() throws Exception {
        List<Company> list = new ArrayList<Company>();
        Company company1 = new Company.CompanyBuilder(null).build();
        Company company2 = new Company.CompanyBuilder(null).build();
        Company company3 = new Company.CompanyBuilder(null).build();
        List<Company> liste = new ArrayList<Company>();
        Company company4 = new Company.CompanyBuilder("Apple Inc.").id(1).build();
        Company company5 = new Company.CompanyBuilder("Thinking Machines").id(2).build();
        Company company6 = new Company.CompanyBuilder("RCA").id(3).build();
        liste.add(company4);
        liste.add(company5);
        liste.add(company6);
        CompanyDAO compDAO = mock(CompanyDAO.class);
        Whitebox.setInternalState(CompanyDAO.class,"INSTANCE", compDAO);
        when(compDAO.readAll(1, 3)).thenReturn(liste);
        list = compDAO.readAll(1, 3);
        company1 = list.get(0);
        company2 = list.get(1);
        company3 = list.get(2);
        assertEquals(company1.getId(), 1);
        assertEquals(company2.getId(), 2);
        assertEquals(company3.getId(), 3);
        assertEquals(company1.getName(), ("Apple Inc."));
        assertEquals(company2.getName(), ("Thinking Machines"));
        assertEquals(company3.getName(), ("RCA"));
    }

    @Test
    public void findsAndReadsExistingCompanysByPageWithFirstIdNotInDB()
            throws Exception {
        List<Company> list = new ArrayList<Company>();
        Company company1 = new Company.CompanyBuilder(null).build();
        Company company2 = new Company.CompanyBuilder(null).build();
        Company company3 = new Company.CompanyBuilder(null).build();
        List<Company> liste = new ArrayList<Company>();
        CompanyDAO compDAO = mock(CompanyDAO.class);
        Whitebox.setInternalState(CompanyDAO.class,"INSTANCE", compDAO);
        when(compDAO.readAll(5000, 3)).thenReturn(liste);
        list = compDAO.readAll(5000, 3);
        if (list.size() != 0) {
            company1 = list.get(0);
            company2 = list.get(1);
            company3 = list.get(2);
        }
        assertEquals(company1.getId(), 0);
        assertEquals(company2.getId(), 0);
        assertEquals(company3.getId(), 0);
        assertNull(company1.getName());
        assertNull(company2.getName());
        assertNull(company3.getName());
    }
    
    @Test
    public void findsAndReadsExistingCompanysByPageGoingTooFar()
            throws Exception {
        List<Company> list = new ArrayList<Company>();
        Company company1 = new Company.CompanyBuilder(null).build();
        Company company2 = new Company.CompanyBuilder(null).build();
        Company company3 = new Company.CompanyBuilder(null).build();
        List<Company> liste = new ArrayList<Company>();
        Company company4 = new Company.CompanyBuilder("Thinking Machines").id(2).build();
        Company company5 = new Company.CompanyBuilder("RCA").id(3).build();
        Company company6 = new Company.CompanyBuilder(null).build();
        liste.add(company4);
        liste.add(company5);
        liste.add(company6);
        CompanyDAO compDAO = mock(CompanyDAO.class);
        Whitebox.setInternalState(CompanyDAO.class,"INSTANCE", compDAO);
        when(compDAO.readAll(2, 3)).thenReturn(liste);
        list = compDAO.readAll(2, 3);
        if (list.size() > 0) {
            company1 = list.get(0);
        }
        if(list.size() > 1){
            company2 = list.get(1);
        }
        if (list.size() > 2){
            company3 = list.get(2);
        }
        assertEquals(company1.getId(), 2);
        assertEquals(company2.getId(), 3);
        assertEquals(company3.getId(), 0);
        assertEquals(company1.getName(),"Thinking Machines");
        assertEquals(company2.getName(), ("RCA"));
        assertNull(company3.getName());
    }

    /*
     * Les tests suivants ont pour but de tester la lecture d'un ordinateur dans
     * la couche de service. 3 cas s'offrent à nous : L'ordinateur n'existe pas, testé avec
     * findsAndReadsUnexistingComputer. L'ordinateur existe, mais seuls les
     * champs obligatoires sont remplis, testé avec
     * findAndReadsExistingComputerWithOnlyMandatoryParameters. L'ordinateur
     * existe et tous les champs sont remplis, testé avec
     * findsAndReadsExistingComputerWithAllParameters.
     */

    @Test
    public void findsAndReadsExistingComputerByIdWithOnlyMandatoryParameters()
            throws Exception {
        Computer computer = new Computer.ComputerBuilder(null).build();
        Computer computer1 = new Computer.ComputerBuilder("iPhone 4S").id(574).build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.read(574)).thenReturn(computer1);
        computer = compDAO.read(574);
        assertEquals(computer.getId(), 574);
        assertEquals(computer.getName(), ("iPhone 4S"));
        assertNull(computer.getManufacturer());
        assertNull(computer.getIntroduceDate());
        assertNull(computer.getDiscontinuedDate());
    }

    @Test
    public void findsAndReadsExistingComputerByIdWithAllParameters()
            throws Exception {
        Computer computer = new Computer.ComputerBuilder(null).build();        
        Company company1 = new Company.CompanyBuilder("Apple Inc.").id(1).build();
        Computer computer1 = new Computer.ComputerBuilder("Gateway LT3103U").id(573).manufacturer(company1).introduceDate(LocalDate.of(2011, 11, 04)).discontinuedDate(LocalDate.of(2013, 10, 12)).build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.read(573)).thenReturn(computer1);
        computer = compDAO.read(573);
        assertEquals(computer.getId(), 573);
        assertEquals(computer.getName(), ("Gateway LT3103U"));
        assertEquals(computer.getManufacturer().getId(), 1);
        assertEquals(computer.getManufacturer().getName(), "Apple Inc.");
        assertEquals(computer.getIntroduceDate(), LocalDate.of(2011, 11, 04));
        assertEquals(computer.getDiscontinuedDate(), LocalDate.of(2013, 10, 12));
    }

    @Test
    public void findsAndReadsUnexistingComputer() throws Exception {
        Computer computer = new Computer.ComputerBuilder(null).build();
        Computer computer1 = new Computer.ComputerBuilder(null).build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.read(580)).thenReturn(computer1);
        computer = compDAO.read(580);
        assertEquals(computer.getId(), 0);
        assertNull(computer.getName());
        assertNull(computer.getManufacturer());
        assertNull(computer.getIntroduceDate());
        assertNull(computer.getDiscontinuedDate());
    }

    /*
     * Les tests suivants ont pour but de tester l'insertion d'un champ via la
     * couche de services. Nous savons maintenant que la lecture du champ
     * fonctionne.
     * 
     * Voici un rappel des contraintes du client :
     * "only the name should remain mandatory when adding a computer, the other fields being filled when possible. Furthermore, the date it was discontinued must be greater than the one he was introduced"
     * 
     * Nous allons donc tester les cas suivants:
     * 
     * Insertion d'un ordinateur sans nom, testé avec createComputerWithoutName
     * Insertion d'un ordinateur avec uniquement un nom, testé avec
     * createComputerWithOnlyName Insertion d'un ordinateur avec tous les champs
     * remplis correctement, testé avec createComputerWithAllParametersOK
     * Insertion d'un ordinateur avec la date d'arrivée plus récente que la date
     * de départ, testé avec
     * createComputerWithDiscontinuedDateElderThanIntroduceDate Insertion d'un
     * ordinateur avec la date d'arrivée seule renseignée, testé avec
     * createComputerWithDiscontinuedDateButNoIntroduceDate
     */

    @Test
    public void createComputerWithoutName() throws Exception {
        Computer computer = new Computer.ComputerBuilder(null).build();
        Computer computer1 = new Computer.ComputerBuilder(null).build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.create(computer)).thenReturn(30l);
        when(compDAO.read(30)).thenReturn(computer1);        
        long create = 0;
        create = compDAO.create(computer);
        Computer computer2 = compDAO.read(create);
        assertNull(computer2.getName());
        assertNull(computer2.getManufacturer());
        assertNull(computer2.getIntroduceDate());
        assertNull(computer2.getDiscontinuedDate());
        assertEquals(computer2.getId(), 0);
    }

    @Test
    public void createComputerWithOnlyName() throws Exception {
        Computer computer = new Computer.ComputerBuilder("monOrdinateur")
                .build();
        Computer computer1 = new Computer.ComputerBuilder("monOrdinateur")
                .build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.create(computer)).thenReturn(30l);
        when(compDAO.read(30)).thenReturn(computer1);        
        long create = compDAO.create(computer);
        Computer computer2 = compDAO.read(create);
        assertEquals(computer2.getName(), "monOrdinateur");
        assertNull(computer2.getManufacturer());
        assertNull(computer2.getIntroduceDate());
        assertNull(computer2.getDiscontinuedDate());
    }

    @Test
    public void createComputerWithAllParametersOK() throws Exception {
        Computer computer = new Computer.ComputerBuilder("monOrdinateur")
                .id(540)
                .introduceDate(LocalDate.of(1998, 07, 03))
                .discontinuedDate(LocalDate.of(2015, 11, 26))
                .manufacturer(
                        new Company.CompanyBuilder("Apple Inc.").id(1).build())
                .build();
        Computer computer1 = new Computer.ComputerBuilder("monOrdinateur")
                .id(540)
                .introduceDate(LocalDate.of(1998, 07, 03))
                .discontinuedDate(LocalDate.of(2015, 11, 26))
                .manufacturer(
                        new Company.CompanyBuilder("Apple Inc.").id(1).build())
                .build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.create(computer)).thenReturn(30l);
        when(compDAO.read(30)).thenReturn(computer1);   
        long create = compDAO.create(computer);
        Computer computer2 = compDAO.read(create);
        assertEquals(computer2.getName(), "monOrdinateur");
        assertEquals(computer2.getManufacturer().getName(), "Apple Inc.");
        assertEquals(computer2.getManufacturer().getId(), 1);
        assertEquals(computer2.getIntroduceDate(), LocalDate.of(1998, 07, 03));
        assertEquals(computer2.getDiscontinuedDate(),
                LocalDate.of(2015, 11, 26));
    }

    @Test
    public void createComputerWithDiscontinuedDateElderThanIntroduceDate()
            throws Exception {
        Computer computer = new Computer.ComputerBuilder("monOrdinateur")
                .id(540)
                .introduceDate(LocalDate.of(2015, 11, 26))
                .discontinuedDate(LocalDate.of(1998, 07, 03))
                .manufacturer(
                        new Company.CompanyBuilder("Apple Inc.").id(1).build())
                .build();
        Computer computer1 = new Computer.ComputerBuilder("monOrdinateur")
                .id(540)
                .introduceDate(LocalDate.of(2015, 11, 26))
                .discontinuedDate(null)
                .manufacturer(
                        new Company.CompanyBuilder("Apple Inc.").id(1).build())
                .build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.create(computer)).thenReturn(30l);
        when(compDAO.read(30)).thenReturn(computer1);   
        long create = compDAO.create(computer);
        Computer computer2 = compDAO.read(create);
        assertEquals(computer2.getName(), "monOrdinateur");
        assertEquals(computer2.getManufacturer().getName(), "Apple Inc.");
        assertEquals(computer2.getManufacturer().getId(), 1);
        assertEquals(computer2.getIntroduceDate(), LocalDate.of(2015, 11, 26));
        assertNull(computer2.getDiscontinuedDate());

    }

    @Test
    public void createComputerWithDiscontinuedDateButNoIntroduceDate()
            throws Exception {
        Computer computer = new Computer.ComputerBuilder("monOrdinateur")
                .id(540)
                .discontinuedDate(LocalDate.of(1998, 07, 03))
                .manufacturer(
                        new Company.CompanyBuilder("Apple Inc.").id(1).build())
                .build();
        Computer computer1 = new Computer.ComputerBuilder("monOrdinateur")
                .id(540)
                .discontinuedDate(LocalDate.of(1998, 07, 03))
                .manufacturer(
                        new Company.CompanyBuilder("Apple Inc.").id(1).build())
                .build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.create(computer)).thenReturn(30l);
        when(compDAO.read(30)).thenReturn(computer1);   
        long create = compDAO.create(computer);
        Computer computer2 = compDAO.read(create);
        assertEquals(computer2.getName(), "monOrdinateur");
        assertEquals(computer2.getManufacturer().getName(), "Apple Inc.");
        assertEquals(computer2.getManufacturer().getId(), 1);
        assertNull(computer2.getIntroduceDate());
        assertEquals(computer2.getDiscontinuedDate(),
                LocalDate.of(1998, 07, 03));

    }

    /*
     * Les tests suivants ont pour but de tester la mise à jour d'un champ via la couche de services. 
     * Plusieurs cas s'offrent à nous : 
     * L'utilisateur met à jour des champs existants testé avec
     * updateComputerWithExistingParameters
     * L'utilisateur met tous les champs à jour
     * testé avec updateComputerWithAllParameters
     * L'utilisateur supprime le nom de l'ordinateur
     * testé avec updateComputerWithoutName
     * l'utilisateur change mal la date de départ
     * updateComputerWithBadDiscontinuedDate
     */
    
    @Test
    public void updateComputerWithoutName() throws Exception {
        Computer computer = new Computer.ComputerBuilder(null).id(563).build();
        Company company1 = new Company.CompanyBuilder(null).id(1).build();
        Computer computer1 = new Computer.ComputerBuilder("Dell Vostro").id(563).introduceDate(LocalDate.of(2011, 11, 04)).discontinuedDate(LocalDate.of(2013, 10, 12)).manufacturer(company1).build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.update(computer)).thenReturn(false);
        when(compDAO.read(computer.getId())).thenReturn(computer1);   
        boolean update = false;
        update = compDAO.update(computer);
        Computer computer2 = compDAO.read(computer.getId());
        assertNotNull(computer2.getName());
        assertNotNull(computer2.getManufacturer());
        assertNotNull(computer2.getIntroduceDate());
        assertNotNull(computer2.getDiscontinuedDate());
        assertEquals(computer2.getId(), 563);
        assertEquals(update,false);
    }
    
    @Test
    public void updateComputerWithBadDiscontinuedDate() throws Exception {
        Computer computer = new Computer.ComputerBuilder("Jean").id(563)
                .introduceDate(LocalDate.of(2011, 11, 04))
                .discontinuedDate(LocalDate.of(2010, 12, 03)).build();
        Computer computer1 = new Computer.ComputerBuilder("Jean").id(563)
                .introduceDate(LocalDate.of(2011, 11, 04)).build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.update(computer)).thenReturn(true);
        when(compDAO.read(computer.getId())).thenReturn(computer1); 
        boolean update = false;
        update = compDAO.update(computer);
        Computer computer2 = compDAO.read(563);
        assertEquals(computer2.getName(),"Jean");
        assertNull(computer2.getManufacturer());
        assertEquals(computer2.getIntroduceDate(),LocalDate.of(2011, 11, 04));
        assertNull(computer2.getDiscontinuedDate());
        assertEquals(computer2.getId(), 563);
        assertEquals(update,true);
    }
    
    @Test
    public void updateComputerWithExistingParameters() throws Exception {
        Computer computer = new Computer.ComputerBuilder("jean").id(563).build();
        Computer computer1 = new Computer.ComputerBuilder("jean").id(563).build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.update(computer)).thenReturn(true);
        when(compDAO.read(563)).thenReturn(computer1); 
        boolean update = false;
        update = compDAO.update(computer);
        Computer computer2 = compDAO.read(563);
        assertEquals(computer2.getName(),"jean");
        assertNull(computer2.getManufacturer());
        assertNull(computer2.getIntroduceDate());
        assertNull(computer2.getDiscontinuedDate());
        assertEquals(computer2.getId(), 563);
        assertEquals(update,true);
    }

    @Test
    public void updateComputerWithAllParameters() throws Exception {
        Company company = new Company.CompanyBuilder("Apple de Terre")
                                     .id(2)
                                      .build();
        Company company1 = new Company.CompanyBuilder("Thinking Machines")
                .id(2)
                 .build();
        Computer computer = new Computer.ComputerBuilder("jean")
                .id(563)
                .introduceDate(LocalDate.of(1998, 03, 10))
                .discontinuedDate(LocalDate.of(2016, 12, 28))
                .manufacturer(company)
                .build();
       Computer computer1 = new Computer.ComputerBuilder("jean")
               .id(563)
               .introduceDate(LocalDate.of(1998, 03, 10))
               .discontinuedDate(LocalDate.of(2016, 12, 28))
               .manufacturer(company1)
               .build();
       ComputerDAO compDAO = mock(ComputerDAO.class);
       Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
       when(compDAO.update(computer)).thenReturn(true);
       when(compDAO.read(563)).thenReturn(computer1); 
        boolean update = false;
        update = compDAO.update(computer);
        Computer computer2 = compDAO.read(563);
        assertEquals(computer2.getName(),"jean");
        assertEquals(computer2.getManufacturer().getName(), "Thinking Machines"); //en effet, la compagnie Apple de Terre n'existe pas dans la BDD ;)
        assertEquals(computer2.getManufacturer().getId(), 2);
        assertEquals(computer2.getIntroduceDate(), LocalDate.of(1998, 03, 10));
        assertEquals(computer2.getDiscontinuedDate(), LocalDate.of(2016, 12, 28));
        assertEquals(computer2.getId(), 563);
        assertEquals(update,true);
    }

    /*
     * Les tests suivants ont pour but de tester la suppression d'un champ via la couche de services. 
     * Deux cas s'offrent à nous : 
     * Le champ existe
     * testé avec DeleteExistingComputer
     * Le champ n'existe pas
     * testé avec DeleteUnexistingComputer
     */
    
    @Test
    public void DeleteExistingComputer() throws Exception {
        Computer computer = new Computer.ComputerBuilder(null).id(561).build();
        Computer computer1 = new Computer.ComputerBuilder(null).build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.delete(computer.getId())).thenReturn(true);
        when(compDAO.read(computer.getId())).thenReturn(computer1); 
        boolean delete = false;
        delete = compDAO.delete(computer.getId());
        Computer computer2 = compDAO.read(computer.getId());
        assertNull(computer2.getName());
        assertNull(computer2.getManufacturer());
        assertNull(computer2.getIntroduceDate());
        assertNull(computer2.getDiscontinuedDate());
        assertEquals(computer2.getId(), 0);
        assertEquals(delete, true);
    }
    
    @Test
    public void DeleteUnexistingComputer() throws Exception {
        Computer computer = new Computer.ComputerBuilder(null).id(2000).build();
        Computer computer1 = new Computer.ComputerBuilder(null).build();
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.delete(computer.getId())).thenReturn(false);
        when(compDAO.read(computer.getId())).thenReturn(computer1); 
        boolean delete = false;
        delete = compDAO.delete(computer.getId());
        Computer computer2 = compDAO.read(computer.getId());
        assertNull(computer2.getName());
        assertNull(computer2.getManufacturer());
        assertNull(computer2.getIntroduceDate());
        assertNull(computer2.getDiscontinuedDate());
        assertEquals(computer2.getId(), 0);
        assertEquals(delete, false);
    }

    /*
     * Le test suivant a pour but de tester le décompte des ordinateurs via la couche de services. 
     */
    
    @Test
    public void TestCountComputers() throws Exception {
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.countComputer()).thenReturn(15);
        when(compDAO.delete(561)).thenReturn(true);
        int count1 = compDAO.countComputer();
        when(compDAO.countComputer()).thenReturn(14);
        boolean delete = compDAO.delete(561);
        int count2 = compDAO.countComputer();
        assertEquals(count1, 15);
        assertEquals(count2, 14);
        assertTrue(delete);
    }

    /*
     * Le test suivant a pour but de tester le décompte des compagnies via la couche de services. 
     * Il n'est pas censé y avoir de variations dans cette base de données
     */
    
    @Test
    public void TestCountCompanies() throws Exception {
        CompanyDAO compDAO = mock(CompanyDAO.class);
        Whitebox.setInternalState(CompanyDAO.class,"INSTANCE", compDAO);
        when(compDAO.countCompanies()).thenReturn(3);
        int count1 = compDAO.countCompanies();
        assertEquals(count1, 3);
    }

    /*
     * Le test suivant a pour but de tester le décompte des pages via la couche de services. 
     */
    
    @Test
    public void TestCountPages() throws Exception {
        ComputerDAO compDAO = mock(ComputerDAO.class);
        Whitebox.setInternalState(ComputerDAO.class,"INSTANCE", compDAO);        
        when(compDAO.countPages(3)).thenReturn(5);
        when(compDAO.countPages(5)).thenReturn(3);
        when(compDAO.countPages(0)).thenReturn(0);
        int count1 = compDAO.countPages(3);
        int count2 = compDAO.countPages(5);
        int count3 = compDAO.countPages(0);
        assertEquals(count1, 5);
        assertEquals(count2, 3);
        assertEquals(count3, 0);
    }
}
