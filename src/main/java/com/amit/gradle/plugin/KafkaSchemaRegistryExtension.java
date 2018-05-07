package com.amit.gradle.plugin;

import java.util.List;

import org.gradle.api.Project;
import org.gradle.api.provider.Property;

public class KafkaSchemaRegistryExtension {

	
	 Property<String> url ;
	 Property<String> output ;
	 Property<List<String>> subjects ;
	
	
	public KafkaSchemaRegistryExtension(Project project) {
	  this.url = project.getObjects().property(String.class);
	  this.output = project.getObjects().property(String.class);
	  this.subjects = project.getObjects().listProperty(String.class);
	}


	public Property<String> getUrl() {
		return url;
	}


	public Property<String> getOutput() {
		return output;
	}


	public Property<List<String>> getSubjects() {
		return subjects;
	}

	
}
