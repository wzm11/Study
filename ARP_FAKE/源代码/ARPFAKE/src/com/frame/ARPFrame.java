package com.frame;
import javax.swing.JFrame;
import java.awt.GridLayout;
import java.io.IOException;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;

import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import javax.swing.JComboBox;
import javax.swing.BorderFactory;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JRadioButton;
import javax.swing.JTextPane;
import javax.swing.SwingConstants;

import com.io.Listenerarp;

import jpcap.JpcapCaptor;


public class ARPFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	public JFrame frame;
	private static ARPFrame arpframe = null;
	Enumeration<NetworkInterface> en = null;
	jpcap.NetworkInterface[] devs = JpcapCaptor.getDeviceList();
	ArrayList<NetworkInterface> list = new ArrayList<>();
	NetworkInterface[] ni = null;
	public JLabel label_2;
	public JLabel label_3;
	public JComboBox<String> comboBox;
	public JComboBox<String> comboBox_1;
	public JTextPane textPane,textPane_1,textPane_2,textPane_3,textPane_4;
	public JRadioButton rdbtnNewRadioButton;
	public JRadioButton rdbtnArp;
	public JTextArea textArea;
	public JScrollPane scrollPane;
	private ICMPFrame icmpframe = ICMPFrame.getInstance();
//	public static void main(String args[]) throws SocketException {
//		ARPFrame.getInstance().creatFrame();
//	}
	public static ARPFrame getInstance() {//单例模式
		if(arpframe == null) {
			arpframe = new ARPFrame();
		}
		return arpframe;
	}
	private ARPFrame() {}

	/**
	 * @throws IOException 
	 * @wbp.parser.entryPoint
	 */
	public void creatFrame() throws IOException {
		frame = new JFrame("ARP欺騙");
		frame.setBounds(100, 100, 600, 450);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		frame.setLocation(350, 120);
		icmpframe.creatFrame();
		
		JPanel panel = new JPanel();
		frame.getContentPane().add(panel, BorderLayout.CENTER);
		panel.setLayout(new GridLayout(0, 2, 0, 0));
		
		JPanel panel_1 = new JPanel();
		panel.add(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		panel_1.setBorder(BorderFactory.createLoweredBevelBorder());
		
		JPanel panel_15 = new JPanel();
		panel_1.add(panel_15, BorderLayout.NORTH);
		panel_15.setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel_1 = new JLabel("发送ARP报文",JLabel.CENTER);
		lblNewLabel_1.setForeground(Color.RED);
		lblNewLabel_1.setFont(new Font("宋体", Font.PLAIN, 25));
		panel_15.add(lblNewLabel_1);
		
		JPanel panel_71 = new JPanel();
		panel_15.add(panel_71, BorderLayout.NORTH);
		
		JPanel panel_72 = new JPanel();
		panel_15.add(panel_72, BorderLayout.SOUTH);
		
		JPanel panel_73 = new JPanel();
		panel_15.add(panel_73, BorderLayout.WEST);
		
		JButton button_4 = new JButton("\u5207\u6362");
		panel_15.add(button_4, BorderLayout.EAST);
		button_4.addActionListener(new Listenerarp());
		
		JPanel panel_16 = new JPanel();
		FlowLayout flowLayout_2 = (FlowLayout) panel_16.getLayout();
		flowLayout_2.setVgap(15);
		panel_1.add(panel_16, BorderLayout.SOUTH);
		
		label_2 = new JLabel("报文持续发送中。。。");
		label_2.setVerticalAlignment(SwingConstants.TOP);
		label_2.setForeground(new Color(0, 0, 0, 0));
		panel_16.add(label_2);
		
		JPanel panel_17 = new JPanel();
		panel_1.add(panel_17, BorderLayout.CENTER);
		panel_17.setLayout(new GridLayout(0, 2, 5, 2));
		
		JPanel panel_18 = new JPanel();
		panel_17.add(panel_18);
		panel_18.setLayout(new GridLayout(7, 1, 0, 0));
		
		JPanel panel_21 = new JPanel();
		panel_18.add(panel_21);
		panel_21.setLayout(new BorderLayout(0, 0));
		
		JLabel label_1 = new JLabel("选择网卡接口：",JLabel.RIGHT);
		label_1.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_21.add(label_1, BorderLayout.CENTER);
		
		JPanel panel_22 = new JPanel();
		panel_18.add(panel_22);
		panel_22.setLayout(new BorderLayout(0, 0));
		
		JLabel lblip = new JLabel("源IP地址：",JLabel.RIGHT);
		lblip.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_22.add(lblip);
		
		JPanel panel_23 = new JPanel();
		panel_18.add(panel_23);
		panel_23.setLayout(new BorderLayout(0, 0));
		
		JLabel lblmac = new JLabel("源MAC地址：",JLabel.RIGHT);
		lblmac.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_23.add(lblmac);
		
		JPanel panel_24 = new JPanel();
		panel_18.add(panel_24);
		panel_24.setLayout(new BorderLayout(0, 0));
		
		JLabel lblip_1 = new JLabel("目的IP地址：",JLabel.RIGHT);
		lblip_1.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_24.add(lblip_1);
		
		JPanel panel_25 = new JPanel();
		panel_18.add(panel_25);
		panel_25.setLayout(new BorderLayout(0, 0));
		
		JLabel lblmac_1 = new JLabel("目的MAC地址：",JLabel.RIGHT);
		lblmac_1.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_25.add(lblmac_1);
		
		JPanel panel_26 = new JPanel();
		panel_18.add(panel_26);
		panel_26.setLayout(new BorderLayout(0, 0));
		
		rdbtnNewRadioButton = new JRadioButton("ARP请求");
		rdbtnNewRadioButton.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_26.add(rdbtnNewRadioButton);
		
		JPanel panel_49 = new JPanel();
		FlowLayout flowLayout_3 = (FlowLayout) panel_49.getLayout();
		flowLayout_3.setHgap(15);
		flowLayout_3.setVgap(25);
		panel_26.add(panel_49, BorderLayout.WEST);
		
		JPanel panel_50 = new JPanel();
		FlowLayout flowLayout_4 = (FlowLayout) panel_50.getLayout();
		flowLayout_4.setHgap(15);
		panel_26.add(panel_50, BorderLayout.EAST);
		
		JPanel panel_20 = new JPanel();
		panel_18.add(panel_20);
		panel_20.setLayout(new BorderLayout(0, 0));
		
		JButton button_2 = new JButton("发送");
		button_2.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_20.add(button_2);
		
		JPanel panel_53 = new JPanel();
		FlowLayout flowLayout_9 = (FlowLayout) panel_53.getLayout();
		flowLayout_9.setHgap(15);
		panel_20.add(panel_53, BorderLayout.WEST);
		
		JPanel panel_54 = new JPanel();
		FlowLayout flowLayout_10 = (FlowLayout) panel_54.getLayout();
		flowLayout_10.setHgap(15);
		panel_20.add(panel_54, BorderLayout.EAST);
		
		JPanel panel_55 = new JPanel();
		FlowLayout flowLayout_8 = (FlowLayout) panel_55.getLayout();
		flowLayout_8.setVgap(2);
		panel_20.add(panel_55, BorderLayout.NORTH);
		
		JPanel panel_56 = new JPanel();
		FlowLayout flowLayout_7 = (FlowLayout) panel_56.getLayout();
		flowLayout_7.setVgap(2);
		flowLayout_7.setHgap(2);
		panel_20.add(panel_56, BorderLayout.SOUTH);
		
		JPanel panel_19 = new JPanel();
		panel_17.add(panel_19);
		panel_19.setLayout(new GridLayout(7, 1, 0, 0));
		
		JPanel panel_28 = new JPanel();
		panel_19.add(panel_28);
		panel_28.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_34 = new JPanel();
		panel_28.add(panel_34, BorderLayout.EAST);
		
		JPanel panel_44 = new JPanel();
		panel_28.add(panel_44, BorderLayout.NORTH);
		
		JPanel panel_45 = new JPanel();
		panel_28.add(panel_45, BorderLayout.SOUTH);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_28.add(scrollPane_1, BorderLayout.CENTER);
		
		comboBox = new JComboBox<String>();
		scrollPane_1.setViewportView(comboBox);
		
		scrollPane_1.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		scrollPane_1.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
		JPanel panel_29 = new JPanel();
		panel_19.add(panel_29);
		panel_29.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_35 = new JPanel();
		panel_29.add(panel_35, BorderLayout.EAST);
		
		textPane = new JTextPane();
		panel_29.add(textPane, BorderLayout.CENTER);
		
		JPanel panel_39 = new JPanel();
		panel_29.add(panel_39, BorderLayout.NORTH);
		
		JPanel panel_43 = new JPanel();
		panel_29.add(panel_43, BorderLayout.SOUTH);
		
		JPanel panel_30 = new JPanel();
		panel_19.add(panel_30);
		panel_30.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_36 = new JPanel();
		panel_30.add(panel_36, BorderLayout.EAST);
		
		textPane_1 = new JTextPane();
		panel_30.add(textPane_1, BorderLayout.CENTER);
		
		JPanel panel_40 = new JPanel();
		panel_30.add(panel_40, BorderLayout.NORTH);
		
		JPanel panel_46 = new JPanel();
		panel_30.add(panel_46, BorderLayout.SOUTH);
		
		JPanel panel_31 = new JPanel();
		panel_19.add(panel_31);
		panel_31.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_37 = new JPanel();
		panel_31.add(panel_37, BorderLayout.EAST);
		
		textPane_2 = new JTextPane();
		panel_31.add(textPane_2, BorderLayout.CENTER);
		
		JPanel panel_41 = new JPanel();
		panel_31.add(panel_41, BorderLayout.NORTH);
		
		JPanel panel_47 = new JPanel();
		panel_31.add(panel_47, BorderLayout.SOUTH);
		
		JPanel panel_32 = new JPanel();
		panel_19.add(panel_32);
		panel_32.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_38 = new JPanel();
		panel_32.add(panel_38, BorderLayout.EAST);
		
		textPane_3 = new JTextPane();
		panel_32.add(textPane_3, BorderLayout.CENTER);
		
		JPanel panel_42 = new JPanel();
		panel_32.add(panel_42, BorderLayout.NORTH);
		
		JPanel panel_48 = new JPanel();
		panel_32.add(panel_48, BorderLayout.SOUTH);
		
		JPanel panel_33 = new JPanel();
		panel_19.add(panel_33);
		panel_33.setLayout(new BorderLayout(0, 0));
		
		rdbtnArp = new JRadioButton("ARP应答");
		rdbtnArp.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_33.add(rdbtnArp);
		
		ButtonGroup bg1 = new ButtonGroup();
		bg1.add(rdbtnNewRadioButton); bg1.add(rdbtnArp);
		
		JPanel panel_51 = new JPanel();
		FlowLayout flowLayout_5 = (FlowLayout) panel_51.getLayout();
		flowLayout_5.setHgap(15);
		panel_33.add(panel_51, BorderLayout.WEST);
		
		JPanel panel_52 = new JPanel();
		FlowLayout flowLayout_6 = (FlowLayout) panel_52.getLayout();
		flowLayout_6.setHgap(15);
		panel_33.add(panel_52, BorderLayout.EAST);
		
		JPanel panel_27 = new JPanel();
		panel_19.add(panel_27);
		panel_27.setLayout(new BorderLayout(0, 0));
		
		JButton button_3 = new JButton("停止");
		button_3.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_27.add(button_3);
		
		//监听按钮
		button_2.addActionListener(new Listenerarp());
		button_3.addActionListener(new Listenerarp());
				
		JPanel panel_57 = new JPanel();
		FlowLayout flowLayout_11 = (FlowLayout) panel_57.getLayout();
		flowLayout_11.setVgap(2);
		panel_27.add(panel_57, BorderLayout.NORTH);
		
		JPanel panel_58 = new JPanel();
		FlowLayout flowLayout_13 = (FlowLayout) panel_58.getLayout();
		flowLayout_13.setHgap(15);
		panel_27.add(panel_58, BorderLayout.WEST);
		
		JPanel panel_59 = new JPanel();
		FlowLayout flowLayout_12 = (FlowLayout) panel_59.getLayout();
		flowLayout_12.setVgap(2);
		panel_27.add(panel_59, BorderLayout.SOUTH);
		
		JPanel panel_60 = new JPanel();
		FlowLayout flowLayout_14 = (FlowLayout) panel_60.getLayout();
		flowLayout_14.setHgap(15);
		panel_27.add(panel_60, BorderLayout.EAST);
		
		JPanel panel_2 = new JPanel();
		panel.add(panel_2);
		panel_2.setLayout(new GridLayout(2, 1, 0, 0));
		
		JPanel panel_4 = new JPanel();
		panel_2.add(panel_4);
		panel_4.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_5 = new JPanel();
		FlowLayout flowLayout = (FlowLayout) panel_5.getLayout();
		flowLayout.setVgap(12);
		panel_4.add(panel_5, BorderLayout.NORTH);
		
		JLabel lblNewLabel = new JLabel("接收ARP&ICMP报文");
		lblNewLabel.setForeground(Color.RED);
		lblNewLabel.setFont(new Font("宋体", Font.PLAIN, 25));
		panel_5.add(lblNewLabel);
		
		JPanel panel_6 = new JPanel();
		panel_4.add(panel_6, BorderLayout.CENTER);
		panel_6.setLayout(new GridLayout(3, 1, 0, 0));
		
		JPanel panel_8 = new JPanel();
		panel_6.add(panel_8);
		panel_8.setLayout(new GridLayout(0, 2, 5, 0));
		
		JPanel panel_10 = new JPanel();
		panel_8.add(panel_10);
		panel_10.setLayout(new BorderLayout(0, 0));
		
		JLabel label = new JLabel("选择网卡接口：",JLabel.RIGHT);
		label.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_10.add(label);
		
		JPanel panel_11 = new JPanel();
		panel_8.add(panel_11);
		panel_11.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_12 = new JPanel();
		panel_11.add(panel_12, BorderLayout.EAST);
		
		JPanel panel_61 = new JPanel();
		panel_11.add(panel_61, BorderLayout.NORTH);
		
		JPanel panel_62 = new JPanel();
		panel_11.add(panel_62, BorderLayout.SOUTH);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		panel_11.add(scrollPane_2, BorderLayout.CENTER);
		
		comboBox_1 = new JComboBox<String>();
		scrollPane_2.setViewportView(comboBox_1);
		
		scrollPane_2.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); 
		scrollPane_2.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_NEVER);
		
		JPanel panel_9 = new JPanel();
		panel_6.add(panel_9);
		panel_9.setLayout(new GridLayout(0, 2, 5, 0));
		
		JPanel panel_13 = new JPanel();
		panel_9.add(panel_13);
		panel_13.setLayout(new BorderLayout(0, 0));
		
		JButton button = new JButton("执行");
		button.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_13.add(button);
		
		JPanel panel_63 = new JPanel();
		FlowLayout flowLayout_15 = (FlowLayout) panel_63.getLayout();
		flowLayout_15.setVgap(2);
		panel_13.add(panel_63, BorderLayout.NORTH);
		
		JPanel panel_64 = new JPanel();
		FlowLayout flowLayout_16 = (FlowLayout) panel_64.getLayout();
		flowLayout_16.setVgap(2);
		panel_13.add(panel_64, BorderLayout.SOUTH);
		
		JPanel panel_65 = new JPanel();
		FlowLayout flowLayout_19 = (FlowLayout) panel_65.getLayout();
		flowLayout_19.setHgap(15);
		panel_13.add(panel_65, BorderLayout.WEST);
		
		JPanel panel_66 = new JPanel();
		FlowLayout flowLayout_20 = (FlowLayout) panel_66.getLayout();
		flowLayout_20.setHgap(15);
		panel_13.add(panel_66, BorderLayout.EAST);
		
		JPanel panel_14 = new JPanel();
		panel_9.add(panel_14);
		panel_14.setLayout(new BorderLayout(0, 0));
		
		JButton button_1 = new JButton("终止");
		button_1.setFont(new Font("宋体", Font.PLAIN, 15));
		panel_14.add(button_1);
		
		//监听按钮
		button.addActionListener(new Listenerarp());
		button_1.addActionListener(new Listenerarp());
				
		JPanel panel_67 = new JPanel();
		FlowLayout flowLayout_17 = (FlowLayout) panel_67.getLayout();
		flowLayout_17.setVgap(2);
		panel_14.add(panel_67, BorderLayout.NORTH);
		
		JPanel panel_68 = new JPanel();
		FlowLayout flowLayout_21 = (FlowLayout) panel_68.getLayout();
		flowLayout_21.setHgap(15);
		panel_14.add(panel_68, BorderLayout.WEST);
		
		JPanel panel_69 = new JPanel();
		FlowLayout flowLayout_18 = (FlowLayout) panel_69.getLayout();
		flowLayout_18.setVgap(2);
		panel_14.add(panel_69, BorderLayout.SOUTH);
		
		JPanel panel_70 = new JPanel();
		FlowLayout flowLayout_22 = (FlowLayout) panel_70.getLayout();
		flowLayout_22.setHgap(15);
		panel_14.add(panel_70, BorderLayout.EAST);
		
		JPanel panel_7 = new JPanel();
		FlowLayout flowLayout_23 = (FlowLayout) panel_7.getLayout();
		flowLayout_23.setVgap(3);
		panel_6.add(panel_7);
		
		label_3 = new JLabel("持续抓包中。。。");
		label_3.setForeground(new Color(0, 0, 0, 0));
		panel_7.add(label_3);
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3);
		panel_3.setLayout(new BorderLayout(0, 0));
		
		scrollPane = new JScrollPane();
		panel_3.add(scrollPane, BorderLayout.CENTER);
		scrollPane.setHorizontalScrollBarPolicy( JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED); 
		scrollPane.setVerticalScrollBarPolicy( JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
		
		textArea = new JTextArea();
		textArea.setTabSize(10);
		scrollPane.setViewportView(textArea);
		
		frame.setVisible(true);
		
		//下拉框的内容
		en = NetworkInterface.getNetworkInterfaces();
		for(;en.hasMoreElements();) {
			list.add(en.nextElement());
		}
		ni = new NetworkInterface[list.size()];
		for(int k = 0;k<list.size();k++) {
			ni[k] = list.get(k);
		}
		for(int i=0;i<devs.length;i++) {
			for(int j = 0; j < ni.length;j++) {
				if(devs[i].mac_address !=null && ni[j].getHardwareAddress() !=null) {
					if(Arrays.equals(devs[i].mac_address, ni[j].getHardwareAddress())) {
						comboBox.addItem(ni[j].getDisplayName());
						comboBox_1.addItem(ni[j].getDisplayName());
					}
				} 
			}
		}
	}

}
