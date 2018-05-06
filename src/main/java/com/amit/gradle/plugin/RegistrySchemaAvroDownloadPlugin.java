package com.amit.gradle.plugin;

import org.gradle.api.Action;
import org.gradle.api.Plugin;
import org.gradle.api.Project;

public class RegistrySchemaAvroDownloadPlugin implements Plugin<Project>{

	@Override
	public void apply(Project project) {

		KafkaSchemaRegistryExtension schemaRegistryExtension = project.getExtensions()
		.create("schemaRegistry", KafkaSchemaRegistryExtension.class, project);
		
		SchemaArtifactDownloadTask task = project.getTasks()
		.create("downloadSchemaTask", SchemaArtifactDownloadTask.class, new Action<SchemaArtifactDownloadTask>() {

			@Override
			public void execute(SchemaArtifactDownloadTask task) {
				task.getUrl().set(schemaRegistryExtension.getUrl());
				task.getSubjects().set(schemaRegistryExtension.getSubjects());
				task.getOutputPath().set(schemaRegistryExtension.getOutput());
			}
		});
		
		project.getTasks().forEach(action -> {
			if(action.getName().equals("compileJava")) {
				action.dependsOn(task);
			}
		});
	
	}

	
}
