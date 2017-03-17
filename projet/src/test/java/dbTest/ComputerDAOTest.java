package dbTest;

import static org.junit.Assert.*;
import java.io.File;
import java.time.LocalDate;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ReplacementDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.Before;
import org.junit.Test;

import com.excilys.cdb.model.Company;
import com.excilys.cdb.model.Computer;
import com.excilys.cdb.persistence.ComputerDAO;

public class ComputerDAOTest {

    private static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    private static final String JDBC_URL = "jdbc:mysql://localhost/dbUnit?zeroDateTimeBehavior=convertToNull";
    private static final String USER = "admindb";
    private static final String PASSWORD = "qwerty1234";

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
     * la BDD 3 cas s'offrent à nous : L'ordinateur n'existe pas, testé avec
     * findsAndReadsUnexistingComputer L'ordinateur existe, mais seuls les
     * champs obligatoires sont remplis, testé avec
     * findAndReadsExistingComputerWithOnlyMandatoryParameters L'ordinateur
     * existe et tous les champs sont remplis, testé avec
     * findsAndReadsExistingComputerWithAllParameters
     */

    @Test
    public void findsAndReadsExistingComputerByIdWithOnlyMandatoryParameters()
            throws Exception {
        Computer computer = new Computer.ComputerBuilder(null).build();
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
        computer = compDAO.read(574);
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
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
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
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
        computer = compDAO.read(580);
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
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
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
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
        long create = compDAO.create(computer);
        Computer computer2 = compDAO.read(create);
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
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
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
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
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
        ComputerDAO compDAO = ComputerDAO.INSTANCE;
        long create = compDAO.create(computer);
        Computer computer2 = compDAO.read(create);
        assertEquals(computer2.getName(), "monOrdinateur");
        assertEquals(computer2.getManufacturer().getName(), "Apple Inc.");
        assertEquals(computer2.getManufacturer().getId(), 1);
        assertNull(computer2.getIntroduceDate());
        assertEquals(computer2.getDiscontinuedDate(),
                LocalDate.of(1998, 07, 03));

    }
}
