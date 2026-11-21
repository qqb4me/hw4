package championship;

import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartUtils;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;

import java.io.File;
import java.io.IOException;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Charts {

    public static void saveAll(List<Player> players, String outDir) throws IOException {
        new File(outDir).mkdirs();

        savePositionsChart(players, outDir + "/variant1.png");
        saveTopTeamsChart(players, outDir + "/variant2.png");
        saveForwardsGoalsChart(players, outDir + "/variant3.png");
        saveNationalitiesChart(players, outDir + "/variant4.png");
    }

    public static void savePositionsChart(List<Player> players, String filePath) throws IOException {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        players.stream()
                .collect(Collectors.groupingBy(p -> p.getPosition().getRussianName(), Collectors.counting()))
                .forEach(dataset::setValue);

        JFreeChart chart = ChartFactory.createPieChart("Доли позиций игроков", dataset, true, true, false);
        ChartUtils.saveChartAsPNG(new File(filePath), chart, 800, 600);
    }

    public static void saveTopTeamsChart(List<Player> players, String filePath) throws IOException {
        DefaultCategoryDataset dataset = new DefaultCategoryDataset();
        players.stream()
                .collect(Collectors.groupingBy(Player::getTeam, Collectors.summingLong(Player::getTransferCost)))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .limit(10)
                .forEach(e -> dataset.addValue(e.getValue() / 1_000_000.0, "Стоимость", e.getKey()));

        JFreeChart chart = ChartFactory.createBarChart(
                "Топ-10 команд по суммарной стоимости",
                "Команда",
                "Стоимость (млн)",
                dataset,
                PlotOrientation.VERTICAL,
                false, true, false
        );
        ChartUtils.saveChartAsPNG(new File(filePath), chart, 800, 600);
    }

    public static void saveForwardsGoalsChart(List<Player> players, String filePath) throws IOException {
        XYSeries series = new XYSeries("Нападающие");
        players.stream()
                .filter(p -> p.getPosition() == Position.FORWARD)
                .sorted(Comparator.comparingLong(Player::getTransferCost))
                .forEach(p -> series.add(p.getTransferCost() / 1_000_000.0, p.getGoals()));

        JFreeChart chart = ChartFactory.createXYLineChart(
                "Голы нападающих от стоимости",
                "Стоимость (млн)",
                "Голы",
                new XYSeriesCollection(series),
                PlotOrientation.VERTICAL,
                false, true, false
        );
        ChartUtils.saveChartAsPNG(new File(filePath), chart, 800, 600);
    }

    public static void saveNationalitiesChart(List<Player> players, String filePath) throws IOException {
        DefaultPieDataset<String> dataset = new DefaultPieDataset<>();
        players.stream()
                .collect(Collectors.groupingBy(Player::getNationality, Collectors.counting()))
                .entrySet().stream()
                .sorted(Map.Entry.<String, Long>comparingByValue().reversed())
                .forEach(e -> dataset.setValue(e.getKey(), e.getValue()));

        JFreeChart chart = ChartFactory.createPieChart("Игроки по странам", dataset, true, true, false);
        ChartUtils.saveChartAsPNG(new File(filePath), chart, 800, 600);
    }
}
