package net.frankheijden.insights.addons.askyblock;

import java.util.Collections;
import com.wasteofplastic.askyblock.ASkyBlockAPI;
import com.wasteofplastic.askyblock.Island;
import com.wasteofplastic.askyblock.Settings;
import net.frankheijden.insights.entities.Area;
import net.frankheijden.insights.entities.CacheAssistant;
import net.frankheijden.insights.entities.CuboidSelection;
import org.bukkit.Location;
import org.bukkit.World;

public class ASkyBlockAssistant extends CacheAssistant {

    public ASkyBlockAssistant() {
        super("ASkyBlock", "ASkyBlock", "island", "1.1.0");
    }

    public String getId(Island island) {
        return getPluginName() + "@" + island.getCenter().getBlockX() + "," + island.getCenter().getBlockZ();
    }

    public Area adapt(Island island) {
        if (island == null) return null;
        return Area.from(this, getId(island), Collections.singletonList(new CuboidSelection(getMin(island), getMax(island))));
    }

    public Location getMin(Island island) {
        return new Location(
                island.getCenter().getWorld(),
                island.getMinProtectedX(),
                0,
                island.getMinProtectedZ()
        );
    }

    public Location getMax(Island island) {
        World world = island.getCenter().getWorld();
        return new Location(
                world,
                island.getMinProtectedX() + Settings.islandProtectionRange,
                world.getMaxHeight() - 1,
                island.getMinProtectedZ() + Settings.islandProtectionRange
        );
    }

    @Override
    public Area getArea(Location location) {
        if (location == null) return null;
        Island island;
        try {
            island = ASkyBlockAPI.getInstance().getIslandAt(location);
        } catch (Throwable th) {
            return null;
        }
        return adapt(island);
    }
}
