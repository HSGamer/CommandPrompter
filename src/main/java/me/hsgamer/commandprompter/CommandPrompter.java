package me.hsgamer.commandprompter;

import co.aikar.taskchain.BukkitTaskChainFactory;
import co.aikar.taskchain.TaskChainFactory;
import java.util.regex.Pattern;
import org.bukkit.plugin.java.JavaPlugin;

public final class CommandPrompter extends JavaPlugin {

  public static final Pattern PATTERN = Pattern.compile("[{]([^{}]+)[}]");
  private static QuestionnaireFactory questionnaireFactory;
  private static TaskChainFactory taskChainFactory;

  public static QuestionnaireFactory getQuestionnaireFactory() {
    return questionnaireFactory;
  }

  public static TaskChainFactory getTaskChainFactory() {
    return taskChainFactory;
  }

  @Override
  public void onEnable() {
    questionnaireFactory = new QuestionnaireFactory(this);
    taskChainFactory = BukkitTaskChainFactory.create(this);
    getCommand("testprompt").setExecutor(new TestCommand());
  }

  @Override
  public void onDisable() {
    // Plugin shutdown logic
  }
}
