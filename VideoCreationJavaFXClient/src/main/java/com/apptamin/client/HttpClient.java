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

import com.apptamin.model.ActionPosition;


public class HttpClient {
	
	public final static void main(String[] args) throws Exception {
		
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
		request.add(new ActionPosition(280, 190, 169, 0, 0, 0, 1));
		request.add(new ActionPosition(386, 464, 210, 0, 1, 0, 1));
		request.add(new ActionPosition(310, 190, 266, 1, 1, 0, 1));
		request.add(new ActionPosition(340, 100, 560, 1, 0, 0, 1));

		// Convert java objects to JSON
		ObjectMapper mapper = new ObjectMapper();
		String jsonpost = mapper.writeValueAsString(request);
		log.info(jsonpost);
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
			log.info(Arrays.toString(result));


			// Close stream
			EntityUtils.consume(entity);
		} finally {
			response.close();
		}
	}
}