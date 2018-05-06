package com.amit.gradle.plugin;

import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

import lombok.Data;

@Data
public class KafkaSchemaRegistryExtension {

	
	private Property<String> url ;
	private Property<String> output ;
	private Property<List<String>> subjects ;
	
	
	public KafkaSchemaRegistryExtension(Project project) {
		super();
	  this.url = project.getObjects().property(String.class);
	  this.output = project.getObjects().property(String.class);
	  this.subjects = project.getObjects().listProperty(String.class);
	}

}
