package com.amit.gradle.plugin;

import org.gradle.api.Project;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

public class KafkaSchemaRegistryExtension {

	
	 Property<String> url ;
	 Property<String> output ;
	 ListProperty<String> subjects ;
	
	
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


	public ListProperty<String> getSubjects() {
		return subjects;
	}

	
}
