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
package io.fusion.air.microservice.server;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

// Logging System
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;

import java.time.LocalDateTime;

import static java.lang.invoke.MethodHandles.lookup;

import io.fusion.air.microservice.ServiceBootStrap;
import io.fusion.air.microservice.server.EchoData;
import io.fusion.air.microservice.server.EchoResponseData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

/**
 * Health Controller for the Service
 * 
 * @author arafkarsh
 * @version 1.0
 * 
 */
@Configuration
@RestController
@RequestMapping("/api/v1/payment/service")
@RequestScope
@Tag(name = "System", description = "System (Health, Readiness, ReStart.. etc)")
public class ServiceHealthController {

	// Set Logger -> Lookup will automatically determine the class name.
	private static final Logger log = getLogger(lookup().lookupClass());
	
	private final String title = "<h1>Welcome to Health Service<h1/>"
					+ ServiceHelp.NL
					+"<h3>Copyright (c) MetaArivu Pvt Ltd, 2021</h3>"
					+ ServiceHelp.NL
					;


	@Autowired
	private ServiceConfiguration serviceConfig;
	private String serviceName;

	/**
	 * Returns the Service Name
	 * @return
	 */
	private String name() {
		if(serviceName == null) {
			if(serviceConfig == null) {
				log.info("|Error Autowiring Service config!!!");
				serviceName = "|NoServiceName";
			} else {
				serviceName = "|" + serviceConfig.getServiceName() + "Service";
				log.info("|Version="+ServiceHelp.VERSION);
			}
		}
		return serviceName;
	}
	
	/**
	 * Get Method Call to Check the Health of the App
	 * 
	 * @return
	 */
    @Operation(summary = "Health Check of the Service")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Health Check OK",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
            description = "Service is in bad health.",
            content = @Content)
    })
	@GetMapping("/health")
	@ResponseBody
	public ResponseEntity<String> getHealth( 
			HttpServletRequest request) throws Exception {
		log.info(name()+"|Request to Health of Service... ");
		return ResponseEntity.ok("200:Service-Health-OK");
	}
    
    @Operation(summary = "Service Readiness Check")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Service Readiness Check",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
            description = "Service is not ready.",
            content = @Content)
    })
	@GetMapping("/ready")
	@ResponseBody
	public ResponseEntity<String> isReady( 
			HttpServletRequest request) throws Exception {
		log.info(name()+"|Request to Ready Check.. ");
		return ResponseEntity.ok("200:Service-Ready");
	}
	
	/**
	 * Check the Current Log Levels
	 * @return
	 */
    @Operation(summary = "Service Log Levels")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Service Log Level Check",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
            description = "Service is not ready.",
            content = @Content)
    })
	@GetMapping("/log")
    public String log() {
		log.info("|Request to Log Level.. ");
    	log.trace("HealthService|This is TRACE level message");
        log.debug("HealthService|This is a DEBUG level message");
        log.info("HealthService|This is an INFO level message");
        log.warn("HealthService|This is a WARN level message");
        log.error("HealthService|This is an ERROR level message");
        return "HealthService|See the log for details";
    }
	
	/**
	 * Restart the Service
	 */
    @Operation(summary = "Service ReStart")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Service ReStart",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
            description = "Service is not ready.",
            content = @Content)
    })
    @PostMapping("/restart")
    public void restart() {
		log.info(name()+"|Server Restart Request Received ....");

		if(serviceConfig != null && serviceConfig.isServerRestart()) {
    		log.info(name()+"|Restarting the service........");
    		ServiceBootStrap.restart();
    	}
    } 
    /**
     * Remote Echo Test
     * @param echoData
     * @return
     */
    @Operation(summary = "Service Remote Echo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Service Remote Echo",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
            description = "Service is not ready.",
            content = @Content)
    })
    @PostMapping("/remoteEcho")
    public ResponseEntity<EchoResponseData> remoteEcho(@RequestBody EchoData echoData) {
		log.info(name()+"|Request for RemoteEcho ... "+echoData);
    	if(echoData == null) {
			return ResponseEntity.notFound().build();
		}
    	return ResponseEntity.ok(new EchoResponseData(echoData.getWord()));
    }
    
	/**
	 * Basic Testing
	 * 
	 * @param request
	 * @return
	 */
    @Operation(summary = "Service Home")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Service Home",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
            description = "Service is not ready.",
            content = @Content)
    })
	@GetMapping("/home")
	@ResponseBody
	public String apiHome(HttpServletRequest request) {
		log.info("|Request to /home/ path... ");
		StringBuilder sb = new StringBuilder();
		sb.append(title);
		sb.append("<br>");
		sb.append(printRequestURI(request));
		return sb.toString();
	}

	/**
	 * Print the Request
	 * 
	 * @param request
	 * @return
	 */
	private String printRequestURI(HttpServletRequest request) {
		StringBuilder sb = new StringBuilder();
		String[] req = request.getRequestURI().split("/");
		sb.append("Params Size = "+req.length+" : ");
		for(int x=0; x < req.length; x++) {
			sb.append(req[x]).append("|");
		}
 		sb.append("\n");
		log.info(sb.toString());
		return sb.toString();
	}
 }

