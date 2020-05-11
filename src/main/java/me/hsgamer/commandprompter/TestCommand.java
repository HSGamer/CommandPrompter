package me.hsgamer.commandprompter;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CompletableFuture;
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
          .asyncFirstFuture(() -> CompletableFuture.supplyAsync(() -> {
            Conversation conversation = CommandPrompter.getQuestionnaireFactory()
                .withQuestion(questions)
                .withLocalEcho(true)
                .withEscapeSequence("stop")
                .buildConversation((Conversable) sender);

            conversation.begin();
            waitForResult(conversation);

            return QuestionnaireFactory.getAnswer(conversation);
          }))
          .syncLast(input -> {
            sender.sendMessage("called");
            input.forEach((q, a) -> {
              sender.sendMessage(q);
              sender.sendMessage("- " + a);
            });
          })
          .sync(() -> sender.sendMessage("FINISH"))
          .execute();
      sender.sendMessage("Send out");
      return true;
    }
    return false;
  }

  private synchronized void waitForResult(Conversation conversation) {
    while (conversation.getState() == ConversationState.STARTED) {
      try {
        this.wait(1);
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
      }
    }
  }
}
