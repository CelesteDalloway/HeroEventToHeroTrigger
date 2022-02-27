package com.sebdev.changeheroeventtotrigger;

import me.xemor.skillslibrary2.Skill;
import me.xemor.skillslibrary2.SkillsLibrary;
import me.xemor.skillslibrary2.triggers.Trigger;
import me.xemor.skillslibrary2.triggers.TriggerData;
import me.xemor.superheroes2.events.PlayerGainedSuperheroEvent;
import me.xemor.superheroes2.events.PlayerLostSuperheroEvent;
import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.plugin.java.JavaPlugin;

import org.jetbrains.annotations.Nullable;

import java.util.Collection;

import static me.xemor.skillslibrary2.triggers.Trigger.registerTrigger;


public final class ChangeHeroEventToTrigger extends JavaPlugin {

    @Override
    public void onEnable() {
        registerTrigger("LOSTSUPERHERO", TriggerData.class);
        registerTrigger("GAINSUPERHERO", TriggerData.class);
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    @EventHandler(ignoreCancelled = true)
    public void onLostSuperhero(PlayerLostSuperheroEvent e) {
        handleSkills(Trigger.getTrigger("LOSTSUPERHERO"), e.getPlayer(), e.getHero());
    }

    @EventHandler(ignoreCancelled = true)
    public void onLostSuperhero(PlayerGainedSuperheroEvent e) {
        handleSkills(Trigger.getTrigger("GAINSUPERHERO"), e.getPlayer(), e.getHero());
    }

    public boolean handleSkills(int trigger, @Nullable Entity entity, Object... objects) {
        Collection<Skill> skills = SkillsLibrary.getSkillsManager().getSkills(trigger);
        boolean cancel = false;
        for (Skill skill : skills) {
            cancel |= skill.handleEffects(entity, objects);
        }
        return cancel;
    }
}
