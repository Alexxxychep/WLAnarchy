package me.alexxxychep.wlanarchy;

import java.util.HashMap;
import java.util.Map;

public class WLException extends RuntimeException {
    private final Map<String, String> context = new HashMap<>();

    public WLException(String message, Throwable cause) {
        super(message, cause);
    }

    public WLException(String message) {
        super(message);
    }

    public WLException context(String contextKey, String contextValue) {
        context.put(contextKey, contextValue);
        return this;
    }

    public void addContext(String contextKey, String contextValue) {
        context.put(contextKey, contextValue);
    }

    public Map<String, String> getContext() {
        return context;
    }

    @Override
    public String getMessage() {
        StringBuilder builder = new StringBuilder();
        builder.append(super.getMessage()).append("\n");
        if(!getContext().isEmpty()) {
            builder.append("Context: \n");
            for(Map.Entry<String, String> contextEntry : getContext().entrySet()) {
                builder.append(contextEntry.getKey()).append(" -> ").append(contextEntry.getValue()).append("\n");
            }
        }
        return builder.toString();
    }

}
