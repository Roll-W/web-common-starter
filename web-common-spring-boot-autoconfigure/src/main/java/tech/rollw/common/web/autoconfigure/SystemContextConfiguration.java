/*
 * Copyright (C) 2023 RollW
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package tech.rollw.common.web.autoconfigure;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import tech.rollw.common.web.components.ContextInitializeFilter;
import tech.rollw.common.web.system.ContextThreadAware;
import tech.rollw.common.web.system.ThreadLocalContextFactory;
import tech.rollw.common.web.system.paged.PageableContext;

/**
 * @author RollW
 */
@Configuration
@EnableConfigurationProperties({WebCommonProperties.class, ParameterProperties.class})
public class SystemContextConfiguration {

    @Bean
    @ConditionalOnMissingBean(value = PageableContext.class,
            parameterizedContainer = ContextThreadAware.class)
    public ContextThreadAware<PageableContext> pageableContextFactory() {
        return new ThreadLocalContextFactory<>();
    }

    @Bean
    @ConditionalOnMissingBean(ContextInitializeFilter.class)
    @ConditionalOnProperty(prefix = "web-common", name = "context-initialize-filter", havingValue = "true")
    public ContextInitializeFilter contextInitializeFilter(
            ContextThreadAware<PageableContext> pageableContextFactory,
            ParameterProperties parameterProperties
    ) {
        return new ContextInitializeFilter(pageableContextFactory, parameterProperties);
    }
}
