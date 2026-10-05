package com.n3.mebe.shared.config;

import com.n3.mebe.shared.enums.LabeledEnum;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.convert.converter.ConverterFactory;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    /**
     * Cho phép @RequestParam / @PathVariable kiểu LabeledEnum nhận label,
     * vd. ?status=ban -> UserStatus.BANNED.
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverterFactory(new StringToLabeledEnumConverterFactory());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    private static class StringToLabeledEnumConverterFactory implements ConverterFactory<String, LabeledEnum> {
        @Override
        public <T extends LabeledEnum> Converter<String, T> getConverter(Class<T> targetType) {
            return source -> (T) LabeledEnum.fromLabel((Class) targetType, source);
        }
    }
}
