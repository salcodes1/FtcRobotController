package org.firstinspires.ftc.teamcode.bt.actions;

import com.acmerobotics.roadrunner.trajectory.Trajectory;

import org.firstinspires.ftc.teamcode.RR.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.bt.Action;
import org.firstinspires.ftc.teamcode.bt.AutonomousOpMode;

public class RunTrajectory extends Action {

    TrajectorySequence trajectorySequence;
    private Trajectory trajectory;

    public RunTrajectory(Trajectory trajectory) {
        this.trajectory = trajectory;
    }

    public RunTrajectory(TrajectorySequence trajectorySequence) {
        this.trajectorySequence = trajectorySequence;
    }

    @Override
    public void _start(AutonomousOpMode context) {
        if(context.drive.isBusy()) {
            throw new RuntimeException("RunTrajectory: You are giving a new trajectory " +
                    "when the current one isn't done!");
        }
        if(trajectory != null)
            context.drive.followTrajectoryAsync(trajectory);
        else
            context.drive.followTrajectorySequenceAsync(trajectorySequence);
    }

    @Override
    public void _tick(AutonomousOpMode state) {

    }

    @Override
    public boolean _hasFinished(AutonomousOpMode state) {
        return !state.drive.isBusy();
    }

    @Override
    public void _end(AutonomousOpMode state) {

    }
}