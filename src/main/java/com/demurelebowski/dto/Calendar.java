package com.demurelebowski.dto;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import static com.demurelebowski.service.Keyboards.InlineKeyboard;
import static com.demurelebowski.service.Keyboards.inButton;

public class Calendar {

    public static final String IGNORE = "ignore!@#$%^&";
    public static final String[] WD = {"M", "T", "W", "T", "F", "S", "S"};

    public static InlineKeyboardMarkup inKeysPeriod(LocalDate date) {

        List<List<InlineKeyboardButton>> rowsInline = new ArrayList<>();
        List<InlineKeyboardButton> rowInline = new ArrayList<>();

        if (date == null) {
            date = LocalDate.now();
        }
        Locale locale = Locale.forLanguageTag("en-EN");

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("LLLL yyyy").withLocale(locale);

        rowInline.add(inButton(date.format(formatter), IGNORE));
        rowsInline.add(rowInline);

        List<InlineKeyboardButton> daysOfWeekRow = new ArrayList<>();
        for (String day : WD) {
            daysOfWeekRow.add(inButton(day, IGNORE));
        }
        rowsInline.add(daysOfWeekRow);

        LocalDate firstDay = date.withDayOfMonth(1);

        int shift = firstDay.getDayOfWeek().getValue() - 1;
        int daysInMonth = YearMonth.of(firstDay.getYear(), firstDay.getMonth()).lengthOfMonth();
        int rows = ((daysInMonth + shift) % 7 > 0 ? 1 : 0) + (daysInMonth + shift) / 7;
        for (int i = 0; i < rows; i++) {
            rowsInline.add(buildRow(firstDay, shift));
            firstDay = firstDay.plusDays(7 - shift);
            shift = 0;
        }

        List<InlineKeyboardButton> controlsRow = new ArrayList<>();
        controlsRow.add(inButton("<", "<"));
        controlsRow.add(inButton(">", ">"));
        rowsInline.add(controlsRow);

        return InlineKeyboard(rowsInline);
    }

    private static List<InlineKeyboardButton> buildRow(LocalDate date, int shift) {
        List<InlineKeyboardButton> row = new ArrayList<>();
        int day = date.getDayOfMonth();
        LocalDate callbackDate = date;
        for (int j = 0; j < shift; j++) {
            row.add(inButton(" ", IGNORE));
        }
        for (int j = shift; j < 7; j++) {
            if (day <= (YearMonth.of(date.getYear(), date.getMonth()).lengthOfMonth())) {
                row.add(inButton(Integer.toString(day++), "cal_date_" + callbackDate));
                callbackDate = callbackDate.plusDays(1);
            } else {
                row.add(inButton(" ", IGNORE));
            }
        }
        return row;
    }


}
