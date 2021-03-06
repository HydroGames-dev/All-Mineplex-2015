package mineplex.core.gadget.gadgets;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton.SkeletonType;
import org.bukkit.inventory.ItemStack;

import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilServer;
import mineplex.core.disguise.disguises.DisguiseSkeleton;
import mineplex.core.gadget.GadgetManager;
import mineplex.core.gadget.types.MorphGadget;
import mineplex.core.visibility.VisibilityManager;

public class MorphPumpkinKing extends MorphGadget
{
	public MorphPumpkinKing(GadgetManager manager)
	{
		super(manager, "Pumpkin Kings Head", new String[] 
				{
				C.cWhite + "Transforms the wearer into",
				C.cWhite + "the dreaded Pumpkin King!",
				"",
				C.cYellow + "Earned by defeating the Pumpkin King",
				C.cYellow + "in the 2013 Halloween Horror Event.",
				},
				-1,
				Material.PUMPKIN, (byte)0);

	}

	@Override
	public void EnableCustom(final Player player) 
	{
		this.ApplyArmor(player);

		DisguiseSkeleton disguise = new DisguiseSkeleton(player);
		disguise.showArmor();
		disguise.setName(player.getName(), Manager.getClientManager().Get(player).GetRank());
		disguise.setCustomNameVisible(true);
		disguise.SetSkeletonType(SkeletonType.WITHER);
		Manager.getDisguiseManager().disguise(disguise);
		
		player.getInventory().setHelmet(new ItemStack(Material.JACK_O_LANTERN));

		VisibilityManager.Instance.setVisibility(player, false, UtilServer.getPlayers());
		VisibilityManager.Instance.setVisibility(player, true, UtilServer.getPlayers());
	}

	@Override
	public void DisableCustom(Player player) 
	{
		this.RemoveArmor(player);
		Manager.getDisguiseManager().undisguise(player);
		player.getInventory().setHelmet(null);
	}

	
}
