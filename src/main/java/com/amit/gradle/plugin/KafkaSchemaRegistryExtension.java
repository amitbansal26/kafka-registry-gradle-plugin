package com.amit.gradle.plugin;

import org.gradle.api.Project;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;

public class KafkaSchemaRegistryExtension {

	
	 Property<String> url ;
	 DirectoryProperty outputPath ;
	 ListProperty<String> subjects ;
	
	
	public KafkaSchemaRegistryExtension(Project project) {
	  this.url = project.getObjects().property(String.class);
	  this.outputPath =  project.getLayout().directoryProperty();
	  this.subjects = project.getObjects().listProperty(String.class);
	}


	public Property<String> getUrl() {
		return url;
	}


	public DirectoryProperty getOutputPath() {
		return outputPath;
	}


	public ListProperty<String> getSubjects() {
		return subjects;
	}

	
}
