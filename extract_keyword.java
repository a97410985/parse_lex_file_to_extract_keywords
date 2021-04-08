import org.antlr.v4.runtime.*;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.io.File;

public class extract_keyword {
    public static void main(String[] args) throws IOException {
        String filePath = new File("").getAbsolutePath();
        filePath.concat("lex_file.txt");

        CharStream charStream = CharStreams.fromString(
                String.join("\n", Files.readAllLines(new File("lex_file.txt").toPath(), Charset.forName("utf8"))));
        CLexer lexer = new CLexer(charStream);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        CParser parser = new CParser(tokens);
        ExtractListener extractListener = new ExtractListener();
        parser.addParseListener(extractListener);
        parser.compilationUnit();

        System.out.println(extractListener.getNames());

    }
}
