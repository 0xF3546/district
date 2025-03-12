package de.district.core.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The {@code PluginConfiguration} class is a Spring component that holds configuration properties
 * for the District-Roleplay plugin.
 * These properties are loaded from the application's external
 * configuration sources (such as `application.yml` or `application.properties`) and are mapped
 * to this class using the specified prefix.
 *
 * <p>The class uses Lombok annotations {@link Getter} and {@link Setter} to automatically
 * generate getter and setter methods for its fields.</p>
 *
 * <p>The configuration is prefixed with "plugin", meaning that any properties defined
 * in the configuration file should start with "plugin.". For example:</p>
 *
 * <pre>
 * plugin:
 *   debug: true
 * </pre>
 *
 * <p>This configuration class is typically used to control various aspects of the plugin's
 * behavior, such as enabling or disabling debug mode.</p>
 *
 * @see ConfigurationProperties
 * @see Component
 * @since 1.0.0
 * @author Erik Pförtner
 */
@Getter
@Setter
@Component
@ConfigurationProperties(prefix = "plugin")
public class PluginConfiguration {

    /**
     * A flag indicating whether debug mode is enabled for the plugin.
     *
     * <p>When set to {@code true}, the plugin will operate in debug mode, providing
     * more detailed logs and potentially enabling additional diagnostic features.</p>
     */
    private boolean debug;
}
