package org.xsnake.cloud.common.feign;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.util.Map;

import org.springframework.boot.json.GsonJsonParser;

import feign.Response;
import feign.codec.ErrorDecoder;

public class UserErrorDecoder implements ErrorDecoder {
	
	GsonJsonParser gsonJsonParser = new GsonJsonParser();
	
	public Exception decode(String methodKey, Response response) {
		InputStream in = null;
		byte[] buff = null;
		try {
			in = response.body().asInputStream();
			buff = new byte[in.available()];
			in.read(buff);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		String errorInfo = new String(buff);
		Map<String, Object> errorMap = gsonJsonParser.parseMap(errorInfo);
		String message = (String) errorMap.get("message");
		String exception = (String) errorMap.get("exception");
		Class<?> clazz = null;
		try {
			clazz = Class.forName(exception);
		} catch (ClassNotFoundException e) {
			clazz = RuntimeException.class;
		}
		Exception resultException = null;
		try {
			Constructor<?> constructor = clazz.getConstructor(String.class);
			resultException = (Exception)constructor.newInstance(message);
		} catch (Exception e) {
			resultException = new RuntimeException(message);
		}
		return resultException;
	}
}
