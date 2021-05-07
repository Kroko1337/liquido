
package net.supercraftalex.liquido.modules.impl.Combat;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import com.darkmagician6.eventapi.EventManager;
import com.darkmagician6.eventapi.EventTarget;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.supercraftalex.liquido.Liquido;
import net.supercraftalex.liquido.events.EventUpdate;
import net.supercraftalex.liquido.modules.Category;
import net.supercraftalex.liquido.modules.Module;
import net.supercraftalex.liquido.utils.Position;
import net.supercraftalex.liquido.utils.RenderUtil;

public class BackTrack extends Module {

	public BackTrack() {
		super("backtrack", "BackTrack", Keyboard.KEY_NONE, Category.COMBAT);
	}
	
	int delay = 0;
	
	@EventTarget
	public void onUpdate(EventUpdate event) {
		delay ++;
		List list = mc.theWorld.loadedEntityList;
		for (int k = 0; k < list.size(); k++) {
			if (((Entity) list.get(k)).getName() == mc.thePlayer.getName()) {
				continue;
			}
			Entity entityplayer = (Entity) list.get(1);
			if (mc.thePlayer.getDistanceToEntity(entityplayer) > mc.thePlayer.getDistanceToEntity((Entity) list.get(k))) {
				entityplayer = (Entity) list.get(k);
			}
			float f = mc.thePlayer.getDistanceToEntity(entityplayer);
			if (f < 4 && !entityplayer.isInvisible() && entityplayer.ticksExisted > 100 && delay >= 40 && entityplayer instanceof EntityPlayer && mc.thePlayer.canEntityBeSeen(entityplayer)) {
				System.out.println("BACKTRACK");
				Backtrack((EntityLivingBase)entityplayer);
				delay = 0;
			}
		}
	}
	
	private void Backtrack(EntityLivingBase e) {
		e.backtrack.trackBack(2);
	}

	@Override
	public void onDisable() {
		EventManager.unregister(this);
	}
	
	@Override
	public void onEnable() {
		EventManager.register(this);
	}
}