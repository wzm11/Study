package com.function;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import javax.swing.JScrollBar;
import com.frame.ARPFrame;
import com.frame.ICMPFrame;
import com.io.Listenerarp;
import com.io.Listenericmp;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.ARPPacket;
import jpcap.packet.DatalinkPacket;
import jpcap.packet.EthernetPacket;
import jpcap.packet.ICMPPacket;
import jpcap.packet.Packet;
public class ReceiveICMP implements Runnable{//接收报文的线程
	static int i = 0; //打印每一个包信息的序号
	String output;    //保存输出信息
	public static StringBuffer outbuff; //保存输出信息（缓存）
	public static int d;    //用户判断是哪个界面在进行抓包
	@Override
	public void run() {  
		ARPFrame arpframe = ARPFrame.getInstance(); //得到arp界面的实例
		ICMPFrame icmpframe = ICMPFrame.getInstance();//得到icmp界面的实例
		NetworkInterface[] devs = JpcapCaptor.getDeviceList();//获取网卡列表
		int num = devs.length;  //记录网卡个数
		JpcapCaptor oD = null;  //抓包类
		BufferedWriter bw = null;  //输出流
		FileWriter fw = null;	//文件流，保存数据
		try {
			//打开网卡连接
			if(d == 0) {//打开arp界面的网卡连接
				oD = JpcapCaptor.openDevice(devs[arpframe.comboBox_1.getSelectedIndex()],
																		64, true, 3000);
			}else {//打开icmp界面的网卡连接
				oD = JpcapCaptor.openDevice(devs[num-icmpframe.comboBox_1.getSelectedIndex()-1], 
																				64, true, 3000);
			}
			//接收报文保存位置
			fw = new FileWriter(Math.round(Math.random()*255)+".txt");
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {
			if(Listenerarp.receive) {//判断是否接收
				break;
			}
			if(Listenericmp.receive) {//判断是否接收
				break;
			}
			StringBuffer src_mac = new StringBuffer();//源mac地址
			StringBuffer dst_mac = new StringBuffer();//目的mac地址
			Packet packet = oD.getPacket();  //开始抓包
			if(packet instanceof ICMPPacket || packet instanceof ARPPacket) {
				//判断报文是否为ICMP与ARP
				DatalinkPacket datalink = null;//报文的链路层报文，以太网帧
				InetAddress dst_ip = null,src_ip = null; //源目的ip地址
				int packetType = 0;  //报文类型
				byte[] data = null;  //报文数据
				if(packet instanceof ICMPPacket) {//获取抓到的icmp报文的数据
					ICMPPacket icmp = (ICMPPacket) packet;
					datalink = icmp.datalink;
					packetType = icmp.type;
					src_ip = icmp.src_ip;
					dst_ip = icmp.dst_ip;
					data = icmp.data;
				}
				if(packet instanceof ARPPacket) {//获取抓到的arp报文的数据
					ARPPacket arp = (ARPPacket) packet;
					datalink = arp.datalink;
					packetType = arp.operation;
					try {
						src_ip = InetAddress.getByAddress(arp.sender_protoaddr);
						dst_ip = InetAddress.getByAddress(arp.target_protoaddr);
					} catch (UnknownHostException e) {
						e.printStackTrace();
					}
					
					data = arp.data;
				}
				EthernetPacket eth = (EthernetPacket) datalink;//获取链路层的信息（mac）
				byte[] s_mac = eth.src_mac;
				byte[] d_mac = eth.dst_mac;
				for (int j = 0;j < 6;j++) { //调整mac地址的打印格式
					String hS = Integer.toHexString(s_mac[j] & 0xff);
					if(hS.length() == 1) {
						src_mac.append('0');
					}
					src_mac.append(hS);
					if(j != 5) {
						src_mac.append('-');
					}
				}
				for (int j = 0;j < 6;j++) {
					String hD = Integer.toHexString(d_mac[j] & 0xff);
					if(hD.length() == 1) {
						dst_mac.append('0');
					}
					dst_mac.append(hD);
					if(j != 5) {
						dst_mac.append('-');
					}
				}
				outbuff = new StringBuffer();
				if(packetType == 0) {  //判断报文的类型，保存在outbuff中
					output = (++i) + "、" + "PacketType:Echo Reply\n";
					outbuff.append(output);
				}
				if(packetType == 8) {
					output = (++i) + "、" + "PacketType:Echo Request\n";
					outbuff.append(output);
				}
				if(packetType == 1) {
					output = (++i) + "、" + "PacketType:ARP_REQUEST\n";
					outbuff.append(output);
				}
				if(packetType == 2) {
					output = (++i) + "、" + "PacketType:ARP_REPLY\n";
					outbuff.append(output);
				}
				if(packetType == 5) {
					output = (++i) + "、" + "PacketType:ICMP Redirect\n";
					outbuff.append(output);
				}
				//将数据都保存在outbuff中
				output = " Src_mac:" + src_mac + "\n" + " Dst_mac:" + dst_mac + "\n";
				outbuff.append(output);
				output = " Src_ip:" + src_ip + "\n" + " Dst_ip:" + dst_ip + "\n";
				outbuff.append(output);
				output = " data:" + new String(data) + "\n";
				outbuff.append(output);
				arpframe.textArea.append(outbuff.toString());  //打印输出到arp界面的输出框
				icmpframe.textArea.append(outbuff.toString()); //打印输出到icmp界面的输出框
				try {
					bw.write(outbuff.toString());  //保存到本地文件中
					bw.write("\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
				//滚动部件自动滚动
				JScrollBar vs = arpframe.scrollPane.getVerticalScrollBar();
				vs.setValue(vs.getMaximum());
				JScrollBar vs1 = icmpframe.scrollPane.getVerticalScrollBar();
				vs1.setValue(vs1.getMaximum());
			}
		}	
		try {
			//关闭资源
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
