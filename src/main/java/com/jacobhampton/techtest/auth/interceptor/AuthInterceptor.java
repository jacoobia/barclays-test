package com.jacobhampton.techtest.auth.interceptor;

import com.jacobhampton.techtest.auth.annotation.Private;
import com.jacobhampton.techtest.auth.context.AuthContext;
import com.jacobhampton.techtest.auth.service.TokenService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Easy way of securing endpoints behind a jwt :)
 * Stolen from my own codebase but without all the extra security fluff
 */
@RequiredArgsConstructor
public class AuthInterceptor implements HandlerInterceptor {

    private final TokenService tokenService;

    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod handlerMethod)
                || !isPrivateEndpoint(handlerMethod)) {
            return true;
        }

        String token = request.getHeader("Authorization");

        if (token == null) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Missing Authorization header");
            return false;
        }

        token = token.replace("Bearer ", "").trim();

        try {
            boolean valid = tokenService.validateToken(token);

            if (!valid) {
                response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
                return false;
            }

            String userId = tokenService.extractUserId(token);

            AuthContext.set(new AuthContext(userId));
            return true;

        } catch (Exception e) {
            response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
            return false;
        }
    }

    @Override
    public void afterCompletion(@NonNull HttpServletRequest request,
                                @NonNull HttpServletResponse response,
                                @NonNull Object handler,
                                Exception exception) {
        AuthContext.clear();
    }

    private boolean isPrivateEndpoint(HandlerMethod handlerMethod) {
        return AnnotatedElementUtils.hasAnnotation(handlerMethod.getMethod(), Private.class)
                || AnnotatedElementUtils.hasAnnotation(handlerMethod.getBeanType(), Private.class);
    }

}
