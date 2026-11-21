package championship;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Charts {

    public static void saveChart(List<Player> players, String filePath) throws IOException {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        players.stream()
                .collect(Collectors.groupingBy(Player::getNationality, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> dataset.setValue(e.getKey(), e.getValue()));

        JFreeChart chart = ChartFactory.createPieChart("Игроки по странам", dataset, true, true, false);

        File file = new File(filePath);
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        ChartUtils.saveChartAsPNG(file, chart, 800, 600);
    }
}
