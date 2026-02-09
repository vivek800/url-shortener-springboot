package com.url.shortner.records;

import jakarta.validation.constraints.NotBlank;

public record UrlRequest(@NotBlank(message = "Long URL cannot be empty")
String longUrl) {

}
