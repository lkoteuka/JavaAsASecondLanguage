package io.github.javaasasecondlanguage.homework02.webserver;

public class MyLogger implements Logger {
    java.util.logging.Logger log =
            java.util.logging.Logger.getLogger(getClass().getSimpleName());

    @Override
    public void info(String msg) {
        this.log.info(msg);
    }
}
