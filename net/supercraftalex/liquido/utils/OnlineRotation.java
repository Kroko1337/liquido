package net.supercraftalex.liquido.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class OnlineRotation {

    private static float onlineYaw = 0.0F;
    private static float onlinePitch = 0.0F;
    private static float lastYaw = 0.0F;
    private static float lastPitch = 0.0F;
    private static boolean setMouseOverObject = true;
    private static boolean useOnlineRots = false;
    private static boolean useSilentRotationForClientMotion = false;
    private static float silentRotationForClientMotion = 0.0F;
    private static boolean useSilentMovementInput = false;
    private static float silentMovementStrafe = 0.0F;
    private static float silentMovementForward = 0.0F;
    private static Minecraft mc = Minecraft.getMinecraft();
    
    public static void onTick() {
        lastYaw = onlineYaw;
        lastPitch = onlinePitch;
        if (useOnlineRots == true) {
            onlineYaw = Minecraft.getMinecraft().thePlayer.rotationYaw;
            onlinePitch = Minecraft.getMinecraft().thePlayer.rotationPitch;
        }
        setMouseOverObject = true;
        useOnlineRots = false;
        useSilentRotationForClientMotion = false;
        useSilentMovementInput = false;
    }

    public static float getYaw(final Vec3 vec3) {
        return (float)((float)Math.atan2(mc.thePlayer.posZ - vec3.zCoord, mc.thePlayer.posX - vec3.xCoord) / 3.141592653589793 * 180.0) - 90.0f;
    }
    
    public static float[] calculateDiff(final float n, final float n2) {
        float abs = Math.abs(n - n2);
        if (abs < 0.0f) {
            abs += 360.0f;
        }
        if (abs > 360.0f) {
            abs -= 360.0f;
        }
        final float n3 = 360.0f - abs;
        float n4 = 0.0f;
        if (abs > n3) {
            ++n4;
        }
        if (abs > n3) {
            abs = n3;
        }
        return new float[] { abs, n4 };
    }
    
    public static float[] calculateRotationDiff(final float n, final float n2) {
        float abs = Math.abs(n - n2);
        if (abs < 0.0f) {
            abs += 360.0f;
        }
        if (abs >= 360.0f) {
            abs -= 360.0f;
        }
        final float n3 = 360.0f - abs;
        float n4 = 0.0f;
        if (abs > n3) {
            ++n4;
        }
        if (abs > n3) {
            abs = n3;
        }
        return new float[] { abs, n4 };
    }
    
    public static float getYaw(final Entity entity, final Entity entity2) {
        final Vec3 vec3 = new Vec3(entity2.posX, entity2.posY, entity2.posZ);
        return (float)((float)Math.atan2(entity.posZ - (vec3.zCoord + (entity2.posZ - entity2.lastTickPosZ)), entity.posX - (vec3.xCoord + (entity2.posX - entity2.lastTickPosX))) / 3.141592653589793 * 180.0) - 90.0f;
    }
    
    public static boolean useSilentMovementInput() {
        return useSilentMovementInput;
    }

    public static void setSilentMovement(float paramFloat1, float paramFloat2) {
        useSilentMovementInput = true;
        silentMovementForward = paramFloat1;
        silentMovementStrafe = paramFloat2;
    }

    public static float getSilentMovementForward() {
        return silentMovementForward;
    }

    public static float getSilentMovementStrafe() {
        return silentMovementStrafe;
    }

    public static boolean useSilentRotationForClientMotion() {
        return useSilentRotationForClientMotion;
    }

    public static float getSilentRotationForClientMotion() {
        return silentRotationForClientMotion;
    }

    public static void setSilentRotationForClientMotion(float paramFloat) {
        useSilentRotationForClientMotion = true;
        silentRotationForClientMotion = paramFloat;
    }

    public static void setYaw(float paramFloat) {
        if (!useOnlineRots) {
            lastYaw = Minecraft.getMinecraft().thePlayer.prevRotationYaw;
        }
        onlineYaw = paramFloat;
        useOnlineRots = true;
    }

    public static void setPitch(float paramFloat) {
        if (!useOnlineRots) {
            lastPitch = Minecraft.getMinecraft().thePlayer.prevRotationPitch;
        }
        onlinePitch = paramFloat;
        useOnlineRots = true;
    }

    public static boolean useOnlineRots() {
        return useOnlineRots;
    }

    public static void useOnlineRots(boolean ya) {
        useOnlineRots = ya;
    }

    public static boolean yawChanged() {
        return onlineYaw != lastYaw;
    }

    public static boolean pitchChanged() {
        return onlinePitch != lastPitch;
    }

    public static float getOnlineYaw() {
        if (!useOnlineRots) {
            return Minecraft.getMinecraft().thePlayer.rotationYaw;
        }
        return onlineYaw;
    }

    public static float getOnlinePitch() {
        if (!useOnlineRots) {
            return Minecraft.getMinecraft().thePlayer.rotationPitch;
        }
        return onlinePitch;
    }

    public static float getLastYaw() {
        if (!useOnlineRots) {
            return Minecraft.getMinecraft().thePlayer.prevRotationYaw;
        }
        return lastYaw;
    }

    public static float getLastPitch() {
        if (!useOnlineRots) {
            return Minecraft.getMinecraft().thePlayer.prevRotationPitch;
        }
        return lastPitch;
    }

    public static float getRawPitch() {
        return onlinePitch;
    }

    public static float getRawYaw() {
        return onlineYaw;
    }

    public static boolean isSetMouseOverObject() {
        return setMouseOverObject;
    }

    public static void setSetMouseOverObject(boolean paramBoolean) {
        setMouseOverObject = paramBoolean;
    }
}




