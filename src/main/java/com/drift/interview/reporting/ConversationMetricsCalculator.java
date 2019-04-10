package com.drift.interview.reporting;

import com.drift.interview.model.Conversation;
import com.drift.interview.model.ConversationResponseMetric;
import com.drift.interview.model.Message;
import java.util.List;

public class ConversationMetricsCalculator {
  public ConversationMetricsCalculator() {}

  /**
   * Returns a ConversationResponseMetric object which can be used to power data visualizations on the front end.
   */
  ConversationResponseMetric calculateAverageResponseTime(Conversation conversation) {
    List<Message> messages = conversation.getMessages();
    long responseTimeMs = 0;

    for (int x = 0; x < messages.size() - 1; x++){

      Message currentMessage = messages.get(x);
      Message nextMessage = messages.get(x + 1);

      if (currentMessage.isTeamMember() == false && nextMessage.isTeamMember() == true){
        responseTimeMs = messages.get(x+1).getCreatedAt() - messages.get(x).getCreatedAt();
        break;
      }
    }

    return ConversationResponseMetric.builder()
        .setConversationId(conversation.getId())
        .setAverageResponseMs(responseTimeMs)
        .build();
  }
}
