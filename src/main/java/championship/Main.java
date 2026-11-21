package championship;

import java.util.List;

public class Main {
    public static void main(String[] args) throws Exception {
        String csvPath = args.length > 0 ? args[0] : "data/fakePlayers.csv";

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

        Charts.saveChart(players, "charts/chart.png");
        System.out.println("График сохранен в charts/chart.png");
    }
}
