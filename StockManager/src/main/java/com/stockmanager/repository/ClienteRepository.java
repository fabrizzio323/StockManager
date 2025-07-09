package com.stockmanager.repository;


import com.stockmanager.model.Client;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ClienteRepository  extends JpaRepository<Client, Long> {
       List<Client> findByStatus(boolean status);
}
