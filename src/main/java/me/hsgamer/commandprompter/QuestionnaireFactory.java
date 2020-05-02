package me.hsgamer.commandprompter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationFactory;
import org.bukkit.plugin.Plugin;

public class QuestionnaireFactory extends ConversationFactory {

  /**
   * Constructs a QuestionnaireFactory.
   *
   * @param plugin The plugin that owns the factory.
   */
  public QuestionnaireFactory(Plugin plugin) {
    super(plugin);
  }

  public QuestionnaireFactory withQuestion(List<String> questions) {
    withFirstPrompt(new SinglePrompt());

    Map<Object, Object> init = new HashMap<>();
    init.put("question", questions);
    init.put("answer", new HashMap<String, String>());
    withInitialSessionData(init);
    return this;
  }

  @SuppressWarnings("unchecked")
  public static Map<String, String> getAnswer(Conversation conversation) {
    return (Map<String, String>) conversation.getContext().getSessionData("answer");
  }
}
