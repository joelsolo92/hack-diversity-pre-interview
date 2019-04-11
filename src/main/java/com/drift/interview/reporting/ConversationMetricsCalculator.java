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
    Message currentMessage;
    Message nextMessage;
    int numOfResponses = 0;
    int totalTimeDifferenceMs = 0;
    long averageResponseTimeMs = 0;

    for (int x = 0; x < messages.size() - 1; x++){

      currentMessage = messages.get(x);

      for (int y = x+1; y < messages.size(); y++){

        nextMessage = messages.get(y);

        if (currentMessage.isTeamMember() == false && nextMessage.isTeamMember() == true){

            totalTimeDifferenceMs += (nextMessage.getCreatedAt() - currentMessage.getCreatedAt());
            numOfResponses++;

            if (y < messages.size() - 1)
              x = y;
            break;
        }
      }
    }

    averageResponseTimeMs = totalTimeDifferenceMs / numOfResponses;

    return ConversationResponseMetric.builder()
        .setConversationId(conversation.getId())
        .setAverageResponseMs(averageResponseTimeMs)
        .build();
  }
}
