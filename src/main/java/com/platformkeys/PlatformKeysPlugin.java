package com.platformkeys;

import com.google.inject.Provides;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;
import javax.inject.Inject;
import lombok.extern.slf4j.Slf4j;
import net.runelite.api.Client;
import net.runelite.api.events.ClientTick;
import net.runelite.api.events.WidgetClosed;
import net.runelite.api.events.WidgetLoaded;
import net.runelite.api.widgets.Widget;
import net.runelite.api.gameval.InterfaceID;
import net.runelite.api.gameval.VarClientID;
import net.runelite.client.callback.ClientThread;
import net.runelite.client.config.ConfigManager;
import net.runelite.client.config.Keybind;
import net.runelite.client.eventbus.Subscribe;
import net.runelite.client.input.KeyListener;
import net.runelite.client.input.KeyManager;
import net.runelite.client.plugins.Plugin;
import net.runelite.client.plugins.PluginDescriptor;
import net.runelite.client.plugins.bank.BankSearch;

@Slf4j
@PluginDescriptor(
	name = "Platform Keys",
	description = "Bank search and key mapping profiles for Windows and Mac",
	tags = {"bank", "search", "hotkey", "mac"}
)
public class PlatformKeysPlugin extends Plugin implements KeyListener
{
	// Modifiers we care about when comparing against the configured profile
	// Text the core Key Remapping plugin shows on the chat input line while chat is locked
	private static final String PRESS_ENTER_TO_CHAT = "Press Enter to Chat...";

	private static final int MOD_MASK = KeyEvent.CTRL_DOWN_MASK | KeyEvent.META_DOWN_MASK
		| KeyEvent.ALT_DOWN_MASK | KeyEvent.SHIFT_DOWN_MASK;

	// Original key code -> remapped key code, for keys currently held down
	private final Map<Integer, Integer> remapped = new HashMap<>();
	private boolean swallowTyped;
	// Tracked from widget events because key events arrive on the AWT thread, where widgets can't be inspected
	private volatile boolean bankOpen;
	private volatile boolean seedVaultOpen;
	// Refreshed every client tick; true while number keys belong to a chatbox dialogue
	private volatile boolean dialogOpen;
	// Refreshed every client tick; true while the core Key Remapping plugin is in its Enter-to-chat typing mode
	private volatile boolean chatTyping;

	@Inject
	private Client client;

	@Inject
	private ClientThread clientThread;

	@Inject
	private KeyManager keyManager;

	@Inject
	private PlatformKeysConfig config;

	@Inject
	private BankSearch bankSearch;

	@Inject
	private ConfigManager configManager;

	@Provides
	PlatformKeysConfig provideConfig(ConfigManager configManager)
	{
		return configManager.getConfig(PlatformKeysConfig.class);
	}

	@Override
	protected void startUp()
	{
		keyManager.registerKeyListener(this);
		clientThread.invoke(() ->
		{
			Widget bank = client.getWidget(InterfaceID.Bankmain.UNIVERSE);
			bankOpen = bank != null && !bank.isHidden();
			Widget vault = client.getWidget(InterfaceID.SeedVault.UNIVERSE);
			seedVaultOpen = vault != null && !vault.isHidden();
		});
	}

	@Override
	protected void shutDown()
	{
		keyManager.unregisterKeyListener(this);
		bankOpen = false;
		seedVaultOpen = false;
	}

	@Subscribe
	public void onWidgetLoaded(WidgetLoaded event)
	{
		if (event.getGroupId() == InterfaceID.BANKMAIN)
		{
			bankOpen = true;
		}
		else if (event.getGroupId() == InterfaceID.SEED_VAULT)
		{
			seedVaultOpen = true;
		}
	}

	@Subscribe
	public void onWidgetClosed(WidgetClosed event)
	{
		if (event.getGroupId() == InterfaceID.BANKMAIN)
		{
			bankOpen = false;
		}
		else if (event.getGroupId() == InterfaceID.SEED_VAULT)
		{
			seedVaultOpen = false;
		}
	}

	private boolean searchActive()
	{
		// The chatbox input widget is shown while a bank search is being typed
		return client.getVarcIntValue(VarClientID.MESLAYERMODE) == 11;
	}

	private boolean inputDialogOpen()
	{
		return client.getVarcIntValue(VarClientID.MESLAYERMODE) != 0;
	}

	/**
	 * Refreshes {@link #dialogOpen} on the client thread, where widgets can be inspected safely.
	 * A dialogue is anything attached to the chatbox's modal layer (NPC chat, option menus, "What would you like
	 * to make?" prompts, level-ups), plus the cases the core Key Remapping plugin checks and the bank pin keypad.
	 */
	@Subscribe
	public void onClientTick(ClientTick event)
	{
		Widget modal = client.getWidget(InterfaceID.Chatbox.CHATMODAL);
		dialogOpen = (modal != null && modal.getNestedChildren().length > 0)
			|| selfHidden(InterfaceID.Chatbox.MES_LAYER_HIDE) || selfHidden(InterfaceID.Chatbox.CHATDISPLAY)
			|| !selfHidden(InterfaceID.BankpinKeypad.UNIVERSE);
		chatTyping = keyRemappingTyping();
	}

	/**
	 * The core Key Remapping plugin locks the chatbox behind "Press Enter to Chat..." and unlocks it while the
	 * player is typing a message. Its typing flag isn't public, so read it back from the chat input line.
	 */
	private boolean keyRemappingTyping()
	{
		if (!"true".equals(configManager.getConfiguration("runelite", "keyremappingplugin")))
		{
			return false;
		}
		Widget input = client.getWidget(InterfaceID.Chatbox.INPUT);
		if (input == null || input.getText() == null)
		{
			return false;
		}
		return !input.getText().endsWith(PRESS_ENTER_TO_CHAT);
	}

	private boolean selfHidden(int component)
	{
		Widget w = client.getWidget(component);
		return w == null || w.isSelfHidden();
	}

	/** The active profile's keys for F1-F12, in order. */
	private Keybind[] fKeys()
	{
		if (config.profile().resolve() == KeyProfile.MAC)
		{
			return new Keybind[]{
				config.macF1(), config.macF2(), config.macF3(), config.macF4(), config.macF5(), config.macF6(),
				config.macF7(), config.macF8(), config.macF9(), config.macF10(), config.macF11(), config.macF12()
			};
		}
		return new Keybind[]{
			config.windowsF1(), config.windowsF2(), config.windowsF3(), config.windowsF4(), config.windowsF5(), config.windowsF6(),
			config.windowsF7(), config.windowsF8(), config.windowsF9(), config.windowsF10(), config.windowsF11(), config.windowsF12()
		};
	}

	/** Index of the F-key (0 = F1) the key code is bound to in the active profile, or -1. */
	private int fKeySlot(int keyCode)
	{
		if (keyCode == KeyEvent.VK_UNDEFINED)
		{
			return -1;
		}
		Keybind[] keys = fKeys();
		for (int i = 0; i < keys.length; i++)
		{
			if (keys[i].getKeyCode() == keyCode)
			{
				return i;
			}
		}
		return -1;
	}

	/** Opens the search prompt of whichever of the bank or seed vault is open. */
	private void openSearch()
	{
		if (bankOpen)
		{
			// Clears any existing search text/filter and opens a fresh search prompt
			bankSearch.initSearch();
			return;
		}

		// The seed vault has no BankSearch equivalent; run its Search button's own script, as the core Bank plugin does
		clientThread.invoke(() ->
		{
			Widget searchButton = client.getWidget(InterfaceID.SeedVault.SEARCH);
			if (searchButton == null || searchButton.isHidden())
			{
				return;
			}
			Object[] onOp = searchButton.getOnOpListener();
			if (onOp != null)
			{
				client.runScript(onOp);
			}
		});
	}

	@Override
	public void keyPressed(KeyEvent e)
	{
		int code = e.getKeyCode();
		int mods = e.getModifiersEx() & MOD_MASK;

		// Configured keys -> F1-F12. Skipped while a chatbox input (bank search, Withdraw-X), a dialogue, or
		// Enter-to-chat typing is active, so the keys still type there and still pick dialogue options.
		boolean keysNeeded = inputDialogOpen() || dialogOpen || chatTyping;
		int slot = config.remapNumbers() && mods == 0 && !keysNeeded ? fKeySlot(code) : -1;
		if (slot >= 0)
		{
			int target = KeyEvent.VK_F1 + slot;
			remapped.put(code, target);
			swallowTyped = true;
			e.setKeyCode(target);
			e.setKeyChar(KeyEvent.CHAR_UNDEFINED);
			return;
		}
		swallowTyped = false;

		if (!bankOpen && !seedVaultOpen)
		{
			return;
		}

		int primary = config.profile().primaryModifier();

		// Hotkey: [Ctrl|Cmd]+<key>
		int wantedMods = config.requireModifier() ? primary : 0;
		if (code == config.searchKey().getKeyCode() && mods == wantedMods)
		{
			// Without a modifier the hotkey is a plain letter, which has to stay typeable in the search box
			if (wantedMods == 0 && searchActive())
			{
				return;
			}
			e.consume();
			openSearch();
			return;
		}

		if (bankOpen && code == KeyEvent.VK_ESCAPE && config.escapeClosesSearch() && searchActive())
		{
			e.consume();
			// Clears the search text and closes the search prompt
			bankSearch.reset(true);
			return;
		}

		if (config.typeToSearch() && !searchActive() && mods == 0 && Character.isLetterOrDigit(e.getKeyChar()))
		{
			// The triggering character is not replayed; the search box opens empty.
			openSearch();
		}
	}

	@Override
	public void keyTyped(KeyEvent e)
	{
		// Stop the character of a remapped key from also being typed into chat
		if (swallowTyped)
		{
			e.consume();
		}
	}

	@Override
	public void keyReleased(KeyEvent e)
	{
		Integer target = remapped.remove(e.getKeyCode());
		if (target != null)
		{
			e.setKeyCode(target);
			e.setKeyChar(KeyEvent.CHAR_UNDEFINED);
		}
	}
}
