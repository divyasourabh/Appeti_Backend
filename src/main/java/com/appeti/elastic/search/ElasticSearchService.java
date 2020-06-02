package com.appeti.elastic.search;

import org.elasticsearch.action.delete.DeleteResponse;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.ImmutableSettings;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class ElasticSearchService {
	
	private TransportClient client;
	private static final String SERVER_NAME = "52.74.236.46";
	private static final int DEFAULT_SERVER_PORT = 9300;
	private static final Logger logger = LoggerFactory.getLogger(ElasticSearchService.class);
	
	public ElasticSearchService(){
		Settings settings = ImmutableSettings.settingsBuilder()
                .put("client.transport.sniff", true)
                .put("cluster.name", "es_appeti")
                .build();
		client = new TransportClient(settings);
		client.addTransportAddress(new InetSocketTransportAddress(SERVER_NAME,DEFAULT_SERVER_PORT));
		logger.info(SERVER_NAME + ": " + DEFAULT_SERVER_PORT);
	}
	
	public void indexDoc(String json){
		IndexResponse response = client.prepareIndex("twitter", "tweet")
		        .setSource(json)
		        .execute()
		        .actionGet();
		
		String _index = response.getIndex();
		logger.info(_index);
		// Type name
		String _type = response.getType();
		logger.info(_type);
		// Document ID (generated or not)
		String _id = response.getId();
		logger.info(_id);
		// Version (if it's the first time you index this document, you will get: 1)
		long _version = response.getVersion();
		logger.info(""+_version);
		// isCreated() is true if the document is a new one, false if it has been updated
		boolean created = response.isCreated();
		logger.info(""+created);
		
	}
	
	public void getDoc(){
		GetResponse response = client.prepareGet("twitter", "tweet", "1")
				.setOperationThreaded(false)
				.execute()
		        .actionGet();
		logger.info(response.getSourceAsString());
	}
	
	public void deleteDoc(){
		DeleteResponse response = client.prepareDelete("twitter", "tweet", "1")
		        .setOperationThreaded(false)
		        .execute()
		        .actionGet();
	}
}
