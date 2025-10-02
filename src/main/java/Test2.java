import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Test2 {
    public static void main(String[] args) {
        Pattern pattern = Pattern.compile("^(?:[1-9]\\d+|[1-9])$", Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(null);
        boolean matchFound = matcher.find();
        if(matchFound) {
            System.out.println("Match found");
        } else {
            System.out.println("Match not found");
        }
    }
}