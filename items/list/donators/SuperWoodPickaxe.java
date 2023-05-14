package items.list.donators;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import items.Item;

public class SuperWoodPickaxe extends Item {

	public SuperWoodPickaxe() {
		super("Super Wooden Pickaxe", Material.WOOD_PICKAXE);
		
		this.setPriority(false);
		this.setDescription("&aVIP DONATORS ITEM.");
		this.setId(1508);
		this.compile();
		
		getItemStack().addUnsafeEnchantment(Enchantment.DURABILITY, 50);
		getItemStack().addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
		
	}

	@Override
	public void onInteract(Player p, PlayerInteractEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onDamageEntity(Player p, EntityDamageByEntityEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onUseOnEntity(Player p, PlayerInteractEntityEvent e) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void onBlockPlace(Player p, BlockPlaceEvent e) {
		// TODO Auto-generated method stub
		
	}


}
