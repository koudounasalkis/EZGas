package it.polito.ezgas.controllertests;

import java.io.IOException;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import it.polito.ezgas.dto.GasStationDto;
import it.polito.ezgas.dto.IdPw;
import it.polito.ezgas.dto.PriceReportDto;
import it.polito.ezgas.dto.UserDto;
import net.minidev.json.JSONObject;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;


@TestMethodOrder(OrderAnnotation.class)
public class TestController {
	
	private static Integer gsDtoId;
	private static Integer uDtoId;

	
	@Test
	@Order(1)
	public void testSaveGasStation() throws ClientProtocolException, IOException {

		HttpPost request = new HttpPost("http://localhost:8080:/gasstation/saveGasStation");

		String jsonGasStation = "{"
	    		+ "\"gasStationName\": \"Torino 1\","
	    		+ "\"gasStationAddress\": \"Corso Inghilterra Torino Piemonte\","
	    		+ "\"hasDiesel\": true," 
	    		+ "\"hasSuper\": false," 
	    		+ "\"hasSuperPlus\": true," 
	    		+ "\"hasGas\": false," 
	    		+ "\"hasMethane\": true," 
	    		+ "\"hasPremiumDiesel\": false," 
	    		+ "\"carSharing\": \"Enjoy\","
	    		+ "\"lat\": 45.07038,"
	    		+ "\"lon\": 7.68581,"
	    		+ "\"dieselPrice\": 1.51,"
	    		+ "\"superPrice\": 0.0,"
	    		+ "\"superPlusPrice\": 1.65,"
	    		+ "\"gasPrice\": 0.0,"
	    		+ "\"methanePrice\": 0.92,"
	    		+ "\"premiumDieselPrice\": 1.01,"
	    		+ "\"reportUser\": -1,"
	    		+ "\"reportTimestamp\": null,"
	    		+ "\"reportDependability\": 0.0"
	    		+ "}";
		
	    StringEntity entity = new StringEntity(jsonGasStation);
	    
	    request.setEntity(entity);
	    request.setHeader("Accept", "application/json");
	    request.setHeader("Content-type", "application/json");
		
		HttpResponse response = HttpClientBuilder.create().build().execute(request);

		assert(response.getStatusLine().getStatusCode() == 200);

	}
	
	
	@Test
	@Order(2)
	public void testGetAllGasStations() throws ClientProtocolException, IOException {
		
		HttpUriRequest request = new HttpGet("http://localhost:8080:/gasstation/getAllGasStations");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);
		
		String jsonFromResponse = EntityUtils.toString(response.getEntity());	
		
		assert(jsonFromResponse.contains("Corso Inghilterra"));
		
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		GasStationDto[] gasStationArray = mapper.readValue(jsonFromResponse, GasStationDto[].class);
		
		for (GasStationDto gsDto : gasStationArray) {
			if (gsDto.getGasStationName().equals("Torino 1") && gsDto.getGasStationAddress().equals("Corso Inghilterra Torino Piemonte"))
				gsDtoId = gsDto.getGasStationId();	
		}
		
	}
	
	
	@Test
	@Order(3)
	public void testGetGasStationById() throws ClientProtocolException, IOException {
				
		HttpUriRequest request = new HttpGet("http://localhost:8080:/gasstation/getGasStation/" + gsDtoId);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);
		
		String jsonFromResponse = EntityUtils.toString(response.getEntity());	
		
		assert(jsonFromResponse.contains("Corso Inghilterra"));

	}
	
	
	@Test
	@Order(4)
	public void testGetGasStationsByGasolineType() throws ClientProtocolException, IOException {
		
		HttpUriRequest request = new HttpGet("http://localhost:8080:/gasstation/searchGasStationByGasolineType/Diesel");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);
		
		String jsonFromResponseDiesel = EntityUtils.toString(response.getEntity());	
		
		assert(jsonFromResponseDiesel.contains("Corso Inghilterra"));
		
	}
	
	
	@Test
	@Order(5)
	public void testGetGasStationsByProximity() throws ClientProtocolException, IOException {
		
		HttpUriRequest request = new HttpGet("http://localhost:8080:/gasstation/searchGasStationByProximity/45.0706476/7.6652976/2");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);
		
		String jsonFromResponse = EntityUtils.toString(response.getEntity());	
		
		assert(jsonFromResponse.contains("Corso Inghilterra"));

	}
	
	
	@Test
	@Order(6)
	public void testGetGasStationsWithCoordinates() throws ClientProtocolException, IOException {
		
		HttpUriRequest request = new HttpGet("http://localhost:8080:/gasstation/getGasStationsWithCoordinates/45.0706476/7.6652976/3/Diesel/Enjoy");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);
		
		String jsonFromResponse = EntityUtils.toString(response.getEntity());	
		
		assert(jsonFromResponse.contains("Corso Inghilterra"));

	}	
	
	
	@Test
	@Order(7)
	public void testSetGasStationReport() throws ClientProtocolException, IOException {

		PriceReportDto prDto = new PriceReportDto(1, 1.53, 0.0, 1.73, 0.0, 0.83, 0.99, 1);
		JSONObject jsonObjectPriceReport = new JSONObject();
		jsonObjectPriceReport.put("gasStationId", prDto.getGasStationId());
		jsonObjectPriceReport.put("dieselPrice", prDto.getDieselPrice());
		jsonObjectPriceReport.put("superPrice", prDto.getSuperPrice());
		jsonObjectPriceReport.put("superPlusPrice", prDto.getSuperPlusPrice());
		jsonObjectPriceReport.put("gasPrice", prDto.getGasPrice());
		jsonObjectPriceReport.put("methanePrice", prDto.getMethanePrice());
		jsonObjectPriceReport.put("premiumDieselPrice", prDto.getPremiumDieselPrice());
		jsonObjectPriceReport.put("userId", prDto.getUserId());

		HttpPost request = new HttpPost("http://localhost:8080/gasstation/setGasStationReport");
		
		StringEntity entity = new StringEntity(jsonObjectPriceReport.toString());
		
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
		request.setEntity(entity);
		
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);
		
	}
	
	
	@Test
	@Order(8)
	public void testDeleteGasStation() throws ClientProtocolException, IOException {
		
		String s = "http://localhost:8080:/gasstation/deleteGasStation/" + gsDtoId; 
		System.out.println(s);

		HttpUriRequest request = new HttpDelete("http://localhost:8080:/gasstation/deleteGasStation/"+ gsDtoId);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);

	}
	
	
	@Test
	@Order(9)
	public void testSaveUser() throws ClientProtocolException, IOException {
		
		UserDto uDto = new UserDto(100, "Andrea", "password", "andrea@ezgas.it", 0);
		JSONObject jsonObjectUser = new JSONObject();
		jsonObjectUser.put("userName", uDto.getUserName());
		jsonObjectUser.put("password", uDto.getPassword());
		jsonObjectUser.put("email", uDto.getEmail());
		jsonObjectUser.put("reputation", uDto.getReputation());
		jsonObjectUser.put("admin", uDto.getAdmin());
		
		HttpPost request = new HttpPost("http://localhost:8080/user/saveUser");
		
		StringEntity entity = new StringEntity(jsonObjectUser.toString());
		
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
		request.setEntity(entity);
		
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);
		
	}
	
	
	@Test
	@Order(10)
	public void testGetAllUsers() throws ClientProtocolException, IOException {
		
		HttpUriRequest request = new HttpGet("http://localhost:8080:/user/getAllUsers");
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);
		
		String jsonFromResponse = EntityUtils.toString(response.getEntity());	
		
		assert(jsonFromResponse.contains("Andrea"));
		
		ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		UserDto[] UserArray = mapper.readValue(jsonFromResponse, UserDto[].class);

		for (UserDto usDto : UserArray) {
			if (usDto.getEmail().equals("andrea@ezgas.it") && usDto.getPassword().equals("password"))
				uDtoId = usDto.getUserId();	
		}
		
	}
	
	
	@Test
	@Order(11)
	public void testGetUser() throws ClientProtocolException, IOException {
				
		HttpUriRequest request = new HttpGet("http://localhost:8080:/user/getUser/" + uDtoId);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);
		
		String jsonFromResponse = EntityUtils.toString(response.getEntity());	
		
		assert(jsonFromResponse.contains("Andrea"));
		
	}
	
	
	@Test
	@Order(12)
	public void testLogin() throws ClientProtocolException, IOException {
		
		IdPw i1 = new IdPw("andrea@ezgas.it", "password");

		JSONObject jsonObjectIdPw = new JSONObject();
		jsonObjectIdPw.put("user", i1.getUser());
		jsonObjectIdPw.put("pw", i1.getPw());
		
		HttpPost request = new HttpPost("http://localhost:8080:/user/login/");

		StringEntity entity = new StringEntity(jsonObjectIdPw.toString());
		
		request.setHeader("Accept", "application/json");
		request.setHeader("Content-type", "application/json");
		request.setEntity(entity);

		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);		

	}
	
	
	@Test
	@Order(13)
	public void testIncreaseUserReputation() throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpPost("http://localhost:8080:/user/increaseUserReputation/"+ uDtoId);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);

	}
	
	
	@Test
	@Order(14)
	public void testDecreaseUserReputation() throws ClientProtocolException, IOException {
		
		HttpUriRequest request = new HttpPost("http://localhost:8080/user/decreaseUserReputation/"+ uDtoId);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);
		
	}
	
	
	@Test
	@Order(15)
	public void testDeleteUser() throws ClientProtocolException, IOException {

		HttpUriRequest request = new HttpDelete("http://localhost:8080:/user/deleteUser/"+ uDtoId);
		HttpResponse response = HttpClientBuilder.create().build().execute(request);
		
		assert(response.getStatusLine().getStatusCode() == 200);

	}

}

