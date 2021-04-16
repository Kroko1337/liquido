package net.supercraftalex.liquido.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.MathHelper;

public class MovementUtil {

    private static final Minecraft mc = Minecraft.getMinecraft();

    public static boolean isMoving() {
        return (mc.thePlayer.moveForward != 0 || mc.thePlayer.moveStrafing != 0);
    }

    public static void strafe() {
        setSpeed(getSpeed());
    }

    public static void setSpeed(final double speed) {
        mc.thePlayer.motionX = (-MathHelper.sin(getDirection()) * speed);
        mc.thePlayer.motionZ = (MathHelper.cos(getDirection()) * speed);
    }

    private static float getDirection() {
        float yaw = mc.thePlayer.rotationYawHead;
        float forward = mc.thePlayer.moveForward;
        float strafe = mc.thePlayer.moveStrafing;
        yaw += (forward < 0.0F ? 180 : 0);
        if (strafe < 0.0F) {
            yaw += (forward < 0.0F ? -45 : forward == 0.0F ? 90 : 45);
        }
        if (strafe > 0.0F) {
            yaw -= (forward < 0.0F ? -45 : forward == 0.0F ? 90 : 45);
        }

        return yaw * 0.017453292F;
    }

    public static double getSpeed() {
        return Math.sqrt((mc.thePlayer.motionX * mc.thePlayer.motionX) + (mc.thePlayer.motionZ * mc.thePlayer.motionZ));
    }

}