package org.firstinspires.ftc.teamcode.user;

import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.ParallelAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.ProfileAccelConstraint;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.SleepAction;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.TrajectoryBuilder;
import com.acmerobotics.roadrunner.TranslationalVelConstraint;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointDrive;

@Autonomous(name="auto_basket_5_SAMPLE")
public class auto_basket_5_SAMPLE extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(new Vector2d(-40,-66), Math.toRadians(0));

        ElapsedTime timerr= new ElapsedTime();
        PinpointDrive drive = new PinpointDrive(hardwareMap,initialPose);
        colection colection = new colection(hardwareMap);
        extension extension = new extension(hardwareMap);
        scoring scoring = new scoring(hardwareMap);
        slides slides = new slides(hardwareMap);
        slides.slide_init();
        colection.init_config();
        extension.extend(extension.extension_retracted);
        scoring.scoring_arm_score_specimen_score();
        slides.reset_encoder();
        scoring.gripper_grab();
        ElapsedTime timer= new ElapsedTime();
        boolean transferz=false;

        TrajectoryActionBuilder start_to_score = drive.actionBuilder( initialPose)
                .afterTime(0,slides.slide_sample())
                .afterTime(0.4,slides.slide_sample())
                .afterTime(0.8,colection.collecting_arm_default())
                .strafeToLinearHeading(new Vector2d(-57,-55),Math.toRadians(46.5));
        TrajectoryActionBuilder start_to_score_to_preload = drive.actionBuilder(new Pose2d(new Vector2d(-57,-55),Math.toRadians(46.5)))
                .strafeToLinearHeading(new Vector2d(31,-63),Math.toRadians(0));
        TrajectoryActionBuilder sample_finish_preload = drive.actionBuilder(new Pose2d(new Vector2d(0,-63),Math.toRadians(0)))
                .strafeToLinearHeading(new Vector2d(-57,-55),Math.toRadians(46.5));
        TrajectoryActionBuilder sample_1 = drive.actionBuilder(new Pose2d(new Vector2d(-57,-55),Math.toRadians(46.5)))
                .afterTime(0.4,slides.slide_init())
                .afterTime(0.7,slides.slide_init())
                .afterTime(0.1,scoring.sample_collect())
                .strafeToLinearHeading(new Vector2d(-50,-40),Math.toRadians(90));
        TrajectoryActionBuilder sample_finish = drive.actionBuilder(new Pose2d(new Vector2d(-50,-40),Math.toRadians(90)))

                .strafeToLinearHeading(new Vector2d(-57,-55),Math.toRadians(46.5));



        TrajectoryActionBuilder sample_2 = drive.actionBuilder(new Pose2d(new Vector2d(-57,-55),Math.toRadians(46.5)))
                .afterTime(0.4,slides.slide_init())
                .afterTime(0.7,slides.slide_init())
                .afterTime(0.5,colection.collecting_arm_default())
                .strafeToLinearHeading(new Vector2d(-61,-40),Math.toRadians(90));

        TrajectoryActionBuilder sample_finish_2 = drive.actionBuilder(new Pose2d(new Vector2d(-61,-40),Math.toRadians(90)))

                .strafeToLinearHeading(new Vector2d(-57,-55),Math.toRadians(46.5));

        TrajectoryActionBuilder sample_3 = drive.actionBuilder(new Pose2d(new Vector2d(-57,-55),Math.toRadians(46.5)))
                .afterTime(0.4,slides.slide_init())
                .afterTime(0.7,slides.slide_init())
                .afterTime(0.5,colection.collecting_arm_default())
                .strafeToLinearHeading(new Vector2d(-60,-26),Math.toRadians(-180));
        TrajectoryActionBuilder sample_3_end = drive.actionBuilder(new Pose2d(new Vector2d(-62,-26),Math.toRadians(-180)))
                .strafeTo(new Vector2d(-49,-24.5));
        TrajectoryActionBuilder sample_finish_3 = drive.actionBuilder(new Pose2d(new Vector2d(-49,-24.5),Math.toRadians(-180)))

                .strafeToLinearHeading(new Vector2d(-57,-55),Math.toRadians(46.5));

        TrajectoryActionBuilder parking_pre = drive.actionBuilder(new Pose2d(new Vector2d(-57,-55),Math.toRadians(46.5)))
                .afterTime(0.2,slides.auto_park())
                .strafeToLinearHeading(new Vector2d(-43,-13),Math.toRadians(180));

        TrajectoryActionBuilder parking = drive.actionBuilder(new Pose2d(new Vector2d(-43,-13),Math.toRadians(180)))
                .afterTime(0.2,slides.auto_park())
                .strafeTo(new Vector2d(-25,-11));






        scoring.gripper(scoring.gripper_hold);
        waitForStart();
        timerr.reset();
        if (isStopRequested()) return;
        scoring.scoring_arm_score_basket();
        slides.culisante(slides.slides_high_basket);
        sleep(300);
        Actions.runBlocking(
                new SequentialAction(
                        start_to_score.build()
                ));
        scoring.gripper(scoring.gripper_release);
        sleep(1400);
        slides.auto_park();
        slides.culisante(slides.slides_init);
        Actions.runBlocking(
                new SequentialAction(
                        start_to_score_to_preload.build()
                ));
        scoring.scoring_arm_default();
        colection.colection_arm(colection.colection_extended_auto);
        sleep(200);
        colection.gripper_grab();
        sleep(300);
        colection.scoring_config();
//        ElapsedTime timer= new ElapsedTime();
//        timer.reset();
//        while(timer.seconds()<)


        Actions.runBlocking(
                new SequentialAction(
                        sample_finish_preload.build()
                ));
        colection.gripper.setPosition(colection.gripper_transfer);

        timer.reset();
        transferz=true;
        while (transferz) {
            extension.extend(extension.extension_forced);
            if ( timer.seconds() > 0.2 && timer.seconds()<0.22) {
                scoring.scoring_arm_colect();
            }
            if (timer.seconds() > 0.5 && timer.seconds() < 0.6) {
                colection.gripper.setPosition(colection.gripper_transfer);

            }
            if (timer.seconds() > 0.5 && timer.seconds() < 0.6) {
                scoring.grip_transfer.setPosition(scoring.gripper_semi_hold);

            } if (timer.seconds() >0.8  && timer.seconds() < 0.9) {
                colection.gripper.setPosition(colection.gripper_release);

            }
            if (timer.seconds() > 1.1 ) {
                colection.default_config();
                extension.extend(extension.extension_retracted);
                slides.culisante(slides.slides_high_basket);
                transferz=false;
            }

        }

        sleep(1000);
        scoring.scoring_arm_score_basket();
        sleep(600);
        scoring.gripper(scoring.gripper_release);
        sleep(300);

        Actions.runBlocking(
                new SequentialAction(
                        sample_1.build()
                ));
//        ElapsedTime timer= new ElapsedTime();
//        timer.reset();
//        while(timer.seconds()<)

        scoring.scoring_arm_default();
        colection.colection_arm(colection.colection_extended_auto);
        sleep(200);
        colection.gripper_grab();
        sleep(300);
        colection.scoring_config();
        sleep(700);
        colection.gripper.setPosition(colection.gripper_transfer);

        timer.reset();
        transferz=true;
        while (transferz) {
            extension.extend(extension.extension_forced);
            if ( timer.seconds() > 0.2 && timer.seconds()<0.22) {
                scoring.scoring_arm_colect();

            }
            if (timer.seconds() > 0.5 && timer.seconds() < 0.6) {
                colection.gripper.setPosition(colection.gripper_transfer);

            }

            if (timer.seconds() > 0.5 && timer.seconds() < 0.6) {
                scoring.grip_transfer.setPosition(scoring.gripper_semi_hold);

            } if (timer.seconds() >0.8  && timer.seconds() < 0.9) {
                colection.gripper.setPosition(colection.gripper_release);

            }
            if (timer.seconds() > 1.1 ) {
                colection.default_config();
                extension.extend(extension.extension_retracted);
                slides.culisante(slides.slides_high_basket);

                transferz=false;

            }

        }        sleep(400);

        scoring.scoring_arm_score_basket();
        sleep(200);
        Actions.runBlocking(
                new SequentialAction(
                        sample_finish.build()
                ));
        scoring.gripper(scoring.gripper_release);
        sleep(400);
//        slides.culisante(slides.slides_init);
        Actions.runBlocking(
                new SequentialAction(
                        sample_2.build()
                ));
//        ElapsedTime timer= new ElapsedTime();
//        timer.reset();
//        while(timer.seconds()<)
        sleep(200);
        scoring.scoring_arm_default();
        colection.colection_arm(colection.colection_extended_auto);
        sleep(200);
        colection.gripper_grab();
        sleep(300);
        colection.scoring_config();
        sleep(700);
        colection.gripper.setPosition(colection.gripper_transfer);

        timer.reset();
        transferz=true;
        while (transferz) {
            extension.extend(extension.extension_forced);
            if ( timer.seconds() > 0.2 && timer.seconds()<0.22) {
                scoring.scoring_arm_colect();

            }
            if (timer.seconds() > 0.5 && timer.seconds() < 0.6) {
                colection.gripper.setPosition(colection.gripper_transfer);

            }

            if (timer.seconds() > 0.5 && timer.seconds() < 0.6) {
                scoring.grip_transfer.setPosition(scoring.gripper_semi_hold);

            } if (timer.seconds() >0.8  && timer.seconds() < 0.9) {
                colection.gripper.setPosition(colection.gripper_release);

            }
            if (timer.seconds() > 1.1) {
                colection.default_config();
                extension.extend(extension.extension_retracted);
                slides.culisante(slides.slides_high_basket);

                transferz=false;

            }
        }
        sleep(400);
        scoring.scoring_arm_score_basket();
sleep(200);
        Actions.runBlocking(
                new SequentialAction(
                        sample_finish_2.build()
                ));

        scoring.gripper(scoring.gripper_release);
        sleep(400);

        Actions.runBlocking(
                new SequentialAction(
                        sample_3.build()
                ));

        colection.gripper_angle.setPosition(colection.gripper_angle_vertical);
        sleep(300);
        scoring.scoring_arm_default();
        colection.colection_arm(colection.colection_extended_auto);
        sleep(200);
        colection.gripper_grab();
        sleep(300);
        Actions.runBlocking(
                new SequentialAction(
                        sample_3_end.build()
                ));
        colection.scoring_config();
        sleep(700);
        colection.gripper.setPosition(colection.gripper_transfer);

        timer.reset();
        transferz=true;
        while (transferz) {
            extension.extend(extension.extension_forced);
            if ( timer.seconds() > 0.2 && timer.seconds()<0.22) {
                scoring.scoring_arm_colect();

            }
            if (timer.seconds() > 0.5 && timer.seconds() < 0.6) {
                colection.gripper.setPosition(colection.gripper_transfer);

            }

            if (timer.seconds() > 0.5 && timer.seconds() < 0.6) {
                scoring.grip_transfer.setPosition(scoring.gripper_semi_hold);

            } if (timer.seconds() >0.8  && timer.seconds() < 0.9) {
                colection.gripper.setPosition(colection.gripper_release);

            }
            if (timer.seconds() > 1.1 ) {
                colection.default_config();
                extension.extend(extension.extension_retracted);
                slides.culisante(slides.slides_high_basket);

                transferz=false;

            }
        }
        scoring.scoring_arm_score_basket();
        sleep(200);
        Actions.runBlocking(
                new SequentialAction(
                        sample_finish_3.build()
                ));
        scoring.gripper(scoring.gripper_release);
        sleep(300);
        colection.gripper_rotation.setPosition(colection.gripper_rotation_default);
        colection.colection_arm(colection.colection_default);
        Actions.runBlocking(
                new SequentialAction(
                        parking_pre.build(),
                        parking.build()
                ));


        scoring.scoring_arm_park();
        scoring.grip_transfer_release();
        colection.init_config();

        telemetry.update();
    }
}
