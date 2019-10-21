package net.nogoodshots.toyrobot.actions.impl;

import net.nogoodshots.toyrobot.ActionHandler;
import net.nogoodshots.toyrobot.Direction;
import net.nogoodshots.toyrobot.commands.CommandsLexer;
import net.nogoodshots.toyrobot.commands.CommandsBaseListener;
import net.nogoodshots.toyrobot.commands.CommandsListener;
import net.nogoodshots.toyrobot.commands.CommandsParser;
import org.antlr.v4.runtime.BaseErrorListener;
import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.RecognitionException;
import org.antlr.v4.runtime.Recognizer;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

public class ActionFactory {

    private final ActionHandler handler;
    private final CommandsListener listener;

    public ActionFactory(final ActionHandler handler) {
        Objects.requireNonNull(handler);

        this.handler = handler;

        this.listener = new CommandsBaseListener() {

            @Override
            public void exitBlank_line(CommandsParser.Blank_lineContext ctx) {
                super.exitBlank_line(ctx);
            }

            @Override
            public void exitPlace_action(CommandsParser.Place_actionContext ctx) {
                final Integer x = Integer.valueOf(ctx.NUMBER(0).getText());
                final Integer y = Integer.valueOf(ctx.NUMBER(1).getText());
                final Direction d = Direction.valueOf(ctx.DIRECTION().getText());
                handler.handleAction(new PlaceAction(x, y, d));
                super.exitPlace_action(ctx);
            }

            @Override
            public void exitMove_action(CommandsParser.Move_actionContext ctx) {
                handler.handleAction(new MoveAction());
                super.exitMove_action(ctx);
            }

            @Override
            public void exitLeft_action(CommandsParser.Left_actionContext ctx) {
                handler.handleAction(new LeftTurnAction());
                super.exitLeft_action(ctx);
            }

            @Override
            public void exitRight_action(CommandsParser.Right_actionContext ctx) {
                handler.handleAction(new RightTurnAction());
                super.exitRight_action(ctx);
            }

            @Override
            public void exitReport_action(CommandsParser.Report_actionContext ctx) {
                handler.handleAction(new ReportAction());
                super.exitReport_action(ctx);
            }
        };
    }

    public void parseActions(final InputStream input) throws IOException, IllegalCommandException {

        // ToDo investigate alternatives to CharStreams.fromStream(input) that would support streaming
        // The problem with CharStreams.fromStream(input) is that it closes input when the stream is empty
        // Setup parser
        final CommandsLexer lexer = new CommandsLexer(CharStreams.fromStream(input));
        final CommandsParser parser = new CommandsParser(new CommonTokenStream(lexer));

        // To aid debugging add an error handler
        parser.addErrorListener(new BaseErrorListener() {
            @Override
            public void syntaxError(Recognizer<?, ?> recognizer, Object offendingSymbol, int line, int charPositionInLine, String msg, RecognitionException e) {
                throw new IllegalStateException("failed to parse at line " + line + " due to " + msg, e);
            }
        });

        try {
            ParseTreeWalker.DEFAULT.walk(listener, parser.input());
        } catch (IllegalStateException e) {
            throw new IllegalCommandException("Illegal game action found.");
        }
    }

}
