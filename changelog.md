# V-Tweaks Changelog MC 1.14.x

## 3.1.6

### Fixed

- [Hopefully] fixed issues with ChallengerMob particles
- Fixed Pillagers being able to become challengers, rendering them useless without their crossbow

## 3.1.5

### Fixed

- Cake Tweaks not working

## 3.1.4

### Changed

- Forge dependency to stable (28.2.0)

### Added

- Challenger Mob config for disabling their custom name tags

## 3.1.3

### Changed

- Challenger Mobs are now defended from fire damage without a fire resistance potion
    - This prevents you from sapping the effect from the mob, if you have mods that add those enchantments
    - This shows the hurt animation *once* every so-often, but rest assured no damage is being done
    - This still fixes the concerns of spoofing them with lava or fire

## 3.1.2

### Fixed

- Broken localization on Man Pants
- Mobs dying without death animation (immediately disappearing on death)

## 3.1.1

### Added

- New damage effects to some challenger mobs!
  - Arcane Challengers inflict blindness, withering and levitation on hit
  - Hungry Challengers inflict 30s of Hunger II

### Fixed

- Lingering Challenger Mob Particles
- Debug statements spamming the game console

## 3.1.0

### Added

- [Configurable] Colored particle effects for challenger mobs (see [#62](https://github.com/oitsjustjose/V-Tweaks/issues/62#issuecomment-552642949))

### Fixed

- Challenger Mob items not being reassigned correctly if the original mob held something (e.g. skeletons)
- Broken death messages and damage source names

### Changed

- Pet Armory:
  - You can now apply any armor to your pet! Just throw armor near it, and it will pick it up!
  - You can also throw weapons at it (if the config is enabled - if it is not it'll throw the item back at you)
  - You can upgrade its armor any time, and in doing so get the old armor back
  - You can forcefully remove the old armor back by right clicking while sneaking on your pet
- Imperishable Logic for Armor
  - Salvaged armor is now moved to your inventory (or thrown at you in world)
  - Salvaged armor cannot be equipped
  - No more dumb stuff with how armor is calculated or what the damage source is

## 3.0.8

### Fixed

- Imperishable
  - Taking no damage / less damage from any source at all, with or without armor on and with or without imperishable
  - Fix armor defense values including an imperishable armor piece that shouldn't be able to protect
  - Fixed armors not taking any damage when hurting the player

## 3.0.7

### Fixed

- Welcome message showing more than once

### Changed

- Welcome message shown status is now stored as a `WorldCapability` instead of within the player, so updating will show players this message ✨one more time✨.
- Imperishable now damages from the correct source, instead of from a generic source

## 3.0.6

### Fixed

- Sapling Self-Planting not working
- Welcome Notification not respecting its config option

### Changed

- Sapling self-planting no longer uses a fake player (I don't know why I did that in the first place?!?)
- Sapling self-planting will plant if the block is normally replaceable on use.

## 3.0.5

### Added

- Enchantment Descriptions Compat

### Fixed

- Imperishable applying armor defense to damage that shouldn't be reduced by armor (e.g. fall damage being reduced by armor).
- Peaceful Surface preventing non-monster spawns

## 3.0.4

### Added

- Arm Swing Parity!

  A feature ported from the 1.15 snapshots (which was ported from Bedrock Edition)

  Forces the player's arm to swing when:

  - Interacting with entities (e.g. milking, shearing, mounting)
  - Placing boats
  - Using buckets
  - Catching fish in buckets

  This has a config option to disable the entire thing, of course.

### Improved

- Crop Helper logic - [#629afb4](https://github.com/oitsjustjose/V-Tweaks/pull/55)

### Fixed

- Delay in right-clicking a crop and the player's arm swinging - [#629afb4](https://github.com/oitsjustjose/V-Tweaks/pull/55)

## 3.0.3c

It's one of those days 😐 I'm having issues with Client/Server relations a lot today.

### Fixed

- Server-side crash with Imperishable (the hurt animation, specifically)

## 3.0.3b

### Fixed

- _Actually_ fixed that Peaceful Surface server-side crash 🤔

## 3.0.3

### Added

- Arm swing when using the Crop Tweak to harvest a crop (this was work, but I felt like it was worth it)
- Crop re-plant sound when using the Crop Tweak to harvest a crop
- Sound when using an imperishable tool with only 1 durability left
- New sound when when striking with an imperishable foe with only 1 durability left
- New text when you go to attack with a broken sword that is saved by imperishable
  - As always this is localizable 😄

### Fixed

- Crop tweak not working on Cocoa or Nether Wart
- Imperishable armor with durability > 1 not taking damage ever
- Imperishable sword being able to break
- Tool efficiency allowing your tool to break a block with Imperishable and 1 durability left
- Lumbering taking _insane_ durability from your axe
- Tool Durability tooltip being 1 higher than actual
- Server crash with Peaceful Surface

### Changed

- Lumbering now only breaks what logs it can before the tool breaks. It will leave your tool with 1 durability and the tree entact.
- Cutting leaves with lumbering doesn't take durability

## 3.0.2

### Added

- Config Option for disabling any modifications to despawn timers at all

### Fixed

- Items despawning if Item Despawn Timer set to -1

### Changed

- Config options for item despawning to be more descriptive

## 3.0.1

### Fixed

- Imperishable enchant causing weird visual glitch on tools
- Imperishable enchant not working on sword attacks
- Imperishable enchant not working on sword breaking blocks
- Imperishable enchant not working on armor

## 3.0.0

### Initial Port

#### What won't be coming

- Sheep Dye Fix - may return if still needed
- Horse Armor Recipes

#### What's ready but doesn't work yet

- Cake drops - they're implemented but forge isn't firing the event that it utilizes

#### What's changed with the port

- Crop Harvest Tweak doesn't swing arm (I can't figure this out w/o using packets)
- Imperishable - I can't figure out how to add the Book to nether fortress loot, so the recipe is an anvil recipe of **Unbreaking III books** on both sides.
- All texts are now translatable - this includes ChallengerMobs' names, the welcome text on first world join, and more.
