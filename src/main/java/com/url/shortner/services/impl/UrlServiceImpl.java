package com.url.shortner.services.impl;

import com.url.shortner.entity.Url;
import com.url.shortner.repository.UrlRepository;
import com.url.shortner.services.UrlService;
import com.url.shortner.utility.Base62Encoder;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

 @Service
public class UrlServiceImpl implements UrlService {

    private final UrlCacheService cacheService;
    private final UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository,
                          UrlCacheService cacheService) {
        this.urlRepository = urlRepository;
        this.cacheService = cacheService;
    }

    @Transactional
    @Override
    public String createShortUrl(String longUrl) {

        Url url = new Url();
        url.setLongUrl(longUrl);

        Url savedUrl = urlRepository.save(url);

        String shortCode = Base62Encoder.encode(savedUrl.getId());
        savedUrl.setShortCode(shortCode);

        urlRepository.save(savedUrl);

         
        cacheService.save(shortCode, longUrl);

        return shortCode;
    }

    @Override
    public String getOriginalUrl(String shortCode) {

        String cachedUrl = cacheService.get(shortCode);
        if (cachedUrl != null) {
            return cachedUrl;
        }

        Url entity = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        cacheService.save(shortCode, entity.getLongUrl());

        return entity.getLongUrl();
    }
}
