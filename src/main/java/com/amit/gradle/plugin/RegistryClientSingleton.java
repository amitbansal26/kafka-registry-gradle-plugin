package com.amit.gradle.plugin;

import io.confluent.kafka.schemaregistry.client.CachedSchemaRegistryClient;
import io.confluent.kafka.schemaregistry.client.SchemaRegistryClient;

public class RegistryClientSingleton {

	private RegistryClientSingleton() {
	
	}

	private static RegistryClientSingleton registryClientWrapper = new RegistryClientSingleton();
	
	private static SchemaRegistryClient schemaRegistryClient = null;
	
	public SchemaRegistryClient client(final String url) {
		 if (schemaRegistryClient == null) {
			 schemaRegistryClient = new CachedSchemaRegistryClient(url, 100);
	        }
		 
		 return schemaRegistryClient;
	}
	
	public static RegistryClientSingleton getInstance() {
		return registryClientWrapper;
	}
}
