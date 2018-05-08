package com.amit.gradle.plugin;

import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class RegistrySchemaAvroDownloadPlugin implements Plugin<Project>{

	@Override
	public void apply(Project project) {

		KafkaSchemaRegistryExtension schemaRegistryExtension = project.getExtensions()
		.create("schemaRegistry", KafkaSchemaRegistryExtension.class, project);
		
		SchemaArtifactDownloadTask task = project.getTasks()
		.create("downloadSchemaTask", SchemaArtifactDownloadTask.class);
	
		task.setOutputPath(schemaRegistryExtension.getOutputPath());
		task.setSubjects(schemaRegistryExtension.getSubjects());
		task.setUrl(schemaRegistryExtension.getUrl());
		project.getTasks().forEach(action -> {
			if(action.getName().equals("compileJava")) {
				action.dependsOn(task);
			}
		});
	
	}

	
}
