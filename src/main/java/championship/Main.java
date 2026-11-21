package championship;

import java.io.File;
import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String csvPath = "data/fakePlayers.csv";
        if (args.length > 0) {
            csvPath = args[0];
        }

        List<Player> players = PlayerReader.read(csvPath);
        IResolver resolver = new PlayerResolver(players);

        System.out.println("1. Без агентства: " + resolver.getCountWithoutAgency());
        System.out.println("2. Макс голов защитника: " + resolver.getMaxDefenderGoalsCount());
        System.out.println("3. Позиция самого дорогого немецкого игрока: " + resolver.getTheExpensiveGermanPlayerPosition());
        System.out.println("4. Игроки по позициям:");
        resolver.getPlayersByPosition().forEach((pos, names) -> System.out.println("   " + pos + ": " + names));
        System.out.println("5. Команды (" + resolver.getTeams().size() + "): " + resolver.getTeams());
        System.out.println("6. Топ-5 команд по забитым мячам: " + resolver.getTop5TeamsByGoalsCount());
        System.out.println("7. Агентство с мин суммой игроков: " + resolver.getAgencyWithMinPlayersCount());
        System.out.println("8. Самая грубая команда: " + resolver.getTheRudestTeam());

        Charts.saveAll(players, "charts");
        System.out.println("Графики сохранены в charts/");
    }
}
