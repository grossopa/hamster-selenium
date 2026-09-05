/*
 * Copyright © 2021 the original author or authors.
 *
 * Licensed under the The MIT License (MIT) (the "License");
 *  You may obtain a copy of the License at
 *
 *         https://mit-license.org/
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software
 * and associated documentation files (the "Software"), to deal in the Software without
 * restriction, including without limitation the rights to use, copy, modify, merge, publish,
 * distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or
 * substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING
 * BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND
 * NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM,
 * DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package com.github.grossopa.selenium.recorder.cli;

import com.github.grossopa.selenium.component.mui.MuiVersion;
import com.github.grossopa.selenium.recorder.config.ComponentFramework;
import com.github.grossopa.selenium.recorder.config.RecorderConfig;
import com.github.grossopa.selenium.recorder.monitor.RecorderEventType;
import com.github.grossopa.selenium.recorder.session.RecorderSession;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;
import org.openqa.selenium.WebDriver;
import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Callable;

/**
 * The picocli entry point of the recorder CLI. It parses the command line options, launches the browser, starts the
 * {@link RecorderSession} and hands over to the interactive {@link Repl}.
 *
 * @author Jack Yin
 * @since 1.15
 * @see Repl
 * @see BrowserFactory
 */
@Command(name = "hamster-recorder", mixinStandardHelpOptions = true, version = "1.15",
        description = "Records page elements at runtime and generates hamster selenium page objects.")
public class RecorderCommand implements Callable<Integer> {

    @Option(names = "--browser", description = "the browser to launch: chrome, edge or firefox (default: chrome)")
    private String browser = "chrome";

    @Option(names = "--url", description = "the initial url to open")
    private String url;

    @Option(names = "--framework", description = "the component framework: mui or html (default: mui)")
    private String framework = "mui";

    @Option(names = "--mui-version", description = "the Material UI version: v4 or v5 (default: v4)")
    private String muiVersion = "v4";

    @Option(names = { "-a", "--attributes" }, split = ",",
            description = "additional key attributes to scan, e.g. data-testid")
    private List<String> attributes = new ArrayList<>();

    @Option(names = { "-s", "--selector" }, split = ",",
            description = "additional CSS selectors to scan, e.g. table,form,h1")
    private List<String> selectors = new ArrayList<>();

    @Option(names = "--output-dir", description = "the output directory of the generated page objects")
    private String outputDir = "generated-pageobjects";

    @Option(names = "--package", description = "the base package of the generated page objects")
    private String basePackage = RecorderConfig.DEFAULT_BASE_PACKAGE;

    /**
     * The main entry of the recorder CLI.
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        System.exit(new CommandLine(new RecorderCommand()).execute(args));
    }

    @Override
    public Integer call() {
        RecorderConfig config;
        try {
            config = buildConfig();
        } catch (RuntimeException exception) {
            System.err.println("Invalid options: " + exception.getMessage());
            return 2;
        }
        WebDriver driver = BrowserFactory.create(browser);
        try (RecorderSession session = new RecorderSession(driver, config)) {
            session.addEventListener(event -> {
                if (event.getType() == RecorderEventType.PAGE_CHANGED && session.getCurrentPage() != null) {
                    System.out.println("[recorder] url changed to " + event.getUrl() + ", current page: "
                            + session.getCurrentPage().getName());
                }
            });
            if (url != null && !url.isBlank()) {
                session.getDriver().get(url);
            }
            try (Terminal terminal = TerminalBuilder.builder().system(true).build()) {
                LineReader lineReader = LineReaderBuilder.builder().terminal(terminal).build();
                new Repl(session, lineReader, System.out).run();
            }
        } catch (IOException exception) {
            System.err.println("Failed to run the recorder: " + exception.getMessage());
            return 1;
        }
        return 0;
    }

    /**
     * Builds the {@link RecorderConfig} from the parsed command line options.
     *
     * @return the built recorder configuration
     */
    RecorderConfig buildConfig() {
        RecorderConfig.RecorderConfigBuilder builder = RecorderConfig.builder()
                .framework(ComponentFramework.of(framework))
                .muiVersion(MuiVersion.valueOf(muiVersion.toUpperCase(Locale.ROOT)))
                .outputDir(java.nio.file.Path.of(outputDir)).basePackage(basePackage);
        attributes.forEach(builder::keyAttribute);
        selectors.forEach(builder::extraSelector);
        return builder.build();
    }
}
