package com.url.shortner.services;

public interface UrlService {
	String createShortUrl(String longUrl);

    String getOriginalUrl(String shortCode);
}
