package com.stockmanager.repository;

import com.stockmanager.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductoRepository extends JpaRepository<Product,Long> {
    List<Product> findByStatus(boolean status);
    Optional<Product> findByIdAndStatus(Long id, boolean status);
}
