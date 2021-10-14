package learning.spring;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.plugin.core.Plugin;
import org.springframework.plugin.core.PluginRegistry;
import org.springframework.plugin.core.config.EnablePluginRegistries;
import org.springframework.stereotype.Component;

@SpringBootApplication
@EnablePluginRegistries(WriterPlugin.class)
public class SpringPluginDemoApplication {

	@Bean
	ApplicationRunner runner(PluginRegistry<WriterPlugin, String> plugins) {
		return args->{
			for(var format:"csv,txt".split(",")) {
				plugins.getPluginFor(format).get().write("Hello,Spring plugin");
			}
		};
	}
	
	
	public static void main(String[] args) {
		SpringApplication.run(SpringPluginDemoApplication.class, args);
		
	
		
	}

}



interface WriterPlugin extends Plugin<String>{
	void write(String message);
}



@Component
class CsvWriter implements WriterPlugin{

	@Override
	public boolean supports(String delimiter) {
		return delimiter.equalsIgnoreCase("csv");
	}

	@Override
	public void write(String message) {
		System.out.println("writing csv:"+message);
		
	}
	
}


@Component
class TextWriter implements WriterPlugin{

	@Override
	public boolean supports(String delimiter) {
		return delimiter.equalsIgnoreCase("txt");
	}

	@Override
	public void write(String message) {
		System.out.println("writing text:"+message);
		
	}

	
	
}
