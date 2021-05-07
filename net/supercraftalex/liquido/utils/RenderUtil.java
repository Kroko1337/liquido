package net.supercraftalex.liquido.utils;

import java.nio.FloatBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.GLU;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.AxisAlignedBB;

public class RenderUtil {
	
	public static void drawString(final double scale, final String text,final float xPos, final float yPos, final int color) {
		GlStateManager.pushMatrix();
		GlStateManager.scale(scale, scale, scale);
		Minecraft.getMinecraft().fontRendererObj.drawStringWithShadow(text, xPos, yPos, color);
		GlStateManager.popMatrix();
	}
	
	public static int[] getScreenCoords(double x, double y, double z) {
	    FloatBuffer screenCoords = BufferUtils.createFloatBuffer(4);
	    IntBuffer viewport = BufferUtils.createIntBuffer(16);
	    FloatBuffer modelView = BufferUtils.createFloatBuffer(16);
	    FloatBuffer projection = BufferUtils.createFloatBuffer(16);
	    // int[] screenCoords = new double[4];
	    // int[] viewport = new int[4];
	    // double[] modelView = new double[16];
	    // double[] projection = new double[16];
	    GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, modelView);
	    GL11.glGetFloat(GL11.GL_PROJECTION_MATRIX, projection);
	    GL11.glGetInteger(GL11.GL_VIEWPORT, viewport);
	    boolean result = GLU.gluProject((float) x, (float) y, (float) z, modelView, projection, viewport, screenCoords);
	    if (result) {
	        return new int[] { (int) screenCoords.get(0), (int) screenCoords.get(1) };
	    }
	    return null;
	}
	
	public static void drawTracerLine(double x, double y, double z, float red, float green, float blue, float alpha, float lineWdith) {
		GL11.glPushMatrix();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_DEPTH_TEST);
        // GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(770, 771);
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glLineWidth(lineWdith);
        GL11.glColor4f(red, green, blue, alpha);
        GL11.glBegin(2);
        GL11.glVertex3d(0.0D, 0.0D + Minecraft.getMinecraft().thePlayer.getEyeHeight(), 0.0D);
        GL11.glVertex3d(x, y, z);
        GL11.glEnd();
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_DEPTH_TEST);
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glDisable(GL11.GL_BLEND);
        // GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
	}
    public static void drawCircle(int x, int y, double r, int c)
    {
        float f = ((c >> 24) & 0xff) / 255F;
        float f1 = ((c >> 16) & 0xff) / 255F;
        float f2 = ((c >> 8) & 0xff) / 255F;
        float f3 = (c & 0xff) / 255F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(GL11.GL_LINE_LOOP);

        for (int i = 0; i <= 360; i++)
        {
            double x2 = Math.sin(((i * Math.PI) / 180)) * r;
            double y2 = Math.cos(((i * Math.PI) / 180)) * r;
            GL11.glVertex2d(x + x2, y + y2);
        }

        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }

    public static void drawFilledCircle(int x, int y, double r, int c)
    {
        float f = ((c >> 24) & 0xff) / 255F;
        float f1 = ((c >> 16) & 0xff) / 255F;
        float f2 = ((c >> 8) & 0xff) / 255F;
        float f3 = (c & 0xff) / 255F;
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glEnable(GL11.GL_LINE_SMOOTH);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(f1, f2, f3, f);
        GL11.glBegin(GL11.GL_TRIANGLE_FAN);

        for (int i = 0; i <= 360; i++)
        {
            double x2 = Math.sin(((i * Math.PI) / 180)) * r;
            double y2 = Math.cos(((i * Math.PI) / 180)) * r;
            GL11.glVertex2d(x + x2, y + y2);
        }

        GL11.glEnd();
        GL11.glDisable(GL11.GL_LINE_SMOOTH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
    public static void drawOutlinedBoundingBox(AxisAlignedBB aa) {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		worldRenderer.begin(3, new VertexFormat());
		worldRenderer.putNormal((float)(float)aa.minX, (float)(float)aa.minY, (float)(float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.minZ);
		tessellator.draw();
		worldRenderer.begin(3, new VertexFormat());
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.minZ);
		tessellator.draw();
		worldRenderer.begin(1, new VertexFormat());
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.maxZ);
		tessellator.draw();
	}
	
	public static void drawBoundingBox(AxisAlignedBB aa)  {
		Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
		worldRenderer.begin(3, new VertexFormat());
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.maxZ);
		tessellator.draw();
		worldRenderer.begin(3, new VertexFormat());
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.maxZ);
		tessellator.draw();
		worldRenderer.begin(3, new VertexFormat());
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.minZ);
		tessellator.draw();
		worldRenderer.begin(3, new VertexFormat());
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.minZ);
		tessellator.draw();
		worldRenderer.begin(3, new VertexFormat());
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.minZ);
		tessellator.draw();
		worldRenderer.begin(3, new VertexFormat());
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.minX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.minZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.maxY, (float)aa.maxZ);
		worldRenderer.putNormal((float)aa.maxX, (float)aa.minY, (float)aa.maxZ);
		tessellator.draw();
	}

	public static void drawOutlinedBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(red, green, blue, alpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void drawBlockESP(double x, double y, double z, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWidth) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glLineWidth(lineWidth);
		GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void drawSolidBlockESP(double x, double y, double z, float red, float green, float blue, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawBoundingBox(new AxisAlignedBB(x, y, z, x + 1D, y + 1D, z + 1D));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void drawOutlinedEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
	public static void drawSolidEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	public static void drawEntityESP(double x, double y, double z, double width, double height, float red, float green, float blue, float alpha, float lineRed, float lineGreen, float lineBlue, float lineAlpha, float lineWdith) {
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(770, 771);
		// GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LINE_SMOOTH);
		GL11.glDisable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(false);
		GL11.glColor4f(red, green, blue, alpha);
		drawBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));
		GL11.glLineWidth(lineWdith);
		GL11.glColor4f(lineRed, lineGreen, lineBlue, lineAlpha);
		drawOutlinedBoundingBox(new AxisAlignedBB(x - width, y, z - width, x + width , y + height, z + width));
		GL11.glDisable(GL11.GL_LINE_SMOOTH);
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		// GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glDepthMask(true);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}
	
    public static void dr(double i, double j, double k, double l, int i1)
    {
        if (i < k)
        {
            double j1 = i;
            i = k;
            k = j1;
        }

        if (j < l)
        {
            double k1 = j;
            j = l;
            l = k1;
        }

        float f = ((i1 >> 24) & 0xff) / 255F;
        float f1 = ((i1 >> 16) & 0xff) / 255F;
        float f2 = ((i1 >> 8) & 0xff) / 255F;
        float f3 = (i1 & 0xff) / 255F;
        Tessellator tessellator = Tessellator.getInstance();
		WorldRenderer worldRenderer = tessellator.getWorldRenderer();
        GL11.glEnable(GL11.GL_BLEND);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        GL11.glColor4f(f1, f2, f3, f);
        worldRenderer.func_181667_k();
        worldRenderer.putNormal((float)i, (float)l, 0.0F);
        worldRenderer.putNormal((float)k, (float)l, 0.0F);
        worldRenderer.putNormal((float)k, (float)j, 0.0F);
        worldRenderer.putNormal((float)i, (float)j, 0.0F);
        tessellator.draw();
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glDisable(GL11.GL_BLEND);
    }
    
}
