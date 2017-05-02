package dbTest;

import static org.junit.Assert.*;
import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.excilys.cdb.core.model.Company;
import com.excilys.cdb.core.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerDAOTest {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost/dbUnit?zeroDateTimeBehavior=convertToNull";
    private static final String USER = "admindb";
    private static final String PASSWORD = "qwerty1234";
    static ApplicationContext context =
            new ClassPathXmlApplicationContext("Spring-Modules.xml");
    ComputerDAO computerDAO = (ComputerDAO) context.getBean("computerDAO");     

    @Before
    public void importDataSet() throws Exception {
        IDataSet dataSet = readDataSet();
        ReplacementDataSet dataSetter = new ReplacementDataSet(dataSet);
        dataSetter.addReplacementObject("[NULL]", null);
        cleanlyInsert(dataSetter);
    }

    private IDataSet readDataSet() throws Exception {
        return new FlatXmlDataSetBuilder().build(new File(
                "src/test/java/dbTest/dataset.xml"));
    }

    private void cleanlyInsert(ReplacementDataSet dataSet) {
        try {
            IDatabaseTester databaseTester = new JdbcDatabaseTester(
                    JDBC_DRIVER, JDBC_URL, USER, PASSWORD);
            databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
            databaseTester.setDataSet(dataSet);
            databaseTester.onSetup();
        } catch (Exception e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /*
     * Les tests suivants ont pour but de tester la lecture d'un ordinateur dans
     * la BDD. 3 cas s'offrent à nous : L'ordinateur n'existe pas, testé avec
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
        computer = computerDAO.read(574);
        assertEquals(computer.getId(), 574);
        assertEquals(computer.getName(), ("iPhone 4S"));
        // si on ne précise pas l'id, celui-ci ne doit pas correspondre à un
        // fabriquant existant
        assertEquals(computer.getManufacturer().getId(), (0));
        // si le fabriquant n'existe pas, aucun nom ne doit être visible
        assertNull(computer.getManufacturer().getName());
        // si la date n'est pas précisée, elle doit être nulle
        assertNull(computer.getIntroduceDate());
        // si la date n'est pas précisée, elle doit être nulle
        assertNull(computer.getDiscontinuedDate());
    }

    @Test
    public void findsAndReadsExistingComputerByIdWithAllParameters()
            throws Exception {
        Computer computer = new Computer.ComputerBuilder(null).build();
        computer = computerDAO.read(573);
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
        computer = computerDAO.read(580);
        assertEquals(computer.getId(), 0);
        assertNull(computer.getName());
        assertNull(computer.getManufacturer());
        assertNull(computer.getIntroduceDate());
        assertNull(computer.getDiscontinuedDate());
    }

    /*
     * Les tests suivants ont pour but de tester l'insertion d'un champ dans la
     * base de données Nous savons maintenant que la lecture du champ
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
        long create = 0;
        create = computerDAO.create(computer);
        Computer computer2 = computerDAO.read(create);
        assertNull(computer2.getName());
        assertNull(computer2.getManufacturer());
        assertNull(computer2.getIntroduceDate());
        assertNull(computer2.getDiscontinuedDate());
    }

    @Test
    public void createComputerWithOnlyName() throws Exception {
        Computer computer = new Computer.ComputerBuilder("monOrdinateur")
                .build();
        long create = computerDAO.create(computer);
        Computer computer2 = computerDAO.read(create);
        assertEquals(computer2.getName(), "monOrdinateur");
        assertNull(computer2.getManufacturer().getName());
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
        long create = computerDAO.create(computer);
        Computer computer2 = computerDAO.read(create);
        assertEquals(computer2.getName(), "monOrdinateur");
        assertEquals(computer2.getManufacturer().getName(), "Apple Inc.");
        assertEquals(computer2.getManufacturer().getId(), 1);
        assertEquals(computer2.getIntroduceDate(), LocalDate.of(1998, 07, 03));
        assertEquals(computer2.getDiscontinuedDate(),
                LocalDate.of(2015, 11, 26));
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
        long create = computerDAO.create(computer);
        Computer computer2 = computerDAO.read(create);
        assertEquals(computer2.getName(), "monOrdinateur");
        assertEquals(computer2.getManufacturer().getName(), "Apple Inc.");
        assertEquals(computer2.getManufacturer().getId(), 1);
        assertNull(computer2.getIntroduceDate());
        assertEquals(computer2.getDiscontinuedDate(),
                LocalDate.of(1998, 07, 03));

    }

    /*
     * Les tests suivants ont pour but de tester la lecture de plusieurs
     * ordinateurs dans la BDD. Plusieurs cas s'offrent à nous : L'utilisateur
     * souhaite afficher 1 seul ordinateur testé avec
     * findsAndReadsExistingComputerByPage. L'utilisateur souhaite afficher
     * plusieurs ordinateurs findsAndReadsExistingComputersByPage(). L'id de
     * départ n'existe pas dans la BDD
     * testé avec findsAndReadsExistingComputersByPageWithFirstIdNotInDB
     *  La liste dépasse la fin de la BDD
     *  findsAndReadsExistingComputersByPageGoingTooFar
     */

    @Test
    public void findsAndReadsExistingComputerByPage() throws Exception {
        List<Computer> list = new ArrayList<Computer>();
        Computer computer = new Computer.ComputerBuilder(null).build();
        list = computerDAO.readAll(14, 1, "'%'", "computer.id");
        computer = list.get(0);
        assertEquals(computer.getId(), 574);
        assertEquals(computer.getName(), ("iPhone 4S"));
        assertEquals(computer.getManufacturer().getId(), (0));
        assertNull(computer.getManufacturer().getName());
        assertNull(computer.getIntroduceDate());
        assertNull(computer.getDiscontinuedDate());
    }

    @Test
    public void findsAndReadsExistingComputersByPage() throws Exception {
        List<Computer> list = new ArrayList<Computer>();
        Computer computer1 = new Computer.ComputerBuilder(null).build();
        Computer computer2 = new Computer.ComputerBuilder(null).build();
        Computer computer3 = new Computer.ComputerBuilder(null).build();
        list = computerDAO.readAll(12, 3, "'%'", "computer.id");
        computer1 = list.get(0);
        computer2 = list.get(1);
        computer3 = list.get(2);
        assertEquals(computer1.getId(), 572);
        assertEquals(computer2.getId(), 573);
        assertEquals(computer3.getId(), 574);
        assertEquals(computer1.getName(), ("Dell Vostro"));
        assertEquals(computer2.getName(), ("Gateway LT3103U"));
        assertEquals(computer3.getName(), ("iPhone 4S"));
        assertEquals(computer1.getManufacturer().getId(), (2));
        assertEquals(computer2.getManufacturer().getId(), (1));
        assertEquals(computer3.getManufacturer().getId(), (0));
        assertEquals(computer1.getManufacturer().getName(), "Thinking Machines");
        assertEquals(computer2.getManufacturer().getName(), "Apple Inc.");
        assertNull(computer3.getManufacturer().getName());
        assertEquals(computer1.getIntroduceDate(), LocalDate.of(2011, 11, 04));
        assertEquals(computer2.getIntroduceDate(), LocalDate.of(2011, 11, 04));
        assertNull(computer3.getIntroduceDate());
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
        list = computerDAO.readAll(5000, 3, "'%'", "computer.id");
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
        list = computerDAO.readAll(13, 3, "'%'", "computer.id");
        if (list.size() > 0) {
            computer1 = list.get(0);
        }
        if(list.size() > 1){
            computer2 = list.get(1);
        }
        if (list.size() > 2){
            computer3 = list.get(2);
        }
        assertEquals(computer1.getId(), 573);
        assertEquals(computer2.getId(), 574);
        assertEquals(computer3.getId(), 0);
        assertEquals(computer1.getName(),"Gateway LT3103U");
        assertEquals(computer2.getName(), ("iPhone 4S"));
        assertNull(computer3.getName());
        assertEquals(computer1.getManufacturer().getId(), 1);
        assertEquals(computer2.getManufacturer().getId(), 0);
        assertEquals(computer1.getManufacturer().getName(), "Apple Inc.");
        assertNull(computer2.getManufacturer().getName());
        assertNull(computer3.getManufacturer());
        assertEquals(computer1.getIntroduceDate(), LocalDate.of(2011, 11, 04));
        assertNull(computer2.getIntroduceDate());
        assertNull(computer3.getIntroduceDate());
        assertEquals(computer1.getDiscontinuedDate(),
                LocalDate.of(2013, 10, 12));
        assertNull(computer2.getDiscontinuedDate());
        assertNull(computer3.getDiscontinuedDate());
    }


    /*
     * Les tests suivants ont pour but de tester la mise à jour d'un champ dans la BDD. 
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
        computerDAO.update(computer);
        Computer computer2 = computerDAO.read(computer.getId());
        assertNull(computer2.getName());
        assertNotNull(computer2.getManufacturer());
        assertNull(computer2.getIntroduceDate());
        assertNull(computer2.getDiscontinuedDate());
        assertEquals(computer2.getId(), 563);
    }
    
    @Test
    public void updateComputerWithExistingParameters() throws Exception {
        Computer computer = new Computer.ComputerBuilder("jean").id(563).build();
        computerDAO.update(computer);
        Computer computer2 = computerDAO.read(563);
        assertEquals(computer2.getName(),"jean");
        assertEquals(computer2.getManufacturer().getId(), 1);
        assertEquals(computer2.getId(), 563);
    }

    @Test
    public void updateComputerWithAllParameters() throws Exception {
        Company company = new Company.CompanyBuilder("Apple de Terre")
                                     .id(2)
                                      .build();
        Computer computer = new Computer.ComputerBuilder("jean")
                .id(563)
                .introduceDate(LocalDate.of(1998, 03, 10))
                .discontinuedDate(LocalDate.of(2016, 12, 28))
                .manufacturer(company)
                .build();
        computerDAO.update(computer);
        Computer computer2 = computerDAO.read(563);
        assertEquals(computer2.getName(),"jean");
        assertEquals(computer2.getManufacturer().getName(), "Thinking Machines"); //en effet, la compagnie Apple de Terre n'existe pas dans la BDD ;)
        assertEquals(computer2.getManufacturer().getId(), 2);
        assertEquals(computer2.getIntroduceDate(), LocalDate.of(1998, 03, 10));
        assertEquals(computer2.getDiscontinuedDate(), LocalDate.of(2016, 12, 28));
        assertEquals(computer2.getId(), 563);
    }

    /*
     * Les tests suivants ont pour but de tester la suppression d'un champ dans la BDD. 
     * Deux cas s'offrent à nous : 
     * Le champ existe
     * testé avec DeleteExistingComputer
     * Le champ n'existe pas
     * testé avec DeleteUnexistingComputer
     */
    
    @Test
    public void DeleteExistingComputer() throws Exception {
        Computer computer = new Computer.ComputerBuilder(null).id(561).build();
        computerDAO.delete(computer.getId());
        Computer computer2 = computerDAO.read(computer.getId());
        assertNull(computer2.getName());
        assertNull(computer2.getManufacturer());
        assertNull(computer2.getIntroduceDate());
        assertNull(computer2.getDiscontinuedDate());
        assertEquals(computer2.getId(), 0);
    }
    
    @Test
    public void DeleteUnexistingComputer() throws Exception {
        Computer computer = new Computer.ComputerBuilder(null).id(2000).build();
        computerDAO.delete(computer.getId());
        Computer computer2 = computerDAO.read(computer.getId());
        assertNull(computer2.getName());
        assertNull(computer2.getManufacturer());
        assertNull(computer2.getIntroduceDate());
        assertNull(computer2.getDiscontinuedDate());
        assertEquals(computer2.getId(), 0);
    }

    /*
     * Le test suivant a pour but de tester le décompte des ordinateurs dans la BDD. 
     */
    
    @Test
    public void TestCountComputers() throws Exception {
        int count1 = computerDAO.countComputer("'%'");
        computerDAO.delete(561);
        int count2 = computerDAO.countComputer("'%'");
        assertEquals(count1, 15);
        assertEquals(count2, 14);
    }
    
    /*
     * Le test suivant a pour but de tester le décompte des pages dans la BDD. 
     */
    
    @Test
    public void TestCountPages() throws Exception {
        int count1 = computerDAO.countPages(3, "'%'");
        int count2 = computerDAO.countPages(5, "'%'");
        int count3 = computerDAO.countPages(0, "'%'");
        assertEquals(count1, 5);
        assertEquals(count2, 3);
        assertEquals(count3, 0);
    }
}