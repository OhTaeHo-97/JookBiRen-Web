package com.ablez.jookbiren.log;

import static ch.qos.logback.classic.Level.DEBUG;
import static ch.qos.logback.classic.Level.ERROR;
import static ch.qos.logback.classic.Level.OFF;

import ch.qos.logback.classic.Level;
import ch.qos.logback.classic.Logger;
import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.encoder.PatternLayoutEncoder;
import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.rolling.RollingFileAppender;
import ch.qos.logback.core.rolling.RollingPolicy;
import ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy;
import ch.qos.logback.core.util.FileSize;
import org.slf4j.LoggerFactory;

public class LogbackRolling {
    private final LoggerContext loggerContext = (LoggerContext) LoggerFactory.getILoggerFactory();

    private final String ROLLING_PATTERN = "%d{yyyy-MM-dd HH:mm:ss.SSS} %logger{5} - %msg %n";
    private final String FILE_NAME = "~/logs/application.log";
    private final String LOG_NAME_PATTERN = "./logs/application-%d{yyyy-MM-dd}.%i.log";
    private final String MAX_FILE_SIZE = "10MB";
    private final String TOTAL_SIZE = "1100MB";
    private final int MAX_HISTORY = 100;

    private RollingFileAppender<ILoggingEvent> rollingAppender;

    public void logConfig() {
        rollingAppender = getLogAppender();
        createLoggers();
    }

    private void createLoggers() {
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
        logger.addAppender(rollingAppender);
    }

    private RollingFileAppender<ILoggingEvent> getLogAppender() {
        final String appendName = "ROLLING_LOG_FILE";
        PatternLayoutEncoder rollingLogEncoder = createLogEncoder(ROLLING_PATTERN);
        RollingFileAppender<ILoggingEvent> rollingFileAppender = createLogAppender(appendName, rollingLogEncoder);
        SizeAndTimeBasedRollingPolicy rollingPolicy = createLogRollingPolicy(rollingFileAppender);

        rollingFileAppender.setRollingPolicy(rollingPolicy);
        rollingFileAppender.start();

        return rollingFileAppender;
    }

    private SizeAndTimeBasedRollingPolicy<RollingPolicy> createLogRollingPolicy(
            RollingFileAppender<ILoggingEvent> rollingLogAppender) {
        SizeAndTimeBasedRollingPolicy<RollingPolicy> policy = new SizeAndTimeBasedRollingPolicy<>();
        policy.setContext(loggerContext);
        policy.setParent(rollingLogAppender);
        policy.setFileNamePattern(LOG_NAME_PATTERN);
        policy.setMaxHistory(MAX_HISTORY);
        policy.setTotalSizeCap(FileSize.valueOf(TOTAL_SIZE));
        policy.setMaxFileSize(FileSize.valueOf(MAX_FILE_SIZE));
        policy.start();

        return policy;
    }

    private PatternLayoutEncoder createLogEncoder(String pattern) {
        PatternLayoutEncoder encoder = new PatternLayoutEncoder();
        encoder.setContext(loggerContext);
        encoder.setPattern(pattern);
        encoder.start();

        return encoder;
    }

    private RollingFileAppender<ILoggingEvent> createLogAppender(String appendName,
                                                                 PatternLayoutEncoder rollingLogEncoder) {
        RollingFileAppender<ILoggingEvent> logRollingAppender = new RollingFileAppender<>();
        logRollingAppender.setName(appendName);
        logRollingAppender.setContext(loggerContext);
        logRollingAppender.setFile(FILE_NAME);
        logRollingAppender.setEncoder(rollingLogEncoder);

        return logRollingAppender;
    }
}
