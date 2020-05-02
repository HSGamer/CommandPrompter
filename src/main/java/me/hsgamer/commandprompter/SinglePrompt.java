package me.hsgamer.commandprompter;

import java.util.List;
import java.util.Map;
import org.bukkit.conversations.ConversationContext;
import org.bukkit.conversations.Prompt;
import org.bukkit.conversations.StringPrompt;

@SuppressWarnings("unchecked")
public class SinglePrompt extends StringPrompt {

  @Override
  public String getPromptText(ConversationContext context) {
    List<String> question = (List<String>) context.getSessionData("question");
    assert question != null;
    String current = question.remove(0);
    context.setSessionData("current", current);
    return current;
  }

  @Override
  public Prompt acceptInput(ConversationContext context, String input) {
    Map<String, String> answer = (Map<String, String>) context.getSessionData("answer");
    List<String> question = (List<String>) context.getSessionData("question");
    assert question != null;
    assert answer != null;

    String current = String.valueOf(context.getSessionData("current"));

    answer.put(current, input);

    if (question.isEmpty()) {
      return END_OF_CONVERSATION;
    }

    return this;
  }
}
