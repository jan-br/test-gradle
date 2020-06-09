package net.labyfy;

import org.gradle.api.Plugin;
import org.gradle.api.Project;
import org.gradle.api.attributes.Attribute;

public class TestPlugin implements Plugin<Project> {

  private final static Attribute<Boolean> TEST = Attribute.of("net.labyfy.requiredeobfuscation", Boolean.class);

  public void apply(Project project) {
    project.getDependencies().getAttributesSchema().attribute(TEST);
    project.getDependencies().getArtifactTypes().getByName("jar").getAttributes().attribute(TEST, false);
    project.getConfigurations().all((configuration) -> {
      project.afterEvaluate((x) -> {
        if (configuration.isCanBeResolved()) {
          configuration.attributes((attributes) -> attributes.attribute(TEST, false));
        }
      });
    });

    project.getDependencies().registerTransform(TestTransformer.class,
        (spec) -> {
          spec.getFrom().attribute(TEST, true);
          spec.getTo().attribute(TEST, false);
        });
  }
}
