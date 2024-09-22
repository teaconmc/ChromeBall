package org.teacon.chromeball.client.entity.renderer;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.entity.EntityRenderer;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityAttachment;
import net.minecraft.world.item.ItemDisplayContext;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.phys.Vec3;
import net.neoforged.neoforge.client.ClientHooks;
import org.jetbrains.annotations.NotNull;
import org.joml.Matrix4f;
import org.teacon.chromeball.ChromeBall;
import org.teacon.chromeball.common.ChromeBallRegistry;
import org.teacon.chromeball.common.entity.ChromeLivingEntity;

public class DoorChromeRenderer extends EntityRenderer<ChromeLivingEntity> {

    private static ItemStack CHROME_BALL;

    private final ItemRenderer itemRenderer;

    public DoorChromeRenderer(EntityRendererProvider.Context context) {
        super(context);
        this.itemRenderer = context.getItemRenderer();

        if (CHROME_BALL == null) {
            CHROME_BALL = new ItemStack(ChromeBallRegistry.CHROME_BALL_ITEM);
        }
    }

    @Override
    public void render(@NotNull ChromeLivingEntity entity, float entityYaw, float partialTick,
                       @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource, int packedLight) {
        poseStack.pushPose();
        poseStack.translate(0, 0.6, 0);
        poseStack.scale(3F, 3F, 3F);
        poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
        this.itemRenderer.renderStatic(CHROME_BALL, ItemDisplayContext.GROUND, packedLight,
                OverlayTexture.NO_OVERLAY, poseStack, bufferSource, entity.level(), entity.getId());
        poseStack.popPose();
        super.render(entity, entityYaw, partialTick, poseStack, bufferSource, packedLight);
    }

    @Override
    protected boolean shouldShowName(@NotNull ChromeLivingEntity entity) {
        return entity.getClientClicked() > 0;
    }

    @Override
    protected void renderNameTag(@NotNull ChromeLivingEntity entity, @NotNull Component displayName,
                                 @NotNull PoseStack poseStack, @NotNull MultiBufferSource bufferSource,
                                 int packedLight, float partialTick) {
        double distance = this.entityRenderDispatcher.distanceToSqr(entity);
        if (ClientHooks.isNameplateInRenderDistance(entity, distance)) {
            Vec3 vec3 = entity.getAttachments().getNullable(EntityAttachment.NAME_TAG, 0, entity.getViewYRot(partialTick));
            if (vec3 != null) {
                boolean flag = !entity.isDiscrete();
                poseStack.pushPose();
                poseStack.translate(vec3.x, vec3.y + 2.3, vec3.z);
                poseStack.mulPose(this.entityRenderDispatcher.cameraOrientation());
                poseStack.scale(0.025F, -0.025F, 0.025F);
                Matrix4f matrix4f = poseStack.last().pose();
                var f = Minecraft.getInstance().options.getBackgroundOpacity(0.25F);
                var j = (int)(f * 255.0F) << 24;
                var f1 = (float)(-getFont().width(displayName) / 2);
                getFont().drawInBatch(displayName, f1, entity.getClientClicked(), 553648127, false, matrix4f, bufferSource, flag ? Font.DisplayMode.SEE_THROUGH : Font.DisplayMode.NORMAL, j, packedLight);
                if (flag) {
                    getFont().drawInBatch(displayName, f1, entity.getClientClicked(), -1, false, matrix4f, bufferSource, Font.DisplayMode.NORMAL, 0, packedLight);
                }
                poseStack.popPose();
            }
        }
    }

    @Override
    public @NotNull ResourceLocation getTextureLocation(@NotNull ChromeLivingEntity chromeLivingEntity) {
        return ResourceLocation.fromNamespaceAndPath(ChromeBall.MOD_ID, "item/chrome");
    }
}
