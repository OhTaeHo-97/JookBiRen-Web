package com.ablez.jookbiren.log;

import static ch.qos.logback.classic.Level.DEBUG;
import static ch.qos.logback.classic.Level.INFO;
import static ch.qos.logback.classic.Level.OFF;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.ConsoleAppender;
import org.slf4j.LoggerFactory;

public class LogbackConsole {
    private final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();
    private final String CONSOLE_PATTERN = "%d{yyyy-MM-dd HH:mm:ss.SSS} %Magenta([%thread]) %highlight([%-3level]) %logger{5} - %msg %n";

    private ConsoleAppender<ILoggingEvent> consoleAppender;

    public void logConfig() {
        loggerContext.reset();
        consoleAppender = getLogAppender();
        createLoggers();
    }

    private void createLoggers() {
        createLogger("root", INFO, true);
        createLogger("jdbc", OFF, false);
        createLogger("jdbc.sqlonly", DEBUG, false);
        createLogger("jdbc.sqltiming", OFF, false);
        createLogger("org.hibernate.SQL", DEBUG, false);

        // 에러 핸들러
        createLogger("com.ablez.jookbiren.advice", DEBUG, false);

        // 시큐리티
        createLogger("com.ablez.jookbiren.security.handler", DEBUG, false);
        createLogger("com.ablez.jookbiren.security.filter", DEBUG, false);
        createLogger("com.ablez.jookbiren.security.userdetails", DEBUG, false);

        // user info
        createLogger("com.ablez.jookbiren.userinfo.service", DEBUG, false);

        // 에피소드 1
        createLogger("com.ablez.jookbiren.episode1.answer.controller", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode1.answer.service", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode1.hint.service", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode1.quiz.controller", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode1.quiz.service", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode1.user.controller", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode1.user.service", DEBUG, false);

        // 에피소드 2
        createLogger("com.ablez.jookbiren.episode2.answer.controller", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode2.answer.service", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode2.hint.service", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode2.quiz.controller", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode2.quiz.service", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode2.user.controller", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode2.user.service", DEBUG, false);

        // 에피소드 3
        createLogger("com.ablez.jookbiren.episode3.answer.controller", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode3.answer.service", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode3.hint.service", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode3.quiz.controller", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode3.quiz.service", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode3.user.controller", DEBUG, false);
        createLogger("com.ablez.jookbiren.episode3.user.service", DEBUG, false);
    }

    private void createLogger(String loggerName, Level logLevel, Boolean additive) {
        Logger logger = loggerContext.getLogger(loggerName);
        logger.setAdditive(additive);
        logger.setLevel(logLevel);

        logger.addAppender(consoleAppender);
    }

    private ConsoleAppender<ILoggingEvent> getLogAppender() {
        final String appendName = "STDOUT";
        PatternLayoutEncoder consoleLogEncoder = createLogEncoder(CONSOLE_PATTERN);
        ConsoleAppender<ILoggingEvent> logConsoleAppender = createLogAppender(appendName, consoleLogEncoder);
        logConsoleAppender.start();

        return logConsoleAppender;
    }

    private PatternLayoutEncoder createLogEncoder(String pattern) {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern(pattern);
        encoder.start();

        return encoder;
    }

    private ConsoleAppender<ILoggingEvent> createLogAppender(String appendName,
                                                             PatternLayoutEncoder consoleLogEncoder) {
        ConsoleAppender<ILoggingEvent> logConsoleAppender = new ConsoleAppender<>();
        logConsoleAppender.setName(appendName);
        logConsoleAppender.setContext(loggerContext);
        logConsoleAppender.setEncoder(consoleLogEncoder);

        return logConsoleAppender;
    }
}
