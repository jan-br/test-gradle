package net.labyfy;

import org.gradle.api.artifacts.transform.*;
import org.gradle.api.file.FileCollection;
import org.gradle.api.file.FileSystemLocation;
import org.gradle.api.provider.Provider;
import org.gradle.api.tasks.CompileClasspath;
import org.gradle.api.tasks.PathSensitive;
import org.gradle.api.tasks.PathSensitivity;

import javax.annotation.Nonnull;
import java.io.File;
import java.nio.file.Files;

public abstract class TestTransformer implements TransformAction<TransformParameters.None> {

  @PathSensitive(PathSensitivity.NONE)
  @InputArtifact
  public abstract Provider<FileSystemLocation> getInputArtifact();

  @CompileClasspath
  @InputArtifactDependencies
  public abstract FileCollection getDependencies();

  public void transform(@Nonnull TransformOutputs outputs) {
    try {
      File input = getInputArtifact().get().getAsFile();
      System.out.println("transforming " + input.getName());

      File output = outputs.file(input.getName());
      Files.copy(input.toPath(), output.toPath());
    } catch (Exception ex) {
      ex.printStackTrace();
    }
  }
}
