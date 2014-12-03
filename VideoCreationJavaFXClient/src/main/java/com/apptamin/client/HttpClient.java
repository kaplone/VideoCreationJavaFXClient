package com.apptamin.client;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.Set;

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
	
	static Map<Integer, Point> resultMap;
	static Collection<Point> points_coll;
	static Set<Integer> framesSet;
	static Point[] points;
	
	public final static Point[] httpClient(Action[] actions, int length) throws Exception {
		
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
			
			resultMap = Point.getPoints(result);
			framesSet = resultMap.keySet();
			
			
			for (int j = 1;j < length; j++){
				if (! framesSet.contains(j)){
					System.out.println(j + " non présent");
					resultMap.put(j, new Point(100, 450, null,  j));
				}
				else {
					System.out.println(j + " présent");
				}
				
			}			

			// Close stream
			EntityUtils.consume(entity);
		} finally {
			response.close();
		}
		
		points = new Point [resultMap.size()];
				
		for (Integer i : framesSet){
			points[i - 1] = resultMap.get(i -1);
			
		}
		return points;
	}
}