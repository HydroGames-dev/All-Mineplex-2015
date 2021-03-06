package nautilus.game.arcade.game.games.hideseek.kits;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Slime;
import org.bukkit.inventory.ItemStack;

import mineplex.core.achievement.Achievement;
import mineplex.core.common.util.C;
import mineplex.core.common.util.UtilServer;
import mineplex.core.disguise.disguises.DisguiseSlime;
import mineplex.core.itemstack.ItemStackFactory;
import nautilus.game.arcade.ArcadeManager;
import nautilus.game.arcade.kit.Kit;
import nautilus.game.arcade.kit.KitAvailability;
import nautilus.game.arcade.kit.Perk;

public class KitHiderTeleporter extends Kit
{
	public KitHiderTeleporter(ArcadeManager manager)
	{
		super(manager, "Infestor Hider", KitAvailability.Achievement, 

				new String[] 
						{ 
				"Can instantly infest a target block.",
				"This is the only way you can hide."
				
						}, 

						new Perk[] 
								{
				
								}, 
								EntityType.SLIME,
								new ItemStack(Material.SLIME_BALL));
		
		this.setAchievementRequirements(new Achievement[] 
				{
				Achievement.BLOCK_HUNT_BAD_HIDER,
				Achievement.BLOCK_HUNT_HUNTER_KILLER,
				Achievement.BLOCK_HUNT_HUNTER_OF_THE_YEAR,
				Achievement.BLOCK_HUNT_MEOW,
				Achievement.BLOCK_HUNT_WINS,
				});
	}

	@Override
	public void GiveItems(Player player) 
	{
		//Swap
		player.getInventory().setItem(3, ItemStackFactory.Instance.CreateStack(Material.MAGMA_CREAM, (byte)0, 1, C.cYellow + C.Bold + "Click Block" + C.cWhite + C.Bold + " - " + C.cGreen + C.Bold + "Infest Block/Animal"));
		
		DisguiseSlime slime = new DisguiseSlime(player);
		slime.SetSize(2);
		slime.setName(C.cAqua + player.getName());
		Manager.GetDisguise().disguise(slime);
	}
	
	@Override
	public void SpawnCustom(LivingEntity ent) 
	{
		((Slime)ent).setSize(2);
	}
}
