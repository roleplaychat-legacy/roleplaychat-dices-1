package ru.xunto.roleplaychat.dices;

import net.minecraft.entity.player.EntityPlayer;
import ru.xunto.roleplaychat.framework.api.Environment;
import ru.xunto.roleplaychat.framework.api.PrefixMatchEndpoint;
import ru.xunto.roleplaychat.framework.api.Priority;
import ru.xunto.roleplaychat.framework.api.Request;
import ru.xunto.roleplaychat.framework.jtwig.JTwigTemplate;
import ru.xunto.roleplaychat.framework.middleware_flow.Flow;
import ru.xunto.roleplaychat.framework.state.IProperty;
import ru.xunto.roleplaychat.framework.state.MessageState;
import ru.xunto.roleplaychat.framework.state.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FudgeDicesEndpoint extends PrefixMatchEndpoint {
    public static final IProperty<Integer> FUDGE_LEVEL = new Property<>("fudge_level");
    public static final IProperty<List<Integer>> FUDGE_SIGNS = new Property<>("fudge_signs");
    public static final IProperty<Integer> FUDGE_RESULT = new Property<>("fudge_result");
    public static final IProperty<String> COMMENTARY = new Property<>("commentary");
    private Random rand = new Random();
    private JTwigTemplate template = new JTwigTemplate("templates/fudge_dice_roll.twig");

    FudgeDicesEndpoint() throws EmptyPrefixException {
        super("%");
    }

    @Override public Priority getPriority() {
        return Priority.HIGH;
    }

    @Override public void processEndpoint(Request request, Environment environment, Flow flow) {
        MessageState state = environment.getState();
        String text = state.getValue(Environment.TEXT);

        List<String> strings = Arrays.asList(text.split(" "));

        EntityPlayer requester = request.getRequester();

        if (strings.size() < 1) {
            RoleplayChatDices.sendError(requester, "Укажите уровень навыка для броска");
            flow.stop();
            return;
        }

        int level;
        try {
            level = Integer.parseInt(strings.get(0));
        } catch (NumberFormatException e) {
            RoleplayChatDices.sendError(requester, "Укажите уровень навыка для броска");
            flow.stop();
            return;
        }

        String comment = null;
        if (strings.size() > 1) {
            comment = String.join(" ", strings.subList(1, strings.size()));
        }

        int result = level;
        List<Integer> signs = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int die = rand.nextInt(3) - 1;
            signs.add(die);
            result += die;
        }

        state.setValue(FUDGE_LEVEL, level);
        state.setValue(FUDGE_SIGNS, signs);
        state.setValue(FUDGE_RESULT, result);
        state.setValue(COMMENTARY, comment);

        environment.setTemplate(template);
    }
}
