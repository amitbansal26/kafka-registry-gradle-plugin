package com.amit.gradle.plugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.apache.avro.Schema;
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
	
	private void downloadSchema(final String url, final String subject) {
		try {
			final SchemaMetadata metadata = RegistryClientSingleton
					.getInstance().client(url).getLatestSchemaMetadata(subject);
			writeSchemas(subject, metadata.getSchema());
		} catch (IOException | RestClientException e) {
			e.printStackTrace();
		}
	}
	
	private void writeSchemas(final String subject, final String schemas) {
		final File outputFile = new File(outputPath.getAsFile().get(), subject+".avsc");
		if(!outputFile.getParentFile().exists()) {
			outputFile.getParentFile().mkdirs();
		}
	      Schema.Parser parser = new Schema.Parser();
	      Schema schema = parser.parse(schemas);
		try (OutputStreamWriter writer = new OutputStreamWriter(
		          new FileOutputStream(outputFile), StandardCharsets.UTF_8)
		      ) {
		        writer.write(schema.toString(true));
		      } catch (IOException e) {
			e.printStackTrace();
		}
   
  }

	@OutputDirectory
	public DirectoryProperty getOutputPath() {
		return outputPath;
	}
	public void setOutputPath(final DirectoryProperty outputPath) {
		this.outputPath = outputPath;
		}
	public ListProperty<String> getSubjects() {
		return subjects;
	}
	public void setSubjects(final ListProperty<String> subjects) {
		this.subjects = subjects;
	}
	public Property<String> getUrl() {
		
		return url;
	}
	public void setUrl(Property<String> url) {
		this.url = url;
	}
}
