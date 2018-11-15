package com.team.app.dao;

import java.io.Serializable;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team.app.domain.TblDevLatlng;

public interface DeviceLatLngDao extends JpaRepository<TblDevLatlng, Serializable> {

	@Query("Select l from TblDevLatlng l where l.device=:devEUI")
	TblDevLatlng getDeviceLatLng(@Param("devEUI") String devEUI);

}
