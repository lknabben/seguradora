package br.com.seguradora.comum;

import org.apache.logging.log4j.util.Strings;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Component
public class GetMessageProperties {
    private final MessageSource messageSource;

    @Autowired
    public GetMessageProperties(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public String getMensagem(String mensagem) {
        if (Strings.isEmpty(mensagem)) return mensagem;
        try {
            return messageSource.getMessage(mensagem, null, LocaleContextHolder.getLocale());
        } catch (NoSuchMessageException e) {
            return mensagem;
        }
    }
}