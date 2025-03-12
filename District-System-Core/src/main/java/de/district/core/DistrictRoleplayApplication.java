package de.district.core;

import de.district.core.config.PluginConfiguration;
import de.district.core.economy.config.BankConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * The {@code DistrictRoleplayApplication} class serves as the main entry point for the Spring Boot
 * application that powers the District-Roleplay plugin. This class is responsible for bootstrapping
 * the application context, configuring Spring components, and enabling configuration properties
 * for the plugin.
 *
 * <p>The class is annotated with {@link SpringBootApplication}, which is a convenience annotation
 * that adds all of the following:</p>
 * <ul>
 *   <li>{@link org.springframework.boot.autoconfigure.EnableAutoConfiguration EnableAutoConfiguration} - enables Spring Boot's auto-configuration mechanism.</li>
 *   <li>{@link org.springframework.context.annotation.ComponentScan ComponentScan} - scans for Spring components in the package and its subpackages.</li>
 *   <li>{@link org.springframework.context.annotation.Configuration Configuration} - marks this class as a source of bean definitions.</li>
 * </ul>
 *
 * <p>The {@link EnableConfigurationProperties} annotation is used to enable support for
 * configuration properties, specifically for the {@link PluginConfiguration} class. This
 * ensures that the properties defined in external configuration files (e.g., `application.yml`)
 * are bound to the fields of the {@code PluginConfiguration} class.</p>
 *
 * <p>As the entry point of the Spring Boot application, this class will be used to initialize
 * and configure the Spring context when the plugin is loaded into the Minecraft server.</p>
 *
 * @author Erik Pförtner
 * @version 1.0.0
 * @see SpringBootApplication
 * @see EnableConfigurationProperties
 * @since 1.0.0
 */
@SpringBootApplication
@EnableConfigurationProperties({
        PluginConfiguration.class,
        BankConfiguration.class
})
public class DistrictRoleplayApplication {
}
