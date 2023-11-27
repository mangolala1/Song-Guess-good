package entities;

import java.time.LocalDateTime;

public interface PlayerFactory {

    Player create(String name, String password, LocalDateTime ltd);
}
