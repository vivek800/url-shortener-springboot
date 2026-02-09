package com.url.shortner.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.url.shortner.entity.Url;

public interface UrlRepository extends JpaRepository<Url,Long>{
	
	Optional<Url> findByShortCode(String shortCode);
}
