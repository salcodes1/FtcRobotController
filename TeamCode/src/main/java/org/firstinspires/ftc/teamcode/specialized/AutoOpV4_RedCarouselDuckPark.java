package org.firstinspires.ftc.teamcode.specialized;

import com.acmerobotics.roadrunner.drive.Drive;
import com.acmerobotics.roadrunner.geometry.Pose2d;
import com.acmerobotics.roadrunner.trajectory.Trajectory;
import com.acmerobotics.roadrunner.trajectory.constraints.MecanumVelocityConstraint;
import com.acmerobotics.roadrunner.trajectory.constraints.TranslationalVelocityConstraint;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import org.firstinspires.ftc.teamcode.Outtake;
import org.firstinspires.ftc.teamcode.RR.drive.DriveConstants;
import org.firstinspires.ftc.teamcode.RR.drive.SampleMecanumDrive;
import org.firstinspires.ftc.teamcode.RR.trajectorysequence.TrajectorySequence;
import org.firstinspires.ftc.teamcode.RR.util.AssetsTrajectoryManager;
import org.firstinspires.ftc.teamcode.bt.Action;
import org.firstinspires.ftc.teamcode.bt.actions.RunCarousel;
import org.firstinspires.ftc.teamcode.bt.actions.RunTrajectory;
import org.firstinspires.ftc.teamcode.bt.actions.controlflow.RunDelay;
import org.firstinspires.ftc.teamcode.bt.actions.controlflow.RunInline;
import org.firstinspires.ftc.teamcode.bt.actions.controlflow.RunLinear;
import org.firstinspires.ftc.teamcode.bt.actions.controlflow.RunParallelWait;
import org.firstinspires.ftc.teamcode.bt.actions.intake.IntakeSetPower;
import org.firstinspires.ftc.teamcode.bt.actions.outtake.OuttakeDropFreight;
import org.firstinspires.ftc.teamcode.bt.actions.outtake.OuttakeSetLevel;

@Autonomous(name = "Red Carousel Duck Park")
public class AutoOpV4_RedCarouselDuckPark extends AutoOpV4Base {

    TrajectorySequence hub_to_carousel;
    Trajectory carousel_to_warehouse, carousel_to_park;

    @Override
    protected void setParams() {
    }

    @Override
    protected void precompileTrajectories() {
        startLocation = StartLocation.CAROUSEL;

        start_to_hub = AssetsTrajectoryManager.load(SIDE("cstart_to_hub"));
        hub_to_carousel = drive.trajectorySequenceBuilder(new Pose2d(-12, -43.25, Math.toRadians(-90)))
            .addTrajectory(AssetsTrajectoryManager.load(SIDE("hub_to_carousel")))
            .build();
        carousel_to_warehouse = AssetsTrajectoryManager.load(SIDE("carousel_to_warehouse"));
        carousel_to_park = AssetsTrajectoryManager.load(SIDE("carousel_to_bridge"));
        carousel_to_hub = AssetsTrajectoryManager.load(SIDE("carousel_to_hub_wduck"));
    }

    @Override
    protected Action getRoutine() {
        return new RunLinear(
            new RunInline(ctx -> ctx.outtake.capServo.setPosition(0.65)),
            new RunParallelWait(
                new RunLinear(
                    new RunDelay(600),
                    new OuttakeSetLevel(preloadLevel)
                ),
                new RunTrajectory(start_to_hub)
            ),
            new OuttakeDropFreight(),
            new RunParallelWait(
                new RunLinear(
                    new RunDelay(300),
                    new OuttakeSetLevel(Outtake.Level.loading)
                ),
                new RunTrajectory(hub_to_carousel)
            ),
            new RunCarousel(-1800, 0.25),
            new RunParallelWait(
                new RunTrajectory(carousel_to_hub),
                new RunLinear(
                    new IntakeSetPower(-1),
                    new RunDelay(5000),
                    new OuttakeSetLevel(Outtake.Level.high)
                )
            ),
            new OuttakeDropFreight(),
            new OuttakeSetLevel(Outtake.Level.loading)
        );
    }
}