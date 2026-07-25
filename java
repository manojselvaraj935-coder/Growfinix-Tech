import java.util.*;
import java.util.regex.*;
import java.util.stream.Collectors;

public class Main {

    public static void main(String[] args) {

        // Sample server logs (no external file needed)
        List<String> logs = List.of(
                "2026-07-25 09:00:15 INFO GET /home 200",
                "2026-07-25 09:01:20 INFO GET /about 200",
                "2026-07-25 09:02:35 ERROR POST /login 500",
                "2026-07-25 09:03:40 INFO GET /contact 200",
                "2026-07-25 09:04:50 ERROR GET /profile 404",
                "2026-07-25 09:05:25 INFO POST /booking 201",
                "2026-07-25 09:06:30 ERROR GET /payment 500",
                "2026-07-25 09:07:15 INFO GET /services 200",
                "2026-07-25 09:08:45 ERROR POST /register 404",
                "2026-07-25 09:09:55 INFO GET /dashboard 200",
                "2026-07-25 09:10:20 INFO POST /checkout 201",
                "2026-07-25 09:11:30 ERROR GET /orders 500",
                "2026-07-25 09:12:40 INFO GET /products 200",
                "2026-07-25 09:13:55 INFO POST /feedback 201",
                "2026-07-25 09:14:10 ERROR GET /admin 403",
                "2026-07-25 09:15:25 INFO GET /home 200",
                "2026-07-25 09:16:30 ERROR POST /payment 500",
                "2026-07-25 09:17:40 INFO GET /blog 200",
                "2026-07-25 09:18:50 ERROR GET /search 404",
                "2026-07-25 09:19:35 INFO POST /login 200"
        );

        System.out.println("===== SERVER LOG ANALYZER =====");

        // Total logs
        System.out.println("Total Logs: " + logs.size());

        // Count status codes using Regex + Streams
        Pattern pattern = Pattern.compile("\\b\\d{3}\\b");

        Map<String, Long> statusCodes = logs.stream()
                .flatMap(line -> {
                    Matcher matcher = pattern.matcher(line);
                    List<String> list = new ArrayList<>();
                    while (matcher.find()) {
                        list.add(matcher.group());
                    }
                    return list.stream();
                })
                .collect(Collectors.groupingBy(code -> code, Collectors.counting()));

        System.out.println("\nStatus Code Counts:");
        statusCodes.forEach((code, count) ->
                System.out.println(code + " : " + count));

        // Count GET requests
        long getCount = logs.stream()
                .filter(line -> line.contains("GET"))
                .count();

        // Count POST requests
        long postCount = logs.stream()
                .filter(line -> line.contains("POST"))
                .count();

        // Count ERROR logs
        long errorCount = logs.stream()
                .filter(line -> line.contains("ERROR"))
                .count();

        System.out.println("\nGET Requests : " + getCount);
        System.out.println("POST Requests: " + postCount);
        System.out.println("ERROR Logs   : " + errorCount);

        // Count requested URLs
        System.out.println("\nRequested URLs:");

        Map<String, Long> urls = logs.stream()
                .map(line -> line.split(" ")[4])
                .collect(Collectors.groupingBy(url -> url, Collectors.counting()));

        urls.forEach((url, count) ->
                System.out.println(url + " : " + count));
    }
}
