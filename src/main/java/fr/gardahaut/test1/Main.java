package fr.gardahaut.test1;

import com.hypixel.hytale.server.core.plugin.JavaPlugin;
import com.hypixel.hytale.server.core.plugin.JavaPluginInit;
import org.checkerframework.checker.nullness.compatqual.NonNullDecl;

public class Main extends JavaPlugin {
    private final LandmarksStore landmarksStore;
    private final ClipboardStore clipboardStore;
    public Main(@NonNullDecl JavaPluginInit init) {
        this.landmarksStore = new LandmarksStore();
        this.clipboardStore = new ClipboardStore();
        super(init);
    }

    @Override
    protected void setup() {
        super.setup();
        this.getCommandRegistry().registerCommand(new WandCommand("wand", "Gives you a wand"));
        this.getCommandRegistry().registerCommand(new LandmarkCommand("landmark", "Creates a landmark", this.landmarksStore));
        this.getCommandRegistry().registerCommand(new LandmarkCommand("lm", "Creates a landmark", this.landmarksStore));
        this.getCommandRegistry().registerCommand(new CutCommand("/cut", "Removes blocks in the region between landmarks and add to clipboard", this.landmarksStore, this.clipboardStore));
        this.getCommandRegistry().registerCommand(new CopyCommand("/copy", "Add blocks between landmarks to clipboard", this.landmarksStore, this.clipboardStore));

        this.getCommandRegistry().registerCommand(new SetCommand("set", "Sets block in the region between landmarks", this.landmarksStore));
        this.getCommandRegistry().registerCommand(new PasteCommand("/paste", "Paste blocks from your clipboard", this.clipboardStore));

        this.getEntityStoreRegistry().registerSystem(new BlockDamageEventHandler(this.landmarksStore));
    }
}
