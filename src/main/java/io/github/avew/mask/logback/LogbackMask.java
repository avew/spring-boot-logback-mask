package io.github.avew.mask.logback;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;
import io.github.avew.mask.MaskWord;

import java.util.Optional;
import java.util.regex.Pattern;

public class LogbackMask extends PatternLayout {
    private String patternsProperty;
    private Optional<Pattern> pattern;

    public LogbackMask() {
    }

    public void setPatternsProperty(String patternsProperty) {
        this.patternsProperty = patternsProperty;
        if (this.patternsProperty != null) {
            this.pattern = Optional.of(Pattern.compile(patternsProperty, 8));
        } else {
            this.pattern = Optional.empty();
        }

    }

    public String doLayout(ILoggingEvent event) {
        MaskWord maskWord = new MaskWord(this.patternsProperty, "=", "([\\w]+)");
        return maskWord.mask(super.doLayout(event));
    }

}
