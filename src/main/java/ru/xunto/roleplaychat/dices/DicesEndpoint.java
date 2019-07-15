package ru.xunto.roleplaychat.dices;

import ru.xunto.roleplaychat.framework.api.*;
import ru.xunto.roleplaychat.framework.jtwig.JTwigTemplate;
import ru.xunto.roleplaychat.framework.middleware_flow.Flow;
import ru.xunto.roleplaychat.framework.state.IProperty;
import ru.xunto.roleplaychat.framework.state.MessageState;
import ru.xunto.roleplaychat.framework.state.Property;

import java.util.Random;

public class DicesEndpoint extends PrefixMatchEndpoint {
    public static final IProperty<Integer> DICE = new Property<>("dice");
    public static final IProperty<Integer> RESULT = new Property<>("dice_result");
    private Random rand = new Random();
    private JTwigTemplate template = new JTwigTemplate("templates/dice_roll.twig");

    public DicesEndpoint() throws EmptyPrefixException {
        super("d");
    }

    @Override public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override public Stage getStage() {
        return Stage.ENDPOINT;
    }

    @Override public void processEndpoint(Request request, Environment environment, Flow flow) {
        MessageState state = environment.getState();
        String text = state.getValue(Environment.TEXT);

        int dice = defineDice(text);
        if (dice < 0) {
            flow.stop();
            return;
        }

        state.setValue(DICE, dice);
        state.setValue(RESULT, rand.nextInt(dice) + 1);

        environment.setTemplate(template);
        environment.setProcessed(true);
    }

    private int defineDice(String text) {
        String str = text.split(" ", 1)[0];

        try {
            return Integer.parseInt(str);
        } catch (NumberFormatException e) {
            return -1;
        }
    }
}
