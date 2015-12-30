package zinjvi;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;
import org.springframework.data.rest.webmvc.config.RepositoryRestMvcConfiguration;
import org.springframework.http.MediaType;
import zinjvi.model.Topic;

/**
 * Created by Vitaliy on 10/23/2015.
 */
@Configuration
//@Import(CustomRepositoryRestMvcConfiguration.class)
public class CustomRepositoryRestMvcConfiguration extends RepositoryRestMvcConfiguration {

    @Override
    protected void configureRepositoryRestConfiguration(RepositoryRestConfiguration config) {
        config.exposeIdsFor(Topic.class);
        config.setDefaultMediaType(MediaType.APPLICATION_JSON);
    }
}
