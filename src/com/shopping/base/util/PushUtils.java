package com.shopping.base.util;


import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.shopping.base.constant.Conf;

import cn.jpush.api.JPushClient;
import cn.jpush.api.common.resp.APIConnectionException;
import cn.jpush.api.common.resp.APIRequestException;
import cn.jpush.api.push.PushResult;
import cn.jpush.api.push.model.Message;
import cn.jpush.api.push.model.Platform;
import cn.jpush.api.push.model.PushPayload;
import cn.jpush.api.push.model.audience.Audience;
import cn.jpush.api.push.model.notification.AndroidNotification;
import cn.jpush.api.push.model.notification.IosNotification;
import cn.jpush.api.push.model.notification.Notification;

public class PushUtils {
	
	
	private final static String appkey = Conf.PUSH_APPKEY;
	private final static String masterSecret = Conf.PUSH_MASTERSECRET;

	protected static final Logger LOG = LoggerFactory.getLogger(PushUtils.class);
	
	

	public static boolean send(String phone,String msgContent,String registrationID){
		JPushClient jpushClient = new JPushClient(masterSecret, appkey);
		   
        try {
        	
        //	PushResult result =	jpushClient.sendPush(buildPushObject_all_alias_alert(registrationID,msgContent,title));
        	
        	PushResult result =	jpushClient.sendPush(buildPushObject_android_and_ios(registrationID,msgContent,phone));
        	 LOG.info("Got result - " + result);
        	 if(result.isResultOK()){
        		 return true;
        	 }   
        
           
            
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.error("HTTP Status: " + e.getStatus());
            LOG.error("Error Code: " + e.getErrorCode());
            LOG.error("Error Message: " + e.getErrorMessage());
            LOG.error("Msg ID: " + e.getMsgId());
            LOG.error("registrationID: " + registrationID);
            LOG.error("phone: " + phone);
            LOG.error("msgContent: " + msgContent);
        }
        return false;
	}
	
	public static boolean sendAll(String msgContent){
		JPushClient jpushClient = new JPushClient(masterSecret, appkey);
		   
        try {
        	
        //	PushResult result =	jpushClient.sendPush(buildPushObject_all_alias_alert(registrationID,msgContent,title));
        	
        	PushResult result =	jpushClient.sendPush(buildPushObject_all_all_alert(msgContent));
        	 LOG.info("Got result - " + result);
        	 if(result.isResultOK()){
        		 return true;
        	 }   
        
           
            
        } catch (APIConnectionException e) {
            LOG.error("Connection error. Should retry later. ", e);
            
        } catch (APIRequestException e) {
            LOG.error("Error response from JPush server. Should review and fix it. ", e);
            LOG.error("HTTP Status: " + e.getStatus());
            LOG.error("Error Code: " + e.getErrorCode());
            LOG.error("Error Message: " + e.getErrorMessage());
            LOG.error("Msg ID: " + e.getMsgId());
            LOG.error("msgContent: " + msgContent);
        }
        return false;
	}
	
	
	
	
	
	
	 public static PushPayload buildPushObject_all_alias_alert(String alias,String content,String phone) {
		 Map<String,Object> extras = new HashMap<String,Object>();
		 extras.put("phones", phone);
		 extras.put("content", content);
	        return PushPayload.newBuilder()
	                .setPlatform(Platform.all())
	                .setAudience(Audience.alias(alias))
	                .setNotification(Notification.alert(extras)).setMessage(Message.content(content))
	                .build();
	}
	 
	 
	public static PushPayload buildPushObject_all_all_alert(String ALERT) {
	        return PushPayload.alertAll(ALERT);
	}
	 
	 
	public static PushPayload buildPushObject_android_and_ios(String tag,String content,String phone) {  
	
	        return PushPayload.newBuilder()  
	                .setPlatform(Platform.android_ios())  
	                .setAudience(Audience.alias(tag))  
	                .setNotification(Notification.newBuilder()  
	                        .setAlert(content)  
	                        .addPlatformNotification(AndroidNotification.newBuilder()
	                        		.setTitle(phone)
	                        		.addExtra("phones", phone).addExtra("content", content)
	                                .build())  
	                        .addPlatformNotification(IosNotification.newBuilder()  
	                                .incrBadge(1)  
	                                .addExtra("content", content).addExtra("phones",phone).build())  
	                        .build())  
	                .build();  
	}  
  
}