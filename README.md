# Platform Keys

A RuneLite plugin for people who play on both a Mac and a Windows PC. It gives you a bank search shortcut that follows the platform you are on (Cmd on Mac, Ctrl on Windows) and lets each platform use a different set of number keys as F1-F10.

## Bank search

While the bank is open:

- **Cmd+F** (Mac) or **Ctrl+F** (Windows/Linux) opens the bank search.
- Pressing the shortcut again clears what you typed and starts a new search.
- **Esc** closes the search instead of closing the bank.

The built-in Bank plugin's own search shortcut is not changed and keeps working alongside this one.

## Number keys as F1-F10

Number keys 1-9 and 0 are sent to the game as F1-F10, so you can switch side panels without reaching for the function row. Each platform has its own choice of keys:

| Profile | Default keys |
| --- | --- |
| Mac | Number row |
| Windows | Numpad |

The remap is paused, so the number keys type or select as normal, while:

- a chatbox prompt is open (bank search, Withdraw-X and similar)
- a dialogue is open (NPC chat, option menus, "What would you like to make?" prompts)
- you are typing a chat message after pressing Enter, if you use the core Key Remapping plugin's "Press Enter to Chat" mode

Without Key Remapping enabled, remapped keys do not type digits into normal chat while the remap is on.

## Settings

| Setting | Default | What it does |
| --- | --- | --- |
| Key profile | Auto-detect | Picks Mac or Windows from the computer you are on. Set it by hand to force one profile. |
| Search hotkey | F | The key that opens bank search. Only the key is used; the modifier comes from the next setting. |
| Require Ctrl/Cmd | On | Require Cmd (Mac) or Ctrl (Windows) with the search hotkey. |
| Type to search | Off | Open the search when you press a letter or digit in the bank. The first key press only opens the search box; it is not typed into it. |
| Esc closes search | On | Esc closes an active search before closing the bank. |
| Remap to F1-F10 | On | Turn the number key remap on or off. |
| Mac keys | Number row | Which number keys are remapped on the Mac profile. |
| Windows keys | Numpad | Which number keys are remapped on the Windows profile. |

## Building from source

Requires JDK 11 to run the client and JDK 17 or newer to run Gradle.

```sh
gradle build   # compile
gradle run     # start RuneLite in developer mode with the plugin loaded
```

## License

BSD 2-Clause. See [LICENSE](LICENSE).
