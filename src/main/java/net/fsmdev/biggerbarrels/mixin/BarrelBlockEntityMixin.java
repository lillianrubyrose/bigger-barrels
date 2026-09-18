package net.fsmdev.biggerbarrels.mixin;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ChestMenu;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BarrelBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BarrelBlockEntity.class)
public abstract class BarrelBlockEntityMixin {
    @Accessor
    abstract void setItems(NonNullList<ItemStack> items);

    /**
     * @author FsmDev
     * @reason Changes size depending on setting
     */
    @Overwrite
    public int getContainerSize() {
        return 54;
    }

    @Inject(method="<init>", at=@At("RETURN"))
    private void init(BlockPos pos, BlockState state, CallbackInfo ci) {
        setItems(NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY));
    }

    /**
     * @author FsmDev
     * @reason Changes grid depending on size
     */
    @Overwrite
    public AbstractContainerMenu createMenu(int syncId, Inventory playerInventory) {
        return ChestMenu.sixRows(syncId, playerInventory, (BarrelBlockEntity) (Object) this);
    }
}
