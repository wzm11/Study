package com.io;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.frame.ARPFrame;
import com.frame.ICMPFrame;
import com.function.ReceiveICMP;
import com.function.SendARP;

public class Listenerarp implements ActionListener{
	//������ť�Ƿ񱻰���
	ARPFrame arpframe = ARPFrame.getInstance();//�õ���������
	ICMPFrame icmpframe = ICMPFrame.getInstance();//�õ���������
	Thread t1,t2;   //�����̣߳������뷢��
	public static boolean send;  //�����߳̽��յ��жϣ����˳�ѭ���ж�
	public static boolean receive;   //�����߳̽��յ��жϣ����˳�ѭ���ж�
	public static boolean show = true;
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("����")) {
			send = false;   //send״̬�ı䣬��ʼ�߳�
			t1 = new Thread(new SendARP());
			t1.start();
		}
		if(e.getActionCommand().equals("ֹͣ")) {
			arpframe.label_2.setForeground(new Color(0, 0, 0, 0));  //��ʾ��͸��
			send = true;   //send״̬�ı䣬�����߳�
		} 
		if(e.getActionCommand().equals("ִ��")) {
			receive = false;     //receive״̬�ı䣬��ʼ�߳�
			arpframe.label_3.setForeground(new Color(0, 0, 0, 230)); //��ʾ��ʾ
			t2 = new Thread(new ReceiveICMP());
			ReceiveICMP.d = 0;
			t2.start();
		}
		if(e.getActionCommand().equals("��ֹ")) { 
			arpframe.label_3.setForeground(new Color(0, 0, 0, 0));  //��ʾ��͸��
			receive = true;    //receive״̬�ı䣬�����߳�
		}
		if(e.getActionCommand().equals("�л�")) {
			show = !show;
			arpframe.frame.setVisible(show);
			icmpframe.frame.setVisible(!show);
		}
	}
	
}
