package com.example.storeproject.Cache;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CacheUserRepository extends JpaRepository<CacheUser,Long> {
}
