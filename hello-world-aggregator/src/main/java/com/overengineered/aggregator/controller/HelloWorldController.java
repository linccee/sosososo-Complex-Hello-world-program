package com.overengineered.aggregator.controller;

import com.overengineered.aggregator.dto.HelloWorldRequestDto;
import com.overengineered.aggregator.dto.HelloWorldResponseDto;
import com.overengineered.aggregator.service.HelloWorldAggregatorService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;

/**
 * REST controller for generating Hello World messages.
 * This class is absurdly over-engineered for a simple hello world application.
 */
@RestController
@RequestMapping("/api/v1/hello-world")
@Validated
@Slf4j
@RequiredArgsConstructor
@Tag(name = "Hello World API", description = "Operations for generating Hello World messages")
public class HelloWorldController {

    private final HelloWorldAggregatorService aggregatorService;

    /**
     * GET /api/v1/hello-world : Get a default Hello World message
     *
     * @return ResponseEntity with the Hello World message
     */
    @GetMapping
    @Operation(summary = "Get a default Hello World message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated message"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HelloWorldResponseDto> getDefaultHelloWorld() {
        log.info("REST request to get default Hello World message");
        HelloWorldResponseDto response = aggregatorService.generateDefaultHelloWorld();
        return ResponseEntity.ok(response);
    }

    /**
     * POST /api/v1/hello-world : Generate a customized Hello World message
     *
     * @param requestDto The request containing all customization parameters
     * @return ResponseEntity with the generated Hello World message
     */
    @PostMapping
    @Operation(summary = "Generate a customized Hello World message")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully generated message"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HelloWorldResponseDto> generateHelloWorld(
            @Parameter(description = "Request with customization parameters", required = true, 
                    schema = @Schema(implementation = HelloWorldRequestDto.class))
            @Valid @RequestBody HelloWorldRequestDto requestDto) {
        
        log.info("REST request to generate customized Hello World message: {}", requestDto);
        HelloWorldResponseDto response = aggregatorService.generateHelloWorld(requestDto);
        return ResponseEntity.ok(response);
    }

    /**
     * GET /api/v1/hello-world/{id} : Get a Hello World message by ID
     *
     * @param id The ID of the Hello World message to retrieve
     * @return ResponseEntity with the Hello World message
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get a Hello World message by ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved message"),
            @ApiResponse(responseCode = "404", description = "Message not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<HelloWorldResponseDto> getHelloWorldById(
            @Parameter(description = "ID of the Hello World message", required = true)
            @PathVariable String id) {
        
        log.info("REST request to get Hello World message with ID: {}", id);
        return aggregatorService.getHelloWorldById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    /**
     * POST /api/v1/hello-world/async : Generate a Hello World message asynchronously
     *
     * @param requestDto The request containing all customization parameters
     * @return ResponseEntity with the request ID that can be used to retrieve the result later
     */
    @PostMapping("/async")
    @Operation(summary = "Generate a Hello World message asynchronously")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Request accepted"),
            @ApiResponse(responseCode = "400", description = "Invalid input"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<Map<String, String>> generateHelloWorldAsync(
            @Parameter(description = "Request with customization parameters", required = true, 
                    schema = @Schema(implementation = HelloWorldRequestDto.class))
            @Valid @RequestBody HelloWorldRequestDto requestDto) {
        
        log.info("REST request to generate Hello World message asynchronously: {}", requestDto);
        String requestId = aggregatorService.generateHelloWorldAsync(requestDto);
        
        Map<String, String> response = new HashMap<>();
        response.put("requestId", requestId);
        response.put("status", "PROCESSING");
        response.put("statusCheckUrl", "/api/v1/hello-world/async/" + requestId);
        
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
    }

    /**
     * GET /api/v1/hello-world/async/{requestId} : Check the status of an asynchronous Hello World message generation
     *
     * @param requestId The request ID returned by the async endpoint
     * @return ResponseEntity with the result if available
     */
    @GetMapping("/async/{requestId}")
    @Operation(summary = "Check the status of an asynchronous Hello World message generation")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Result available"),
            @ApiResponse(responseCode = "202", description = "Processing"),
            @ApiResponse(responseCode = "404", description = "Request ID not found"),
            @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    public ResponseEntity<?> checkAsyncResult(
            @Parameter(description = "Request ID from async generation", required = true)
            @PathVariable String requestId) {
        
        log.info("REST request to check async Hello World generation with request ID: {}", requestId);
        return aggregatorService.checkAsyncResult(requestId)
                .map(result -> ResponseEntity.ok().body(result))
                .orElseGet(() -> {
                    Map<String, String> response = new HashMap<>();
                    response.put("requestId", requestId);
                    response.put("status", "PROCESSING");
                    return ResponseEntity.status(HttpStatus.ACCEPTED).body(response);
                });
    }
}
