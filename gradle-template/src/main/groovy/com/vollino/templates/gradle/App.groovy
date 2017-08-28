package com.vollino.templates.gradle

import org.slf4j.Logger
import org.slf4j.LoggerFactory

class App {

	static final Logger LOGGER = LoggerFactory.getLogger(App.class)

	static void main(String[] args) {
		LOGGER.info("Args: $args")
	}
}

