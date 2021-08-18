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
package io.fusion.air.microservice.server.controller;

import javax.servlet.http.HttpServletRequest;

import io.fusion.air.microservice.server.config.ServiceConfiguration;
import io.fusion.air.microservice.server.config.ServiceHelp;
import io.fusion.air.microservice.server.models.EchoResponseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;

// Logging System
import org.slf4j.Logger;
import static org.slf4j.LoggerFactory.getLogger;
import static java.lang.invoke.MethodHandles.lookup;

import io.fusion.air.microservice.ServiceBootStrap;
import io.fusion.air.microservice.server.models.EchoData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import java.util.HashMap;
import java.util.Map;

/**
 * Health Controller for the Service
 * 
 * @author arafkarsh
 * @version 1.0
 * 
 */
@Configuration
@RestController
// "/api/v1/payments/service"
@RequestMapping("${service.api.path}"+ServiceConfiguration.HEALTH)
@RequestScope
@Tag(name = "System", description = "Health (Liveness, Readiness, ReStart.. etc)")
public class HealthController extends AbstractController {

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
	@GetMapping("/live")
	@ResponseBody
	public ResponseEntity<Map<String,Object>> getHealth(
			HttpServletRequest request) throws Exception {
		log.info(name()+"|Request to Health of Service... ");
		HashMap<String,Object> status = new HashMap<String,Object>();
		status.put("Code", 200);
		status.put("Status", true);
		status.put("Message","Service is OK!");
		return ResponseEntity.ok(status);
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
	public ResponseEntity<Map<String,Object>> isReady(
			HttpServletRequest request) throws Exception {
		log.info(name()+"|Request to Ready Check.. ");
		HashMap<String,Object> status = new HashMap<String,Object>();
		status.put("Code", 200);
		status.put("Status", true);
		status.put("Message","Service is Ready!");
		return ResponseEntity.ok(status);
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
    @Operation(summary = "Service Echo")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            		description = "Service Echo",
            		content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "400",
					description = "Service unable to deserialize!",
					content = @Content),
			@ApiResponse(responseCode = "404",
            		description = "Service unable to do Echo!",
            		content = @Content)
    })
    @PostMapping("/echo")
    public ResponseEntity<EchoResponseData> remoteEcho(@RequestBody EchoData echoData) {
		log.info(name()+"|Request for Echo ... "+echoData);
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
            content = {@Content(mediaType = "application/text")}),
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
 }

