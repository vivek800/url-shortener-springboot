package com.url.shortner.repository;

import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.url.shortner.entity.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

     
	@Query(value = """
		    SELECT *
		    FROM message
		    WHERE status = 'PENDING'
		      AND next_retry_time <= NOW()
		    ORDER BY id
		    """, nativeQuery = true)
		List<Message> fetchPendingMessages(Pageable pageable);


     
    @Modifying
    @Query("""
        UPDATE Message m
        SET m.status = 'PROCESSING'
        WHERE m.id IN :ids
          AND m.status = 'PENDING'
    """)
    int markAsProcessing(@Param("ids") List<Long> ids);
}
