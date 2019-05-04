package ru.xunto.roleplaychat.dices;

import ru.xunto.roleplaychat.features.endpoints.PrefixMatchEndpoint;
import ru.xunto.roleplaychat.framework.api.Environment;
import ru.xunto.roleplaychat.framework.api.Request;
import ru.xunto.roleplaychat.framework.jtwig.JTwigTemplate;

public class FudgeDicesEndpoint extends PrefixMatchEndpoint {
    private JTwigTemplate template = new JTwigTemplate("templates/fudge_dice_roll.twig");

    FudgeDicesEndpoint() throws EmptyPrefixException {
        super("4dF", "4df", "%");
    }

    @Override public void processEndpoint(Request request, Environment environment) {
        environment.setTemplate(template);
    }
}
