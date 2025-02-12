package io.github.avew.mask;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
public class MaskWord {
    private String patternsProperty;
    private String separator;
    private String regex;

    public MaskWord(String patternsProperty, String separator,String regex) {
        this.patternsProperty = patternsProperty;
        this.separator = separator;
        this.regex = regex;
    }

    public String mask(String data) {
        StringBuilder message = new StringBuilder(data);
        List<String> maskPatterns = Arrays
                .stream(this.patternsProperty
                        .split(",")).toList()
                .stream()
                .map((s) -> s + separator + regex)
                .collect(Collectors.toList());
        log.debug("Mask patterns: {}", maskPatterns);
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
        return message.toString();
    }
}
