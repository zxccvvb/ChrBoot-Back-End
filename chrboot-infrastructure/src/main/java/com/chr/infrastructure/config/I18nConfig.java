package com.chr.infrastructure.config;

import com.chr.common.utils.i18n.MessageUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Configuration;

@Configuration
public class I18nConfig implements InitializingBean {

    @Autowired
    private MessageSource messageSource;

    @Override
    public void afterPropertiesSet() {
        MessageUtils.setMessageSource(messageSource);
    }

}