package com.io;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

import com.frame.ARPFrame;
import com.frame.ICMPFrame;

import jpcap.packet.ARPPacket;

public class GetValue {
	
	public int devIndex;
	public short operation;
	public String src_ip,dst_ip,route_ip;
	public byte[] src_mac,dst_mac;
	public String s_mac;
	public String d_mac;
	public boolean judge(JFrame frame) {//获取用户输入的数据并判断是否正确
		
		if(frame instanceof ARPFrame) {
			ARPFrame arpframe = (ARPFrame) frame;
			devIndex = arpframe.comboBox.getSelectedIndex();
			if(arpframe.rdbtnNewRadioButton.isSelected()) {
				operation = ARPPacket.ARP_REQUEST;
			}else {
				operation = ARPPacket.ARP_REPLY;
			}
			src_ip = arpframe.textPane.getText();
			dst_ip = arpframe.textPane_2.getText();
			s_mac = arpframe.textPane_1.getText();
			d_mac = arpframe.textPane_3.getText();
		}else {
			ICMPFrame icmpframe = (ICMPFrame) frame;
			devIndex = icmpframe.comboBox.getSelectedIndex();
			src_ip = icmpframe.textPane.getText();
			dst_ip = icmpframe.textPane_2.getText();
			s_mac = icmpframe.textPane_1.getText();
			d_mac = icmpframe.textPane_3.getText();
			route_ip = icmpframe.textPane_4.getText();
		}
		
		if( (!src_ip.matches("([0-9]{1,3}\\.){3}[0-9]{1,3}")) || (!dst_ip.matches("([0-9]{1,3}\\.){3}[0-9]{1,3}")) ){
			//错误弹窗
			JOptionPane.showMessageDialog(null, "IP格式：xxx.xxx.xxx.xxx", "出错", JOptionPane.WARNING_MESSAGE);
			return false;
		}
		if((!s_mac.matches("([A-Fa-f0-9]{2}-){5}[A-Fa-f0-9]{2}")) || (!d_mac.matches("([A-Fa-f0-9]{2}-){5}[A-Fa-f0-9]{2}"))){
			//错误弹窗
			JOptionPane.showMessageDialog(null, "MAC格式：xx-xx-xx-xx-xx-xx", "出错", JOptionPane.WARNING_MESSAGE);
			return false;
		}else {
			src_mac = new byte[6];
			dst_mac = new byte[6];
			String[] str1 = s_mac.split("-");
			String[] str2 = d_mac.split("-");
			for(int i = 0;i < 6;i++) {
				src_mac[i] = (byte)Integer.parseInt(str1[i],16);
				dst_mac[i] = (byte)Integer.parseInt(str2[i],16);
			}
		}
		return true;
	}
}
