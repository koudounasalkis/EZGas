package it.polito.ezgas;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.context.SpringBootTest;

import it.polito.ezgas.integration.*;
import it.polito.ezgas.unit.*;


@RunWith(Suite.class)

/*
 * SuiteClasses Unit Tests

@SuiteClasses({ UserDtoTest.class, PriceReportDtoTest.class, LoginDtoTest.class, IdPwDtoTest.class, GasStationDtoTest.class, 
	 UserRepositoryTest.class, GasStationRepositoryTest.class, 
	 UserTest.class, GasStationTest.class }) 

 */

/*
 * SuiteClasses Integration Tests
 * @SuiteClasses( { GasStationServiceConverterRepositoryTest.class,GasStationServiceRepositoryTest.class, 
	GasStationServiceConverterTest.class, GasStationServiceTest.class, UserServiceConverterRepositoryTest.class, 
	UserServiceRepositoryTest.class, UserServiceConverterTest.class, UserServiceTest.class } )
 */


/*
 * SuiteClasses System Tests
 */ @SuiteClasses({ GasStationServiceConverterRepositoryTest.class,GasStationServiceRepositoryTest.class, GasStationServiceConverterTest.class, GasStationServiceTest.class, 
	UserServiceConverterRepositoryTest.class, UserServiceRepositoryTest.class, UserServiceConverterTest.class, UserServiceTest.class, 
	UserDtoTest.class, LoginDtoTest.class, IdPwDtoTest.class, GasStationDtoTest.class, PriceReportDtoTest.class, 
	UserRepositoryTest.class, GasStationRepositoryTest.class, 
	UserTest.class, GasStationTest.class, 
	PerformanceTesting.class, })



@SpringBootTest
public class EZGasApplicationTests {

	@Test
	public void contextLoads() {
		
	}

}
