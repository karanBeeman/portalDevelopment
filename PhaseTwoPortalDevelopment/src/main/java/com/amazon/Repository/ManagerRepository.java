package com.amazon.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.amazon.portaldevelopment.entity.ManagerEntity;

public interface ManagerRepository extends JpaRepository<ManagerEntity, Integer>{

}
