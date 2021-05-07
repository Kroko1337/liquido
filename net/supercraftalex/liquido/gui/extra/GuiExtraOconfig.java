package net.supercraftalex.liquido.gui.extra;

import java.awt.BorderLayout;
import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import net.supercraftalex.liquido.ErrorManager;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.modules.Config;
import net.supercraftalex.liquido.modules.ConfigMode;
import net.supercraftalex.liquido.modules.Module;

import javax.swing.JToggleButton;
import javax.swing.JRadioButton;
import javax.swing.JComboBox;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import javax.swing.WindowConstants;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JSlider;

public class GuiExtraOconfig extends JFrame implements ActionListener {

	private JPanel contentPane;
	
	private Module m;
	
	private JToggleButton m1;
	private JComboBox m2;
	
	public GuiExtraOconfig(Module m) {
		this.m = m;
		setDefaultCloseOperation(WindowConstants.HIDE_ON_CLOSE);
		setBounds(100, 100, 222, 309);
		contentPane = new JPanel();
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		setTitle("LiqudioClient > Module configs > config");
		
		int y = 44;
		
		JLabel lblNewLabel = new JLabel(m.getDisplayname());
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
		lblNewLabel.setHorizontalAlignment(SwingConstants.CENTER);
		lblNewLabel.setBounds(10, 11, 186, 29);
		contentPane.add(lblNewLabel);
		
		/*
		JComboBox comboBox = new JComboBox();
		comboBox.setModel(new DefaultComboBoxModel(new String[] {"AAC", "NCP", "Hypixel", "Cubecraft TEST"}));
		comboBox.setBounds(10, 78, 186, 23);
		contentPane.add(comboBox);
		*/
		
		List<JSlider> slider = new ArrayList<JSlider>();
		
		try {
			for(Config c : m.getConfigs()) {
				if(c.getValue() instanceof Boolean) {
					m1 = new JToggleButton(c.getName());
					m1.setBounds(10, y, 186, 23);
					contentPane.add(m1);
					m1.addActionListener(this);
					m1.setSelected(new Boolean(c.getValue().toString()));
				}
				if(c.getValue() instanceof Integer) {
					slider.add(new JSlider());
					slider.get(slider.size()-1).setMinimum(1);
					slider.get(slider.size()-1).setToolTipText(c.getName());
					slider.get(slider.size()-1).setMaximum(10);
					slider.get(slider.size()-1).setValue(new Integer(c.getValue().toString()));
					slider.get(slider.size()-1).addChangeListener(new ChangeListener() {
						@Override
						public void stateChanged(ChangeEvent e) {
							int loop = 0;
							for(JSlider s : slider) {
								if(e.getSource().equals(s)) {
									c.setValue(new Integer(s.getValue()));
								}
							}
							loop++;
						}
					});
					slider.get(slider.size()-1).setBounds(80, y, 120, 26);
					contentPane.add(slider.get(slider.size()-1));
					
					JLabel lblNewLabel_1 = new JLabel(c.getName() + ":");
					lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
					lblNewLabel_1.setBounds(5, y, 75, 22);
					contentPane.add(lblNewLabel_1);
				}
				if(c.getValue() instanceof ConfigMode) {
					m2 = new JComboBox();
					m2.setModel(new DefaultComboBoxModel(c.getConfigMode().getAviable().toArray()));
					m2.setBounds(10, y, 186, 23);
					m2.addActionListener(this);
					contentPane.add(m2);
				}
				y = y + 50;
			}
		} catch(Exception err) {
			ErrorManager.addException(err);
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.toString().contains("comboBoxChanged")) {
			for(Config c : m.getConfigs()) {
				if(c.getValue() instanceof ConfigMode) {
					for(String s : c.getConfigMode().getAviable()) {
						if(e.toString().contains(s)) {
							c.getConfigMode().setValue(s);
						}
					}
				}
			}
			return;
		}
		try {
			for(Config c : m.getConfigs()) {
				if(e.toString().contains(c.getName())) {
					if(c.getValue() instanceof Boolean) {
						c.setValue(!new Boolean(c.getValue().toString()));
					}
				}
			}
		} catch (Exception err) {
			ErrorManager.addException(err);
		}
	}
}
