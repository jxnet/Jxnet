/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.jxpcap.util.packet;

import com.jxpcap.packet.Packet;
import com.jxpcap.packet.PacketData;
import com.jxpcap.packet.PacketHeader;

/**
 *
 * @author langkuy
 */
public interface PacketListener extends PacketHeader, PacketData {
    void packetArrived(Packet packet);
}
