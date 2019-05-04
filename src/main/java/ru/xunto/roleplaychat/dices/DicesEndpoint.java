package ru.xunto.roleplaychat.dices;

import ru.xunto.roleplaychat.features.endpoints.PrefixMatchEndpoint;
import ru.xunto.roleplaychat.framework.api.Environment;
import ru.xunto.roleplaychat.framework.api.Request;
import ru.xunto.roleplaychat.framework.jtwig.JTwigTemplate;

public class DicesEndpoint extends PrefixMatchEndpoint {
    private JTwigTemplate template = new JTwigTemplate("templates/dice_roll.twig");

    DicesEndpoint() throws EmptyPrefixException {
        super("d");
    }

    @Override public void processEndpoint(Request request, Environment environment) {
        environment.setTemplate(template);
    }
}
