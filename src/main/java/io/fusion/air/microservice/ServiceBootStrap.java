/**
 * (C) Copyright 2021 Araf Karsh Hamid
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.fusion.air.microservice;

import javax.annotation.PostConstruct;
import javax.servlet.MultipartConfigElement;
import javax.servlet.http.HttpServletRequest;

import io.fusion.air.microservice.server.config.ServiceConfiguration;
import io.fusion.air.microservice.server.controller.HealthController;
import io.swagger.v3.oas.models.info.Contact;
import org.slf4j.Logger;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.unit.DataSize;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;

import java.util.Arrays;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

import static io.fusion.air.microservice.server.config.ServiceHelp.VERSION;;

/**
 * Micro Service - Spring Boot Application
 * API URL : http://localhost:9090/api/v1/service/swagger-ui.html
 *
 * @author arafkarsh
 */
@EnableScheduling
@ServletComponentScan
@RestController
@SpringBootApplication(scanBasePackages = { "io.fusion.air.microservice" })
public class ServiceBootStrap {

	// Set Logger -> Lookup will automatically determine the class name.
	private static final Logger log = getLogger(lookup().lookupClass());

	private final String title = "<h1>Welcome to MICRO Service<h1/>"
			+"<h3>Copyright (c) MetaArivu Pvt Ltd, 2021</h3>"
			+"<h5>Build No: BN :: Build Date: BD :: </h5>";

	private static ConfigurableApplicationContext context;

	@Autowired
	private ServiceConfiguration serviceConfig;

	// Get the Service Name from the properties file
	@Value("${service.name:NameNotDefined}")
	private String serviceName = "Unknown";
	
	/**
	 * Start the Micro Service
	 *
	 * @param args
	 */
	public static void main(String[] args) {

		// Start the Server
		start(args);

		// API URL : http://localhost:9090/api/v1/service/swagger-ui.html
	}

	/**
	 * Start the Server
	 * @param args
	 */
	public static void start(String[] args) {
		log.info("Booting Service ..... ..");
		try {
			context = SpringApplication.run(ServiceBootStrap.class, args);
			log.info("Booting Service ..... ...Startup completed!");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Restart the Server
	 */
	public static void restart() {
		log.info("Restarting Service ..... .. 1");
		ApplicationArguments args = context.getBean(ApplicationArguments.class);
		log.info("Restarting Service ..... .. 2");

		Thread thread = new Thread(() -> {
			context.close();
			start(args.getSourceArgs());
		});
		log.info("Restarting Service ..... .. 3");

		thread.setDaemon(false);
		thread.start();
	}

	/**
	 * Load the Configuration
	 */
	@PostConstruct
	public void configure() {
	}

	/**
	 * Micro Service - Home Page
	 * @return
	 */
	@GetMapping("/")
	public String home(HttpServletRequest request) {
		log.info("Request to Home Page of Service... "+printRequestURI(request));
		return (serviceConfig == null) ? this.title :
				this.title.replaceAll("MICRO", serviceConfig.getServiceName())
						.replaceAll("BN", "" + serviceConfig.getBuildNumber())
						.replaceAll("BD", serviceConfig.getBuildDate());
	}

	/**
	 * Print the Request
	 *
	 * @param request
	 * @return
	 */
	private String printRequestURI(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		sb.append("URI: ").append(request.getRequestURI());
		String[] req = request.getRequestURI().split("/");
		sb.append("Params Size = "+req.length+" : ");
		for(int x=0; x < req.length; x++) {
			sb.append(req[x]).append("|");
		}
		sb.append("\n");
		log.info("HttpServletRequest: ["+sb.toString()+"]");
		return sb.toString();
	}

	@Bean
	public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
		return args -> {
			log.debug("Inspect the beans provided by Spring Boot:");
			String[] beanNames = ctx.getBeanDefinitionNames();
			Arrays.sort(beanNames);
			for (String beanName : beanNames) {
				log.debug(beanName);
			}
		};
	}

	/**
	 * Open API v3 Docs - All
	 * Reference: https://springdoc.org/faq.html
	 * @return
	 */
	@Bean
	public GroupedOpenApi allPublicApi() {
		return GroupedOpenApi.builder()
				.group(serviceConfig.getServiceName()+"-service")
				.pathsToMatch("/api/**")
				.build();
	}

	/**
	 * Open API v3 Docs - Micro Service
	 * Reference: https://springdoc.org/faq.html
	 * @return
	 */
	@Bean
	public GroupedOpenApi appPublicApi() {
		return GroupedOpenApi.builder()
				.group(serviceConfig.getServiceName()+"-service-core")
				.pathsToMatch(serviceConfig.getServiceApiPath()+"/**")
				.pathsToExclude(serviceConfig.getServiceApiPath()+"/service/**", serviceConfig.getServiceApiPath()+"/config/**")
				.build();
	}

	/**
	 * Open API v3 Docs - Core Service
	 * Reference: https://springdoc.org/faq.html
	 * Change the Resource Mapping in HealthController
	 *
	 * @see HealthController
	 */
	@Bean
	public GroupedOpenApi configPublicApi() {
		// System.out.println;
		return GroupedOpenApi.builder()
				.group(serviceConfig.getServiceName()+"-service-config")
				.pathsToMatch(serviceConfig.getServiceApiPath()+"/config/**")
				.build();
	}

	@Bean
	public GroupedOpenApi systemPublicApi() {
		return GroupedOpenApi.builder()
				.group(serviceConfig.getServiceName()+"-service-health")
				.pathsToMatch(serviceConfig.getServiceApiPath()+"/service/**")
				.build();
	}

	/**
	 * Open API v3
	 * Reference: https://springdoc.org/faq.html
	 * @return
	 */
	@Bean
	public OpenAPI orderOpenAPI() {
		return new OpenAPI()
				.info(new Info()
						.title(serviceConfig.getServiceName()+" Service")
						.description(serviceConfig.getServiceDetails())
						.version(serviceConfig.getServerVersion())
						.license(new License().name("License: Apache 2.0")
								.url(serviceConfig.getServiceUrl()))
				)
				.externalDocs(new ExternalDocumentation()
						.description(serviceConfig.getServiceName()+" Service Source Code")
						.url(serviceConfig.getServiceApiRepository()));
	}

	/**
	 * Returns the REST Template
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate() {
		return new RestTemplate();
	}

	/**
	 * Returns the Object Mapper
	 * @return
	 */

	@Bean
	public ObjectMapper objectMapper() {
		return new ObjectMapper()
				.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
				.findAndRegisterModules();
	}


	/**
	 * All file upload till 512 MB
	 * returns MultipartConfigElement
	 * @return
	 */
	@Bean
	public MultipartConfigElement multipartConfigElement() {
		MultipartConfigFactory factory = new MultipartConfigFactory();
		factory.setMaxFileSize(DataSize.ofBytes(500000000L));
		return factory.createMultipartConfig();
	}
}
