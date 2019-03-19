package com.function;

import java.awt.Color;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JOptionPane;
import org.jnetpcap.Pcap;
import org.jnetpcap.PcapIf;
import org.jnetpcap.packet.JMemoryPacket;
import org.jnetpcap.packet.JPacket;
import org.jnetpcap.protocol.JProtocol;
import com.frame.ICMPFrame;
import com.io.GetValue;
import com.io.Listenericmp;
import jpcap.JpcapCaptor;
import jpcap.NetworkInterface;
import jpcap.packet.IPPacket;
import jpcap.packet.Packet;

public class SendICMP implements Runnable{

	static GetValue getvalue = new GetValue();
	@Override
	public void run() {
		ICMPFrame icmpframe = ICMPFrame.getInstance();
		if(getvalue.judge(icmpframe)) {//�ж������Ƿ���ȷ
			//��ʾ��ʾ
			icmpframe.label_2.setForeground(new Color(0, 0, 0, 230));
			//��ȡ�����豸
			List<PcapIf> alldevs = new ArrayList<>();
			StringBuilder errbuf = new StringBuilder();
			Pcap.findAllDevs(alldevs, errbuf);
			PcapIf device = alldevs.get(getvalue.devIndex);
			//�������豸
			Pcap pcap = Pcap.openLive(device.getName(), 64*1024, 
					Pcap.MODE_PROMISCUOUS, 10*1000, errbuf);
			
			String data = null; //��������data
			try {
				data = SendICMP.captor();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
			//����ICMP�ض����
			String str_ip = SendICMP.ipToString(getvalue.src_ip);
			String dst_ip = SendICMP.ipToString(getvalue.dst_ip);
			String route = SendICMP.ipToString(getvalue.route_ip);
			String str_mac = SendICMP.macToString(getvalue.s_mac);
			String dst_mac = SendICMP.macToString(getvalue.d_mac);
			String header1 = dst_mac+str_mac+ "08004500004100000000ff01";
			String iphex = "0000";
			String header2 = str_ip + dst_ip;
			String length = SendICMP.getlength(header1 + iphex + header2  + "05010000"+ route + data);
			header1 = dst_mac+str_mac+ "08004500" + length + "00000000ff01";
			iphex = SendICMP.caculate((header1 + iphex + header2).substring(28));
			String totle ="05010000" + route + data;
			String icmphex = SendICMP.caculate(totle);
			
			JPacket packet = new JMemoryPacket(JProtocol.ETHERNET_ID,
					header1 + iphex + header2 
				    + "0501" +icmphex+ route + data); 
			byte[] byteArray = packet.getByteArray(0,packet.size());
			
			//��ʼ�������ݰ�
			while(true) {
				if(Listenericmp.send) {//�ж��Ƿ����
					break;
				}
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				if(pcap.sendPacket(byteArray)!= Pcap.OK) {
					JOptionPane.showMessageDialog(null, "����ʧ��", "����", JOptionPane.WARNING_MESSAGE);
					pcap.close();
					break;
				}
			}
		}	
	}
	
	static String getlength(String packet) {//�õ����ĵĳ��ȣ�����ICMP����
		String str = new String();
		String length = Integer.toHexString((packet.length()-28)/2);
		for(int i = 4;i - length.length() > 0;i--) {
			str += "0";
		}
		str += length;
		return str;
	}
	
	static String ipToString(String ip) {//��ȡip��ַ
		String[] strings = ip.split("\\.");
		String str = new String();
		for(int k = 0;k<strings.length;k++) {
			int num = Integer.valueOf(strings[k]);
			if(Integer.toHexString(num).length() == 1) {
				str+="0";	
			}
			str+=Integer.toHexString(num);
		}
		return str;
	}
	
	static String macToString(String mac) {//��ȡmac��ַ
		String[] strs = mac.split("-");
		String str = new String();
		for(int i=0;i<strs.length;i++) {
			str += strs[i];
		}
		return str;
	}
	
	static String caculate(String data){//data��16������ʽ���룬���ڼ����ͷУ���
		int len = data.length() % 4;
		int[] num = new int[data.length()/4];
		int sum=0;
		for(int i = 0;i<data.length()/4;i++) {
			num[i]=Integer.parseInt(data.substring(4*i, 4*i+4),16);
			sum+=num[i];
		}
		if(len !=0) {
			sum+=Integer.parseInt(data.substring(data.length()-2),16);
		}
		String str = Integer.toHexString(sum);
		while(str.length() > 4) {
			int high = Integer.parseInt(str.substring(0, str.length()-4),16);
			int low = Integer.parseInt(str.substring(str.length()-4),16);
			str = Integer.toHexString(high+low);
		}
		str = Integer.toHexString(~Integer.parseInt(str,16));
		return str.substring(4);
	}
	
	static String captor() throws UnknownHostException{  //����IP������ȡ��ߵ�����ͷ��Ϣ���������ICMP�ض���
		NetworkInterface[] devs = JpcapCaptor.getDeviceList();
		int num = devs.length;  //��¼��������
		JpcapCaptor oD = null;
		String str = new String();
		int k = 25;
		try {
			//����������
			oD = JpcapCaptor.openDevice(devs[num - getvalue.devIndex - 1], 1024*1000, true, 3000);
		} catch (IOException e) {
			e.printStackTrace();
		}
		while(true) {
			k--;
			System.out.println(k);
			if(k <= 0) {
				JOptionPane.showMessageDialog(null, "��ʱʧ�ܣ�δ�յ�ָ��Ŀ��ICMP��", "����", JOptionPane.WARNING_MESSAGE);
				Listenericmp.send = false;
				break;
			}
			Packet packet = oD.getPacket();
			if(packet!=null && packet instanceof IPPacket) {
				IPPacket ippacket = (IPPacket) packet;
				if(ippacket.src_ip.equals(InetAddress.getByName(getvalue.dst_ip))) {
					byte[] header = packet.header;
					for(int i = 14;i<header.length;i++) {
						if(Integer.toHexString(header[i] & 0xff).length() == 1) {
							str += "0";
						}
						str += Integer.toHexString(header[i] & 0xff);
					}
					break;
				}
			}
		}
		return str;
	}

}
