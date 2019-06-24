package ru.xunto.roleplaychat.dices;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextFormatting;
import ru.xunto.roleplaychat.framework.api.Environment;
import ru.xunto.roleplaychat.framework.api.PrefixMatchEndpoint;
import ru.xunto.roleplaychat.framework.api.Priority;
import ru.xunto.roleplaychat.framework.api.Request;
import ru.xunto.roleplaychat.framework.jtwig.JTwigTemplate;
import ru.xunto.roleplaychat.framework.state.IProperty;
import ru.xunto.roleplaychat.framework.state.MessageState;
import ru.xunto.roleplaychat.framework.state.Property;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

public class FudgeDicesEndpoint extends PrefixMatchEndpoint {
    private Random rand = new Random();

    public static final IProperty<Integer> FUDGE_LEVEL = new Property<>("fudge_level");
    public static final IProperty<List<Integer>> FUDGE_SIGNS = new Property<>("fudge_signs");
    public static final IProperty<Integer> FUDGE_RESULT = new Property<>("fudge_result");
    public static final IProperty<String> COMMENTARY = new Property<>("commentary");

    private JTwigTemplate template = new JTwigTemplate("templates/fudge_dice_roll.twig");

    FudgeDicesEndpoint() throws EmptyPrefixException {
        super("%");
    }

    @Override public Priority getPriority() {
        return Priority.HIGH;
    }

    protected void sendError(EntityPlayer player, String errorMessage) {
        TextComponentString component = new TextComponentString(errorMessage);
        component.getStyle().setColor(TextFormatting.DARK_RED);
        player.sendMessage(component);
    }

    @Override public void processEndpoint(Request request, Environment environment) {
        MessageState state = environment.getState();
        String text = state.getValue(Environment.TEXT);

        List<String> strings = Arrays.asList(text.split(" "));

        EntityPlayer requester = request.getRequester();

        if (strings.size() < 1) {
            sendError(requester, "Укажите уровень навыка для броска");
            environment.interrupt();
            return;
        }

        int level;
        try {
            level = Integer.parseInt(strings.get(0));
        } catch (NumberFormatException e) {
            sendError(requester, "Укажите уровень навыка для броска");
            environment.interrupt();
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
