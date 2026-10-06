# Platform Keys

A RuneLite plugin for people who play on both a Mac and a Windows PC. It gives you a bank search shortcut that follows the platform you are on (Cmd on Mac, Ctrl on Windows) and lets each platform use its own set of keys as F1-F12.

## Bank search

While the bank is open:

- **Cmd+F** (Mac) or **Ctrl+F** (Windows/Linux) opens the bank search.
- Pressing the shortcut again clears what you typed and starts a new search.
- **Esc** closes the search instead of closing the bank.

The same shortcut opens the search in the seed vault. Clearing with a second press and closing with Esc are bank-only.

The built-in Bank plugin's own search shortcut is not changed and keeps working alongside this one.

## Keys as F1-F12

You choose which key sends each of F1-F12, so you can switch side panels without reaching for the function row. Each platform has its own set of twelve keys:

| Profile | Default keys for F1-F10 | F11 | F12 |
| --- | --- | --- | --- |
| Mac | Number row 1-9, 0 | - | = |
| Windows | Numpad 1-9, 0 | Not set | Not set |

To change a key, open the "Mac F-keys" or "Windows F-keys" section in the plugin settings, click the F-key's box and press the key you want. Only plain keys are used, without Ctrl, Cmd, Alt or Shift.

- A key bound to an F-key no longer does its normal job in the game while the remap is on, so avoid keys you need for something else.
- The Windows defaults are numpad keys, which need Num Lock on.
- The Windows profile is also used on Linux.
- If the same key is bound to two F-keys in one profile, the lower-numbered F-key wins.

The remap is paused, so the keys type or select as normal, while:

- a chatbox prompt is open (bank search, Withdraw-X and similar)
- a dialogue is open (NPC chat, option menus, "What would you like to make?" prompts)
- you are typing a chat message after pressing Enter, if you use the core Key Remapping plugin's "Press Enter to Chat" mode

Without Key Remapping enabled, remapped keys do not type into normal chat while the remap is on.

## Settings

### Platform

| Setting | Default | What it does |
| --- | --- | --- |
| Key profile | Auto-detect | Picks Mac or Windows from the computer you are on. Set it by hand to force one profile. |

### Bank search

| Setting | Default | What it does |
| --- | --- | --- |
| Search hotkey | F | The key that opens bank search. Only the key is used; the modifier comes from the next setting. |
| Require Ctrl/Cmd | On | Require Cmd (Mac) or Ctrl (Windows) with the search hotkey. |
| Type to search | Off | Open the search when you press a letter or digit in the bank. The first key press only opens the search box; it is not typed into it. |
| Esc closes search | On | Esc closes an active search before closing the bank. |

### F-key remapping

| Setting | Default | What it does |
| --- | --- | --- |
| Remap to F1-F12 | On | Turn the F-key remap on or off for both profiles. |

### Mac F-keys and Windows F-keys

Each section has one box per F-key. Only the section for the active profile is used.

| F-key | Mac default | Windows default |
| --- | --- | --- |
| F1-F9 | 1-9 | Numpad 1-9 |
| F10 | 0 | Numpad 0 |
| F11 | - | Not set |
| F12 | = | Not set |

## Building from source

Requires JDK 11 to run the client and JDK 17-21 to run Gradle. Newer JDKs (such as 23) fail at compile time with the Lombok version in `build.gradle`.

```sh
gradle build   # compile
gradle run     # start RuneLite in developer mode with the plugin loaded
```

## License

BSD 2-Clause. See [LICENSE](LICENSE).
