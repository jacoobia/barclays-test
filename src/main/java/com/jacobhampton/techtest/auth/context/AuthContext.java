package com.jacobhampton.techtest.auth.context;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AuthContext {

    private static final ThreadLocal<AuthContext> context = new ThreadLocal<>();

    private String userId;

    public static void set(AuthContext userContext) {
        context.set(userContext);
    }

    public static AuthContext get() {
        return context.get();
    }

    public static void clear() {
        context.remove();
    }

}
