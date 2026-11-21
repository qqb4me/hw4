package championship;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

public class PlayerReader {
    public static List<Player> read(String filePath) throws IOException {
        List<Player> players = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(filePath, StandardCharsets.UTF_8))) {
            String line = br.readLine();
            while ((line = br.readLine()) != null) {
                if (line.isBlank()) {
                    continue;
                }
                String[] parts = line.split(";", -1);
                Player player = new Player(
                        parts[0].trim(),
                        parts[1].trim(),
                        parts[2].trim(),
                        Position.fromCsv(parts[3]),
                        parts[4].trim(),
                        parts[5].trim(),
                        Long.parseLong(parts[6].trim()),
                        Integer.parseInt(parts[7].trim()),
                        Integer.parseInt(parts[8].trim()),
                        Integer.parseInt(parts[9].trim()),
                        Integer.parseInt(parts[10].trim()),
                        Integer.parseInt(parts[11].trim())
                );
                players.add(player);
            }
        }
        return players;
    }
}
