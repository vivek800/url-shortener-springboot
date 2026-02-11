package com.url.shortner.controllers;

import com.url.shortner.dto.*;
import com.url.shortner.records.UrlRequest;
import com.url.shortner.records.UrlResposne;
import com.url.shortner.services.UrlService;
import jakarta.validation.Valid;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/url")
public class UrlController {

    private final UrlService urlService;

    public UrlController(UrlService urlService) {
        this.urlService = urlService;
    }

    @PostMapping("/shorten")
    public ResponseEntity<ApiResponse<UrlResposne>> shorten(
            @Valid @RequestBody UrlRequest request) {
    	
        String shortCode = urlService.createShortUrl(request.longUrl());

        String shortUrl = "https://short.ly/" + shortCode;
        
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(new ApiResponse<>(
                        true,
                        "Short URL created successfully",
                        new UrlResposne(shortUrl)
                ));
    }
}
