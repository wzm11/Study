package com.function;
import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import com.frame.ARPFrame;
import com.io.GetValue;
import com.io.Listenerarp;
import jpcap.JpcapCaptor;
import jpcap.JpcapSender;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.EthernetPacket;
public class SendARP implements Runnable{
	//发送ARP报文的线程
	@Override
	public void run() {
		GetValue getvalue = new GetValue();
		ARPFrame arpframe = ARPFrame.getInstance();
		if(getvalue.judge(arpframe)) {//判断用户输入的信息是否正确
			arpframe.label_2.setForeground(new Color(0, 0, 0, 230));//显示发送提示
			NetworkInterface[] devs = JpcapCaptor.getDeviceList();//获取主机网卡列表
			JpcapSender od = null;
			try {//打开一个网卡的连接
				od = JpcapSender.openDevice(devs[getvalue.devIndex]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ARPPacket arp = new ARPPacket();//制作ARP报文
			arp.hardtype = ARPPacket.HARDTYPE_ETHER;//以太网类型
			arp.prototype = ARPPacket.PROTOTYPE_IP;//协议类型
			arp.operation = getvalue.operation;//arp报文类型（请求或应答）
			arp.hlen = 6;  //mac地址长度
			arp.plen = 4;  //IP地址长度
			arp.sender_hardaddr = getvalue.src_mac;  //源MAC地址
			arp.target_hardaddr = getvalue.dst_mac;	 //目的MAC地址
			try {//源IP地址和目的IP地址
				arp.sender_protoaddr = InetAddress.getByName(getvalue.src_ip).getAddress();
				arp.target_protoaddr = InetAddress.getByName(getvalue.dst_ip).getAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			EthernetPacket ether = new EthernetPacket();//制作以太网帧
			ether.frametype = EthernetPacket.ETHERTYPE_ARP;//帧封装的包类型
			ether.dst_mac = getvalue.dst_mac;   //目的MAC地址
			ether.src_mac = getvalue.src_mac;   //源MAC地址
			arp.datalink = ether;  //帧封装ARP报文
			while(true) {//持续发送
				if(Listenerarp.send) {//判断是否结束
					break;
				}
				od.sendPacket(arp);  //发送报文
				try {   //1s发两个
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}	
	}
}
