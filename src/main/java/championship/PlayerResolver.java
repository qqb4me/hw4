package championship;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

public class PlayerResolver implements IResolver {
    private final List<Player> players;

    public PlayerResolver(List<Player> players) {
        this.players = players;
    }

    @Override
    public List<Player> getPlayers() {
        return players;
    }

    @Override
    public int getCountWithoutAgency() {
        return (int) players.stream()
                .filter(p -> p.getAgency() == null || p.getAgency().isBlank())
                .count();
    }

    @Override
    public int getMaxDefenderGoalsCount() {
        return players.stream()
                .filter(p -> p.getPosition() == Position.DEFENDER)
                .mapToInt(Player::getGoals)
                .max()
                .orElse(0);
    }

    @Override
    public String getTheExpensiveGermanPlayerPosition() {
        return players.stream()
                .filter(p -> "Germany".equalsIgnoreCase(p.getNationality()))
                .max(Comparator.comparingLong(Player::getTransferCost))
                .map(p -> p.getPosition().getRussianName())
                .orElse("");
    }

    @Override
    public Map<String, String> getPlayersByPosition() {
        return players.stream()
                .collect(Collectors.groupingBy(
                        p -> p.getPosition().getRussianName(),
                        Collectors.mapping(Player::getName, Collectors.joining(", "))
                ));
    }

    @Override
    public Set<String> getTeams() {
        return players.stream()
                .map(Player::getTeam)
                .collect(Collectors.toSet());
    }

    @Override
    public Map<String, Integer> getTop5TeamsByGoalsCount() {
        return players.stream()
                .collect(Collectors.groupingBy(Player::getTeam, Collectors.summingInt(Player::getGoals)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Integer>comparingByValue().reversed())
                .limit(5)
                .collect(Collectors.toMap(
                        Map.Entry::getKey,
                        Map.Entry::getValue,
                        (e1, e2) -> e1,
                        LinkedHashMap::new
                ));
    }

    @Override
    public String getAgencyWithMinPlayersCount() {
        return players.stream()
                .filter(p -> p.getAgency() != null && !p.getAgency().isBlank())
                .collect(Collectors.groupingBy(Player::getAgency, Collectors.summingLong(Player::getTransferCost)))
                .entrySet().stream()
                .min(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }

    @Override
    public String getTheRudestTeam() {
        return players.stream()
                .collect(Collectors.groupingBy(Player::getTeam, Collectors.averagingInt(Player::getRedCards)))
                .entrySet().stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse("");
    }
}
