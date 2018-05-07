package com.amit.gradle.plugin;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import org.gradle.api.DefaultTask;
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
	private Property<List<String>> subjects;
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
		File outputFile = new File(outputDir, subject+".avsc");
		if(!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}

      PrintWriter printWriter;
	try {
		printWriter = new PrintWriter(outputFile);
		printWriter.write(schemas);
	} catch (FileNotFoundException e) {
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
		 this.outputDir = new File(this.getProject().getBuildDir(), "src/main/avro");
	}
	public Property<List<String>> getSubjects() {
		return subjects;
	}
	public void setSubjects(Property<List<String>> subjects) {
		this.subjects = subjects;
	}
	public Property<String> getUrl() {
		return url;
	}
	public void setUrl(Property<String> url) {
		this.url = url;
	}
}
