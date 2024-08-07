package com.vehiculo.repository;

import com.vehiculo.entity.UserVehicule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehiculoRepository extends JpaRepository<UserVehicule,Long> {



}
