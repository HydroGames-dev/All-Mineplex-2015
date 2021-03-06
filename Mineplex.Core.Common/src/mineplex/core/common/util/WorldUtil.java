package mineplex.core.common.util;

import java.io.File;

import net.minecraft.server.v1_7_R4.ConvertProgressUpdater;
import net.minecraft.server.v1_7_R4.Convertable;
import net.minecraft.server.v1_7_R4.EntityTracker;
import net.minecraft.server.v1_7_R4.EnumDifficulty;
import net.minecraft.server.v1_7_R4.EnumGamemode;
import net.minecraft.server.v1_7_R4.IWorldAccess;
import net.minecraft.server.v1_7_R4.ServerNBTManager;
import net.minecraft.server.v1_7_R4.WorldLoaderServer;
import net.minecraft.server.v1_7_R4.WorldManager;
import net.minecraft.server.v1_7_R4.WorldServer;
import net.minecraft.server.v1_7_R4.WorldSettings;
import net.minecraft.server.v1_7_R4.WorldType;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.craftbukkit.v1_7_R4.CraftServer;
import org.bukkit.event.world.WorldInitEvent;
import org.bukkit.event.world.WorldLoadEvent;
import org.bukkit.generator.ChunkGenerator;

public class WorldUtil 
{
	public static World LoadWorld(WorldCreator creator)
	{
		CraftServer server = (CraftServer)Bukkit.getServer();
        if (creator == null) 
        {
            throw new IllegalArgumentException("Creator may not be null");
        }

        String name = creator.name();
        System.out.println("Loading world '" + name + "'");
        ChunkGenerator generator = creator.generator();
        File folder = new File(server.getWorldContainer(), name);
        World world = server.getWorld(name);
        WorldType type = WorldType.getType(creator.type().getName());
        boolean generateStructures = creator.generateStructures();

        if (world != null) 
        {
            return world;
        }

        if ((folder.exists()) && (!folder.isDirectory())) 
        {
            throw new IllegalArgumentException("File exists with the name '" + name + "' and isn't a folder");
        }

        if (generator == null) 
        {
            generator = server.getGenerator(name);
        }

        Convertable converter = new WorldLoaderServer(server.getWorldContainer());
        if (converter.isConvertable(name)) 
        {
        	server.getLogger().info("Converting world '" + name + "'");
            converter.convert(name, new ConvertProgressUpdater(server.getServer()));
        }

        int dimension = server.getWorlds().size() + 1;
        boolean used = false;
        do 
        {
            for (WorldServer worldServer : server.getServer().worlds) 
            {
                used = worldServer.dimension == dimension;
                if (used) 
                {
                    dimension++;
                    break;
                }
            }
        } while(used);
        boolean hardcore = false;

        System.out.println("Loaded world with dimension : " + dimension);
        
        WorldServer internal = new WorldServer(server.getServer(), new ServerNBTManager(server.getWorldContainer(), name, true), name, dimension, new WorldSettings(creator.seed(), EnumGamemode.getById(server.getDefaultGameMode().getValue()), generateStructures, hardcore, type), server.getServer().methodProfiler, creator.environment(), generator);
        
        boolean containsWorld = false;
        for (World otherWorld : server.getWorlds()) 
        {
        	if (otherWorld.getName().equalsIgnoreCase(name.toLowerCase()))
        	{
        		containsWorld = true;
        		break;
        	}
        }
        
        if (!containsWorld)
        	return null;

        System.out.println("Created world with dimension : " + dimension);
        
        internal.scoreboard = server.getScoreboardManager().getMainScoreboard().getHandle();
        internal.worldMaps = server.getServer().worlds.get(0).worldMaps;
        internal.tracker = new EntityTracker(internal); // CraftBukkit
        internal.addIWorldAccess((IWorldAccess) new WorldManager(server.getServer(), internal));
        internal.difficulty = EnumDifficulty.HARD;
        internal.setSpawnFlags(true, true);
        internal.savingDisabled = true;
        server.getServer().worlds.add(internal);

        /*
        for (WorldServer worlder : server.getServer().worlds) 
        {
        	System.out.println(worlder.getWorldData().getName() + " with dimension: " + worlder.dimension);
        }
        */
        
        if (generator != null) 
        {
            internal.getWorld().getPopulators().addAll(generator.getDefaultPopulators(internal.getWorld()));
        }

        server.getPluginManager().callEvent(new WorldInitEvent(internal.getWorld()));
        server.getPluginManager().callEvent(new WorldLoadEvent(internal.getWorld()));
        
        /*
        for (WorldServer worlder : server.getServer().worlds) 
        {
        	System.out.println(worlder.getWorldData().getName() + " with dimension: " + worlder.dimension);
        }
        */
        
        return internal.getWorld();
	}
}
