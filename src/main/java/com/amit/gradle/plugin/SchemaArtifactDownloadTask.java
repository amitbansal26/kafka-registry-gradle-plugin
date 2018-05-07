package com.amit.gradle.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.gradle.api.DefaultTask;
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

	private File outputDir;
	private Property<String> outputPath;
	private ListProperty<String> subjects;
	private Property<String> url ;
	
	@TaskAction
	private void downloadSchemas() {
		System.out.println("***************URL is " + url);
		subjects.get().forEach(action -> {downloadSchema(url.get(), action);});
	}
	
	private void downloadSchema(String url, String subject) {
		try {
			SchemaMetadata metadata = RegistryClientSingleton
					.getInstance().client(url).getLatestSchemaMetadata(subject);
			System.out.println("Schema Meta Data" + metadata.getSchema());
			writeSchemas(subject, metadata.getSchema());
		} catch (IOException | RestClientException e) {
			e.printStackTrace();
		}
	}
	
	private void writeSchemas(String subject, String schemas) {
		File outputFile = new File(outputDir, subject+".avsc");
		System.out.println("Output file path" + outputFile.getPath());
		if(!outputFile.getParentFile().exists()) {
			System.out.println("Inside if condition" + outputFile.getPath());
			outputFile.getParentFile().mkdirs();
		}
        try {
			Files.write(Paths.get(outputFile.getPath()), schemas.getBytes());
		} catch (IOException e) {
			e.printStackTrace();
		}
   
  }
	@OutputDirectory
	public File getOutputDir() {
		return outputDir;
	}
	public void setOutputDir(File outputDir) {
		this.outputDir = outputDir;
	}
	public Property<String> getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(Property<String> outputPath) {
		this.outputPath = outputPath;
		 this.outputDir = new File(this.getProject().getProjectDir(), "src/main/avro");
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
