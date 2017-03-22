package dbTest;

import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import com.excilys.cdb.model.Company;
import com.excilys.cdb.persistence.CompanyDAO;


public class CompanyDAOTest {

    /*
     * Les tests suivants ont pour but de tester la lecture de plusieurs
     * compagnies dans la BDD. Plusieurs cas s'offrent à nous :
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
        CompanyDAO compDAO = CompanyDAO.INSTANCE;
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
        CompanyDAO compDAO = CompanyDAO.INSTANCE;
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
        CompanyDAO compDAO = CompanyDAO.INSTANCE;
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
        CompanyDAO compDAO = CompanyDAO.INSTANCE;
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
     * Le test suivant a pour but de tester le décompte des compagnies dans la BDD. 
     * Il n'est pas censé y avoir de variations dans cette base de données
     */
    
    @Test
    public void TestCountCompanies() throws Exception {
        CompanyDAO compDAO = CompanyDAO.INSTANCE;
        int count1 = compDAO.countCompanies();
        assertEquals(count1, 3);
    }
    
}
