package io.github.javaasasecondlanguage.homework02.webserver;

public class MyLogger implements Logger {
    java.util.logging.Logger log =
            java.util.logging.Logger.getLogger(getClass().getSimpleName());

    @Override
    public void info(String msg) {
        this.log.info(msg);
    }

    public void warning(String msg) {
        this.log.warning(msg);
    }

    public void fine(String msg) {
        this.log.fine(msg);
    }

    public void severe(String msg) {
        this.log.severe(msg);
    }
}
