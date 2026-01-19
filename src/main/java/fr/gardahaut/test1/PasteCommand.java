package fr.gardahaut.test1;

import com.hypixel.hytale.component.Ref;
import com.hypixel.hytale.component.Store;
import com.hypixel.hytale.server.core.command.system.CommandContext;
import com.hypixel.hytale.server.core.command.system.basecommands.AbstractPlayerCommand;
import com.hypixel.hytale.server.core.universe.PlayerRef;
import com.hypixel.hytale.server.core.universe.world.World;
import com.hypixel.hytale.server.core.universe.world.storage.EntityStore;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

import java.util.List;

public class PasteCommand extends AbstractPlayerCommand {
    private final ClipboardStore clipboardStore;
    public PasteCommand(@NonNullDecl String name, @NonNullDecl String description, ClipboardStore clipboardStore) {
        super(name, description);
        this.clipboardStore = clipboardStore;
    }

    @Override
    protected void execute(@NonNullDecl CommandContext commandContext, @NonNullDecl Store<EntityStore> store, @NonNullDecl Ref<EntityStore> ref, @NonNullDecl PlayerRef playerRef, @NonNullDecl World world) {
       List<Block> blocks = clipboardStore.getBlocksWithAbsolutePosition(playerRef);

       for (Block block : blocks) {
           world.setBlock((int) block.position().x, (int) block.position().y, (int) block.position().z, block.type());
        }
    }
}
