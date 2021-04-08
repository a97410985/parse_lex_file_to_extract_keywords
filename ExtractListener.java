import org.antlr.v4.runtime.*;

import java.util.*;

public class ExtractListener extends CBaseListener {
    String curTypedef = "";
    int initializerDepth = 0;
    String curDeclaratorName = "";
    Map<String, List<String>> names = new HashMap<String, List<String>>();

    public Map<String, List<String>> getNames() {
        return names;
    }

    @Override
    public void exitDeclaration(CParser.DeclarationContext ctx) {
        curTypedef = "";
        initializerDepth = 0;
        curDeclaratorName = "";
        // System.out.println(names);
    }

    @Override
    public void exitTypedefName(CParser.TypedefNameContext ctx) {
        // System.out.println("enterTypedefName : " + ctx.getText());
        curTypedef = ctx.getText();
    }

    @Override
    public void exitDeclarator(CParser.DeclaratorContext ctx) {
        // System.out.println("exitDeclarator : " + ctx.getText());
        curDeclaratorName = ctx.getText();
    }

    @Override
    public void enterInitializer(CParser.InitializerContext ctx) {
        initializerDepth++;
    }

    @Override
    public void exitInitializer(CParser.InitializerContext ctx) {
        if (curTypedef.equals("SYMBOL") && initializerDepth == 3 && !ctx.getText().startsWith("SYM")) {
            if (names.get(curDeclaratorName) == null) {
                List<String> newList = new ArrayList<>();
                newList.add(ctx.getText());
                names.put(curDeclaratorName, newList);
            } else {
                names.get(curDeclaratorName).add(ctx.getText());
            }

        }
        // System.out.println("exitInitializer : " + ctx.getText() + " , depth = " +
        // initializerDepth);

        initializerDepth--;
    }

}
