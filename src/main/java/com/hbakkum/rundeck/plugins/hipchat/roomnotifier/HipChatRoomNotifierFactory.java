package com.hbakkum.rundeck.plugins.hipchat.roomnotifier;

import com.hbakkum.rundeck.plugins.hipchat.HipChatNotificationPluginException;
import com.hbakkum.rundeck.plugins.hipchat.http.HttpRequestExecutor;
import com.hbakkum.rundeck.plugins.hipchat.http.RestyHttpRequestExecutor;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Hayden Bakkum
 */
public class HipChatRoomNotifierFactory {

    private static final Map<String, HipChatRoomNotifier> HIPCHAT_ROOM_NOTIFIERS = new HashMap<String, HipChatRoomNotifier>();

    static {
        final HttpRequestExecutor httpRequestExecutor = new RestyHttpRequestExecutor();

        final HipChatRoomNotifier[] hipChatRoomNotifiers = {
            new HipChatApiVersion1RoomNotifier(httpRequestExecutor),
            new HipChatApiVersion2RoomNotifier(httpRequestExecutor)
        };

        for (final HipChatRoomNotifier hipChatRoomNotifier : hipChatRoomNotifiers) {
            HIPCHAT_ROOM_NOTIFIERS.put(hipChatRoomNotifier.getSupportedApiVersion(), hipChatRoomNotifier);
        }
    }

    public static HipChatRoomNotifier get(final String apiVersion) {
        final HipChatRoomNotifier hipChatRoomNotifier = HIPCHAT_ROOM_NOTIFIERS.get(apiVersion);

        if (hipChatRoomNotifier == null) {
            throw new HipChatNotificationPluginException("Unknown or unsupported HipChat API version: ["+apiVersion+"]");
        }

        return hipChatRoomNotifier;
    }

}
