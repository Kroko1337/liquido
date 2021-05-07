package net.supercraftalex.liquido.utils;

import net.minecraft.client.Minecraft;
import net.minecraft.util.Vec3;

import java.util.ArrayList;
import java.util.List;

public class MotionUtils {

    public static void setMotionForPlayer(Vec3 paramVec3) {
        Minecraft.getMinecraft().thePlayer.motionX = paramVec3.xCoord;
        Minecraft.getMinecraft().thePlayer.motionZ = paramVec3.zCoord;
    }

    public static double getMotion() {
        Minecraft localMinecraft = Minecraft.getMinecraft();
        double d1 = localMinecraft.thePlayer.motionX;
        double d2 = localMinecraft.thePlayer.motionZ;
        return Math.sqrt(d1 * d1 + d2 * d2);
    }
    
    public static float[] translateMovementInput(float paramFloat1, float paramFloat2, float paramFloat3) {
        float[] arrayOfFloat = new float[2];
        float f = paramFloat2 == 0.0F ? 1 : 0;
        paramFloat3 %= 360.0F;
        if (paramFloat3 < 0.0F) {
            paramFloat3 += 360.0F;
        }
        if ((paramFloat3 < 23.0F) || (paramFloat3 > 337.0F)) {
            arrayOfFloat[0] = paramFloat1;
            arrayOfFloat[1] = paramFloat2;
        } else if ((paramFloat3 > 23.0F) && (paramFloat3 < 68.0F)) {
            arrayOfFloat[0] = paramFloat1;
            arrayOfFloat[1] = paramFloat1;
            if (paramFloat2 != 0.0F) {
                arrayOfFloat[0] -= paramFloat2;
                arrayOfFloat[1] += paramFloat2;
            }
        } else if ((paramFloat3 > 68.0F) && (paramFloat3 < 113.0F)) {
            arrayOfFloat[0] = (-paramFloat2);
            arrayOfFloat[1] = paramFloat1;
        } else if ((paramFloat3 > 113.0F) && (paramFloat3 < 158.0F)) {
            arrayOfFloat[0] = (-paramFloat1);
            arrayOfFloat[1] = paramFloat1;
            if (paramFloat2 != 0.0F) {
                arrayOfFloat[0] -= paramFloat2;
                arrayOfFloat[1] -= paramFloat2;
            }
        } else if ((paramFloat3 > 158.0F) && (paramFloat3 < 203.0F)) {
            arrayOfFloat[0] = (-paramFloat1);
            arrayOfFloat[1] = (-paramFloat2);
        } else if ((paramFloat3 > 203.0F) && (paramFloat3 < 248.0F)) {
            arrayOfFloat[0] = (-paramFloat1);
            arrayOfFloat[1] = (-paramFloat1);
            if (paramFloat2 != 0.0F) {
                arrayOfFloat[0] += paramFloat2;
                arrayOfFloat[1] -= paramFloat2;
            }
        } else if ((paramFloat3 > 248.0F) && (paramFloat3 < 293.0F)) {
            arrayOfFloat[0] = paramFloat2;
            arrayOfFloat[1] = (-paramFloat1);
        } else {
            arrayOfFloat[0] = paramFloat1;
            arrayOfFloat[1] = (-paramFloat1);
            if (paramFloat2 != 0.0F) {
                arrayOfFloat[0] += paramFloat2;
                arrayOfFloat[1] += paramFloat2;
            }
        }
        if ((arrayOfFloat[0] > 1.0F) || (arrayOfFloat[0] < -1.0F)) {
            arrayOfFloat[0] = (arrayOfFloat[0] > 0.0F ? 1 : -1);
        }
        if ((arrayOfFloat[1] > 1.0F) || (arrayOfFloat[1] < -1.0F)) {
            arrayOfFloat[1] = (arrayOfFloat[1] > 0.0F ? 1 : -1);
        }
        return arrayOfFloat;
    }
}




