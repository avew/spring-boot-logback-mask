package io.github.avew.mask;

import ch.qos.logback.classic.PatternLayout;
import ch.qos.logback.classic.spi.ILoggingEvent;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MaskingLog extends PatternLayout {
    private String patternsProperty;
    private Optional<Pattern> pattern;

    public MaskingLog() {
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
        StringBuilder message = new StringBuilder(super.doLayout(event));
        if (this.pattern.isPresent()) {
            List<String> maskPatterns = (List) Arrays.stream(this.patternsProperty.split(",")).toList().stream().map((s) -> {
                return s + "=([\\w]+)";
            }).collect(Collectors.toList());
            Pattern multilinePattern = Pattern.compile(String.join("|", maskPatterns), 8);
            Matcher matcher = multilinePattern.matcher(message);

            while (matcher.find()) {
                IntStream.rangeClosed(1, matcher.groupCount()).forEach((group) -> {
                    if (matcher.group(group) != null) {
                        IntStream.range(matcher.start(group), matcher.end(group)).forEach((i) -> {
                            message.setCharAt(i, '*');
                        });
                    }

                });
            }
        }

        return message.toString();
    }

    public String getPatternsProperty() {
        return this.patternsProperty;
    }
}
