package com.team.app.config;
import java.nio.charset.Charset;
import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.apache.commons.codec.binary.Base64;
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken;
import org.eclipse.paho.client.mqttv3.MqttCallback;
import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.team.app.dao.FrameDao;
import com.team.app.domain.LoraFrame;
import com.team.app.logger.AtLogger;
import com.team.app.service.ConsumerInstrumentService;

@Service
public class MqttBroker implements MqttCallback,MqttIntrf {
	
	private static final AtLogger logger = AtLogger.getLogger(MqttBroker.class);
	
		
	@Autowired
	private FrameDao frameDao;	
	
	@Autowired
	private ConsumerInstrumentService consumerInstrumentServiceImpl;
	
	MqttClient client;
	
	MqttMessage message;
	
	
	
	 public void doDemo(String appId, String devId)  {
	    try {
	    	logger.debug("/ INside MQTT Broker"+devId);	
	    	MqttConnectOptions connOpts = new MqttConnectOptions();
	        connOpts.setUserName("loragw");
	        connOpts.setPassword("loragw".toCharArray());
	        connOpts.setCleanSession(true);
	        client = new MqttClient("tcp://139.59.14.31:1883", MqttClient.generateClientId());
	        
	        client.connect(connOpts);
	        client.setCallback(this);
	        client.subscribe("application/"+appId+"/node/"+devId+"/rx");
	        MqttMessage message = new MqttMessage();
	        message.setPayload("sending......."
	                .getBytes());
	        client.publish("application/"+appId+"/node/"+devId+"/tx", message);
	        System.out.println("Message printing here "+message);
	        //System.exit(0);
	    } catch (MqttException e) {
	        e.printStackTrace();
	    }
	}    
	
	public void doDemo() {
	  
	 
	}

	
	public void connectionLost(Throwable cause) {
	   

	}
	
	
	
	@Transactional
	public void messageArrived(String topic, MqttMessage message) throws Exception {
		logger.debug("Inside messageArrived");
		try{
			LoraFrame frm=null;
			if(!message.toString().isEmpty()){
				Charset UTF8 = Charset.forName("UTF-8");
				  JSONObject json=null;
				  		json=new JSONObject();
				  		json=(JSONObject)new JSONParser().parse(message.toString());
				  		logger.debug("REsultant json",json);  			  		 
				  		 
				  	  	
				  		logger.debug("Data data",json.get("data").toString());
				  	  	
				  	  	if(json.get("data")!=null){	
				  	  		 
				     		 byte[] decoded=Base64.decodeBase64(json.get("data").toString());
				     		logger.debug("length as ",decoded.length);   		 					     		 	
				     		 	if(decoded!=null && decoded.length>0){
				     		 		frm=new LoraFrame();
							  		
				     		 		frm.setApplicationID(json.get("applicationID").toString());
				     		 		frm.setApplicationName(json.get("applicationName").toString());
				     		 		frm.setNodeName(json.get("nodeName").toString());
				     		 		frm.setDevEUI(json.get("devEUI").toString());
							  		logger.debug("applicationID",json.get("applicationID").toString());
										logger.debug("applicationName",json.get("applicationName").toString());
											logger.debug("nodeName",json.get("nodeName").toString());
												logger.debug("devEUI",json.get("devEUI").toString());											
												

										  		 JSONArray arr=(JSONArray) json.get("rxInfo");
										  		 
										  		 if(arr!=null && arr.size()>0){
							   						 for (int i = 0; i < arr.size(); i++) {
							   							 JSONObject jsonObj = (JSONObject) arr.get(i);
							   							frm.setGatewayMac(jsonObj.get("mac").toString());
							   							frm.setGatewayName(jsonObj.get("name").toString());
							   							 
								   							logger.debug("mac",jsonObj.get("mac").toString());
								   								logger.debug("name",jsonObj.get("name").toString());
							   						 }
										  		 }
										  		 
										  		
										  		logger.debug("fport",json.get("fPort").toString());  	
										  	  		
										  		frm.setfPort(json.get("fPort").toString().trim());
										  		
				     		 							   				     		 		
				     		 	  // for(Byte b : decoded){
				     		 		   
				     		 		
				     		 		 String decodeBinary = Integer.toBinaryString(decoded[0]);	
				     		 		 //String firstByte=String.format("%02X ", b);
				     		 		 
				     		 		 logger.debug("decodeBinary val: ",decodeBinary);
				     		 		 //logger.debug("firstByte val: ",firstByte);
				     		 		 
			     		 			 if(decodeBinary.equals("0")){
				     		 		   frm.setLed1("0");
				     		 		   frm.setLed2("0");
				     		 		   frm.setLed3("0");
				     		 		   frm.setLed4("0");
			     		 			 }else{
			     		 				String led1 ="";
			     		 				String led2 ="";
			     		 				String led3 ="";
			     		 				String led4 ="";
			     		 				 
			     		 				 if(decodeBinary.length()==4){
			     		 					 logger.debug("Length: 4 ");
			     		 					led1 = decodeBinary.substring(0, 1);	
			     		 					led2 = decodeBinary.substring(1, 2);	
			     		 					led3 = decodeBinary.substring(2, 3);	
			     		 					led4 = decodeBinary.substring(3, 4);	
				  	  				 		
			     		 				 }else if(decodeBinary.length()==3){
			     		 					logger.debug("Length: 3 ");
			     		 					led1="0";
			     		 					led2 = decodeBinary.substring(0, 1);	
			     		 					led3 = decodeBinary.substring(1, 2);	
			     		 					led4 = decodeBinary.substring(2, 3);
			     		 				 }else if(decodeBinary.length()==2){
			     		 					logger.debug("Length: 2 ");
			     		 					led1="0";
			     		 					led2="0";
			     		 					led3 = decodeBinary.substring(0, 1);	
			     		 					led4 = decodeBinary.substring(1, 2);
			     		 					 
			     		 				 }else if(decodeBinary.length()==1){
			     		 					logger.debug("Length: 1 ");
			     		 					led1="0";
			     		 					led2="0";
			     		 					led3="0";
			     		 					led4 = decodeBinary.substring(0, 1);
			     		 					 
			     		 				 } 
			     		 				logger.debug("led1: 1 ",led1);
			     		 				logger.debug("led2: 1 ",led2);
			     		 				logger.debug("led3: 1 ",led3);
			     		 				logger.debug("led4: 1 ",led4);
			     		 				frm.setLed1(led1);
					     		 		   frm.setLed2(led2);
					     		 		   		frm.setLed3(led3);
					     		 		   			frm.setLed4(led4);
			     		 			 }
			     		 			 
			     		 			 frm.setCreatedAt(new Date(System.currentTimeMillis()));
			     		 			 frm.setUpdatedAt(new Date(System.currentTimeMillis()));
			     		 			 LoraFrame lfrm=frameDao.save(frm);
			     		 			 
				     		 	//}// for loop end here		  	
				     		 		
				     	}//if end here
				     		 	
				     		 	
					
				  	  	}	
				  	  	
				  				  		
				  		
				  		
				  		
		}
		
		}catch(Exception e){
			logger.error("Error",e);
			e.printStackTrace();
		}
	}
	
	



	public void deliveryComplete(IMqttDeliveryToken token) {
	    // TODO Auto-generated method stub

	}

}
