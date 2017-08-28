package com.vollino.templates.gradle

import spock.lang.Specification

class AppTest extends Specification {

	def "should run the main method"() {
		when:
		App.main(['-c', 'src/test/resources/config.config'] as String[])

		then: "No exception thrown"
	}
}
