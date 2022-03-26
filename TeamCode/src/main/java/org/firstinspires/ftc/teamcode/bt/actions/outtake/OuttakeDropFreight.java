package org.firstinspires.ftc.teamcode.bt.actions.outtake;

import org.firstinspires.ftc.teamcode.Outtake;
import org.firstinspires.ftc.teamcode.bt.Action;
import org.firstinspires.ftc.teamcode.bt.ActionGroup;
import org.firstinspires.ftc.teamcode.bt.actions.controlflow.RunDelay;
import org.firstinspires.ftc.teamcode.bt.actions.controlflow.RunInline;
import org.firstinspires.ftc.teamcode.bt.actions.controlflow.RunLinear;

public class OuttakeDropFreight extends ActionGroup {

    @Override
    protected Action constructGroup() {
        return new RunLinear(
            new RunInline((context) -> context.containerServo.setPosition(Outtake.SERVO_DROP_NORMAL)),
            new RunDelay(400),
            new RunInline((context) -> context.containerServo.setPosition(Outtake.SERVO_LOADING))
        );
    }
}