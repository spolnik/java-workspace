package com.data;

import org.junit.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

@SpringBootApplication
public class Application {

    private static final Logger log = LoggerFactory.getLogger(Application.class);

    @Bean
    BookingService bookingService() {
        return new BookingService();
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource);

        log.info("Creating tables");
        jdbcTemplate.execute("drop table BOOKINGS if exists");
        jdbcTemplate.execute("create table BOOKINGS("
                + "ID serial, FIRST_NAME varchar(5) NOT NULL)");

        return jdbcTemplate;
    }

    public static void main(String[] args) {
        ApplicationContext ctx = SpringApplication.run(Application.class, args);

        BookingService bookingService = ctx.getBean(BookingService.class);
        firstThreeBookings(bookingService);

        failureBecauseOfTooLongName(bookingService);

        printResults(bookingService);

        checkState(
                "You shouldn't see Chris or Samuel. Samuel violated DB constraints, and Chris was rolled back in the same TX",
                "'Samuel' should have triggered a rollback",
                bookingService.findAllBookings().size()
        );

        try {
            bookingService.book("Buddy", null);
        }
        catch (RuntimeException e) {
            log.info("v--- The following exception is expect because null is not valid for the DB ---v");
            log.error(e.getMessage());
        }

        printResults(bookingService);

        checkState(
                "You shouldn't see Buddy or null. null violated DB constraints, and Buddy was rolled back in the same TX",
                "'null' should have triggered a rollback",
                bookingService.findAllBookings().size()
        );

    }

    private static void checkState(String msg, String message, int size) {
        log.info(msg);
        Assert.assertEquals(message, 3, size);
    }

    private static void printResults(BookingService bookingService) {
        bookingService.findAllBookings().forEach(
                person -> log.info("So far, " + person + " is booked.")
        );
    }

    private static void failureBecauseOfTooLongName(BookingService bookingService) {
        try {
            bookingService.book("Chris", "Samuel");
        }
        catch (RuntimeException e) {
            log.info("v--- The following exception is expect because 'Samuel' is too big for the DB ---v");
            log.error(e.getMessage());
        }
    }

    private static void firstThreeBookings(BookingService bookingService) {
        bookingService.book("Alice", "Bob", "Carol");
        Assert.assertEquals("First booking should work with no problem", 3,
                bookingService.findAllBookings().size());
    }

}
