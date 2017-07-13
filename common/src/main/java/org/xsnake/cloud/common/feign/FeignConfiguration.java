package org.xsnake.cloud.common.feign;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import feign.codec.ErrorDecoder;

@Configuration
public class FeignConfiguration{
	@Bean
	public ErrorDecoder errorDecoder(){
		return new UserErrorDecoder();
	}

}
