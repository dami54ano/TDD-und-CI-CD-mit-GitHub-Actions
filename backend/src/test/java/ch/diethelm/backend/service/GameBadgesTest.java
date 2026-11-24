package ch.diethelm.backend.service;

import ch.diethelm.backend.model.Game;
import org.junit.jupiter.api.Test;

import java.time.Clock;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class GameBadgesTest {
    private final GameBadges badges = new GameBadges(Clock.fixed(
            Instant.parse("2026-10-27T10:00:00Z"), ZoneId.of("Europe/Zurich")));

    @Test
    void releasedLastWeek_isNew() {
        assertTrue(badges.isNew(gameReleasedOn(LocalDate.of(2026, 10, 20))));
    }

    @Test
    void releasedLastYear_isNotNew() {
        assertFalse(badges.isNew(gameReleasedOn(LocalDate.of(2025, 10, 27))));
    }

    @Test
    void releasedExactlyThirtyDaysAgo_isNew() {
        assertTrue(badges.isNew(gameReleasedOn(LocalDate.of(2026, 9, 27))));
    }

    @Test
    void releasedThirtyOneDaysAgo_isNotNew() {
        assertFalse(badges.isNew(gameReleasedOn(LocalDate.of(2026, 9, 26))));
    }

    @Test
    void futureRelease_isNotNew() {
        assertFalse(badges.isNew(gameReleasedOn(LocalDate.of(2026, 10, 28))));
    }

    private Game gameReleasedOn(LocalDate date) {
        return Game.builder().title("X").releaseDate(date).build();
    }
}
