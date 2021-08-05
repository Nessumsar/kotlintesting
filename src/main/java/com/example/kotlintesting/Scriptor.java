package com.example.kotlintesting;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.FileSystemXmlApplicationContext;
import org.springframework.core.env.AbstractEnvironment;
import org.springframework.core.env.EnumerablePropertySource;
import org.springframework.core.env.Environment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.script.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import java.util.Properties;
import java.util.stream.StreamSupport;

@Component
public class Scriptor {

    @Autowired
    Environment springEnv;

    @PostConstruct
    public void init() throws ScriptException, IOException {
        ApplicationContext context = new FileSystemXmlApplicationContext("src/main/resources/beans.xml");
        System.out.println("testing kotlin");
        File script = new File("/Users/lukasrasmussen/Desktop/kotlintesting/src/main/resources/test.kts");
        String statements = Files.readString(script.toPath());
        Bindings bindings = new SimpleBindings();
        bindings.put("arg1", "val1");
        bindings.put("arg2", "val2");

        ScriptEngine kt = new ScriptEngineManager().getEngineByExtension("kts");

        Properties props = new Properties();
        MutablePropertySources propSrcs = ((AbstractEnvironment) springEnv).getPropertySources();
        StreamSupport.stream(propSrcs.spliterator(), false)
                .filter(ps -> ps instanceof EnumerablePropertySource)
                .map(ps -> ((EnumerablePropertySource) ps).getPropertyNames())
                .flatMap(Arrays::<String>stream)
                .forEach(propName -> props.setProperty(propName, springEnv.getProperty(propName)));
        bindings.put("props", props);
        //Map<String, Object> map = Map.of("arg1", "val1", "arg2", "val2");
        //new KotlinScriptExecutor().executeScript(new ResourceScriptSource(new FileSystemResource("/Users/lukasrasmussen/Desktop/kotlintesting/src/main/resources/test.kts")));

        kt.eval(statements, bindings);
    }
}
