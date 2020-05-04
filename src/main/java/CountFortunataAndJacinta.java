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
    private static String OUTFILENAME = "C:\\workspace\\TC10_3FortunataJacinta\\output\\outputChallenge3.txt";
    private static Scanner in;
    private static PrintWriter out;
    private static int countCases = 1;

    // Stream<String> stream = Stream.of(text.toLowerCase().split("\\W+")).parallel();

    //Map<String, Long> wordFreq = stream.collect(Collectors.groupingBy(String::toString,Collectors.counting()));

    public static void main(String[] args) throws IOException {
        HashMap<String, Long> countWords = new HashMap<String, Long>();
        in = new Scanner(new FileReader(INFILENAME));
        out = new PrintWriter(OUTFILENAME);
        int cases = Integer.parseInt(in.nextLine());
        int countCases = 1;
        Path path = Paths.get("C:\\workspace\\TC10_3FortunataJacinta\\input\\testInputC.txt");
        countWords = countEveryWord(path);
        orderNumbers(countWords, out);
        orderWords(countWords, out);
        in.close();
        out.close();
    }

    private static void orderNumbers(HashMap<String, Long> countWords, PrintWriter out) {
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
            //System.out.println("Case #" + (countCases) + ": " + entry.getKey() + " #"+entry.getValue());
            countCases++;
        }
    }

    public static boolean isNumeric(String str) {
        try {
            Double.parseDouble(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static void orderWords(HashMap<String, Long> countWords, PrintWriter out) {
        HashMap<String, Long> hashmapWords = new HashMap<String, Long>(countWords);
        hashmapWords.entrySet().removeIf(entry -> isNumeric((String)entry.getKey()));
        hashmapWords.entrySet().removeIf(entry -> entry.getKey().length()<3);
        TreeMap<String, Long> wordsSortMap = new TreeMap (hashmapWords);
        wordsSortMap.forEach((k, v) -> out.println("Case #" + (countCases++) + ": " + k +" " + v) );
    }

    private static HashMap<String, Long> countEveryWord(final Path tmpFile) throws IOException {
        String content = new String ( Files.readAllBytes( tmpFile), StandardCharsets.UTF_8);
        int firstIntro = content.indexOf("\n");
        content= content.substring(firstIntro+1);

        return Pattern.compile("\\W+").splitAsStream(content.toLowerCase())
                .collect(Collectors.groupingBy(Function.<String>identity(), HashMap::new, Collectors.counting()));
    }

    private void countWordsStream(final Path tmpFile) throws IOException {

        Arrays.stream(new String(Files.readAllBytes(tmpFile), StandardCharsets.UTF_8).split("\\W+"))
                .collect(Collectors.groupingBy(Function.<String>identity(), HashMap::new, Collectors.counting()));
    }

}
