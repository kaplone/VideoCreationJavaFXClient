package com.apptamin.client;

import java.io.InputStream;
import java.util.Arrays;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.annotate.JsonIgnoreProperties;
import org.codehaus.jackson.map.ObjectMapper;

import utils.Touch;

import com.apptamin.model.Action;
import com.apptamin.model.ActionPosition;
import com.apptamin.client.Point;


public class HttpClient {
	
	public final static void httpClient(Action[] actions) throws Exception {
		
		//Logger
		Log log = LogFactory.getLog(HttpClient.class);
		
		// Prepare query
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httppost = new HttpPost(
				//"http://salty-dusk-3129.herokuapp.com/calculateFrames");
				"http://localhost:9000/calculateFrames");
		httppost.setHeader("Content-type", "application/json");

		// test data
		PositionsRequest request = new PositionsRequest();
		
		for (Action a : actions){
			log.info(a.positionProperty().getValue().getCoordX() + "  " +
		    		 a.positionProperty().getValue().getCoordY() + "  " +
		    		 a.positionProperty().getValue().getImageNumber() + "  " +
		    		 a.preActionProperty().getValue().getCode() + "  " +
		    		 a.postActionProperty().getValue().getCode() + "  " +
		    		 0 + "  " +
		    		 a.actionTypeProperty().getValue().getCode());
			
		    request.add(new ActionPosition(a.positionProperty().getValue().getCoordX(),
		    		                       a.positionProperty().getValue().getCoordY(),
		    		                       a.positionProperty().getValue().getImageNumber(),
		    		                       a.preActionProperty().getValue().getCode(),
		    		                       a.postActionProperty().getValue().getCode(),
		    		                       0,
		    		                       a.actionTypeProperty().getValue().getCode()
		    		                       ));
		}

		// Convert java objects to JSON
		ObjectMapper mapper = new ObjectMapper();
		String jsonpost = mapper.writeValueAsString(request);
		log.info("_______");
		log.info(jsonpost);
		log.info("_______");
		httppost.setEntity(new StringEntity(jsonpost));

		// Execute query
		CloseableHttpResponse response = httpclient.execute(httppost);

		try {
			// Get result
			log.info(response.getStatusLine());
			HttpEntity entity = response.getEntity();
			InputStream in = entity.getContent();

			// Convert result to java object
			
			ActionPosition[] result = mapper.readValue(in, ActionPosition[].class);
			
			for (ActionPosition a : result){
				log.info(a.print());
			}

			Point [] points = Point.getPoints(result);
			PrincipalClient.principalClient(points);
			


			// Close stream
			EntityUtils.consume(entity);
		} finally {
			response.close();
		}
	}
}