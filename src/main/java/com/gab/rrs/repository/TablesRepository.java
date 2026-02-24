package com.gab.rrs.repository;

import com.gab.rrs.entities.tables.Tables;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TablesRepository extends JpaRepository<Tables, Integer> {
}
