package me.hsgamer.commandprompter;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.conversations.Conversable;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.Conversation.ConversationState;

public class TestCommand implements CommandExecutor {

  @Override
  public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
    if (sender instanceof Conversable) {
      List<String> questions = new ArrayList<>();
      questions.add("What is your name??");
      questions.add("How old are you??");

      CommandPrompter.getTaskChainFactory().newChain()
          .<Map<String,String>>asyncFirstCallback(next -> {
            Conversation conversation = CommandPrompter.getQuestionnaireFactory()
                .withQuestion(questions)
                .withLocalEcho(true)
                .withEscapeSequence("stop")
                .buildConversation((Conversable) sender);

            conversation.begin();

            while (conversation.getState() == ConversationState.STARTED) {
              try {
                Thread.sleep(1000);
              } catch (InterruptedException e) {
                e.printStackTrace();
              }
            }

            next.accept(QuestionnaireFactory.getAnswer(conversation));
          })
          .syncLast(input -> {
            sender.sendMessage("called");
            input.forEach((q, a) -> {
              sender.sendMessage(q);
              sender.sendMessage("- " + a);
            });
          })
          .execute();
      return true;
    }
    return false;
  }

}
