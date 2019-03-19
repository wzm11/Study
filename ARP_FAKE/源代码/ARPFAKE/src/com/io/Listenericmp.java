package com.io;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.frame.ICMPFrame;
import com.function.ReceiveICMP;
import com.function.SendICMP;

public class Listenericmp implements ActionListener{
	ICMPFrame icmpframe = ICMPFrame.getInstance();//�õ���������
	Thread t1,t2;   //һ���̣߳�����icmp
	public static boolean send;  //�����߳̽��յ��жϣ����˳�ѭ���ж�
	public static boolean receive;   //�����߳̽��յ��жϣ����˳�ѭ���ж�
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("����")) {
			send = false;   //send״̬�ı䣬��ʼ�߳�
			t1 = new Thread(new SendICMP());
			t1.start();
		}
		if(e.getActionCommand().equals("ֹͣ")) {
			icmpframe.label_2.setForeground(new Color(0, 0, 0, 0));  //��ʾ��͸��
			send = true;   //send״̬�ı䣬�����߳�
		} 
		if(e.getActionCommand().equals("ִ��")) {
			receive = false;     //receive״̬�ı䣬��ʼ�߳�
			icmpframe.label_3.setForeground(new Color(0, 0, 0, 230)); //��ʾ��ʾ
			t2 = new Thread(new ReceiveICMP());
			ReceiveICMP.d = 1;
			t2.start();
		}
		if(e.getActionCommand().equals("��ֹ")) { 
			icmpframe.label_3.setForeground(new Color(0, 0, 0, 0));  //��ʾ��͸��
			receive = true;    //receive״̬�ı䣬�����߳�
		}
	}
}
