package com.amit.gradle.plugin;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.gradle.api.DefaultTask;
import org.gradle.api.file.DirectoryProperty;
import org.gradle.api.provider.ListProperty;
import org.gradle.api.provider.Property;
import org.gradle.api.tasks.OutputDirectory;
import org.gradle.api.tasks.TaskAction;

import io.confluent.kafka.schemaregistry.client.SchemaMetadata;
import io.confluent.kafka.schemaregistry.client.rest.exceptions.RestClientException;

/**
 * 
 */
public class SchemaArtifactDownloadTask extends DefaultTask{

	
	private DirectoryProperty outputPath;
	private ListProperty<String> subjects;
	private Property<String> url ;
	
	@TaskAction
	private void downloadSchemas() {
		subjects.get().forEach(action -> {downloadSchema(url.get(), action);});
	}
	
	private void downloadSchema(String url, String subject) {
		try {
			SchemaMetadata metadata = RegistryClientSingleton
					.getInstance().client(url).getLatestSchemaMetadata(subject);
			writeSchemas(subject, metadata.getSchema());
		} catch (IOException | RestClientException e) {
			e.printStackTrace();
		}
	}
	
	private void writeSchemas(String subject, String schemas) {
		File outputFile = new File(outputPath.getAsFile().get(), subject+".avsc");
		if(!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}
        try {
			Files.write(Paths.get(outputFile.getPath()), schemas.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
   
  }

	@OutputDirectory
	public DirectoryProperty getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(DirectoryProperty outputPath) {
		this.outputPath = outputPath;
		}
	public ListProperty<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(ListProperty<String> subjects) {
		this.subjects = subjects;
	}
	public Property<String> getUrl() {
		
		return url;
	}
	public void setUrl(Property<String> url) {
		this.url = url;
	}
}
