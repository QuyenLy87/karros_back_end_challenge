package com.example.demo.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;

import com.example.demo.domain.GPS;

public interface GPSRepository extends CrudRepository<GPS, Long>, PagingAndSortingRepository<GPS, Long> {
}
