package com.main;

import java.io.IOException;

import com.frame.ARPFrame;


public class run { //�������
	public static void main(String[] args) throws IOException{
		ARPFrame arpframe = ARPFrame.getInstance();
		arpframe.creatFrame();
	}
}

