package net.supercraftalex.liquido.modules.impl.Combat;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.supercraftalex.liquido.utils.Position;

public class TrackEntity {
	public EntityLivingBase entity;
	private List<Position> pos = new ArrayList<Position>();
	public double lastX = 0.0;
	public double lastY = 0.0;
	public double lastZ = 0.0;
	public boolean trackingback = false;
	private int tbm = 0;
	public TrackEntity(EntityLivingBase e) {
		entity = e;
		updateLastPos();
		this.pos.add(new Position(lastZ, lastY, lastX));
	}
	public void update() {
		if(lastX - entity.posX >= 1.0D || lastY - entity.posY >= 1.0D || lastZ - entity.posZ >= 1.0D) {
			pos.add(new Position(entity.posX,entity.posY,entity.posZ));
			updateLastPos();
		}
	}
	//amout should be 1 - 3
	public void trackBack(int amount) {
		tbm++;
		trackingback = true;
		int a= 1;
		if(pos.size() > amount) {
			a = amount;
		} else {
			a = 1;
		}
		try {
			
		} catch (Exception e) {}
		entity.setPosition(pos.get(a).x, pos.get(a).y, pos.get(a).z);
		tbm--;
		if (tbm <= 0) {
			trackingback = false;
			tbm = 0;
		}
	}
	private void updateLastPos() {
		this.lastX = entity.posX;
		this.lastY = entity.posZ;
		this.lastZ = entity.posY;
	}
}