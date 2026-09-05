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

import com.github.grossopa.selenium.recorder.model.PageElementModel;
import com.github.grossopa.selenium.recorder.model.PageModel;
import com.github.grossopa.selenium.recorder.model.ScannedElement;
import com.github.grossopa.selenium.recorder.page.PageIdentificationStrategy;
import com.github.grossopa.selenium.recorder.session.RecorderSession;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.UserInterruptException;

import java.io.PrintStream;
import java.nio.file.Path;
import java.util.List;
import java.util.Locale;
import java.util.stream.Collectors;

import static java.util.Objects.requireNonNull;

/**
 * The interactive REPL of the recorder CLI reading user commands via JLine and executing them against the
 * {@link RecorderSession}. Supported commands: {@code scan}, {@code select <n> <fieldName>}, {@code pages},
 * {@code page new <name>}, {@code page use <name>}, {@code strategy <FQCN>}, {@code status}, {@code generate},
 * {@code help} and {@code quit}.
 *
 * @author Jack Yin
 * @since 1.15
 * @see RecorderSession
 */
public class Repl {

    /**
     * The prompt shown when waiting for the next command.
     */
    public static final String PROMPT = "hamster-recorder> ";

    private final RecorderSession session;
    private final LineReader lineReader;
    private final PrintStream out;

    /**
     * Constructs an instance with the recorder session, the JLine line reader and the output stream.
     *
     * @param session the recorder session to execute the commands against, must not be null
     * @param lineReader the JLine line reader for reading user input, must not be null
     * @param out the output stream for printing results, must not be null
     */
    public Repl(RecorderSession session, LineReader lineReader, PrintStream out) {
        this.session = requireNonNull(session);
        this.lineReader = requireNonNull(lineReader);
        this.out = requireNonNull(out);
    }

    /**
     * Runs the REPL loop until the user quits or the input ends.
     */
    public void run() {
        out.println("Hamster Selenium Recorder - type 'help' for commands, 'quit' to exit.");
        while (true) {
            String line;
            try {
                line = lineReader.readLine(PROMPT);
            } catch (UserInterruptException exception) {
                continue;
            } catch (EndOfFileException exception) {
                break;
            }
            if (line == null || !execute(line.trim())) {
                break;
            }
        }
    }

    /**
     * Executes one command line against the recorder session.
     *
     * @param line the command line to execute
     * @return true if the REPL should continue, false if it should stop
     */
    public boolean execute(String line) {
        if (line.isBlank()) {
            return true;
        }
        String[] parts = line.split("\\s+");
        try {
            switch (parts[0].toLowerCase(Locale.ROOT)) {
                case "scan" -> scan();
                case "select" -> select(parts);
                case "pages" -> pages();
                case "page" -> page(parts);
                case "strategy" -> strategy(parts);
                case "status" -> status();
                case "generate" -> generate();
                case "help" -> help();
                case "quit", "exit" -> {
                    return false;
                }
                default -> out.println("Unknown command: " + parts[0] + ", type 'help' for usage.");
            }
        } catch (RuntimeException exception) {
            out.println("Error: " + exception.getMessage());
        }
        return true;
    }

    private void scan() {
        List<ScannedElement> elements = session.scan();
        out.println("Scanned " + elements.size() + " elements:");
        for (ScannedElement element : elements) {
            out.println(formatScanned(element));
        }
    }

    private String formatScanned(ScannedElement element) {
        String type = element.getDetectedComponent() != null ? element.getDetectedComponent().getTypeName()
                : element.getTagName();
        String attributes = element.getAttributes().entrySet().stream()
                .map(entry -> entry.getKey() + "=" + entry.getValue()).collect(Collectors.joining(", "));
        String locator = element.getBestLocator() != null ? element.getBestLocator().getDescription() : "-";
        return String.format(Locale.ROOT, "  [%2d] %-14s %-30s %-20s %s", element.getIndex(), type,
                abbreviate(attributes, 30), abbreviate(element.getText(), 20), locator);
    }

    private void select(String[] parts) {
        if (parts.length < 3) {
            out.println("Usage: select <index> <fieldName>");
            return;
        }
        int index = Integer.parseInt(parts[1]);
        PageElementModel selected = session.select(index, parts[2]);
        String type = selected.getDetectedComponent() != null ? selected.getDetectedComponent().getTypeName()
                : "WebComponent";
        out.println("Selected element " + index + " as '" + parts[2] + "' (" + type + ") into page '"
                + session.getCurrentPage().getName() + "'.");
    }

    private void pages() {
        List<PageModel> pages = session.getPages();
        if (pages.isEmpty()) {
            out.println("No pages collected yet.");
            return;
        }
        for (PageModel page : pages) {
            String marker = page == session.getCurrentPage() ? "* " : "  ";
            out.println(marker + page.getName() + " (key=" + page.getPageKey() + ", elements="
                    + page.getElements().size() + ")");
        }
    }

    private void page(String[] parts) {
        if (parts.length < 3 || !"new".equals(parts[1]) && !"use".equals(parts[1])) {
            out.println("Usage: page new <name> | page use <name>");
            return;
        }
        if ("new".equals(parts[1])) {
            PageModel page = session.newPage(parts[2]);
            out.println("Created new page '" + page.getName() + "' (key=" + page.getPageKey() + ").");
        } else {
            PageModel page = session.usePage(parts[2]);
            out.println("Switched to page '" + page.getName() + "'.");
        }
    }

    private void strategy(String[] parts) {
        if (parts.length < 2) {
            out.println("Usage: strategy <fully qualified class name of PageIdentificationStrategy>");
            return;
        }
        Object instance;
        try {
            instance = Class.forName(parts[1]).getDeclaredConstructor().newInstance();
        } catch (ReflectiveOperationException | RuntimeException exception) {
            out.println("Error: failed to load strategy class " + parts[1] + ": " + exception.getMessage());
            return;
        }
        if (!(instance instanceof PageIdentificationStrategy strategy)) {
            out.println("Class " + parts[1] + " does not implement PageIdentificationStrategy.");
            return;
        }
        session.setPageStrategy(strategy);
        out.println("Page identification strategy set to " + parts[1] + ".");
    }

    private void status() {
        out.println("URL: " + session.getDriver().getCurrentUrl());
        PageModel current = session.getCurrentPage();
        out.println("Current page: " + (current != null ? current.getName() : "-"));
        out.println("Pages: " + session.getPages().size() + ", scanned elements: "
                + session.getScannedElements().size());
    }

    private void generate() {
        List<Path> files = session.generate();
        if (files.isEmpty()) {
            out.println("No pages to generate.");
            return;
        }
        out.println("Generated " + files.size() + " page object(s):");
        for (Path file : files) {
            out.println("  " + file);
        }
    }

    private void help() {
        out.println("Commands:");
        out.println("  scan                        scan the current page and list selectable elements");
        out.println("  select <n> <fieldName>      add the scanned element n to the current page");
        out.println("  pages                       list all collected pages");
        out.println("  page new <name>             start a new page with the given name");
        out.println("  page use <name>             switch to an existing page");
        out.println("  strategy <FQCN>             set a custom page identification strategy");
        out.println("  status                      show the current url, page and counts");
        out.println("  generate                    generate the page object source code");
        out.println("  help                        show this help");
        out.println("  quit                        exit the recorder");
    }

    private String abbreviate(String value, int maxLength) {
        if (value == null) {
            return "-";
        }
        return value.length() <= maxLength ? value : value.substring(0, maxLength - 3) + "...";
    }
}
