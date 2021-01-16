package net.supercraftalex.liquido.cosmetics.effects;

import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.model.ModelCreeper;
import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderCreeper;
import net.minecraft.client.renderer.entity.RenderPlayer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.supercraftalex.liquido.Booleans;
import net.supercraftalex.liquido.cosmetics.Cosmetic;
import net.supercraftalex.liquido.cosmetics.capes.Cape;

public class CreeperOverlay extends Cosmetic implements LayerRenderer<AbstractClientPlayer> {
	
    private static final ResourceLocation LIGHTNING_TEXTURE = new ResourceLocation("textures/entity/creeper/creeper_armor.png");
    private RenderPlayer creeperRenderer;
    private ModelPlayer creeperModel = new ModelPlayer(2.0F, true);
	
    public CreeperOverlay() {
    	super("creeper","CreeperOverlay");
    }
    
    public CreeperOverlay(RenderPlayer renderPlayer) {
    	super("creeper","CreeperOverlay");
		this.creeperRenderer = renderPlayer;
		this.creeperModel = renderPlayer.getMainModel();
    }
    
	@Override
	public void doRenderLayer(AbstractClientPlayer entitylivingbaseIn, float p_177141_2_, float p_177141_3_,
		float partialTicks, float p_177141_5_, float p_177141_6_, float p_177141_7_, float scale) {
        if(!this.isToggled()) {
        	//return;
        }
        boolean flag = entitylivingbaseIn.isInvisible();
        GlStateManager.depthMask(!flag);
        this.creeperRenderer.bindTexture(LIGHTNING_TEXTURE);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        float f = (float)entitylivingbaseIn.ticksExisted + partialTicks;
        GlStateManager.translate(f * 0.01F, f * 0.01F, 0.0F);
        GlStateManager.matrixMode(5888);
        GlStateManager.enableBlend();
        float f1 = 0.5F;
        GlStateManager.color(f1, f1, f1, 1.0F);
        GlStateManager.disableLighting();
        GlStateManager.blendFunc(1, 1);
        this.creeperModel.setModelAttributes(this.creeperRenderer.getMainModel());
        this.creeperModel.render(entitylivingbaseIn, p_177141_2_, p_177141_3_, p_177141_5_, p_177141_6_, p_177141_7_, scale);
        GlStateManager.matrixMode(5890);
        GlStateManager.loadIdentity();
        GlStateManager.matrixMode(5888);
        GlStateManager.enableLighting();
        GlStateManager.disableBlend();
        GlStateManager.depthMask(flag);
		
	}

	@Override
	public boolean shouldCombineTextures() {
		return true;
	}

}
