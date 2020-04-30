import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import java.util.Collections.*;

import static java.util.stream.Collectors.toMap;

public class CountFortunataAndJacinta {

    private static final String INFILENAME = "C:\\workspace\\TC10_3FortunataJacinta\\input\\testInput.txt";
    private static String OUTFILENAME = "C:\\workspace\\TC10_3FortunataJacinta\\output\\outputChallenge2.txt";
    private static Scanner in;
    private static PrintWriter out;

    // Stream<String> stream = Stream.of(text.toLowerCase().split("\\W+")).parallel();

    //Map<String, Long> wordFreq = stream.collect(Collectors.groupingBy(String::toString,Collectors.counting()));

    public static void main(String[] args) throws FileNotFoundException, IOException {
        HashMap<String, Long> countWords = new HashMap<String, Long>();
        in = new Scanner(new FileReader(INFILENAME));
        out = new PrintWriter(OUTFILENAME);
        int cases = Integer.parseInt(in.nextLine());
        int countCases = 1;
        Path path = Paths.get("C:\\workspace\\TC10_3FortunataJacinta\\input\\testInput.txt");
        countWords = countEveryWord(path);
        countWords.remove(String.valueOf(cases));
        orderNumbers(countWords, countCases, out);
        orderWords(countWords, countCases, out);
        in.close();
        out.close();
    }

    private static void orderNumbers(HashMap<String, Long> countWords, int countCases, PrintWriter out) {
        HashMap<String, Long> hashmapNumbers = new HashMap<String, Long>(countWords);
        hashmapNumbers.entrySet().removeIf(entry -> !isNumeric((String)entry.getKey()));

        TreeMap<Integer, Long> numbersSortMap = new TreeMap(
                hashmapNumbers.entrySet().stream()
                        .collect(Collectors.toMap(
                        entry -> Integer.parseInt(entry.getKey()),
                        entry -> entry.getValue())));

        NavigableMap<Integer, Long> reversednumbersSortMap = numbersSortMap.descendingMap();
        Iterator<Map.Entry<Integer, Long>> iterator = reversednumbersSortMap.entrySet().iterator();
        Map.Entry<Integer, Long> entry = null;
        while(iterator.hasNext()){
           entry = iterator.next();
            out.println("Case #" + (countCases) + ": " + entry.getKey() + " #"+entry.getValue());
            System.out.println("Case #" + (countCases) + ": " + entry.getKey() + " #"+entry.getValue());
            countCases++;
        }
        //numbersSortMap.forEach((k, v) -> out.println("Case #" + (countCases + 1) + ": " + v));

        //
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void orderWords(HashMap<String, Long> countWords, int countCases, PrintWriter out) {
    }

    private static HashMap<String, Long> countEveryWord(final Path tmpFile) throws IOException {
        return Pattern.compile("\\W+").splitAsStream(new String(Files.readAllBytes(tmpFile), StandardCharsets.UTF_8).toLowerCase())
                .collect(Collectors.groupingBy(Function.<String>identity(), HashMap::new, Collectors.counting()));
    }

    private void countWordsStream(final Path tmpFile) throws IOException {
        Arrays.stream(new String(Files.readAllBytes(tmpFile), StandardCharsets.UTF_8).split("\\W+"))
                .collect(Collectors.groupingBy(Function.<String>identity(), HashMap::new, Collectors.counting()));
    }

}
