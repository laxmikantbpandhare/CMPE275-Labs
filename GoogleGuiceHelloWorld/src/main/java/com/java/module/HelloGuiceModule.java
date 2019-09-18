
package com.java.module;

import com.google.inject.AbstractModule;
import com.java.service.HelloGuiceService;
import com.java.service.HelloGuiceServiceImpl;

public class HelloGuiceModule extends AbstractModule {

	protected void configure() {
		// add configuration logic here
		bind(HelloGuiceService.class).to(HelloGuiceServiceImpl.class);

	}
}
