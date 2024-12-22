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

import org.firstinspires.ftc.teamcode.MecanumDrive;

@Autonomous(name="auto_basket_4_sample")
public class auto_basket_sample extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(new Vector2d(-40,-66), Math.toRadians(0));

        ElapsedTime timerr= new ElapsedTime();
        MecanumDrive drive = new MecanumDrive(hardwareMap,initialPose);
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
        TrajectoryActionBuilder start = drive.actionBuilder(new Pose2d(new Vector2d(-53,-60), Math.toRadians(-43)))
                .strafeToLinearHeading(new Vector2d(-16.5,-62), Math.toRadians(-90));
        TrajectoryActionBuilder start_to_score = drive.actionBuilder( initialPose)
                .afterTime(0,slides.slide_sample())
                .afterTime(0.4,slides.slide_sample())
                .afterTime(0.8,colection.collecting_arm_default())
                .strafeToLinearHeading(new Vector2d(-53.5,-60),Math.toRadians(40));

        TrajectoryActionBuilder sample_1 = drive.actionBuilder(new Pose2d(new Vector2d(-53.5,-60),Math.toRadians(40)))
                .afterTime(0.1,slides.slide_init())
                .afterTime(0.1,scoring.sample_collect())
                .strafeToLinearHeading(new Vector2d(-50.7,-42.7),Math.toRadians(94));
        TrajectoryActionBuilder sample_finish = drive.actionBuilder(new Pose2d(new Vector2d(-51.1,-42.7),Math.toRadians(94)))

                .strafeToLinearHeading(new Vector2d(-53.5,-60),Math.toRadians(40));



        TrajectoryActionBuilder sample_2 = drive.actionBuilder(new Pose2d(new Vector2d(-53.5,-60),Math.toRadians(40)))
                .afterTime(0.1,slides.slide_init())
                .afterTime(0.4,slides.slide_init())
                .afterTime(0.5,colection.collecting_arm_default())
                .strafeToLinearHeading(new Vector2d(-59.5,-41.8),Math.toRadians(98));

        TrajectoryActionBuilder sample_finish_2 = drive.actionBuilder(new Pose2d(new Vector2d(-59.5,-41.8),Math.toRadians(98)))

                .strafeToLinearHeading(new Vector2d(-53.5,-60),Math.toRadians(40));

        TrajectoryActionBuilder sample_3 = drive.actionBuilder(new Pose2d(new Vector2d(-53.5,-60),Math.toRadians(40)))
                .afterTime(0,slides.slide_init())
                .afterTime(0.4,slides.slide_init())
                .afterTime(0.5,colection.collecting_arm_default())
                .strafeToLinearHeading(new Vector2d(-56.5,-29),Math.toRadians(-180));
        TrajectoryActionBuilder sample_3_end = drive.actionBuilder(new Pose2d(new Vector2d(-56,-29),Math.toRadians(-180)))
                .strafeTo(new Vector2d(-50,-29));
        TrajectoryActionBuilder sample_finish_3 = drive.actionBuilder(new Pose2d(new Vector2d(-56,-27),Math.toRadians(-180)))

                .strafeToLinearHeading(new Vector2d(-53,-60),Math.toRadians(39));

        TrajectoryActionBuilder parking_pre = drive.actionBuilder(new Pose2d(new Vector2d(-53,-60),Math.toRadians(39)))
                .afterTime(0.2,slides.slide_init())
                .strafeToLinearHeading(new Vector2d(-43,-13),Math.toRadians(0));

        TrajectoryActionBuilder parking = drive.actionBuilder(new Pose2d(new Vector2d(-43,-13),Math.toRadians(0)))
                .afterTime(0.2,slides.slide_init())
                .strafeTo(new Vector2d(-28,-11));






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
        sleep(400);
//        slides.culisante(slides.slides_init);
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
        colection.gripper.setPosition(colection.gripper_transfer);
        timer.reset();
        transferz=true;
        while (transferz) {
            extension.extend(extension.extension_forced);
            if (timer.seconds() > 1 && timer.seconds()<1.2) {
                scoring.scoring_arm_colect();
            }

            if (timer.seconds() > 1.2 && timer.seconds() < 1.4) {
                scoring.grip_transfer_grab();

            }
            if (timer.seconds() > 1.4 && timer.seconds() < 1.6) {
                colection.gripper.setPosition(colection.gripper_release);
//                    colection.colection_arm(colection.colection_extended);

            }
            if (timer.seconds() > 2) {
                colection.default_config();
                extension.extend(extension.extension_retracted);
                slides.culisante(slides.slides_high_basket);
                scoring.scoring_arm_score_basket();
            }
            if(timer.seconds()>2.6){
                transferz=false;

            }

        }
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
        colection.gripper.setPosition(colection.gripper_transfer);
        timer.reset();
        transferz=true;
        while (transferz) {
            extension.extend(extension.extension_forced);
            if (timer.seconds() > 1 && timer.seconds()<1.2) {
                scoring.scoring_arm_colect();
            }

            if (timer.seconds() > 1.2 && timer.seconds() < 1.4) {
                scoring.grip_transfer_grab();

            }
            if (timer.seconds() > 1.4 && timer.seconds() < 1.6) {
                colection.gripper.setPosition(colection.gripper_release);
//                    colection.colection_arm(colection.colection_extended);

            }
            if (timer.seconds() > 2) {
                colection.default_config();
                extension.extend(extension.extension_retracted);
                slides.culisante(slides.slides_high_basket);
                scoring.scoring_arm_score_basket();
            }
            if(timer.seconds()>2.6){
                transferz=false;

            }

        }
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
        colection.gripper.setPosition(colection.gripper_transfer);
        timer.reset();
        transferz=true;
        while (transferz) {
            extension.extend(extension.extension_forced);
            if (timer.seconds() > 1 && timer.seconds()<1.2) {
                scoring.scoring_arm_colect();
            }

            if (timer.seconds() > 1.2 && timer.seconds() < 1.4) {
                scoring.grip_transfer_grab();

            }
            if (timer.seconds() > 1.4 && timer.seconds() < 1.6) {
                colection.gripper.setPosition(colection.gripper_release);
//                    colection.colection_arm(colection.colection_extended);

            }
            if (timer.seconds() > 2) {
                colection.default_config();
                extension.extend(extension.extension_retracted);
                slides.culisante(slides.slides_high_basket);
                scoring.scoring_arm_score_basket();
            }
            if(timer.seconds()>2.6){
                transferz=false;

            }

        }
        Actions.runBlocking(
                new SequentialAction(
                                sample_finish_3.build()
                ));
        scoring.gripper(scoring.gripper_release);
        sleep(300);
        Actions.runBlocking(
                new SequentialAction(
                        parking_pre.build(),
                        parking.build()
                ));
        double x=extension.extension_retracted;
        colection.colection_arm(colection.colection_extended_auto_submersible);
        boolean force_stop=false;
        while(x<extension.extension_extended && timer.seconds()<25 && !force_stop){
            extension.extend(x);
            sleep(500);
            if(colection.senzor.alpha()>80){
                colection.colection_arm(colection.colection_extended);
                sleep(200);
                colection.gripper_grab();
                sleep(200);
                if(colection.senzor.alpha()<1000){
                    colection.gripper_release();
                    sleep(200);
                    colection.colection_arm(colection.colection_extended_auto_submersible);
                    sleep(200);
                    colection.gripper_angle.setPosition(colection.gripper_angle_vertical);
                    sleep(200);
                    colection.colection_arm(colection.colection_extended);
                    sleep(200);
                    colection.gripper_grab();
                }

                    if(colection.senzor.alpha()>1000){
                    colection.scoring_config();
                    force_stop=true;
                }
                else{
                    colection.gripper_release();
                }
            }
            x+=0.05;
        }
        colection.scoring_config();
        sleep(4000);
        while(!isStopRequested()){
            scoring.scoring_arm_auto_init_end();
            scoring.grip_transfer_release();
            slides.culisante(slides.slides_init);
            colection.init_config();
        }
        telemetry.update();
    }
}
