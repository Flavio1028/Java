package br.com.agendamento;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.sun.istack.Nullable;

public class AuthorizationInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {

		if (request.getRequestURI().contains("login") || request.getRequestURI().contains("recuperar")
				|| request.getRequestURI().contains("/formulario/validar/")
				|| request.getRequestURI().contains("formulario/reprovar") || request.getRequestURI().contains("/carga")
				|| request.getRequestURI().contains("swagger")
				|| request.getRequestURI().equalsIgnoreCase("/dfsfieldservicedesk/service/")
				|| request.getRequestURI().equalsIgnoreCase("/dfsfieldservicedesk/service/csrf")
				|| request.getRequestURI().equalsIgnoreCase("/dfsfieldservicedesk/service/error")) {
			return Boolean.TRUE;
		}

		return Boolean.FALSE;
	}

	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable ModelAndView modelAndView) throws Exception {
	}

	@Override
	public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler,
			@Nullable Exception ex) throws Exception {
	}

}