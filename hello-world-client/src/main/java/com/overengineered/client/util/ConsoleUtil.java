package com.overengineered.client.util;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import org.fusesource.jansi.Ansi;

/**
 * Utility class for formatting console output.
 */
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public final class ConsoleUtil {

    /**
     * Format text with a specified color.
     *
     * @param text The text to format
     * @param color The color to use
     * @return The formatted text
     */
    public static String colorize(String text, Ansi.Color color) {
        return Ansi.ansi().fg(color).a(text).reset().toString();
    }

    /**
     * Format text with bold and a specified color.
     *
     * @param text The text to format
     * @param color The color to use
     * @return The formatted text
     */
    public static String bold(String text, Ansi.Color color) {
        return Ansi.ansi().bold().fg(color).a(text).reset().toString();
    }

    /**
     * Format text with italic and a specified color.
     *
     * @param text The text to format
     * @param color The color to use
     * @return The formatted text
     */
    public static String italic(String text, Ansi.Color color) {
        return Ansi.ansi().a(Ansi.Attribute.ITALIC).fg(color).a(text).reset().toString();
    }

    /**
     * Format text with underline and a specified color.
     *
     * @param text The text to format
     * @param color The color to use
     * @return The formatted text
     */
    public static String underline(String text, Ansi.Color color) {
        return Ansi.ansi().a(Ansi.Attribute.UNDERLINE).fg(color).a(text).reset().toString();
    }

    /**
     * Print a horizontal rule.
     *
     * @param color The color of the rule
     * @param width The width of the rule
     * @return The horizontal rule
     */
    public static String horizontalRule(Ansi.Color color, int width) {
        return colorize("─".repeat(width), color);
    }

    /**
     * Print a box with a title.
     *
     * @param title The title of the box
     * @param content The content of the box
     * @param color The color of the box
     * @param width The width of the box
     * @return The formatted box
     */
    public static String box(String title, String content, Ansi.Color color, int width) {
        StringBuilder sb = new StringBuilder();
        sb.append(colorize("┌" + "─".repeat(width - 2) + "┐\n", color));
        sb.append(colorize("│ ", color))
          .append(bold(centerText(title, width - 4), color))
          .append(colorize(" │\n", color));
        sb.append(colorize("├" + "─".repeat(width - 2) + "┤\n", color));
        
        for (String line : content.split("\n")) {
            sb.append(colorize("│ ", color))
              .append(line)
              .append(" ".repeat(Math.max(0, width - line.length() - 4)))
              .append(colorize(" │\n", color));
        }
        
        sb.append(colorize("└" + "─".repeat(width - 2) + "┘", color));
        return sb.toString();
    }

    /**
     * Center text within a specific width.
     *
     * @param text The text to center
     * @param width The width to center within
     * @return The centered text
     */
    public static String centerText(String text, int width) {
        if (text.length() >= width) {
            return text;
        }
        
        int leftPadding = (width - text.length()) / 2;
        int rightPadding = width - text.length() - leftPadding;
        
        return " ".repeat(leftPadding) + text + " ".repeat(rightPadding);
    }
}
