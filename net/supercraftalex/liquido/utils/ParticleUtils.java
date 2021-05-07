package net.supercraftalex.liquido.utils;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.minecraft.client.gui.Gui;
import net.supercraftalex.liquido.Liquido;

public class ParticleUtils {
	
	private final List<Particle> particles;
	private int width,heigth,count;
	
	public ParticleUtils(final int w, final int h) {
		this.width = w;
		this.heigth = h;
		this.count = 100;
		this.particles = new ArrayList<Particle>();
		for(int count = 0; count <= this.count; ++count) {
			this.particles.add(new Particle(new Random().nextInt(width),new Random().nextInt(heigth)));
		}
	}
	
	public void drawParticle() {
		this.particles.forEach(particle -> particle.drawParticle());
	}
	
	public class Particle {
		
		private int xPos, yPos;
		private int size;
		
		public Particle(int w, int h) {
			size = 1 + (int)(Math.random() * ((3 - 1) + 1));
			this.xPos = w;
			this.yPos = h;
		}
		
		public void drawParticle() {
			++this.xPos;
			++this.yPos;
			final int particleSize = this.size;
			
			if(this.xPos > ParticleUtils.this.width) {
				this.xPos = -particleSize;
			}
			if(this.yPos > ParticleUtils.this.heigth) {
				this.yPos = -particleSize;
			}
			
			
			Gui.drawRect(this.xPos,this.yPos,this.xPos+particleSize,this.yPos+particleSize,Liquido.INSTANCE.themeManager.theme().mainMenuParticles.getRGB());
			
		}
	}
	
}
