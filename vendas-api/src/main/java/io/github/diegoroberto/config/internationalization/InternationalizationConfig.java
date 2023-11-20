package io.github.diegoroberto.config.internationalization;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

import java.util.Locale;

@Configuration
public class InternationalizationConfig {

    @Bean
    public MessageSource messageSource(){
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:messages");
        ms.setDefaultEncoding("ISO-8859-1");
        ms.setDefaultLocale(contextLocale());
        return ms;
    }

    public String getMessage(String msg) {
        return messageSource().getMessage(msg, null, contextLocale());
    }

    @Bean
    public Locale contextLocale(){
            //getDefault pega do sistema
        return Locale.getDefault();
    }

    public void setLocale(Locale newLocale){
            //para alterar o idioma, alterar o param setado
        Locale.setDefault(newLocale);
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactoryBean() {
        LocalValidatorFactoryBean lvfb = new LocalValidatorFactoryBean();
        lvfb.setValidationMessageSource(messageSource());
        return lvfb;
    }

}
