package net.supercraftalex.liquido.cosmetics.effects;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelWither;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.RenderWither;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.cosmetics.Cosmetic;
import net.supercraftalex.liquido.cosmetics.capes.Cape;

public class WitherOverlay extends Cosmetic implements LayerRenderer<AbstractClientPlayer>{
	
    private static final ResourceLocation WITHER_ARMOR = new ResourceLocation("textures/entity/wither/wither_armor.png");
    private RenderPlayer witherRenderer;
    private ModelPlayer witherModel = new ModelPlayer(0.5F, true);
	
    public WitherOverlay() {
    	super("whiter","WhiterOverlay");
	}
    
    public WitherOverlay(RenderPlayer renderPlayer) {
    	super("whiter","WhiterOverlay");
		this.witherRenderer = renderPlayer;
		this.witherModel = renderPlayer.getMainModel();
	}
    
	@Override
	public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_, float p_177141_3_,
		float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
        if(!this.isToggled()) {
        	//return;
        }
        GlStateManager.depthMask(!entitylivingbaseIn.isInvisible());
        this.witherRenderer.bindTexture(WITHER_ARMOR);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        float f = (float)entitylivingbaseIn.ticksExisted + partialTicks;
        float f1 = MathHelper.cos(f * 0.02F) * 3.0F;
        float f2 = f * 0.01F;
        GlStateManager.translate(f1, f2, 0.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.enableBlend();
        float f3 = 0.5F;
        GlStateManager.color(7, 1, 5, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(1, 1);
        this.witherModel.setLivingAnimations(entitylivingbaseIn, p_177141_2_, p_177141_3_, partialTicks);
        this.witherModel.setModelAttributes(this.witherRenderer.getMainModel());
        this.witherModel.render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
		
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}
}
