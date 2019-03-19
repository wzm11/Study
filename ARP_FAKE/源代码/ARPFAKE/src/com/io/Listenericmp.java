package com.io;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import com.frame.ICMPFrame;
import com.function.ReceiveICMP;
import com.function.SendICMP;

public class Listenericmp implements ActionListener{
	ICMPFrame icmpframe = ICMPFrame.getInstance();//得到单例窗口
	Thread t1,t2;   //一个线程，发送icmp
	public static boolean send;  //发送线程接收的判断，即退出循环判断
	public static boolean receive;   //接收线程接收的判断，即退出循环判断
	
	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("发送")) {
			send = false;   //send状态改变，开始线程
			t1 = new Thread(new SendICMP());
			t1.start();
		}
		if(e.getActionCommand().equals("停止")) {
			icmpframe.label_2.setForeground(new Color(0, 0, 0, 0));  //提示变透明
			send = true;   //send状态改变，结束线程
		} 
		if(e.getActionCommand().equals("执行")) {
			receive = false;     //receive状态改变，开始线程
			icmpframe.label_3.setForeground(new Color(0, 0, 0, 230)); //显示提示
			t2 = new Thread(new ReceiveICMP());
			ReceiveICMP.d = 1;
			t2.start();
		}
		if(e.getActionCommand().equals("终止")) { 
			icmpframe.label_3.setForeground(new Color(0, 0, 0, 0));  //提示变透明
			receive = true;    //receive状态改变，结束线程
		}
	}
}
