package com.url.shortner.services.impl;

import com.url.shortner.entity.Url;
import com.url.shortner.repository.UrlRepository;
import com.url.shortner.services.UrlService;
import com.url.shortner.utility.Base62Encoder;

import jakarta.transaction.Transactional;

import org.springframework.stereotype.Service;

 @Service
public class UrlServiceImpl implements UrlService {

     private final UrlRepository urlRepository;

    public UrlServiceImpl(UrlRepository urlRepository
                           ) {
        this.urlRepository = urlRepository;
         
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

         
         

        return shortCode;
    }

    @Override
    public String getOriginalUrl(String shortCode) {

        
        Url entity = urlRepository.findByShortCode(shortCode)
                .orElseThrow(() -> new RuntimeException("URL not found"));

        

        return entity.getLongUrl();
    }
}
