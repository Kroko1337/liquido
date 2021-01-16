package net.supercraftalex.liquido.gui.extra;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.modules.Module;

import javax.swing.border.CompoundBorder;
import java.awt.Color;
import javax.swing.JLabel;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JList;
import javax.swing.Box;

public class GuiExtraOmain extends JFrame implements ActionListener {

	private JPanel contentPane;
	
	JLabel lblNewLabel = new JLabel("Module configs");
	
	public GuiExtraOmain() {
		JButton b;
		int x = 10;
		int y = 55;
		int loop = 1;
		
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 100, 875, 647);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setFont(new Font("Segoe UI", Font.BOLD, 23));
		lblNewLabel.setBounds(74, 11, 272, 33);
		contentPane.add(lblNewLabel);
		
		for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
			b = new JButton(m.getDisplayname());
			if(loop <= 11) {
				b.setBounds(x, y, 403, 45);
				if(loop == 11) {
					y = 5;
				}
			} else {
				b.setBounds(x+403+10, y, 403, 45);
			}
			b.addActionListener(this);
			contentPane.add(b);
			y = y + 50;
			loop++;
		}
		
	}
	
	@Override
    public void actionPerformed(ActionEvent e) {
		for(Module m : Liquido.INSTANCE.moduleManager.getModules()) {
			if(e.toString().contains(m.getDisplayname())) {
				GuiExtraMain.initConfigGUI(m);
			}
		}
    }
    
}
