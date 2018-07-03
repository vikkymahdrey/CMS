package com.team.app.dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.team.app.domain.LoraFrame;

public interface FrameDao extends JpaRepository<LoraFrame, Serializable> {

	@Query("Select f from LoraFrame f order by id desc")
	List<LoraFrame> getFrames();

	/*@Query(value="Select * from lora_frames f where f.nodeName=?1 and f.DeviceId=?2  ORDER BY id DESC LIMIT 10",nativeQuery = true)
	List<LoraFrame> getFramesByLoraIdAndDevId(@Param("loraId") String loraId, @Param("deviceId") String deviceId);*/
	
	@Query(value="Select * from lora_frames f where f.nodeName=?1 and f.DeviceId=?2  ORDER BY id DESC LIMIT 1",nativeQuery = true)
	LoraFrame getFramesByLoraIdAndDevId(@Param("loraId") String loraId, @Param("deviceId") String deviceId);

	/*@Query(value="Select * from lora_frames f group by f.DeviceId",nativeQuery = true)
	List<LoraFrame> getFrameByDeviceId();*/
	
	@Query(value="Select f from LoraFrame f group by f.deviceId,f.nodeName")
	List<LoraFrame> getFrameByDeviceId();

	@Query("Select f from LoraFrame f where f.deviceId=:deviceId and f.nodeName=:nodeName order by id desc")
	List<LoraFrame> getFrameByDevId(@Param("deviceId") String deviceId,@Param("nodeName") String nodeName);

	@Query("Select f from LoraFrame f where f.deviceId=:deviceId and f.devEUI=:devEUI")
	List<LoraFrame> getFramesByNodeNameAndID(@Param("deviceId") String deviceId,@Param("devEUI") String devEUI);
		
	@Modifying
	@Query(value="UPDATE lora_frames f SET f.central=?1 ,f.peripheral=?2, f.nodeName=?3 WHERE f.DeviceId=?4 and f.devEUI=?5",nativeQuery = true)
	@Transactional
	void setUpdateLoraFrames(@Param("central") String central,@Param("peripheral") String peripheral, @Param("nodeName") String nodeName,@Param("deviceId") String deviceId, @Param("devEUI") String devEUI);

	@Query(value="Select f.id, round(avg(f.Temperature)), round(avg(f.Pressure)), round(avg(f.Humidity)) from lora_frames f where f.created_at>=?1 and f.nodeName=?2 and f.DeviceId=?3 ",nativeQuery = true)
	List<Object[]> getFramesByReqDt(@Param("createdAt") Date createdAt, @Param("nodeName") String nodeName, @Param("deviceId") String deviceId);

	
	@Query(value="Select f.id, round(avg(f.Temperature)), round(avg(f.Pressure)), round(avg(f.Humidity)) from lora_frames f where (f.created_at between ?1 AND ?2) and f.nodeName=?3 and f.DeviceId=?4 ",nativeQuery = true)
	List<Object[]> getFramesByBtnDts(@Param("createdAt") Date createdAt,@Param("diffDt") Date diffDt, @Param("nodeName") String nodeName, @Param("deviceId") String deviceId);

	@Query(value="SELECT f1.* FROM (Select max(id) as id from lora_frames group by nodeName, deviceId) f join lora_frames f1 on f.id=f1.id",nativeQuery = true)
	List<LoraFrame> deviceInfoFrames();

	@Query(value="Select f.* from lora_frames f where f.DeviceId=?1 and f.devEUI=?2 and f.central!='null' and f.peripheral!='null' order by id desc limit 1",nativeQuery = true)
	LoraFrame getNamingPacket(@Param("deviceId") String deviceId, @Param("devEUI") String devEUI);
	
	@Modifying
	@Query(value="UPDATE lora_frames f SET f.nodeName=?1 WHERE f.devEUI=?2", nativeQuery = true)
	@Transactional
	void setUpdateNodeName(@Param("nodeName") String nodeName, @Param("devEUI") String devEUI);

	@Query(value="Select f.* from lora_frames f where f.devEUI=?1 group by f.DeviceId",nativeQuery = true)
	List<LoraFrame> getDeviceIdByDevEUI(@Param("devEUI") String devEUI);

	@Modifying
	@Query(value="delete from lora_frames  where applicationID=?1 and devEUI=?2 and DeviceId=?3 ",nativeQuery = true)
	@Transactional
	void deleteDevByDevEUI(@Param("applicationID") String applicationID,@Param("devEUI") String devEUI,@Param("DeviceId") String DeviceId);

	@Query(value="Select f.* from lora_frames f where f.DeviceId=?1 and f.devEUI=?2 order by id desc limit 1",nativeQuery = true)
	LoraFrame getNamingPacket1(@Param("deviceId") String deviceId,@Param("devEUI") String devEUI);

	@Modifying
	@Query(value="delete from lora_frames  where applicationID=?1 and devEUI=?2",nativeQuery = true)
	@Transactional
	void deleteDevEUI(@Param("applicationID") String applicationID, @Param("devEUI") String devEUI);

	@Query("Select f from LoraFrame f where f.applicationID=:applicationID group by f.devEUI")
	List<LoraFrame> getDevEUIByAppId(@Param("applicationID") String applicationID);

	@Query("Select f from LoraFrame f group by f.devEUI")
	List<LoraFrame> devEUIGroupBy();
	
	@Query(value="Select f from LoraFrame f where f.devEUI=:devEUI order by f.id desc")
	List<LoraFrame> getFrameByDevEUI(@Param("devEUI") String devEUI);

	@Query(value="Select f.* from lora_frames f where f.devEUI=?1 order by f.id desc limit 1",nativeQuery = true)
	LoraFrame getFrameByDeviceEUI(@Param("devEUI") String devEUI);
	
	



}
