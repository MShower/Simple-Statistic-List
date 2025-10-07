# Simple Statistic List

![Minecraft](https://img.shields.io/badge/Minecraft-1.14.4--1.21.9-green)
![Loader](https://img.shields.io/badge/Loader-Fabric-blue)
![License](https://img.shields.io/badge/License-MIT-purple)

A lightweight Fabric mod that tracks player **block mining**, **block placing** and many other statistics, integrating them with Minecraft’s **scoreboard system**.  
Perfect for servers that want to display and compare player activity.

---

## ✨ Features
- 🪓 Count how many blocks a player has mined
- 🧱 Count how many blocks a player has placed
- ⚔️ Count how many entities a player has killed
- ☠️ Count how many times a player has dead
- 📊 Show results with the vanilla scoreboard system
- 🔄 Multiple display modes:
    - Show single scoreboard
    - Rotate among several scoreboards
    - Hide
- ⚡ Supports multiple Minecraft versions (1.14.4 ~ 1.21.9)

---

## 📦 Installation
1. Install [Fabric Loader](https://fabricmc.net/use/installer/).
2. Install the [Fabric API](https://modrinth.com/mod/fabric-api).
3. Download the latest `.jar` file from the [Releases](https://github.com/YOUR_NAME/Simple-Statistic-List/releases) or [Modrinth](https://modrinth.com/mod/simple-statistic-list/versions).
4. Place the `.jar` into your Minecraft `mods` folder.

---

## ⚙️ Usage
- The mod automatically tracks many statistics when players are in-game.
- Stats are stored in the scoreboard system.
- Server admins can use commands like:
  ```mcfunction
  /simplestatisticlist display cycle
  ```
- You can also use methods like
- ```mcfunction
  /simplestatisticlist method removeScoresWithSuffixInPlayerName bot_
  ```
- to batch process some operations related to scoreboards.
---

## 📜 License
- This project is licensed under MIT.
- See the `LICENSE` and `NOTICE` file to check project license.

---

## 🙌 Credits
- Built with the [Fabric API](https://modrinth.com/mod/fabric-api).
- Thanks to Fallen_Breath for [fabric-mod-template](https://github.com/Fallen-Breath/fabric-mod-template).
- Inspiration and some code sources: [TheStarryMiningList](https://github.com/crackun24/TheStarryMiningList).
- Thanks to all Minecraft server owners who inspired this project.

---
