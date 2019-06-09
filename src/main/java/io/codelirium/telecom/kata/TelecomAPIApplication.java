package io.codelirium.telecom.kata;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;

import static java.lang.Boolean.FALSE;


@SpringBootApplication
public class TelecomAPIApplication extends SpringBootServletInitializer {


	@Override
	protected SpringApplicationBuilder configure(final SpringApplicationBuilder application) {

		return application.sources(TelecomAPIApplication.class);

	}


	public static void main(final String ... args) {

		new SpringApplicationBuilder(TelecomAPIApplication.class)
				.logStartupInfo(FALSE)
				.run(args);

	}
}
