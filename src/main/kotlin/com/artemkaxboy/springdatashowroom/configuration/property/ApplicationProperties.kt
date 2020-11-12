package com.artemkaxboy.springdatashowroom.configuration.property

import org.hibernate.validator.constraints.URL
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank

private const val DEFAULT_APPLICATION_NAME = "Spring Boot Application"

@Validated
@ConfigurationProperties("application")
class ApplicationProperties {

    /** Application version. */
    @NotBlank
    var version: String = "debug"

    /** Application name. */
    @NotBlank
    var name: String = DEFAULT_APPLICATION_NAME

    /** Application description. */
    var description: String? = null

    /** Developer contact. */
    val contact: Contact = Contact()

    class Contact {

        /** Developer name. */
        @NotBlank
        var name: String? = null

        /** Developer url. */
        @URL
        var url: String? = null

        /** Developer email. */
        @Email
        var email: String? = null
    }
}
