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
	//����ARP���ĵ��߳�
	@Override
	public void run() {
		GetValue getvalue = new GetValue();
		ARPFrame arpframe = ARPFrame.getInstance();
		if(getvalue.judge(arpframe)) {//�ж��û��������Ϣ�Ƿ���ȷ
			arpframe.label_2.setForeground(new Color(0, 0, 0, 230));//��ʾ������ʾ
			NetworkInterface[] devs = JpcapCaptor.getDeviceList();//��ȡ���������б�
			JpcapSender od = null;
			try {//��һ������������
				od = JpcapSender.openDevice(devs[getvalue.devIndex]);
			} catch (IOException e) {
				e.printStackTrace();
			}
			ARPPacket arp = new ARPPacket();//����ARP����
			arp.hardtype = ARPPacket.HARDTYPE_ETHER;//��̫������
			arp.prototype = ARPPacket.PROTOTYPE_IP;//Э������
			arp.operation = getvalue.operation;//arp�������ͣ������Ӧ��
			arp.hlen = 6;  //mac��ַ����
			arp.plen = 4;  //IP��ַ����
			arp.sender_hardaddr = getvalue.src_mac;  //ԴMAC��ַ
			arp.target_hardaddr = getvalue.dst_mac;	 //Ŀ��MAC��ַ
			try {//ԴIP��ַ��Ŀ��IP��ַ
				arp.sender_protoaddr = InetAddress.getByName(getvalue.src_ip).getAddress();
				arp.target_protoaddr = InetAddress.getByName(getvalue.dst_ip).getAddress();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			EthernetPacket ether = new EthernetPacket();//������̫��֡
			ether.frametype = EthernetPacket.ETHERTYPE_ARP;//֡��װ�İ�����
			ether.dst_mac = getvalue.dst_mac;   //Ŀ��MAC��ַ
			ether.src_mac = getvalue.src_mac;   //ԴMAC��ַ
			arp.datalink = ether;  //֡��װARP����
			while(true) {//��������
				if(Listenerarp.send) {//�ж��Ƿ����
					break;
				}
				od.sendPacket(arp);  //���ͱ���
				try {   //1s������
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}	
	}
}
