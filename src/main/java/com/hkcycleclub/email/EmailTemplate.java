package com.hkcycleclub.email;

import java.io.StringWriter;
import java.util.Map;
import java.util.Properties;

import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;

public class EmailTemplate {

  private final VelocityEngine velocityEngine;
  private final Template template;

  public EmailTemplate(String templateFile) throws Exception {
    Properties templateProperties = new Properties();
    templateProperties.setProperty("resource.loader", "class");
    templateProperties.setProperty(
        "class.resource.loader.class",
        "org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader");

    velocityEngine = new VelocityEngine();
    velocityEngine.init(templateProperties);

    template = velocityEngine.getTemplate("/" + templateFile);
  }

  public String generate(Map<String, String> variables) throws Exception {
    VelocityContext context = new VelocityContext(variables);

    StringWriter writer = new StringWriter();
    template.merge(context, writer);

    return writer.toString();
  }
}
