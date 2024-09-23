package com.hotelking.global.resolver;

import com.hotelking.domain.common.AppUser;
import com.hotelking.global.util.AuthorizationExtractor;
import com.hotelking.global.util.TokenProvider;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

@Component
public class AppUserArgumentResolver implements HandlerMethodArgumentResolver {

  private final TokenProvider tokenProvider;

  public AppUserArgumentResolver(TokenProvider tokenProvider) {
    this.tokenProvider = tokenProvider;
  }

  @Override
  public boolean supportsParameter(MethodParameter parameter) {
    boolean hasLoginAnnotation = parameter.hasParameterAnnotation(Login.class);
    boolean hasAppUserType = AppUser.class.isAssignableFrom(parameter.getParameterType());
    return hasLoginAnnotation && hasAppUserType;
  }

  @Override
  public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer,
      NativeWebRequest webRequest, WebDataBinderFactory binderFactory){
    HttpServletRequest request = webRequest.getNativeRequest(HttpServletRequest.class);
    final String token = AuthorizationExtractor.extract(request);
    return new AppUser(tokenProvider.parseUserId(token));
  }
}
