## JBang with Minecraft

This is a work in progress in trying to see how to make
Minecraft launching and development easier with jbang.

Currently using [sponge](https://www.spongepowered.org) as it seemed
to be the Minecraft server with the best documentation and least
amount of "hacks".

Requirements: Nothing. This will download jbang and java when needed. 

### Run Minecraft

If you do not have jbang installed you can run the following:

```shell
curl -sL https://sh.jbang.dev | bash -s -- app setup && jbang sponge@jbangdev/jbang-minecraft
```

if on Windows it is:

```shell
iex "& { $(iwr https://ps.jbang.dev) } app setup"
jbang sponge@jbangdev/jbang-minecraft
```

When you have [jbang](https://jbang.dev) installed you can just run:

```shell
jbang sponge@jbangdev/jbang-minecraft
```

or if you want to run the spongevanilla jar directly - this has the advantage of a simpler classpath; but disadvantage 
is that you need to have to specify a very long url and it will not setup `eula.txt`:

```
jbang --java 8 https://repo.spongepowered.org/maven/org/spongepowered/spongevanilla/1.12.2-7.3.0/spongevanilla-1.12.2-7.3.0.jar
```

To run with debugging just add `--debug`, as in `jbang --debug ...` and connect a debugger on port 4004.
### Write a Minecraft plugin

Note: You do not need to run Minecraft with jbang to develop a plugin with jbang. It is completely independent.

Given a Sponge Minecraft install do the following:

```
cd mods
jbang init ExamplePlugin.java
jbang edit --open=code ExamplePlugin.java
```

Then paste the following into the newly created file, overwriting its contents:

```java
//REPOS sponge=https://repo.spongepowered.org/maven
//DEPS org.spongepowered:spongeapi:7.2.0
//JAVA 8

import java.util.Optional;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.EventListener;
import org.spongepowered.api.event.action.CollideEvent;
import org.spongepowered.api.text.Text;

import org.spongepowered.api.plugin.Plugin;

import org.spongepowered.api.plugin.Plugin;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.game.state.GameStartedServerEvent;

// Imports for logger
import com.google.inject.Inject;
import org.slf4j.Logger;

@Plugin(id = "exampleplugin", name = "Example Plugin", version = "1.0", description = "Example")
public class ExamplePlugin {

    @Inject
    private Logger logger;

    @Listener
    public void onServerStart(GameStartedServerEvent event) {
        logger.info("Successfully running ExamplePlugin!!!");
    }

    int counter = 0;

    @Listener
    public void handle(CollideEvent event) {
        logger.info("colliding: " + counter++);
    
        Optional<Player> playerOptional = event.getCause().<Player>first(Player.class);
        playerOptional.ifPresent(player -> {
            player.sendMessage(Text.builder("boing").build());
        });
    }
}
```

It is a very dumb/simple plugin that will just print out to console on every collide event.

Now, to get it build and made visible to sponge simple export the jar:

```
jbang export ExamplePlugin.java
```

You should now have `mods\ExamplePlugin.jar`.

Now restart your Minecraft Server and you should see it start and keep counting up.

For every change you will do the same, export the jar and then restart the server.






