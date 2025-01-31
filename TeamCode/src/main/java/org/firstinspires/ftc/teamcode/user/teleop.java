package org.firstinspires.ftc.teamcode.user;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import androidx.annotation.NonNull;

import com.acmerobotics.dashboard.telemetry.TelemetryPacket;
import com.acmerobotics.roadrunner.Action;
import com.acmerobotics.roadrunner.InstantAction;
import com.acmerobotics.roadrunner.Pose2d;
import com.acmerobotics.roadrunner.PoseVelocity2d;
import com.acmerobotics.roadrunner.SequentialAction;
import com.acmerobotics.roadrunner.TimeTrajectory;
import com.acmerobotics.roadrunner.TrajectoryActionBuilder;
import com.acmerobotics.roadrunner.Vector2d;
import com.acmerobotics.roadrunner.ftc.Actions;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.teamcode.MecanumDrive;
import org.firstinspires.ftc.teamcode.PinpointDrive;

@TeleOp(name="TELEOP")
public class teleop extends LinearOpMode {


    @Override
    public void runOpMode() throws InterruptedException {
        Pose2d initialPose = new Pose2d(new Vector2d(40, -62), Math.toRadians(90));

        PinpointDrive drive = new PinpointDrive(hardwareMap, initialPose);
        colection colection = new colection(hardwareMap);
        extension extension = new extension(hardwareMap);
        scoring scoring = new scoring(hardwareMap);
        slides slides = new slides(hardwareMap);
        boolean hang = false;
        boolean blockage = false;
        ElapsedTime timer = new ElapsedTime(0);
        ElapsedTime timer2 = new ElapsedTime(0);
        ElapsedTime timer_score = new ElapsedTime(0);
        boolean to_score = false;
        boolean grab = false;
        boolean alt_transfer = false;
        boolean scramble = false;
        ElapsedTime scrambler_time = new ElapsedTime(0);
        colection.init_config();
        scoring.init_config();
        boolean specimen_cycling = false;
        slides.culisante(slides.slides_init);
        extension.extend(extension.extension_retracted);
        boolean manual = false;
        boolean transfer_extend = false;
        boolean transfer_extend_counter = false;
        boolean transfer_retracted = false;
        boolean transfer_retracted_counter = false;
        boolean auto_specimen_score = false;
        boolean auto_specimen_intermediary = false;
        int auto_specimen_score_counter = 0;
        boolean is_automation_ready = false;
        boolean first_automated_cycle = false;
        boolean is_collected = false;
        TrajectoryActionBuilder scoring_spec = drive.actionBuilder(new Pose2d(new Vector2d(45, -64), Math.toRadians(90)))
                .afterTime(0.1, slides.specimen_score_high())
                .afterTime(0.1, scoring.specimen_prepare())
                .afterTime(0.8, slides.specimen_score_high())

                .strafeToConstantHeading(new Vector2d(8, -27));

        TrajectoryActionBuilder scoring_spec_first_cycle = drive.actionBuilder(initialPose)
                .afterTime(0.1, slides.first_slide_cycle())
                .afterTime(0.1, scoring.first_cycle())
                .afterTime(0.8, slides.first_slide_cycle())
                .afterTime(1.2, slides.first_slide_cycle())
                .strafeToLinearHeading(new Vector2d(2, -27), Math.toRadians(90));

        TrajectoryActionBuilder scoring_spec_finish_first_cycle = drive.actionBuilder(new Pose2d(new Vector2d(2, -27), Math.toRadians(90)))
                .strafeToLinearHeading(new Vector2d(18, -27),Math.toRadians(90))
                .strafeToLinearHeading(new Vector2d(0, -27),Math.toRadians(90))
                .afterTime(0.1, scoring.gripper_release())
                .afterTime(0.2, scoring.specimen_collect())
                .afterTime(0.2, slides.slide_init())
                //  .setTangent(Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(45, -64));

        TrajectoryActionBuilder scoring_spec_finish = drive.actionBuilder(new Pose2d(new Vector2d(8, -27), Math.toRadians(90)))
                .afterTime(0, slides.auto_score())
                .strafeTo(new Vector2d(-2, -27))
                .afterTime(0.2, scoring.gripper_release())
                .afterTime(0.4, scoring.specimen_collect())
                .afterTime(0.4, slides.slide_init())
                //   .setTangent(Math.toRadians(90))
                .strafeToConstantHeading(new Vector2d(45, -64));
//        TrajectoryActionBuilder scoring_spec_finish_5    = drive.actionBuilder(new Pose2d(new Vector2d(12,-32),Math.toRadians(90)))
//                .afterTime(0,slides.auto_score())
//                .strafeTo(new Vector2d(8,-32))
//                .afterTime(0.6,scoring.gripper_release())
//                .afterTime(1,scoring.specimen_collect())
//                .afterTime(1,slides.slide_init())
//                .splineToConstantHeading(new Vector2d(42,-62),-Math.PI/8);

//        TrajectoryActionBuilder specimen_end = drive.actionBuilder(new Pose2d(new Vector2d(12,-32),Math.toRadians(90)))
//                .afterTime(0,slides.auto_score())
//                .afterTime(0.2,slides.auto_score())
//                .afterTime(0.4,slides.auto_score())
//                .strafeTo(new Vector2d(8,-32))
//                .afterTime(0.6,scoring.gripper_release())
//                .afterTime(1,scoring.specimen_collect())
//                .afterTime(1,slides.slide_init())
//                .strafeToLinearHeading(new Vector2d(-12,-47),Math.toRadians(90));


        while (!opModeIsActive()) {
            slides.culisante(0);
            if (!opModeInInit()) break;
        }
        waitForStart();
        // default init config
//        colection.start_config();
        scoring.init_config();
        // resets slides encoder after start is pressed
        slides.reset_encoder();

        while (opModeIsActive()) {


            // changes the turn speed if the robot is in it s extened config for better precision
            if (extension.left_extension.getPosition() > extension.extension_retracted + 0.1 && !is_collected) {
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(
                                -gamepad1.left_stick_y,
                                -gamepad1.left_stick_x
                        ),
                        -gamepad1.right_stick_x * 0.25
                ));
            } else
                drive.setDrivePowers(new PoseVelocity2d(
                        new Vector2d(
                                -gamepad1.left_stick_y,
                                -gamepad1.left_stick_x
                        ),
                        -gamepad1.right_stick_x
                ));
            // driver 2

            if (blockage == false) {
                // enable/disable automations with the "manual" variable
                if (gamepad2.cross) manual = false;
                if (gamepad2.square) manual = true;
                // high sample scoring config
                if (gamepad2.dpad_up) {
                    slides.culisante(slides.slides_high_basket);
                    scoring.scoring_arm_score_basket();
                }
                // low sample scoring config
                if (gamepad2.dpad_left) {
                    slides.culisante(slides.slides_low_basket);
                    scoring.scoring_arm_score_basket();

                }
                // high specimen score config
                if (gamepad2.dpad_right) {
                    is_automation_ready = false;
                    slides.culisante(slides.slides_specimen_high);
                    scoring.scoring_arm_specimen_prepare();
                }
                // low specimen score config
                if (gamepad2.dpad_down) {
                    slides.culisante(slides.slides_init);
                }
                //changes the robots config to the specimen colection config
                if (gamepad2.share) {
                    is_automation_ready = true;
                    alt_transfer = true;
                    slides.culisante(slides.slides_init);
                    scoring.scoring_arm_score_specimen_collect();
                }
                // particualr reset if grabbed in a peculiar way after transfer, used extremly rarely if ever
                if (gamepad2.triangle) {
                    slides.culisante(slides.slides_init);
                    scoring.scoring_arm_default();
                }
                // manual change between specimen_cycling, if true then robots default config is specimen cycling, if false then it is sample cycling
                if (gamepad2.right_bumper) {
                    colection.colection_arm(colection.colection_default);
                    colection.gripper_angle.setPosition(colection.gripper_angle_default);
                    colection.gripper_rotation.setPosition(colection.gripper_rotation_collect);

                    specimen_cycling = true;
                }
                if (gamepad2.left_bumper) {
                    colection.colection_arm(colection.colection_retracted);
                    colection.gripper_angle.setPosition(colection.gripper_angle_tranfer);
                    colection.gripper_rotation.setPosition(colection.gripper_rotation_score);
                    colection.gripper.setPosition(colection.gripper_transfer);
                    specimen_cycling = false;
                }
                // intermediary check for succseful transfer of the sample in the auto-transfer process
                if (gamepad1.left_trigger == 0) {
                    if (transfer_extend == true) {
                        timer.reset();
                        transfer_extend_counter = true;
                        transfer_extend = false;
                    }
                    if (transfer_retracted == true) {
                        timer.reset();
                        transfer_retracted_counter = true;
                        transfer_retracted = false;
                    }
                }
                // transfers the sample from a colected config to a scoring config, also blocks off other controls while the transfer is happening
                // and is automated based on a distance/color sensor, can also be used manually
                if (gamepad2.touchpad || (transfer_retracted_counter && timer.seconds() > 0.3) || (transfer_extend_counter == true && timer.seconds() > 0.8)) {
                    transfer_extend_counter = false;
                    transfer_retracted_counter = false;
                    if (blockage == false
                            && slides.right_slide.getCurrentPosition() < 15) {
                        colection.gripper_rotation.setPosition(colection.gripper_angle_default);
                        alt_transfer = false;
                        colection.scoring_config();
                        scoring.scoring_arm_default();
                        colection.gripper.setPosition(colection.gripper_hold);

                        blockage = true;
                        timer.reset();
                    }

                }

                // sequence of code that puts the robot in the first half of the hanging sequence
                if (gamepad2.left_trigger != 0) {
                    extension.extend(extension.extension_hang);
                    slides.culisante(slides.slides_high_basket);
                    hang = true;
                }
                // general reset of all functions, press if any errors arise or if in need of config reset
                if (gamepad2.right_trigger != 0) {
                    is_collected = false;
                    is_automation_ready = false;
                    colection.default_config();
                    scoring.init_config();
                    slides.culisante(0);
                    hang = false;
                }


                // finishes the hanging sequence by lowering the slides
                if (gamepad1.share) {
                    slides.culisante(slides.slides_hang);
                }

                // sequence of code that allows the scrambling of samples inside the submersible in case of unlucky positioning
                if (gamepad1.right_trigger != 0) {
                    colection.colection_arm(colection.colection_extended);
                    colection.gripper_rotation.setPosition(colection.gripper_rotation_collect);
                    colection.gripper_angle.setPosition(colection.gripper_angle_default);
                    scramble = true;
                    scrambler_time.reset();
                }

                if (scramble == true && !(gamepad1.right_trigger != 0)) {

                    if (scrambler_time.seconds() > 0.3) {
                        colection.colection_arm(colection.colection_default);
                        scramble = false;
                    }
                }
                // scores the already collected specimen on the high bar
                if (gamepad1.b) {
                    to_score = true;
                    timer_score.reset();
                    scoring.scoring_arm_score_specimen_score();
                }
                if (to_score == true) {
                    if (timer_score.seconds() > 0.5) {
//                        scoring.gripper_release();

                    }
                    if (timer_score.seconds() > 1) {
//                        scoring.init_config();
                        to_score = false;
                    }
                }
                // changes the angle of the gripper for sample colection on multiple axis
                if (gamepad1.dpad_left)
                    colection.gripper_angle.setPosition(colection.gripper_angle_vertical);
                if (gamepad1.dpad_right)
                    colection.gripper_angle.setPosition(colection.gripper_angle_default);

                // sequence that grabs sample and depending on the cycling mode either readies it for
                // transfer or puts the robot into the specimen colection configuration
                if ((gamepad1.x)) {
                    colection.collecting_config();
                    grab = true;
                    timer2.reset();
                }
                if (timer2.seconds() > 0.1 && grab == true) {
                    colection.gripper_grab();
                }

                if (timer2.seconds() > 0.5 && grab == true) {
                    colection.gripper_angle.setPosition(colection.gripper_angle_default);
                    colection.colection_arm(colection.colection_default);
                    if (timer2.seconds() > 0.7) {
                        if (specimen_cycling == false) {

                            if (!manual) {

                                if (colection.senzor.getDistance(DistanceUnit.CM) < colection.distance_to_collected_sample) {
                                    colection.gripper_angle.setPosition(colection.gripper_angle_default);
                                    colection.gripper.setPosition(colection.gripper_transfer);
                                    colection.scoring_config();

                                    if (gamepad1.left_trigger != 0) transfer_extend = true;
                                    else transfer_retracted = true;
                                } else {
                                    colection.gripper_release();
                                }
                            } else if (manual) {
                                colection.gripper.setPosition(colection.gripper_transfer);
                                colection.gripper_angle.setPosition(colection.gripper_angle_default);

                                colection.scoring_config();
                            }
                        } else {
                            if (!manual) {
                                if (colection.senzor.getDistance(DistanceUnit.CM) < colection.distance_to_collected_sample) {
                                    colection.colection_arm(colection.colection_specimen);
                                    is_collected = true;
                                } else {
                                    colection.default_config();
                                    colection.gripper.setPosition(colection.gripper_release);
                                }
                            } else if (manual) {
                                is_collected = true;
                                colection.colection_arm(colection.colection_specimen);
                            }
                        }
                        grab = false;
                    }
                }

                // releases the sample from the sample colection mechanism, used
                if (gamepad1.right_bumper) {
                    colection.gripper_release();
                    colection.colection_arm(colection.colection_default);
                    is_collected = false;
                }
                // expansion and retraction
                if (gamepad1.left_trigger != 0) {
                    extension.extend(extension.extension_extended);
                }

                if (gamepad1.left_trigger == 0 && hang == false) {
                    extension.extend(extension.extension_retracted);
                }
                // specimen colection
                if (gamepad1.right_stick_button)
                    scoring.grip_transfer.setPosition(scoring.gripper_hold);

                if (gamepad1.triangle) {
                    if (alt_transfer == true) {
                        scoring.grip_transfer.setPosition(scoring.gripper_semi_hold);
                    } else scoring.grip_transfer_release();
                }
                if (gamepad1.dpad_up && is_automation_ready) {
                    is_automation_ready = false;
                    drive.pose = initialPose;
                    auto_specimen_score = true;
                    auto_specimen_intermediary = false;
                    slides.culisante(slides.slides_auto_score + 300);
                    auto_specimen_score_counter = 0;
                }
                if (gamepad1.dpad_down && is_automation_ready) {
                    is_automation_ready = false;
                    drive.pose = initialPose;
                    auto_specimen_score = true;
                    auto_specimen_intermediary = false;
                    slides.culisante(slides.slides_auto_score + 300);
                    auto_specimen_score_counter = 0;
                    first_automated_cycle = true;
                }

                while (auto_specimen_score && opModeIsActive() && !auto_specimen_intermediary) {
                    telemetry.addData("robot pose", drive.pose.position);
                    telemetry.addData("robot heading", drive.pose.heading);
                    telemetry.addData("robot ", drive.pose);
                    colection.init_config();
                    if (gamepad2.right_trigger != 0) auto_specimen_score = false;
                    if (auto_specimen_score) {
                        if (first_automated_cycle) {
                            telemetry.update();
                            Actions.runBlocking(
                                    new SequentialAction(
                                            scoring_spec_first_cycle.build(),
                                            scoring.specimen_score()

                                    ));
                            telemetry.update();
                            sleep(100);
                            Actions.runBlocking(
                                    new SequentialAction(
                                            scoring_spec_finish_first_cycle.build()
                                    ));
                        } else {


                            Actions.runBlocking(
                                    new SequentialAction(
                                            scoring_spec.build(),
                                            scoring.specimen_score()

                                    ));
                            sleep(100);
//                            if(auto_specimen_score_counter<7) {
                            Actions.runBlocking(
                                    new SequentialAction(
                                            scoring_spec_finish.build()
                                    ));
//                            }
//                            else{
//                                Actions.runBlocking(
//                                        new SequentialAction(
//                                                scoring_spec_finish_5.build()
//                                        ));
//                            }
                        }
                    }
                    scoring.gripper(scoring.gripper_semi_hold);
                    sleep(200);
                    if (gamepad2.right_trigger != 0) auto_specimen_score = false;
                    auto_specimen_score_counter += 1;
                    first_automated_cycle = false;
                    telemetry.update();
                }

            }


            if (blockage == true) {
                extension.extend(extension.extension_forced);
                if (timer.seconds() < 0.1) {
                    scoring.grip_transfer.setPosition(scoring.gripper_hold);

                }
                if (timer.seconds() > 0.1 && timer.seconds() < 0.2) {
                    colection.gripper.setPosition(colection.gripper_release);

                }
                if (timer.seconds() > 0.4) {
                    colection.default_config();
                    extension.extend(extension.extension_retracted);
                    scoring.scoring_arm_score_basket();
                    slides.culisante(slides.slides_high_basket);
                    blockage = false;
                }
            }

        telemetry.addData("Cycling_specimen", specimen_cycling);
        telemetry.addData("robot position", drive.pose);
        telemetry.addData("Color", colection.senzor.getDistance(DistanceUnit.CM));
        telemetry.addData("automations", manual);
        drive.updatePoseEstimate();
        telemetry.update();


        }

    }
}
