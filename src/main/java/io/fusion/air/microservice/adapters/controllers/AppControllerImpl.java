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
package io.fusion.air.microservice.adapters.controllers;

import io.fusion.air.microservice.ServiceBootStrap;
import io.fusion.air.microservice.domain.models.PaymentDetails;
import io.fusion.air.microservice.domain.models.PaymentStatus;
import io.fusion.air.microservice.domain.models.PaymentType;
import io.fusion.air.microservice.server.config.ServiceConfiguration;
import io.fusion.air.microservice.server.config.ServiceHelp;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.annotation.RequestScope;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.UUID;

import static java.lang.invoke.MethodHandles.lookup;
import static org.slf4j.LoggerFactory.getLogger;

/**
 * App Controller for the Service
 * 
 * @author arafkarsh
 * @version 1.0
 * 
 */
@Configuration
@RestController
@RequestMapping("/api/v1/payment")
@RequestScope
@Tag(name = "Payment", description = "Payment Service ")
public class AppControllerImpl {

	// Set Logger -> Lookup will automatically determine the class name.
	private static final Logger log = getLogger(lookup().lookupClass());
	
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
				log.info(LocalDateTime.now() + "|" + name() + "|Error Autowiring Service config!!!");
				serviceName = "NoServiceName";
			} else {
				serviceName = serviceConfig.getServiceName() + "Service";
				log.info("|"+name()+"|Version="+ServiceHelp.VERSION);
			}
		}
		return serviceName;
	}

	/**
	 * Get Method Call to Check the Health of the App
	 * 
	 * @return
	 */
    @Operation(summary = "Check the Payment status")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Payment Status Check",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
            description = "Invalid Payment Reference No.",
            content = @Content)
    })
	@GetMapping("/status/{referenceNo}")
	@ResponseBody
	public ResponseEntity<String> getStatus(@PathVariable("referenceNo") String _referenceNo,
			HttpServletRequest request) throws Exception {
		log.info("|"+name()+"|Request to Payment Status of Service... ");
		return ResponseEntity.ok("200:Service-Health-OK");
	}

	/**
	 * Process the Payments
	 */
    @Operation(summary = "Process Payments")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200",
            description = "Process the payment",
            content = {@Content(mediaType = "application/json")}),
            @ApiResponse(responseCode = "404",
            description = "Unable to process the payment",
            content = @Content)
    })
    @PostMapping("/processPayments")
    public ResponseEntity<PaymentStatus> processPayments(@RequestBody PaymentDetails _payDetails) {
		log.info("|"+name()+"|Request to process payments... ");
		PaymentStatus ps = new PaymentStatus(
				"fb908151-d249-4d30-a6a1-4705729394f4",
				LocalDateTime.now(),
				"Accepted",
				UUID.randomUUID().toString(),
				LocalDateTime.now(),
				PaymentType.CREDIT_CARD);
		return ResponseEntity.ok(ps);
    }

	/**
	 * Cancel the Payment
	 */
	@Operation(summary = "Cancel Payments")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Cancel the payment",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "404",
					description = "Unable to Cancel the payment",
					content = @Content)
	})
	@DeleteMapping("/cancel/{referenceNo}")
	public ResponseEntity<String> cancel(@PathVariable("referenceNo") String _referenceNo) {
		log.info("|"+name()+"|Request to process payments... ");
		return ResponseEntity.ok("200:Cancellation-OK");
	}

	/**
	 * Cancel the Payment
	 */
	@Operation(summary = "Update Payments")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200",
					description = "Update the payment",
					content = {@Content(mediaType = "application/json")}),
			@ApiResponse(responseCode = "404",
					description = "Unable to Update the payment",
					content = @Content)
	})
	@PutMapping("/update/{referenceNo}")
	public ResponseEntity<String> updatePayment(@PathVariable("referenceNo") String _referenceNo) {
		log.info("|"+name()+"|Request to Update payments... ");
		return ResponseEntity.ok("200:Update-OK");
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

