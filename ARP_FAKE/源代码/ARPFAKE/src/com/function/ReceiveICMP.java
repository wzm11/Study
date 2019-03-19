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
public class ReceiveICMP implements Runnable{//���ձ��ĵ��߳�
	static int i = 0; //��ӡÿһ������Ϣ�����
	String output;    //���������Ϣ
	public static StringBuffer outbuff; //���������Ϣ�����棩
	public static int d;    //�û��ж����ĸ������ڽ���ץ��
	@Override
	public void run() {  
		ARPFrame arpframe = ARPFrame.getInstance(); //�õ�arp�����ʵ��
		ICMPFrame icmpframe = ICMPFrame.getInstance();//�õ�icmp�����ʵ��
		NetworkInterface[] devs = JpcapCaptor.getDeviceList();//��ȡ�����б�
		int num = devs.length;  //��¼��������
		JpcapCaptor oD = null;  //ץ����
		BufferedWriter bw = null;  //�����
		FileWriter fw = null;	//�ļ�������������
		try {
			//����������
			if(d == 0) {//��arp�������������
				oD = JpcapCaptor.openDevice(devs[arpframe.comboBox_1.getSelectedIndex()],
																		64, true, 3000);
			}else {//��icmp�������������
				oD = JpcapCaptor.openDevice(devs[num-icmpframe.comboBox_1.getSelectedIndex()-1], 
																				64, true, 3000);
			}
			//���ձ��ı���λ��
			fw = new FileWriter(Math.round(Math.random()*255)+".txt");
			bw = new BufferedWriter(fw);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {
			if(Listenerarp.receive) {//�ж��Ƿ����
				break;
			}
			if(Listenericmp.receive) {//�ж��Ƿ����
				break;
			}
			StringBuffer src_mac = new StringBuffer();//Դmac��ַ
			StringBuffer dst_mac = new StringBuffer();//Ŀ��mac��ַ
			Packet packet = oD.getPacket();  //��ʼץ��
			if(packet instanceof ICMPPacket || packet instanceof ARPPacket) {
				//�жϱ����Ƿ�ΪICMP��ARP
				DatalinkPacket datalink = null;//���ĵ���·�㱨�ģ���̫��֡
				InetAddress dst_ip = null,src_ip = null; //ԴĿ��ip��ַ
				int packetType = 0;  //��������
				byte[] data = null;  //��������
				if(packet instanceof ICMPPacket) {//��ȡץ����icmp���ĵ�����
					ICMPPacket icmp = (ICMPPacket) packet;
					datalink = icmp.datalink;
					packetType = icmp.type;
					src_ip = icmp.src_ip;
					dst_ip = icmp.dst_ip;
					data = icmp.data;
				}
				if(packet instanceof ARPPacket) {//��ȡץ����arp���ĵ�����
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
				EthernetPacket eth = (EthernetPacket) datalink;//��ȡ��·�����Ϣ��mac��
				byte[] s_mac = eth.src_mac;
				byte[] d_mac = eth.dst_mac;
				for (int j = 0;j < 6;j++) { //����mac��ַ�Ĵ�ӡ��ʽ
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
				if(packetType == 0) {  //�жϱ��ĵ����ͣ�������outbuff��
					output = (++i) + "��" + "PacketType:Echo Reply\n";
					outbuff.append(output);
				}
				if(packetType == 8) {
					output = (++i) + "��" + "PacketType:Echo Request\n";
					outbuff.append(output);
				}
				if(packetType == 1) {
					output = (++i) + "��" + "PacketType:ARP_REQUEST\n";
					outbuff.append(output);
				}
				if(packetType == 2) {
					output = (++i) + "��" + "PacketType:ARP_REPLY\n";
					outbuff.append(output);
				}
				if(packetType == 5) {
					output = (++i) + "��" + "PacketType:ICMP Redirect\n";
					outbuff.append(output);
				}
				//�����ݶ�������outbuff��
				output = " Src_mac:" + src_mac + "\n" + " Dst_mac:" + dst_mac + "\n";
				outbuff.append(output);
				output = " Src_ip:" + src_ip + "\n" + " Dst_ip:" + dst_ip + "\n";
				outbuff.append(output);
				output = " data:" + new String(data) + "\n";
				outbuff.append(output);
				arpframe.textArea.append(outbuff.toString());  //��ӡ�����arp����������
				icmpframe.textArea.append(outbuff.toString()); //��ӡ�����icmp����������
				try {
					bw.write(outbuff.toString());  //���浽�����ļ���
					bw.write("\r\n");
				} catch (IOException e) {
					e.printStackTrace();
				}
				//���������Զ�����
				JScrollBar vs = arpframe.scrollPane.getVerticalScrollBar();
				vs.setValue(vs.getMaximum());
				JScrollBar vs1 = icmpframe.scrollPane.getVerticalScrollBar();
				vs1.setValue(vs1.getMaximum());
			}
		}	
		try {
			//�ر���Դ
			bw.close();
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
