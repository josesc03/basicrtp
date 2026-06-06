# BasicRTP

## Description
BasicRTP is a simple and lightweight plugin for Minecraft 1.21, designed to allow random teleportation within a defined range. Created out of necessity for my own server, it provides a quick and configurable way to explore the world without complex commands.

### Features
- **Random Teleportation:** Quick teleportation within a configurable area.
- **Customizable Start Point:** Set the center of the teleportation zone easily in-game.
- **Cooldown System:** Prevents excessive use and server strain.
- **Fully Configurable:** Easily customize ranges, coordinates, and messages.
- **Optimized:** Lightweight design tailored for Minecraft 1.21.X.

---

## Configuration (`config.yml`)
When the plugin loads for the first time, a `config.yml` file will be generated automatically in the `plugins/BasicRTP/` directory. You can customize the following values:

```yaml
# Center coordinates for the random teleportation area
initial_x: -958
initial_z: -468

# Maximum distance (in blocks) from the initial coordinates
max_range: 5000

# Message sent to the player upon successful teleportation
teleport_message: "Has sido teletransportado por magia!"

# Cooldown time in seconds between uses
cooldown_seconds: 10
