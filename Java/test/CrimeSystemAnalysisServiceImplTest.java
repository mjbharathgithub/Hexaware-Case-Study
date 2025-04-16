import static org.junit.jupiter.api.Assertions.*;

import java.sql.Connection;
import java.sql.Date;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import com.hexawareTraining.crimeAnalysisAndReportingSystem.entity.Incident;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.exception.IllegalUserInputException;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.util.DBConnUtil;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.service.*;
import com.hexawareTraining.crimeAnalysisAndReportingSystem.service.GetInputImpl;


class CrimeSystemAnalysisServiceImplTest {
  
	static CrimeAnalysisServiceImpl processor;
	static GetInputImpl input;
	static Incident incident;
	@BeforeAll
	static void createProcessor() {
		processor= new CrimeAnalysisServiceImpl();
		input = new GetInputImpl();
	}
	
	
	
	
	@Test
	void testConnection() {
		 Connection actualValue =DBConnUtil.getConn();
		assertNotNull(actualValue,"DB Connection is a Failure");
		
	}
	
	@Test
	void testIncidentIncreation() {
		
		 incident = new Incident(0,"Testing",java.sql.Date.valueOf("2003-01-01"),"SimsPark","Testing Using JUNIT","Open",2,2);
		
		Boolean expectedValue = true;
		Boolean actualValue=processor.createIncident(incident);
		
		assertEquals(expectedValue,actualValue,"The test failed Joseph");
	}
	
	private boolean compareObjects(Incident incident1,Incident incident2) {
		return (incident1.getIncidentId()==incident2.getIncidentId()&& 
				incident1.getDescription().equals(incident2.getDescription())&&
				incident1.getIncidentDate().equals(incident2.getIncidentDate())&& 
				incident1.getLocation().equals(incident2.getLocation())&&
				incident1.getSuspectId()==incident2.getSuspectId()&&
				incident1.getVictimId()==incident2.getVictimId());
		
	}
	
	

	
	
	@Test
	void testAccuracyOfCreatedIncident() {
		incident =new Incident(1,"Theft",java.sql.Date.valueOf("2025-04-05"),"T. Nagar, Chennai","Mobile phone stolen from pedestrian.","Open",1,1);
		Incident actualValue= processor.searchIncidents("1").get(0);
		assertTrue(compareObjects(incident,actualValue));
	}
	@Test
	void testUpdateIncidentMethod() {
		boolean actualValue= processor.updateIncidentStatus("closed",2);
		boolean expectedValue=true;
		
		assertEquals(expectedValue, actualValue);
		
		
		
	}
	@Test
	void testUpdateIncidentMethodException() {
		
		IllegalUserInputException exception= assertThrows(IllegalUserInputException.class, ()->{
			input.getStatus();
			
		});
		assertEquals(IllegalUserInputException.class, exception.getClass());
		
		
	}
	
	

}

