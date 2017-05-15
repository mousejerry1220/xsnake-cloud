package org.xsnake.miss.exception;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.SimpleMappingExceptionResolver;

public class ExceptionResolver extends SimpleMappingExceptionResolver {

	@Override
	public ModelAndView doResolveException(HttpServletRequest request, HttpServletResponse response, Object handler,
			Exception ex) {
		if (request.getHeader("accept").indexOf("application/json") > -1
				|| (request.getContentType() != null && (request.getContentType().indexOf("application/json") > -1
				|| request.getContentType().indexOf("application/x-www-form-urlencoded") > -1))
				|| "JSON".equalsIgnoreCase(request.getParameter("_ContentType"))
				|| "JSON".equalsIgnoreCase(request.getParameter("_contentType"))
				|| "JSON".equalsIgnoreCase(request.getParameter("_contenttype"))
				|| (request.getContentType() != null && request.getContentType().indexOf("multipart/form-data") > -1)) {
			try {
				printErrorMessage(response, ex.getMessage());
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				String error = "未定义错误";
				response.sendError(500);
				request.setAttribute("error", error);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return null;
	}

	private void printErrorMessage(HttpServletResponse response, String messages) throws IOException {
		PrintWriter writer = null;
		try {
			response.setStatus(500);
			response.setContentType("application/json");
			response.setCharacterEncoding("utf-8");
			writer = response.getWriter();
			writer.write(messages);
			writer.flush();
		} finally {
			if (writer != null) {
				writer.close();
			}
		}
	}

}
