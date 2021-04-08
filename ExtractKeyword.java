import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;
import java.util.regex.Pattern;

public class ExtractKeyword {

    public static String removeMacroLine(String code) {
        String regex = "^#.*$";
        Pattern pattern = Pattern.compile(regex, Pattern.MULTILINE);
        String newCode = pattern.matcher(code).replaceAll("");
        return newCode;
    }

    public static void main(String[] args) throws IOException {
        String filePath = new File("").getAbsolutePath();
        filePath.concat("lex_file.txt");

        String code = String.join("\n", Files.readAllLines(new File("lex_file.txt").toPath(), Charset.forName("utf8")));
        CharStream charStream = CharStreams.fromString(removeMacroLine(code));
        CLexer lexer = new CLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        ExtractListener extractListener = new ExtractListener();
        parser.addParseListener(extractListener);
        parser.compilationUnit();

        System.out.println(extractListener.getNames());

    }
}
