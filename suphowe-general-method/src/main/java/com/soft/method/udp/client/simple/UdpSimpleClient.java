package com.soft.method.udp.client.simple;

import com.soft.method.udp.constant.UdpConstants;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetSocketAddress;

/**
 * 默认发送方式
 * @author suphowe
 */
@Slf4j
@Service
public class UdpSimpleClient {

    public void sendMessage(String message) {
        log.info("发送UDP: {}", message);
        InetSocketAddress inetSocketAddress = new InetSocketAddress(UdpConstants.UDP_HOST, UdpConstants.UDP_PORT);
        byte[] udpMessage = message.getBytes();
        DatagramPacket datagramPacket = null;
        try (DatagramSocket datagramSocket = new DatagramSocket()) {
            datagramPacket = new DatagramPacket(udpMessage, udpMessage.length, inetSocketAddress);
            datagramSocket.send(datagramPacket);
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
        log.info("发送成功");
    }

}
