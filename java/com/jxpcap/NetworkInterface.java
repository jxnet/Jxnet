package com.jxpcap;

public class NetworkInterface {
    
    private NetworkInterface next;
    
    public String getName() {
    	return name;
    }
    
    public String getDescription() {
    	return description;
    }
    
    public String getNetmask() {
    	return netmask;
    }
    
    public String getBroadcastAddress() {
    	return broadcast_address;
    }
    
    public String getIPAddress() {
    	return ip_address;
    }
    
    public String getMACAddress() {
    	return mac_address;
    }
    
    public String getDestionationAddress() {
    	return destination_address;
    }
    
    public void closeNext() {
    	this.next = null;
    }
    
    public NetworkInterface getNext() {
    	return next;
    }
    
    private String name;
   
    private String description;

    private String netmask;
    
    private String broadcast_address;
    
    private String ip_address;
    
    private String mac_address;
    
    private String destination_address;
    
    //private String AF_NAME;
}
