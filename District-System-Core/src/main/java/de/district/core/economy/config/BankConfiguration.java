package de.district.core.economy.config;

import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * The {@code BankConfiguration} class is a Spring component that holds the configuration properties
 * for the bank within the plugin. This class uses the prefix "plugin.bank" to map properties from the
 * application's configuration file.
 *
 * <p>The class is annotated with Lombok annotations to generate boilerplate code such as getters, setters,
 * and constructors. It also includes the {@code @ConfigurationProperties} annotation to bind the properties
 * from the configuration file.</p>
 *
 * @since 1.0.0
 * @author Erik Pförtner
 */
@Getter
@Setter
@Component
@NoArgsConstructor
@Data
@ConfigurationProperties(prefix = "plugin.bank")
public class BankConfiguration {
    private String name;
}
